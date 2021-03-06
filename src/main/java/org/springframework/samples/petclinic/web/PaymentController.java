/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.PaymentMethodService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stripe.model.PaymentMethod;

@Controller
@RequestMapping("payments")
public class PaymentController {

	@Value("${STRIPE_PUBLIC_KEY}")
	private String API_PUBLIC_KEY;

	private static final String VIEWS_PAYMENT_METHOD_FORM = "payments/paymentMethodForm";

	private PaymentMethodService paymentMethodService;
	private ClientService clientService;
	private StripeService stripeService;

	@Autowired
	public PaymentController(final PaymentMethodService paymentMethodService, final StripeService stripeService,
			final ClientService clientService) {
		this.paymentMethodService = paymentMethodService;
		this.stripeService = stripeService;
		this.clientService = clientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/methods")
	public String paymentMethodList(final Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Client currentClient = this.clientService.findClientByUsername(auth.getName());

		model.put("paymentMethods", this.paymentMethodService.findByClient(currentClient));
		return "payments/paymentMethodList";
	}

	@GetMapping(value = "/new-method")
	public String paymentMethodForm(final Map<String, Object> model) throws Exception {
		org.springframework.samples.petclinic.model.PaymentMethod paymentMethod = new org.springframework.samples.petclinic.model.PaymentMethod();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Client currentClient = this.clientService.findClientByUsername(auth.getName());

		String stripeCustomerId = currentClient.getStripeId();
		if (stripeCustomerId == null) {
			stripeCustomerId = this.stripeService.createCustomer(currentClient.getEmail()).getId();
			currentClient.setStripeId(stripeCustomerId);
			this.clientService.saveClient(currentClient);
		}

		String intentClientSecret = this.stripeService.setupIntent(stripeCustomerId).getClientSecret();
		model.put("intentClientSecret", intentClientSecret);
		model.put("paymentMethod", paymentMethod);
		model.put("apiKey", this.API_PUBLIC_KEY);

		return PaymentController.VIEWS_PAYMENT_METHOD_FORM;
	}

	@PostMapping(value = "/new-method")
	public String processPaymentMethodForm(
			@Valid final org.springframework.samples.petclinic.model.PaymentMethod method, final BindingResult result,
			final ModelMap model) throws Exception {
		if (result.hasErrors()) {
			result.rejectValue("token", "invalid", "Invalid payment method.");
			return PaymentController.VIEWS_PAYMENT_METHOD_FORM;
		} else {
			// Check payment method token sent by user is valid
			PaymentMethod paymentMethod = this.stripeService.retrievePaymentMethod(method.getToken());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Client currentClient = this.clientService.findClientByUsername(auth.getName());
			// Check payment method is not duplicated
			if (paymentMethod == null || this.paymentMethodService.isDuplicated(currentClient, paymentMethod)) {
				String intentClientSecret = this.stripeService.setupIntent(currentClient.getStripeId())
						.getClientSecret();
				model.put("intentClientSecret", intentClientSecret);
				model.put("paymentMethod", method);
				model.put("apiKey", this.API_PUBLIC_KEY);

				if (paymentMethod == null) {
					result.rejectValue("token", "invalid", "Invalid payment method.");
				} else {
					result.rejectValue("token", "duplicated", "Card already exists.");
				}

				return PaymentController.VIEWS_PAYMENT_METHOD_FORM;

			} else {

				method.setClient(this.clientService
						.findClientByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
				method.setBrand(paymentMethod.getCard().getBrand().toUpperCase());
				method.setLast4(paymentMethod.getCard().getLast4());
				this.paymentMethodService.savePaymentMethod(method);
			}
		}

		return "redirect:/payments/methods";
	}
}
