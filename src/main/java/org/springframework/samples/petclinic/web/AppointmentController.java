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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("appointments")
public class AppointmentController {
	
	private final AppointmentService appointmentService;
	private final ProfessionalService professionalService;
	private final SpecialtyService specialtyService;
	private final ClientService clientService;
	private final CenterService centerService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService, ProfessionalService professionalService, 
			SpecialtyService specialtyService, ClientService clientService, CenterService centerService, AuthoritiesService authoritiesService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.specialtyService = specialtyService;
		this.clientService = clientService;
		this.centerService = centerService;
	}
	
	@ModelAttribute("centers")
	public Iterable<Center> populateCenters() {
		return this.centerService.findAll();
	}
	
	@ModelAttribute("professionals")
	public Iterable<Professional> populateProfessionals() {
		return this.professionalService.findAll();
	}
	
	
	@ModelAttribute("specialties")
	public Iterable<Specialty> populateSpecialties() {
		return this.specialtyService.findAll();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping()
	public String listAppointments(Map<String, Object> model) {
		Iterable<Appointment> appointments = this.appointmentService.listAppointments();
		model.put("appointments", appointments);
		return "appointments/list";
	}
	
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {
		Appointment appointment = new Appointment();
		model.put("appointment", appointment);
		return "appointments/new";
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Appointment appointment, final BindingResult result, final ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		appointment.setClient(clientService.findClientByUsername(auth.getName()));
		if (result.hasErrors()) {
			model.put("appointment", appointment);
			System.out.println(result.getAllErrors());
			return "appointments/new";
		} else {
			// Save appointment if valid
			
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/appointments";
		}
	}

}
