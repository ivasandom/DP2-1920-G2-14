package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HealthCardNumberPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
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
	).pause(20)
	}

	object NewClient1 {
		val newClient1 = exec(http("NewClientForm")
			.get("/clients/new")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
		 .doIf("${stoken.exists()}") {
			exec(http("NewClient1")
			.post("/clients/new")
			.headers(headers_3)
			.formParam("firstName", "Pepe")
			.formParam("lastName", "Gotera")
			.formParam("document", "23445567")
			.formParam("documentType", "nif")
			.formParam("healthInsurance", "Aadeslas")
			.formParam("healthCardNumber", "12345")
			.formParam("email", "pepegotera@gmail.com")
			.formParam("user.username", "pepegotera3")
			.formParam("user.password", "pepegotera")
			.formParam("_csrf", "${stoken}") 
			).pause(10)
		 }
	}

	object NewClient2 {
		val newClient2 = exec(http("NewClientForm")
			.get("/clients/new")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
		 .doIf("${stoken.exists()}") {
			exec(http("NewClient2")
			.post("/clients/new")
			.headers(headers_3)
			.formParam("firstName", "Elena")
			.formParam("lastName", "Nito")
			.formParam("document", "23445567")
			.formParam("documentType", "nif")
			.formParam("healthInsurance", "Sanitas")
			.formParam("email", "elenanito@gmail.com")
			.formParam("user.username", "elenanito")
			.formParam("user.password", "elenanito")
			.formParam("_csrf", "${stoken}") 
			).pause(10)
		 }
	}

	val healthCardNumberPositiveSnc = scenario("HealthCardNumberPositive").exec(Home.home,
														Login.login,
														NewClient1.newClient1)
	val healthCardNumberNegativeSnc = scenario("HealthCardNumberNegative").exec(Home.home,
														Login.login,
														NewClient2.newClient2)


	setUp(
		healthCardNumberPositiveSnc.inject(rampUsers(6000) during (50 seconds)),
		healthCardNumberNegativeSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}