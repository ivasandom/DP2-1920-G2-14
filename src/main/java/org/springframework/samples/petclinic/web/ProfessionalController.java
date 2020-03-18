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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class ProfessionalController {

	private final ProfessionalService professionalService;
	private final SpecialtyService specialtyService;
	private final CenterService centerService;

	@Autowired
	public ProfessionalController(ProfessionalService professionalService, SpecialtyService specialtyService, CenterService centerService,
			AuthoritiesService authoritiesService) {
		this.professionalService = professionalService;
		this.specialtyService = specialtyService;
		this.centerService = centerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("specialties")
	public Iterable<Specialty> populateSpecialties() {
		return this.specialtyService.findAll();
	}
	
	@ModelAttribute("centers")
	public Iterable<Center> populateCenters() {
		return this.centerService.findAll();
	}
	
	@GetMapping(value = "/professionals/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("professional", new Professional());
		return "professionals/find";
	}

	@GetMapping(value = "/professionals")
	public String processFindForm(Professional professional, BindingResult result, Map<String, Object> model) {
		model.put("professional", professional);

		Iterable<Professional> results = this.professionalService.findProfessionalBySpecialtyAndCenter(
				professional.getSpecialty().getId(), professional.getCenter().getId());
		
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
	
	@GetMapping("/professionals/filter")     
	@ResponseBody
	public ResponseEntity<Object> filterJSON(@RequestParam Optional<Integer> centerId, @RequestParam Optional<Integer> specialtyId, Model model) {
		if (centerId.isPresent() && specialtyId.isPresent()) {
			List<Map<String, Object>> entities = new ArrayList<>();
		    for (Professional p : this.professionalService.findProfessionalBySpecialtyAndCenter(specialtyId.get(), centerId.get())) {
		    	HashMap<String, Object> professional = new HashMap<String, Object>();
		        professional.put("id", p.getId());
		        professional.put("fullName", p.getFullName());
		        entities.add(professional);
		    }
		    return new ResponseEntity<Object>(entities, HttpStatus.OK);
		} else {
			HashMap<String, String> response = new HashMap<String, String>();
			response.put("error", "Invalid request");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
