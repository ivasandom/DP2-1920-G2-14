package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class EmailPerformanceTests extends Simulation {

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
			.formParam("firstName", "Luis")
			.formParam("lastName", "P�rez")
			.formParam("document", "23445567")
			.formParam("documentType", "nif")
			.formParam("healthInsurance", "Mapfre")
			.formParam("healthCardNumber", "12345")
			.formParam("email", "luisperez@gmail.com")
			.formParam("user.username", "luisperez")
			.formParam("user.password", "luisperez")
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
			.formParam("firstName", "María")
			.formParam("lastName", "Sanz")
			.formParam("document", "23445567")
			.formParam("documentType", "nif")
			.formParam("healthInsurance", "Cigna")
			.formParam("healthCardNumber", "43235")
			.formParam("user.username", "marisan")
			.formParam("user.password", "marisan")
			.formParam("_csrf", "${stoken}") 
			).pause(10)
		 }
	}

	val emailPositiveSnc = scenario("EmailPositive").exec(Home.home,
														Login.login,
														NewClient1.newClient1)
	val emailNegativeSnc = scenario("EmailNegative").exec(Home.home,
														Login.login,
														NewClient2.newClient2)


	setUp(
		emailPositiveSnc.inject(rampUsers(6000) during (50 seconds)),
		emailNegativeSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}