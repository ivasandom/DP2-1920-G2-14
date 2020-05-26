
package org.springframework.samples.petclinic.service;

import java.util.Collection;

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
	
	
	
}
