
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


	@ParameterizedTest
	@CsvSource({
		"pepegotera"
	})
	@Transactional
	public void shouldFindPaymentMethodsfindByClient(final String name) {

		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({
		"elenanito"
	})
	@Transactional
	public void shouldNotFindPaymentMethodsfindByClient(final String name) {

		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(0);
	}

	@ParameterizedTest
	@CsvSource({
		"tok_visa, pepegotera"
	})
	@Transactional
	public void shouldSaveMedicine(final String token, final String name) {
		Client client = this.clientService.findClientByUsername(name);
		Collection<PaymentMethod> paymentMethods = this.paymentMethodService.findByClient(client);

		int found = paymentMethods.size();

		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setToken(token);
		paymentMethod.setClient(client);

		this.paymentMethodService.savePaymentMethod(paymentMethod);
		;
		org.assertj.core.api.Assertions.assertThat(paymentMethod.getId().longValue()).isNotEqualTo(0);

		paymentMethods = this.paymentMethodService.findByClient(client);
		org.assertj.core.api.Assertions.assertThat(paymentMethods.size()).isEqualTo(found + 1);
	}

}
