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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.AppointmentValidator;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ConsultationValidator;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.DeseaseService;
import org.springframework.samples.petclinic.service.MedicineService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("appointments")
public class AppointmentController {

	private final AppointmentService	appointmentService;
	private final ProfessionalService	professionalService;
	private final SpecialtyService		specialtyService;
	private final ClientService			clientService;
	private final CenterService			centerService;
	private final MedicineService		medicineService;
	private final DeseaseService		deseaseService;
	private final StripeService			stripeService;


	@Autowired
	public AppointmentController(final AppointmentService appointmentService, final ProfessionalService professionalService, final SpecialtyService specialtyService, final ClientService clientService, final CenterService centerService,
		final AuthoritiesService authoritiesService, final MedicineService medicineService, final DeseaseService deseaseService, final StripeService stripeService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.specialtyService = specialtyService;
		this.clientService = clientService;
		this.centerService = centerService;
		this.medicineService = medicineService;
		this.deseaseService = deseaseService;
		this.stripeService = stripeService;
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
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping()
	public String listAppointments(final Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Client currentClient = this.clientService.findClientByUsername(auth.getName());
		Iterable<Appointment> appointments = this.appointmentService.findAppointmentByUserId(currentClient.getId());
		model.put("appointments", appointments);
		return "appointments/list";
	}

	@GetMapping("/{appointmentId}")
	public ModelAndView detailtsAppointment(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
		ModelAndView mav = new ModelAndView("appointments/new");
		Appointment a = this.appointmentService.findAppointmentById(appointmentId);
		Client c = this.clientService.findClientById(a.getClient().getId());

		model.put("client", c);
		mav.addObject(a);
		return mav;
	}

	@GetMapping("/pro")
	public String listAppointmentsProfessional(final Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Professional currentPro = this.professionalService.findProByUsername(auth.getName());
		Appointment nextAppointment = null;
		Iterator<Appointment> todayPendingAppointments = this.appointmentService.findTodayPendingByProfessionalId(currentPro.getId()).iterator();
		System.out.println(todayPendingAppointments);
		Collection<Appointment> todayCompletedAppointments = this.appointmentService.findTodayCompletedByProfessionalId(currentPro.getId());

		if (todayPendingAppointments.hasNext()) {
			nextAppointment = todayPendingAppointments.next();
		}

		model.put("nextAppointment", nextAppointment);
		model.put("pendingAppointments", todayPendingAppointments);
		model.put("completedAppointments", todayCompletedAppointments);
		return "appointments/pro";
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {
		Appointment appointment = new Appointment();
		Collection<String> types = this.appointmentService.findAppointmentByTypes();
		model.put("types", types);
		model.put("appointment", appointment);
		return "appointments/new";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Appointment appointment, final BindingResult result, final ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		appointment.setClient(this.clientService.findClientByUsername(auth.getName()));
		AppointmentValidator appointmentValidator = new AppointmentValidator();
		appointmentValidator.validate(appointment, result);

		if (result.hasErrors()) {
			Collection<String> types = this.appointmentService.findAppointmentByTypes();
			model.put("types", types);
			model.put("appointment", appointment);
			System.out.println(result.getAllErrors());
			return "appointments/new";
		} else {
			// Save appointment if valid
			appointment.setStatus(AppointmentStatus.PENDING);
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/appointments";
		}
	}

	@GetMapping(value = "busy")
	@ResponseBody
	public ResponseEntity<Object> getBusyStartTimes(@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") final LocalDate date, @RequestParam(name = "professionalId", required = false) final Integer professionalId,
		final Model model) {

		if (date != null && professionalId != null) {
			Optional<Professional> professional = this.professionalService.findById(professionalId);
			if (professional.isPresent()) {
				Collection<LocalTime> busyStartTimes = this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional.get());
				return new ResponseEntity<Object>(busyStartTimes, HttpStatus.OK);
			}
		}

		// Invalid request
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("error", "Invalid request");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/{appointmentId}/absent")
	public String markAbsent(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
			appointment.setStatus(AppointmentStatus.ABSENT);
		}
		this.appointmentService.saveAppointment(appointment);
		return "redirect:/appointments/pro";
	}

	@GetMapping("/{appointmentId}/consultation")
	public String showAppointment(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) throws Exception {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		List<PaymentMethod> paymentMethods = appointment.getClient().getPaymentMethods().stream().collect(Collectors.toList());
		for (int i = 0; i < paymentMethods.size(); i++) {
			com.stripe.model.PaymentMethod pM = this.stripeService.retrievePaymentMethod(paymentMethods.get(i).getToken());
			String brand = pM.getType();
			paymentMethods.get(i).setBrand(brand);
		}
		PaymentMethod p = new PaymentMethod();
		p.setBrand("efectivo");
		p.setClient(appointment.getClient());
		p.setToken("efective_token");
		paymentMethods.add(p);
		appointment.getClient().getPaymentMethods().add(p);
		Iterable<Desease> deseases = this.deseaseService.findAll();
		System.out.println(appointment.getDiagnosis());
		// Diagnosis
		model.put("medicineList", medicines);
		model.put("paymentMethodsList", paymentMethods);
		model.put("appointment", appointment);
		model.put("deseaseList", deseases);
		return "appointments/consultationPro";
	}

	@PostMapping(value = "/{appointmentId}/consultation")
	public String processUpdateAppForm(@Valid final Appointment appointment, final BindingResult result, @PathVariable("appointmentId") final int appointmentId, final ModelMap model) throws Exception {
		Appointment a = this.appointmentService.findAppointmentById(appointmentId);
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		ConsultationValidator consultationValidator = new ConsultationValidator();
		consultationValidator.validate(appointment, result);
		Iterable<Desease> deseases = this.deseaseService.findAll();
		List<PaymentMethod> paymentMethods = appointment.getClient().getPaymentMethods().stream().collect(Collectors.toList());
		for (int i = 0; i < paymentMethods.size(); i++) {
			com.stripe.model.PaymentMethod pM = this.stripeService.retrievePaymentMethod(paymentMethods.get(i).getToken());
			String brand = pM.getType();
			paymentMethods.get(i).setBrand(brand);
		}
		PaymentMethod p = new PaymentMethod();
		p.setBrand("efectivo");
		p.setClient(appointment.getClient());
		p.setToken("efective_token");
		paymentMethods.add(p);
		appointment.getClient().getPaymentMethods().add(p);
		if (result.hasErrors()) {
			model.put("medicineList", medicines);
			model.put("deseaseList", deseases);
			model.put("paymentMethodsList", paymentMethods);
			model.put("appointment", a);
			model.addAttribute("errors", result.getAllErrors());
			return "appointments/consultationPro";
		} else {
			a.setDiagnosis(appointment.getDiagnosis());
			a.setReceipt(appointment.getReceipt());
			a.setStatus(AppointmentStatus.COMPLETED);
			this.appointmentService.saveAppointment(a);
			this.appointmentService.chargeAppointment(a);
			return "redirect:/appointments/pro";
		}
	}

	@GetMapping("/{appointmentId}/details")
	public String showAppointmentByClient(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) throws Exception {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		List<PaymentMethod> paymentMethods = appointment.getClient().getPaymentMethods().stream().collect(Collectors.toList());
		//Collection<String> brands = Collections.emptyList();
		for (int i = 0; i < paymentMethods.size(); i++) {
			com.stripe.model.PaymentMethod pM = this.stripeService.retrievePaymentMethod(paymentMethods.get(i).getToken());
			String brand = pM.getType();
			//brands.add(brand);
			paymentMethods.get(i).setBrand(brand);
		}
		PaymentMethod p = new PaymentMethod();
		p.setBrand("efectivo");
		p.setClient(appointment.getClient());
		p.setToken("efective_token");
		paymentMethods.add(p);
		appointment.getClient().getPaymentMethods().add(p);
		Iterable<Desease> deseases = this.deseaseService.findAll();
		System.out.println(appointment.getDiagnosis());
		// Diagnosis
		model.put("medicineList", medicines);
		//model.put("brands", brands);
		model.put("appointment", appointment);
		model.put("deseaseList", deseases);
		return "appointments/details";
	}

	@GetMapping(path = "/delete/{appointmentId}")
	public String deleteAppointment(@PathVariable("appointmentId") final Integer appointmentId, final ModelMap modelMap) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String vista = "redirect:/appointments";
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		try {
			if (!authentication.getName().equals(this.appointmentService.findAppointmentById(appointmentId).getClient().getUser().getUsername())) {
				modelMap.addAttribute("message", "You cannot delete another user's appointment");
				return "exception";
			} else {
				this.appointmentService.delete(appointment);
				modelMap.addAttribute("message", "Appointment successfully deleted");
			}
		} catch (Exception e) {
			modelMap.addAttribute("message", "Error: " + e.getMessage());
			return "exception";
		}
		return vista;
	}

	//	@GetMapping(value = "/{appointmentId}/edit")
	//	public String initUpdateAppointmentForm(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
	//		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
	//		model.put("appointment", appointment);
	//		return "appointments/new";
	//	}
	//
	//	@PostMapping(value = "/{appointmentId}/edit")
	//	public String processUpdateAppointmentForm(@Valid final Appointment appointment, final BindingResult result, @PathVariable("appointmentId") final int appointmentId, final ModelMap model) throws Exception {
	//		Appointment app = this.appointmentService.findAppointmentById(appointmentId);
	//		if (result.hasErrors()) {
	//			model.put("appointment", appointment);
	//			return "appointments";
	//		} else {
	//			this.appointmentService.saveAppointment(app);
	//			this.appointmentService.chargeAppointment(app);
	//			return "redirect:/appointments";
	//		}
	//	}
}
