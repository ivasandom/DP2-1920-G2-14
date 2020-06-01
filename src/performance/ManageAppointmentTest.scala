package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ManageAppointmentTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_8 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_14 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive",
		"X-Requested-With" -> "XMLHttpRequest")


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
        	.formParam("username", "miguelperez")
        	.formParam("password", "miguelperez")        
        	.formParam("_csrf", "${stoken}")
    	).pause(142) }
    }

	object Login2 {
    	val login2 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(30)
		.doIf("${stoken.exists()}") {
    	exec(http("Logged")
			.post("/signin")
			.headers(headers_2)
			.formParam("username", "pepegotera")
			.formParam("password", "pepegotera")
			.formParam("_csrf", "${stoken}")
		).pause(20)
		}
  	}

	object ListAppointments {
    	val listAppointments = exec(http("ListAppointments")
			.get("/appointments")
			.headers(headers_0)
		).pause(12)
   	}

	object NewAppointment {
   		val newAppointment = exec(http("NewAppointmentForm")
			.get("/appointments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.doIf("${stoken.exists()}") {
			exec(http("FilterProfessional")
				.get("/professionals/filter?centerId=1&specialtyId=1")
				.headers(headers_5)
		).pause(67)
			.exec(http("Professional")
				.get("/appointments/busy?date=28%2F08%2F2020&professionalId=1")
				.headers(headers_5)
		).pause(1)
			.exec(http("CreatedAppointment")
				.post("/appointments/new")
				.headers(headers_2)
				.formParam("center", "1")
				.formParam("specialty", "1")
				.formParam("professional", "1")
				.formParam("reason", "Checking")
				.formParam("type.name", "checking")
				.formParam("date", "28/08/2020")
				.formParam("startTime", "08:15:00")
				.formParam("_csrf", "${stoken}")
		).pause(40)
		}
   	}

	object NotNewAppointment {
   		val notNewAppointment = exec(http("NewAppointmet")
			.get("/appointments/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(3)
		.doIf("${stoken.exists()}") {
		 exec(http("FilterProfessional")
			.get("/professionals/filter?centerId=1&specialtyId=2")
			.headers(headers_14)
		).pause(1)
		.exec(http("NotNewAppointment")
			.post("/appointments/new")
			.headers(headers_2)
			.formParam("center", "1")
			.formParam("specialty", "2")
			.formParam("reason", "")
			.formParam("type.name", "analisis")
			.formParam("date", "")
			.formParam("_csrf", "${stoken}")
			.resources(http("FilterProfessional")
			.get("/professionals/filter?centerId=1&specialtyId=2")
			.headers(headers_14))
		).pause(9)
		}
	}

	object CancelAppointment {
    	val cancelAppointment = exec(http("ViewMoreAppointment")
			.get("/appointments/3/details")
			.headers(headers_0)
		).pause(15)
		.exec(http("CancelAppointment")
			.get("/appointments/delete/3")
			.headers(headers_0))
		.pause(8)
   	}

	object NotCancelAppointment {
    	val notCancelAppointment = exec(http("ViewMoreAppointment")
			.get("/appointments/124/details")
			.headers(headers_0)
		).pause(1)
		.exec(http("CancelAppointment")
			.get("/appointments/delete/124")
			.headers(headers_0)
			.resources(http("CancelNotSuccess")
			.get("/resources/images/acme.jpg")
			.headers(headers_8))
		).pause(25)
   	}




	val manageNewAppointment1Scn = scenario("ManageNewAppointmentTest1").exec(Home.home,
																	Login1.login1,
																	ListAppointments.listAppointments,
																	NewAppointment.newAppointment)

	val manageNewAppointment2Scn = scenario("ManageNewAppointmentTest2").exec(Home.home,
																	Login1.login1,
																	ListAppointments.listAppointments,
																	NotNewAppointment.notNewAppointment)

	val manageCancelAppointment1Scn = scenario("ManageCancelAppointmentTest1").exec(Home.home,
																	Login2.login2,
																	ListAppointments.listAppointments,
																	CancelAppointment.cancelAppointment)

	val manageCancelAppointment2Scn = scenario("ManageCancelAppointmentTest2").exec(Home.home,
																	Login2.login2,
																	ListAppointments.listAppointments,
																	NotCancelAppointment.notCancelAppointment)


	setUp(
		manageNewAppointment1Scn.inject(rampUsers(2100) during (20 seconds)),
		manageNewAppointment2Scn.inject(rampUsers(2100) during (20 seconds)),
		manageCancelAppointment1Scn.inject(rampUsers(2100) during (20 seconds)),
		manageCancelAppointment2Scn.inject(rampUsers(2100) during (20 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}