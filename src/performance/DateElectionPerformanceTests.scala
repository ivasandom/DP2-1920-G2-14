package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DateElectionPerformanceTests extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("image/webp,image/apng,image/*,*/*;q=0.8")
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

	val headers_4 = Map("Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
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

	object CenterAndSpecialtyElection {
		val centerAndSpecialtyElection = exec(http("CenterAndSpecialtyElection")
			.get("/professionals/filter?centerId=1&specialtyId=1")
			.headers(headers_4))
		.pause(20)
	}

	object ProfessionalAndDateElection1 {
		val professionalAndDateElection1 = exec(http("ProfessionalAndDateElection1")
			.get("/appointments/busy?date=29%2F05%2F2019&professionalId=1")
			.headers(headers_5))
		.pause(2)
	}

	object ProfessionalAndDateElection2 {
		val professionalAndDateElection2 = exec(http("ProfessionalAndDateElection2")
			.get("/appointments/busy?date=29%2F07%2F2020&professionalId=1")
			.headers(headers_5))
		.pause(2)
	}

	object NewAppointment1 {
		val newAppointment1 = exec(http("NewAppointmentForm")
			.get("/appointments/new")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
		 .doIf("${stoken.exists()}") {
			exec(http("NewAppointment1")
			.post("/appointments/new")
			.headers(headers_2)
			.formParam("center", "1")
			.formParam("specialty", "1")
			.formParam("professional", "1")
			.formParam("reason", "Revision mensual")
			.formParam("type.name", "analisis")
			.formParam("date", "29/05/2019")
			.formParam("startTime", "12:30:00")
			.formParam("_csrf", "${stoken}") 
			).pause(10)
		 }
	}

	object NewAppointment2 {
		val newAppointment2 = exec(http("NewAppointmentForm")
			.get("/appointments/new")
			.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(11)
		 .doIf("${stoken.exists()}") {
			exec(http("NewAppointment2")
			.post("/appointments/new")
			.headers(headers_2)
			.formParam("center", "1")
			.formParam("specialty", "1")
			.formParam("professional", "1")
			.formParam("reason", "Revision mensual")
			.formParam("type.name", "analisis")
			.formParam("date", "29/07/2020")
			.formParam("startTime", "12:30:00")
			.formParam("_csrf", "${stoken}") 
			).pause(10)
		 }
	}

		

	
	val dateElectionNegativeSnc = scenario("DateElectionNegative").exec(Home.home,
														Login.login,
														NewAppointmentForm.newAppointmentForm,
														CenterAndSpecialtyElection.centerAndSpecialtyElection,
														ProfessionalAndDateElection1.professionalAndDateElection1,
														NewAppointment1.newAppointment1)
	val dateElectionPositiveSnc = scenario("DateElectionPositive").exec(Home.home,
														Login.login,
														NewAppointmentForm.newAppointmentForm,
														CenterAndSpecialtyElection.centerAndSpecialtyElection,
														ProfessionalAndDateElection2.professionalAndDateElection2,
														NewAppointment2.newAppointment2)


	setUp(
		dateElectionNegativeSnc.inject(rampUsers(6000) during (50 seconds)),
		dateElectionPositiveSnc.inject(rampUsers(6000) during (50 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}