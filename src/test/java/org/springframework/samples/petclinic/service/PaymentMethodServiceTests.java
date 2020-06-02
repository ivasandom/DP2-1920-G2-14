
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentMethodServiceTests {

	@Autowired
	protected PaymentMethodService	paymentMethodService;

	@Autowired
	protected ClientService			clientService;
	
	@Autowired
	protected StripeService			stripeService;


	@ParameterizedTest
	@CsvSource({
		"pepegotera"
	})
	@Transactional
	public void shouldFindPaymentMethodsByClient(final String name) {

		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({
		"elenanito"
	})
	@Transactional
	public void shouldNotFindPaymentMethodsByClient(final String name) {

		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		"pepegotera, pm_1Ggr7GDfDQNZdQMbCcCoxzEI"
	})
	@Transactional
	public void shouldFindByTokenAndClient(final String name, final String token) {

		Client client = this.clientService.findClientByUsername(name);
		PaymentMethod paymentMethod = this.paymentMethodService.findByTokenAndClient(token, client);
		org.assertj.core.api.Assertions.assertThat(paymentMethod).isNotNull();
		org.assertj.core.api.Assertions.assertThat(paymentMethod.getToken()).isEqualTo(token);
		org.assertj.core.api.Assertions.assertThat(paymentMethod.getBrand()).isEqualTo("VISA");
	}
	
	@ParameterizedTest
	@CsvSource({
		"elenanito, pm_1Ggr7GDfDQNZdQMbCcCoxzEI"
	})
	@Transactional
	public void shouldNotFindByTokenAndClient(final String name, final String token) {

		Client client = this.clientService.findClientByUsername(name);
		PaymentMethod paymentMethod = this.paymentMethodService.findByTokenAndClient(token, client);
		org.assertj.core.api.Assertions.assertThat(paymentMethod).isNull();
	}

	@ParameterizedTest
	@CsvSource({
		"tok_visa, pepegotera"
	})
	@Transactional
	public void shouldSavePaymentMethod(final String token, final String name) {
		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);

		int found = paymentMethods.size();

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setToken(token);
		paymentMethod.setClient(client);

		this.paymentMethodService.savePaymentMethod(paymentMethod);

		org.assertj.core.api.Assertions.assertThat(paymentMethod.getId().longValue()).isNotEqualTo(0);

		paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(found + 1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"pm_1Ggr7GDfDQNZdQMbCcCoxzEI, pepegotera"
	})
	@Transactional
	public void testIsDuplicated(final String token, final String name) throws Exception {
		Client client = this.clientService.findClientByUsername(name);
		Boolean isDuplicated = this.paymentMethodService.isDuplicated(client, stripeService.retrievePaymentMethod(token));

		org.assertj.core.api.Assertions.assertThat(isDuplicated).isTrue();
	}
	
	@ParameterizedTest
	@CsvSource({
		"pm_1Ggr7GDfDQNZdQMbCcCoxzEI, elenanito"
	})
	@Transactional
	public void testIsNotDuplicated(final String token, final String name) throws Exception {
		Client client = this.clientService.findClientByUsername(name);
		Boolean isDuplicated = this.paymentMethodService.isDuplicated(client, stripeService.retrievePaymentMethod(token));

		org.assertj.core.api.Assertions.assertThat(isDuplicated).isFalse();
	}

}
