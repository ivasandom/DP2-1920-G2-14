
package org.springframework.samples.petclinic.service;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.SetupIntent;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StripeServiceTests {

	@Autowired
	protected StripeService			stripeService;

	@Autowired
	protected TransactionService	transactionService;

	@Value("${STRIPE_PUBLIC_KEY}")
	private String					API_PUBLIC_KEY;


	//pm_1GeMrkDfDQNZdQMbRGxPDHlz

	@Test
	void shouldretrievePaymenMethod() throws Exception {
		String token1 = "pm_1GeMp7DfDQNZdQMbRXv4nR3i";
		PaymentMethod paymentMethod = this.stripeService.retrievePaymentMethod(token1);
		System.out.println("=========" + paymentMethod + "=============*");
		Assertions.assertThat(paymentMethod).isNotNull();
	}

	@Test
	void shouldsetupIntent() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", "cus_HCpLlL5UZqugk1");
		SetupIntent intent = SetupIntent.create(params);
		SetupIntent sI = this.stripeService.setupIntent("cus_HCpLlL5UZqugk1");
		System.out.println(sI + "==========================");
		Assertions.assertThat(sI.getCustomer()).isEqualTo(intent.getCustomer());
	}

	@Test
	void shouldCreateCostumer() throws Exception {
		//		Map<String, Object> params = new HashMap<String, Object>();
		String email = "frankcuesta@gmail.com";
		//		params.put("email", email);
		//		Customer costumer = Customer.create(params);
		Customer c = this.stripeService.createCustomer(email);
		Assertions.assertThat(c).isNotNull();
	}

	@Test
	void shouldCharge() throws Exception {
		String costumerId = "cus_HCpLlL5UZqugk1";
		String token1 = "pm_1GePiLDfDQNZdQMbExRewEOH";
		PaymentIntent pay = this.stripeService.charge(token1, 100., costumerId);
		System.out.println("=====================" + pay);
		Assertions.assertThat(pay).isNotNull();
	}

}
