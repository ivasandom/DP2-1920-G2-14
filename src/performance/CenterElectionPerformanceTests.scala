package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CenterElectionPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive",
		"X-Requested-With" -> "XMLHttpRequest")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
	).pause(8)
		.doIf("${stoken.exists()}") {
     exec(			  
      http("Logged1")
        .post("/signin")
        .headers(headers_2)
        .formParam("username", "pepegotera")
        .formParam("password", "pepegotera")        
        .formParam("_csrf", "${stoken}")			
      ).pause(142)
	 }
  }
	object NewAppointmentForm {
		val newAppointmentForm = exec(http("NewAppointmentForm")
			.get("/appointments/new")
			.headers(headers_0))
		.pause(11)
	}

	object CenterElection1 {
		val centerElection1 = exec(http("CenterElection1")
			.get("/professionals/filter?centerId=1&specialtyId=1")
			.headers(headers_4))
		.pause(20)
	}

	object CenterElection2 {
		val centerElection2 = exec(http("CenterElection2")
			.get("/professionals/filter?centerId=3&specialtyId=1")
			.headers(headers_4))
		.pause(7)
	}

	val centerElectionPositiveSnc = scenario("CenterElection1").exec(Home.home,
														Login.login,
														NewAppointmentForm.newAppointmentForm,
														CenterElection1.centerElection1)
	val centerElectionNegativeSnc = scenario("CenterElection2").exec(Home.home,
														Login.login,
														NewAppointmentForm.newAppointmentForm,
														CenterElection2.centerElection2)


	setUp(
		centerElectionPositiveSnc.inject(rampUsers(6000) during (50 seconds)),
		centerElectionNegativeSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}