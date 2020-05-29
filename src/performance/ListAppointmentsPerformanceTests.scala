package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListAppointmentsPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

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

	object Login1 {
    val login1 = exec(
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

  object Login2 {
    val login2 = exec(
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

	val listAppointments1Snc = scenario("ListAppointments1").exec(Home.home,
														Login1.login1,
														ListAppointments.listAppointments)
	val listAppointments2Snc = scenario("ListAppointments2").exec(Home.home,
														Login2.login2,
														ListAppointments.listAppointments)


	setUp(
		listAppointments1Snc.inject(rampUsers(6000) during (50 seconds)),
		listAppointments2Snc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}