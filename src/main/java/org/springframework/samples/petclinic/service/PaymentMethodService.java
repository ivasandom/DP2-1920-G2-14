
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.repository.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentMethodService {

	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private StripeService stripeService;

	@Autowired
	public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
		this.paymentMethodRepository = paymentMethodRepository;
	}

	@Transactional
	public void savePaymentMethod(PaymentMethod paymentMethod) throws DataAccessException {
		paymentMethodRepository.save(paymentMethod);
	}

	@Transactional
	public Collection<PaymentMethod> findByClient(Client client) throws DataAccessException {
		return paymentMethodRepository.findByClient(client);
	}

	@Transactional
	public PaymentMethod findByTokenAndClient(String token, Client client) throws DataAccessException {
		return paymentMethodRepository.findByTokenAndClient(token, client);
	}

	/*
	 * Given a client and a stripe payment method, check if card is not duplicated.
	 * This doesn't check if token is duplicated but if the card is the same, without saving card detains in our 
	 */
	@Transactional
	public Boolean isDuplicated(Client currentClient, com.stripe.model.PaymentMethod paymentMethod) throws Exception {
		Collection<PaymentMethod> paymentMethodsClient = this.findByClient(currentClient);
		Collection<com.stripe.model.PaymentMethod> stripePaymentMethods = new ArrayList<>();

		for (int i = 0; i < paymentMethodsClient.size(); i++) {
			com.stripe.model.PaymentMethod paymentStripe = this.stripeService
					.retrievePaymentMethod(paymentMethodsClient.stream().collect(Collectors.toList()).get(i).getToken());
			stripePaymentMethods.add(paymentStripe);
		}

		return paymentMethodsClient.size() != 0 && stripePaymentMethods.stream().map(x -> x.getCard().getFingerprint())
				.collect(Collectors.toSet()).contains(paymentMethod.getCard().getFingerprint());
	}

}
