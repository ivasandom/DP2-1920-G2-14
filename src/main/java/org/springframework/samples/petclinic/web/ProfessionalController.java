/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class ProfessionalController {

	private final ProfessionalService professionalService;

	@Autowired
	public ProfessionalController(ProfessionalService professionalService, AuthoritiesService authoritiesService) {
		this.professionalService = professionalService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}


	@GetMapping(value = "/professionals/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("professional", new Professional());
		return "professionals/find";
	}

	@GetMapping(value = "/professionals")
	public String processFindForm(Professional professional, BindingResult result, Map<String, Object> model) {
		model.put("professional", professional);
		
		if (professional.getSpecialty() == null) {
			professional.setSpecialty("");
		}

		
		Iterable<Professional> results = this.professionalService.findProfessionalBySpecialty(professional.getSpecialty());
		if (!results.iterator().hasNext()) {
			// no owners found
			result.rejectValue("specialty", "notFound", "not found");
			return "professionals/find";
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "professionals/list";
		}
	}

}
