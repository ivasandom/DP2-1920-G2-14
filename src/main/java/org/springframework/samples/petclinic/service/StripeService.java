
package org.springframework.samples.petclinic.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Refund;
import com.stripe.model.SetupIntent;

@Service
public class StripeService {
	
	
	@Value("${STRIPE_SECRET_KEY}")
	private String API_SECRET_KEY;
	
	@Autowired
	public StripeService() {
//		Stripe.apiKey = API_SECRET_KEY;
		Stripe.apiKey = "sk_test_pYL496vLKMppHC1GOEJL2DS500KJ0X4omJ";
	}
	
	
	public PaymentMethod retrievePaymentMethod(String paymentMethodToken) throws Exception {
		/**
		 * Retrieve payment method to store details in database
		 */
		
		try {
			PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodToken);
			return paymentMethod;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public SetupIntent setupIntent(String customerId) throws Exception {
		/**
		 * Create a setup intent to confirm the payment method when adding it.
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customerId);
		
		SetupIntent intent = SetupIntent.create(params);
		return intent;
	}
	
	
	public Customer createCustomer(String email) throws Exception {
		/**
		 * Create a customer from client email
		 */
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		
		Customer customer = Customer.create(params);
		return customer;
	}
	
	
	public PaymentIntent charge(String paymentMethodToken, Double amount, String customerId) throws Exception {
		/**
		 * This method charges an amount to a payment method.
		 * A payment method can be a card or a bank account associated to the clinic client.
		 */
		
		// Stripe processes amounts as integers, so 10.50€ must be 1050.
		Integer validAmount = (int)(amount * 100);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", validAmount);
		params.put("currency", "EUR");
		params.put("customer", customerId);
		params.put("confirm", true);
		params.put("off_session", true);
		params.put("payment_method", paymentMethodToken);
		
		PaymentIntent intent = PaymentIntent.create(params);
		return intent;
	}
	
	public Refund refund(String paymentIntentToken) throws Exception {
		/**
		 * This methods allows administrators to refund money charged previously for different reasons.
		 * For example if the client has been charged wrongly or has cancelled an appointnment.
		 */
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payment_intent", paymentIntentToken);
		
		Refund refund = Refund.create(params);
		return refund;
	}
	
	
	
}
