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
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.AppointmentValidator;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.HealthValidator;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AppointmentService appointmentService;
	private final ProfessionalService professionalService;
	private final ClientService clientService;
	private final CenterService centerService;
	private final SpecialtyService specialtyService;
	
	
	@Autowired
	public AdminController(final AppointmentService appointmentService, final ProfessionalService professionalService,  final ClientService clientService, final CenterService centerService, final SpecialtyService specialtyService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.clientService = clientService;
		this.centerService = centerService;
		this.specialtyService = specialtyService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping()
	public String dashboard(Map<String, Object> model) {
		return "admin/dashboard";
	}
	
	/**
	 * CLIENTS
	 */
	
	@GetMapping("/clients")
	public String clientList(Map<String, Object> model) {
		Collection<Client> clients = this.clientService.findAll();
		model.put("clients", clients);
		return "admin/clients/list";
	}
	
	@GetMapping("/clients/{clientId}")
	public String clientDetail(@PathVariable("clientId") final int clientId, final ModelMap model) {
		Client client = this.clientService.findClientById(clientId);
		model.put("client", client);
		return "admin/clients/detail";
	}

	@GetMapping("/clients/{clientId}/edit")
	public String clientEditForm(@PathVariable("clientId") final int clientId, final ModelMap model) {
		Client client = this.clientService.findClientById(clientId);
		java.util.List<String> lista = new ArrayList<>();
		HealthInsurance[] h = HealthInsurance.values();
		for (HealthInsurance hi : h) {
			lista.add(hi.getDisplayName());
		}
		model.put("client", client);
		model.put("healthInsurances", lista);
		return "admin/clients/form";
	}


	@PostMapping("/clients/{clientId}/edit")
	public String processClientEditForm(@Valid final Client client, @PathVariable("clientId") final int clientId, final BindingResult result, final ModelMap model) throws Exception {
		HealthValidator healthValidator = new HealthValidator();
		healthValidator.validate(client, result);
		
		if (result.hasErrors()) {
			java.util.List<String> lista = new ArrayList<>();
			HealthInsurance[] h = HealthInsurance.values();
			
			for (HealthInsurance hi : h) {
				lista.add(hi.getDisplayName());
			}
			
			model.put("client", client);
			model.put("healthInsurances", lista);
			return "admin/clients/form";
		} else {
			client.setId(clientId);
			this.clientService.saveClient(client);
			return "redirect:/admin/clients/" + clientId;
		}
	}
	
	@GetMapping("/clients/create")
	public String clientCreateForm(final ModelMap model) {
		Client client = new Client();
		java.util.List<String> lista = new ArrayList<>();
		HealthInsurance[] h = HealthInsurance.values();
		for (HealthInsurance hi : h) {
			lista.add(hi.getDisplayName());
		}
		model.put("client", client);
		model.put("healthInsurances", lista);
		return "admin/clients/form";
	}
	
	@PostMapping("/clients/create")
	public String processClientCreateForm(@Valid final Client client, final BindingResult result, final ModelMap model) {
		HealthValidator healthValidator = new HealthValidator();
		healthValidator.validate(client, result);
		if (result.hasErrors()) {
			java.util.List<String> lista = new ArrayList<>();
			HealthInsurance[] h = HealthInsurance.values();
			
			for (HealthInsurance hi : h) {
				lista.add(hi.getDisplayName());
			}
			model.put("client", client);
			model.put("healthInsurances", lista);
			return "admin/clients/form";
		} else {
			this.clientService.saveClient(client);
			return "redirect:/admin/clients";
		}
	}
	
	@PostMapping("/clients/{clientId}/delete")
	public String clientDelete(@PathVariable("clientId") final int clientId, final ModelMap model) {
		this.clientService.deleteById(clientId);
		return "redirect:/admin/clients";
	}
	
	
	/**
	 * PROFESSIONALS
	 */

	
	@GetMapping("/professionals")
	public String professionalList(Map<String, Object> model) {
		Iterable<Professional> professionals = this.professionalService.findAll();
		model.put("professionals", professionals);
		return "admin/professionals/list";
	}
	
	
	@GetMapping("/professionals/{professionalId}")
	public String professionalDetail(@PathVariable("professionalId") final int professionalId, final ModelMap model) {
		Professional professional = this.professionalService.findById(professionalId).get();
		model.put("professional", professional);
		return "admin/professionals/detail";
	}

	@GetMapping("/professionals/{professionalId}/edit")
	public String professionalEditForm(@PathVariable("professionalId") final int professionalId, final ModelMap model) {
		Professional professional = this.professionalService.findById(professionalId).get();
		Iterable<Center> centers = this.centerService.findAll();
		Iterable<Specialty> specialties = this.specialtyService.findAll();
		
		model.put("professional", professional);
		model.put("centers", centers);
		model.put("specialties", specialties);	
		return "admin/professionals/form";
	}
	
	@PostMapping("/professionals/{professionalId}/edit")
	public String processProfessionalEditForm(@Valid final Professional professional, @PathVariable("professionalId") final int professionalId, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();
			
			model.put("professional", professional);
			model.put("centers", centers);
			model.put("specialties", specialties);
			return "admin/professionals/form";
		} else {
			professional.setId(professionalId);
			this.professionalService.saveProfessional(professional);
			return "redirect:/admin/professionals/" + professionalId;
		}
	}
	
	@GetMapping("/professionals/create")
	public String professionalCreateForm(final ModelMap model) {
		Professional professional = new Professional();
		Iterable<Center> centers = this.centerService.findAll();
		Iterable<Specialty> specialties = this.specialtyService.findAll();
		
		model.put("professional", professional);
		model.put("centers", centers);
		model.put("specialties", specialties);
		return "admin/professionals/form";
	}
	
	@PostMapping("/professionals/create")
	public String processProfessionalCreateForm(@Valid final Professional professional, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();
			
			model.put("professional", professional);
			model.put("centers", centers);
			model.put("specialties", specialties);
			return "admin/professionals/form";
		} else {
			this.professionalService.saveProfessional(professional);
			return "redirect:/admin/professionals";
		}
	}
	
	@PostMapping("/professionals/{professionalId}/delete")
	public String professionalDelete(@PathVariable("professionalId") final int professionalId, final ModelMap model) {
		this.professionalService.deleteById(professionalId);
		return "redirect:/admin/professionals";
	}
	
	/**
	 * APPOINTMENTS
	 */
	
	@GetMapping("/appointments")
	public String appointmentList(Map<String, Object> model) {
		Iterable<Appointment> appointments = this.appointmentService.listAppointments();
		model.put("appointments", appointments);
		return "admin/appointments/list";
	}

	@GetMapping("/appointments/{appointmentId}")
	public String appointmentDetail(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		model.put("appointment", appointment);
		return "admin/appointments/detail";
	}

	@GetMapping("/appointments/{appointmentId}/edit")
	public String appointmentEditForm(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Iterable<Client> clients = this.clientService.findAll();
		Iterable<Professional> professionals = this.professionalService.findAll();
		Iterable<Center> centers = this.centerService.findAll();
		Iterable<Specialty> specialties = this.specialtyService.findAll();		
		
		model.put("appointment", appointment);
		model.put("clients", clients);
		model.put("professionals", professionals);
		model.put("centers", centers);
		model.put("specialties", specialties);
		model.put("statusChoices", AppointmentStatus.values());
		return "admin/appointments/form";
	}
	
	@PostMapping("/appointments/{appointmentId}/edit")
	public String processAppointmentEditForm(@Valid final Appointment appointment, @PathVariable("appointmentId") final int appointmentId, final BindingResult result, final ModelMap model) {
		AppointmentValidator appointmentValidator = new AppointmentValidator();
		appointmentValidator.validate(appointment, result);
		
		if (result.hasErrors()) {
			Iterable<Client> clients = this.clientService.findAll();
			Iterable<Professional> professionals = this.professionalService.findAll();
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();
			
			model.put("appointment", appointment);
			model.put("clients", clients);
			model.put("professionals", professionals);
			model.put("centers", centers);
			model.put("specialties", specialties);
			model.put("statusChoices", AppointmentStatus.values());
			return "admin/appointments/form";
		} else {
			appointment.setId(appointmentId);
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/admin/appointments/" + appointmentId;
		}
	}
	
	@GetMapping("/appointments/create")
	public String appointmentCreateForm(final ModelMap model) {		
		Appointment appointment = new Appointment();
		Iterable<Client> clients = this.clientService.findAll();
		Iterable<Professional> professionals = this.professionalService.findAll();
		Iterable<Center> centers = this.centerService.findAll();
		Iterable<Specialty> specialties = this.specialtyService.findAll();
		
		model.put("appointment", appointment);
		model.put("clients", clients);
		model.put("professionals", professionals);
		model.put("centers", centers);
		model.put("specialties", specialties);
		model.put("statusChoices", AppointmentStatus.values());
		return "admin/appointments/form";
	}
	
	@PostMapping("/appointments/create")
	public String processAppointmentCreateForm(@Valid final Appointment appointment, final BindingResult result, final ModelMap model) {
		AppointmentValidator appointmentValidator = new AppointmentValidator();
		appointmentValidator.validate(appointment, result);
		
		if (result.hasErrors()) {
			Iterable<Client> clients = this.clientService.findAll();
			Iterable<Professional> professionals = this.professionalService.findAll();
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();
			
			model.put("appointment", appointment);
			model.put("clients", clients);
			model.put("professionals", professionals);
			model.put("centers", centers);
			model.put("specialties", specialties);
			model.put("statusChoices", AppointmentStatus.values());
			return "admin/appointments/form";
		} else {
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/admin/appointments";
		}
	}

}
