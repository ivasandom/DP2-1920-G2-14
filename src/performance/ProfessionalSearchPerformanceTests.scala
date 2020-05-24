package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ProfessionalSearchPerformanceTests extends Simulation {

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

   object Login {
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/signin")
        .headers(headers_2)
        .formParam("username", "pepegotera")
        .formParam("password", "pepegotera")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

	object FindProfessionals {
		val findProfessionals = exec(http("FindProfessionals")
			.get("/professionals/find")
			.headers(headers_0))
		pause(21)
	}

	object SelectProfessional1 {
		val selectProfessional1 = exec(http("SelectProfessional1")
			.get("/professionals?center=1&specialty=1")
			.headers(headers_0))
		.pause(9)
	}

	object SelectProfessional2 {
		val selectProfessional2 = exec(http("SelectProfessional2")
			.get("/professionals?center=2&specialty=2")
			.headers(headers_0))
		.pause(9)
	}

	val professionalSearchPositiveSnc = scenario("ProfessionalSearchPositive").exec(Home.home,
														Login.login,
														FindProfessionals.findProfessionals,
														SelectProfessional1.selectProfessional1)
	val professionalSearchNegativeSnc = scenario("ProfessionalSearchNegative").exec(Home.home,
														Login.login,
														FindProfessionals.findProfessionals,
														SelectProfessional2.selectProfessional2)


	setUp(
		professionalSearchPositiveSnc.inject(rampUsers(6000) during (50 seconds)),
		professionalSearchNegativeSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}