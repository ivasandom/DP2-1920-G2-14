
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
import com.stripe.model.Refund;
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


	@Test
	void shouldRetrievePaymentMethod() throws Exception {
		String token1 = "pm_1GeMp7DfDQNZdQMbRXv4nR3i";
		PaymentMethod paymentMethod = this.stripeService.retrievePaymentMethod(token1);
		
		Assertions.assertThat(paymentMethod).isNotNull();
	}
	
	@Test
	void shouldNotRetrievePaymenMethod() throws Exception {
		String token1 = "not a valid payment method";
		PaymentMethod paymentMethod = this.stripeService.retrievePaymentMethod(token1);
		
		Assertions.assertThat(paymentMethod).isNull();;
	}

	@Test
	void shouldSetupIntent() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", "cus_HCpLlL5UZqugk1");
		SetupIntent intent = SetupIntent.create(params);
		SetupIntent sI = this.stripeService.setupIntent("cus_HCpLlL5UZqugk1");
		
		Assertions.assertThat(sI.getCustomer()).isEqualTo(intent.getCustomer());
	}

	@Test
	void shouldCreateCostumer() throws Exception {
		String email = "frankcuesta@gmail.com";
		Customer c = this.stripeService.createCustomer(email);
		
		Assertions.assertThat(c).isNotNull();
		Assertions.assertThat(c.getEmail()).isEqualTo(email);
	}

	@Test
	void shouldCharge() throws Exception {
		String costumerId = "cus_HCpLlL5UZqugk1";
		String token1 = "pm_1GePiLDfDQNZdQMbExRewEOH";
		
		PaymentIntent pay = this.stripeService.charge(token1, 100., costumerId);
		Assertions.assertThat(pay).isNotNull();
		Assertions.assertThat(pay.getAmount()).isEqualTo(10000);
		Assertions.assertThat(pay.getStatus()).isEqualTo("succeeded");
	}
	
	@Test
	void shouldNotCharge() throws Exception {
		String costumerId = "cus_HCpLlL5UZqugk1";
		String token1 = "pm_1GePiLDfDQNZdQMbExRewEOH";

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> this.stripeService.charge(token1, -100., costumerId));
	}
	
	@Test
	void shouldRefundCharge() throws Exception {
		String costumerId = "cus_HCpLlL5UZqugk1";
		String token1 = "pm_1GePiLDfDQNZdQMbExRewEOH";
		
		PaymentIntent pay = this.stripeService.charge(token1, 50., costumerId);
		Refund paymentRefund = this.stripeService.refund(pay.getId());
		
		Assertions.assertThat(paymentRefund).isNotNull();
		Assertions.assertThat(paymentRefund.getAmount()).isEqualTo(5000);
	}
	
	@Test
	void shouldNotRefundChargeTwice() throws Exception {
		String costumerId = "cus_HCpLlL5UZqugk1";
		String token1 = "pm_1GePiLDfDQNZdQMbExRewEOH";
		
		PaymentIntent pay = this.stripeService.charge(token1, 50., costumerId);
		this.stripeService.refund(pay.getId());
		
		// Refunding twice should throw an exception
		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> this.stripeService.refund(pay.getId()));
	}

}
