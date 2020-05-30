package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DescPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,en-US;q=0.7,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
	}

   object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .doIf("${stoken.exists()}") {
     exec(			  
      http("Logged1")
        .post("/signin")
        .headers(headers_2)
        .formParam("username", "professional1")
        .formParam("password", "professional1")        
        .formParam("_csrf", "${stoken}")			
      ).pause(142)
	 }
  }


  object ListAppointments {
		val listAppointments = exec(http("ListAppointments")
			.get("/appointments/pro")
			.headers(headers_0)
		).pause(20)
	}

object Consultation {
		val consultation = exec(http("Consultation")
			.get("/appointments/25/consultation")
			.headers(headers_0)
		).pause(140)
	}
		
object NewConsultation1 {
		val newConsultation1 = exec(http("NewConsultationForm")
			.get("/appointments/25/consultation")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
			.doIf("${stoken.exists()}") {
			 exec(http("NewConsultation")
			.post("/appointments/25/consultation")
			.headers(headers_2)
			.formParam("diagnosis.description", "test")
			.formParam("diagnosis.deseases", "2")
			.formParam("_diagnosis.deseases", "1")
			.formParam("diagnosis.medicines", "3")
			.formParam("_diagnosis.medicines", "1")
			.formParam("receipt.price", "10")
			.formParam("client.paymentMethods", "")
			.formParam("_client.paymentMethods", "1")
			.formParam("_csrf", "${stoken}"))
		.pause(22)
	}
}

	object NewConsultation2 {
		val newConsultation2 = exec(http("NewConsultationForm")
			.get("/appointments/25/consultation")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
			.doIf("${stoken.exists()}") {
			exec(http("NewConsultation")
			.post("/appointments/25/consultation")
			.headers(headers_2)
			.formParam("diagnosis.deseases", "2")
			.formParam("_diagnosis.deseases", "1")
			.formParam("diagnosis.medicines", "3")
			.formParam("_diagnosis.medicines", "1")
			.formParam("receipt.price", "10")
			.formParam("client.paymentMethods", "")
			.formParam("_client.paymentMethods", "1")
			.formParam("_csrf", "${stoken}"))
		.pause(22)
	}
}

	val desc1Snc = scenario("DescPos").exec(Home.home,
														Login.login,
														ListAppointments.listAppointments,
														Consultation.consultation,
														NewConsultation1.newConsultation1
														)
	val desc2Snc = scenario("DescNeg").exec(Home.home,
														Login.login,
														ListAppointments.listAppointments,
														Consultation.consultation,
														NewConsultation2.newConsultation2
														)

	setUp(
		desc1Snc.inject(rampUsers(400) during (60 seconds)),
		desc2Snc.inject(rampUsers(400) during (60 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
	 }