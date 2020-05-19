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

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AppointmentService appointmentService;
	private final ProfessionalService professionalService;
	private final ClientService clientService;
	private final CenterService centerService;
	
	@Autowired
	public AdminController(final AppointmentService appointmentService, final ProfessionalService professionalService,  final ClientService clientService, final CenterService centerService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.clientService = clientService;
		this.centerService = centerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping()
	public String dashboard(Map<String, Object> model) {
		return "admin/dashboard";
	}
	
	@GetMapping("/clients")
	public String clientList(Map<String, Object> model) {
		Collection<Client> clients = this.clientService.findAll();
		model.put("clients", clients);
		return "admin/clients";
	}
	
	
	@GetMapping("/professionals")
	public String professionalList(Map<String, Object> model) {
		Iterable<Professional> professionals = this.professionalService.findAll();
		model.put("professionals", professionals);
		return "admin/professionals";
	}
	
	@GetMapping("/appointments")
	public String appointmentList(Map<String, Object> model) {
		Iterable<Appointment> appointments = this.appointmentService.listAppointments();
		model.put("appointments", appointments);
		return "admin/appointments";
	}
	
	@GetMapping("/payments")
	public String paymentList(Map<String, Object> model) {
		return "admin/payments";
	}
	
	@GetMapping("/centers")
	public String centerList(Map<String, Object> model) {
		Iterable<Center> centers = this.centerService.findAll();
		model.put("centers", centers);
		return "admin/centers";
	}

}
