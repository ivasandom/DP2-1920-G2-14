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
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.HealthValidator;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


	@Autowired
	public ClientController(final ClientService clientService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.clientService = clientService;
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
	public String processCreationForm(@Valid final Client client, final BindingResult result, final ModelMap model) throws DataAccessException {
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
			this.clientService.saveClient(client);

			return "redirect:/";
		}
	}

}
