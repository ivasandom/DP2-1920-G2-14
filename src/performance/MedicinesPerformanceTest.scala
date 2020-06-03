package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class MedicinesPerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Purpose" -> "prefetch")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"A-IM" -> "x-bm,gzip",
		"Accept-Encoding" -> "gzip, deflate",
		"Proxy-Connection" -> "keep-alive")

object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0)
		).pause(5)
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


	object ConsultationMode1 {
    	val consultationMode1 = exec(http("Consultation")
			.get("/appointments/33/consultation")
			.headers(headers_0)
       	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
   	 	).pause(20)
    	.doIf("${stoken.exists()}") {
    	exec(http("FormPositive")
			.post("/appointments/33/consultation")
			.headers(headers_5)
			.formParam("diagnosis.description", "Description")
			.formParam("diagnosis.deseases", "7")
			.formParam("_diagnosis.deseases", "1")
			.formParam("diagnosis.medicines", "6")
			.formParam("_diagnosis.medicines", "1")
			.formParam("bill.price", "100.00")
			.formParam("bill.iva", "21.0")
			.formParam("_csrf", "${stoken}")
		).pause(18)
		}
 	}

	object ConsultationMode2 {
    	val consultationMode2 = exec(http("Consultation")
			.get("/appointments/33/consultation")
			.headers(headers_0)
       	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
   	 	).pause(20)
    	.doIf("${stoken.exists()}") {
    	exec(http("FormNegative")
			.post("/appointments/33/consultation")
			.headers(headers_5)
			.formParam("diagnosis.description", "Description")
			.formParam("diagnosis.deseases", "2")
			.formParam("_diagnosis.deseases", "1")
			.formParam("_diagnosis.medicines", "1")
			.formParam("bill.price", "100.00")
			.formParam("bill.iva", "21.0")
			.formParam("_csrf", "${stoken}")
		).pause(8)
		}
 	}


	val listMedicines1Snc = scenario("listMedicines1").exec(Home.home,
														Login.login,
														ListAppointments.listAppointments,
														ConsultationMode1.consultationMode1)
	val listMedicines2Snc = scenario("listMedicines2").exec(Home.home,
														Login.login,
														ListAppointments.listAppointments,
														ConsultationMode2.consultationMode2)


	setUp(
		listMedicines1Snc.inject(rampUsers(4200) during (50 seconds)),
		listMedicines2Snc.inject(rampUsers(4200) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}