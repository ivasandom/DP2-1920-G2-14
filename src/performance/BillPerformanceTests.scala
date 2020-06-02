package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BillPerformanceTests extends Simulation {

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


	object Login {
		val login = exec(
		http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(20)
		.doIf("${stoken.exists()}") {
			exec(http("Logged1")
				.post("/signin")
				.headers(headers_2)
				.formParam("username", "admin1")
				.formParam("password", "admin1")        
				.formParam("_csrf", "${stoken}")
			).pause(20)
		}
	}
	
	object ListBills {
		val listBills = exec(http("ListBills")
			.get("/admin/bills/")
		).pause(20)
	}

	object DetailBill {
		val detailBill = exec(http("DetailBill")
			.get("/admin/bills/2/")
		).pause(20)
	}
	
	object ChargeBill {
		val chargeBill = exec(http("ChargeForm")
			.get("/admin/bills/2/charge")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(10)
		.doIf("${stoken.exists()}") {
			exec(http("ChargePost")
				.post("/admin/bills/2/charge")
				.headers(headers_0)
				.formParam("amount", "1.0")
				.formParam("paymentMethod.token", "CASH")
				.formParam("_csrf", "${stoken}")
			).pause(20)
		}
		
	}
	
	object ChargeBill2 {
		val chargeBill2 = exec(http("ChargeForm")
			.get("/admin/bills/2/charge")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(10)
		.doIf("${stoken.exists()}") {
			exec(http("ChargePost")
				.post("/admin/bills/2/charge")
				.headers(headers_0)
				.formParam("amount", "-10")
				.formParam("paymentMethod.token", "CASH")
				.formParam("_csrf", "${stoken}")
			).pause(20)
		}
	}

	val billScn = scenario("BillScenario").exec(Login.login,
												ListBills.listBills,
												DetailBill.detailBill,
												ChargeBill.chargeBill)

	val billScn2 = scenario("BillScenario2").exec(Login.login,
												ListBills.listBills,
												DetailBill.detailBill,
												ChargeBill2.chargeBill2)

	setUp(
		billScn.inject(rampUsers(5000) during (50 seconds)),
		billScn2.inject(rampUsers(5000) during (50 seconds)),
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}