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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.HealthValidator;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("clients")
public class ClientController {

	private static final String	VIEWS_CLIENTS_SIGN_UP	= "users/createClientForm";

	private final ClientService	clientService;
	
	private final StripeService stripeService;


	@Autowired
	public ClientController(final ClientService clientService, final StripeService stripeService, final AuthoritiesService authoritiesService) {
		this.clientService = clientService;
		this.stripeService = stripeService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final Map<String, Object> model) throws DataAccessException {
		Client client = new Client();
		java.util.List<String> lista = new ArrayList<>();
		HealthInsurance[] h = HealthInsurance.values();
		for (HealthInsurance hi : h) {
			lista.add(hi.getDisplayName());
		}
		model.put("client", client);
		model.put("documentTypes", Arrays.asList(org.springframework.samples.petclinic.model.DocumentType.values()));
		model.put("healthInsurances", lista);
		return ClientController.VIEWS_CLIENTS_SIGN_UP;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Client client, final BindingResult result, final ModelMap model) throws Exception {
		HealthValidator healthValidator = new HealthValidator();
		//UserValidator userValidator = new UserValidator();
		healthValidator.validate(client, result);
		//userValidator.validate(client.getUser(), result);
		java.util.List<String> lista = new ArrayList<>();
		HealthInsurance[] h = HealthInsurance.values();
		for (HealthInsurance hi : h) {
			lista.add(hi.getDisplayName());
		}
		if (result.hasErrors()) {
			model.put("client", client);
			model.put("documentTypes", Arrays.asList(org.springframework.samples.petclinic.model.DocumentType.values()));
			model.put("healthInsurances", lista);
			System.out.println(result.getAllErrors());
			return ClientController.VIEWS_CLIENTS_SIGN_UP;
		} else {
			//creating owner, user and authorities
			String customerId = this.stripeService.createCustomer(client.getEmail()).getId();
			client.setStripeId(customerId);
			this.clientService.saveClient(client);

			return "redirect:/";
		}
	}


	


//	@GetMapping(value = "/clients/{ownerId}/edit")
//	public String initUpdateOwnerForm(@PathVariable("ownerId") final int ownerId, final Model model) {
//		Owner owner = this.ownerService.findOwnerById(ownerId);
//		model.addAttribute(owner);
//		return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
//	}
//
//	@PostMapping(value = "/clients/{ownerId}/edit")
//	public String processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result, @PathVariable("ownerId") final int ownerId) throws DataAccessException {
//		if (result.hasErrors()) {
//			return OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
//		} else {
//			owner.setId(ownerId);
//			this.ownerService.saveOwner(owner);
//			return "redirect:/clients/{ownerId}";
//		}
//	}
	
	
	@GetMapping("/clients/{clientId}")
	public ModelAndView showClient(@PathVariable("clientId") final int clientId) {
		ModelAndView mav = new ModelAndView("clients/clientDetails");
		mav.addObject(this.clientService.findClientById(clientId));
		return mav;
	}
	
	
	@GetMapping(value = { "/clients" })
	public String showClientsList(Map<String, Object> model) {
		Collection<Client> clients = this.clientService.findClients();
		model.put("clients", clients);
		return "clients/clientList";
	}
}
