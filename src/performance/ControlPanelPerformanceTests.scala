package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ControlPanelPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Purpose" -> "prefetch")

	val headers_1 = Map("Proxy-Connection" -> "keep-alive")

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
        .formParam("username", "admin1")
        .formParam("password", "admin")        
        .formParam("_csrf", "${stoken}")			
      ).pause(142)
	 }
  }
	
	object HomeAdmin {
		val homeAdmin = exec(http("HomeAdmin")
			.get("/admin")
			.headers(headers_1))
		.pause(2)
	}

	object ProfessionalListAdmin {
		val professionalListAdmin = exec(http("ProfessionalListAdmin")
			.get("/admin/professionals")
			.headers(headers_1))
		.pause(1)
	}

	object NewProfessional1 {
		val newProfessional1 = exec(http("ProfessionalCreateForm")
			.get("/admin/professionals/create")
			.headers(headers_1)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(33)
		.doIf("${stoken.exists()}") {
		exec(http("NewProfessional1")
			.post("/admin/professionals/create")
			.headers(headers_3)
			.formParam("firstName", "Ortega")
			.formParam("lastName", "Y Gasset")
			.formParam("document", "23445567")
			.formParam("documentType", "NIF")
			.formParam("collegiateNumber", "00001")
			.formParam("center", "1")
			.formParam("specialty", "2")
			.formParam("email", "ortegaygasset@gmail.com")
			.formParam("user.username", "ortegaygasset")
			.formParam("user.password", "ortegaygasset")
			.formParam("_csrf", "f68c7c6a-865a-40dc-8a26-bba894f527cd"))
	}
	}

	object NewProfessional2 {
		val newProfessional2 = exec(http("ProfessionalCreateForm")
			.get("/admin/professionals/create")
			.headers(headers_1)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(33)
		.doIf("${stoken.exists()}") {
		exec(http("NewProfessional2")
			.post("/admin/professionals/create")
			.headers(headers_3)
			.formParam("firstName", "Ortega")
			.formParam("lastName", "Y Gasset")
			.formParam("document", "23445567")
			.formParam("documentType", "NIF")
			.formParam("collegiateNumber", "00001")
			.formParam("center", "1")
			.formParam("specialty", "2")
			.formParam("email", "ortegaygasset.wgrp.es")
			.formParam("user.username", "ortegaygasset")
			.formParam("user.password", "ortegaygasset")
			.formParam("_csrf", "f68c7c6a-865a-40dc-8a26-bba894f527cd"))
	}
	}
	
	val controlPanelPositiveSnc = scenario("ControlPanel1").exec(Home.home,
														Login.login,
														HomeAdmin.homeAdmin,
														ProfessionalListAdmin.professionalListAdmin,
														NewProfessional1.newProfessional1)
	val controlPanelNegativeSnc = scenario("ControlPanel2").exec(Home.home,
														Login.login,
														HomeAdmin.homeAdmin,
														ProfessionalListAdmin.professionalListAdmin,
														NewProfessional2.newProfessional2)


	setUp(
		controlPanelPositiveSnc.inject(rampUsers(6000) during (50 seconds)),
		controlPanelNegativeSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
	
}