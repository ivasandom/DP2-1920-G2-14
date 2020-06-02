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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.AppointmentType;
import org.springframework.samples.petclinic.model.AppointmentValidator;
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ConsultationValidator;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.projections.ListAppointmentsClient;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BillService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.DeseaseService;
import org.springframework.samples.petclinic.service.MedicineService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.util.EntityUtils;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("appointments")
public class AppointmentController {

	private final AppointmentService appointmentService;
	private final ProfessionalService professionalService;
	private final SpecialtyService specialtyService;
	private final ClientService clientService;
	private final CenterService centerService;
	private final MedicineService medicineService;
	private final DeseaseService deseaseService;
	private final BillService billService;

	@Autowired
	public AppointmentController(final AppointmentService appointmentService,
			final ProfessionalService professionalService, final SpecialtyService specialtyService,
			final ClientService clientService, final CenterService centerService,
			final AuthoritiesService authoritiesService, final MedicineService medicineService,
			final DeseaseService deseaseService, final BillService billService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.specialtyService = specialtyService;
		this.clientService = clientService;
		this.centerService = centerService;
		this.medicineService = medicineService;
		this.deseaseService = deseaseService;
		this.billService = billService;
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
		Iterable<ListAppointmentsClient> appointments = this.appointmentService.findAppointmentByUserId(currentClient.getId());
		model.put("appointments", appointments);
		return "appointments/list";
	}

	@GetMapping("/pro")
	public String listAppointmentsProfessional(final Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Professional currentPro = this.professionalService.findProByUsername(auth.getName());
		Appointment nextAppointment = null;
		Iterator<Appointment> todayPendingAppointments = this.appointmentService
				.findTodayPendingByProfessionalId(currentPro.getId()).iterator();
		System.out.println(todayPendingAppointments);
		Collection<Appointment> todayCompletedAppointments = this.appointmentService
				.findTodayCompletedByProfessionalId(currentPro.getId());

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
		model.put("types", AppointmentType.values());
		model.put("appointment", appointment);
		return "appointments/new";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Appointment appointment, final BindingResult result,
			final ModelMap model) throws Exception {

		EntityUtils.setRelationshipAttribute(appointment, Professional.class, this.professionalService, "findById");
		EntityUtils.setRelationshipAttribute(appointment, Center.class, this.centerService, "findCenterById");
		EntityUtils.setRelationshipAttribute(appointment, Specialty.class, this.specialtyService, "findSpecialtyById");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		appointment.setClient(this.clientService.findClientByUsername(auth.getName()));
		AppointmentValidator appointmentValidator = new AppointmentValidator();
		appointmentValidator.validate(appointment, result);

		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			model.put("types", AppointmentType.values());
			model.put("appointment", appointment);
			return "appointments/new";
		} else {
			// Save appointment if valid
			appointment.setStatus(AppointmentStatus.PENDING);
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/appointments";
		}
	}

	@PostMapping("/{appointmentId}/absent")
	public String markAbsent(@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
		return this.appointmentService.findAppointmentById(appointmentId).map(appointment -> {
			if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
				appointment.setStatus(AppointmentStatus.ABSENT);
			}
			this.appointmentService.saveAppointment(appointment);
			return "redirect:/appointments/pro";
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
	}

	@GetMapping("/{appointmentId}/consultation")
	public String appointmentConsultationForm(@PathVariable("appointmentId") final int appointmentId,
			final Map<String, Object> model) throws Exception {

		return this.appointmentService.findAppointmentById(appointmentId).map(appointment -> {
			if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
				return "redirect:/appointments/pro";
			} else {
				Collection<Medicine> medicines = this.medicineService.findMedicines();
				Iterable<Desease> deseases = this.deseaseService.findAll();

				model.put("medicineList", medicines);
				model.put("appointment", appointment);
				model.put("deseaseList", deseases);
				return "appointments/consultationPro";
			}
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

	}

	@PostMapping(value = "/{appointmentId}/consultation")
	public String processAppointmentConsultationForm(@Valid final Appointment appointment, final BindingResult result,
			@PathVariable("appointmentId") final int appointmentId, final ModelMap model) throws Exception {

		return this.appointmentService.findAppointmentById(appointmentId).map(a -> {

			if (a.getStatus() == AppointmentStatus.COMPLETED) {
				return "redirect:/appointments/pro";
			} else {
				ConsultationValidator consultationValidator = new ConsultationValidator();
				consultationValidator.validate(appointment, result);

				if (result.hasErrors()) {
					Iterable<Desease> deseases = this.deseaseService.findAll();
					Collection<Medicine> medicines = this.medicineService.findMedicines();
					BeanUtils.copyProperties(a, appointment, "bill", "diagnosis");
					
					model.put("medicineList", medicines);
					model.put("deseaseList", deseases);
					model.put("appointment", appointment);
					return "appointments/consultationPro";
				} else {
					Client appointmentClient = a.getClient();

					Bill bill = appointment.getBill();
					bill.setAppointment(a);
					bill.setHealthInsurance(appointmentClient.getHealthInsurance());
					bill.setCreatedAt(LocalDateTime.now());

					if (appointmentClient.getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE)) {
						bill.setDocument(appointmentClient.getDocument());
						bill.setDocumentType(appointmentClient.getDocumentType());
						bill.setName(appointmentClient.getFullName());
					} else {
						bill.setDocument(appointmentClient.getHealthInsurance().getCif());
						bill.setDocumentType(DocumentType.CIF);
						bill.setName(appointmentClient.getHealthInsurance().getLegalName());
					}

					appointment.getDiagnosis().setDate(LocalDate.now());
					a.setDiagnosis(appointment.getDiagnosis());
					a.setStatus(AppointmentStatus.COMPLETED);

					this.appointmentService.saveAppointment(a);
					this.billService.saveBill(bill);
					return "redirect:/appointments/pro";
				}

			}
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
	}

	@GetMapping("/{appointmentId}/details")
	public String showAppointment(@PathVariable("appointmentId") final int appointmentId,
			final ModelMap model) {

		return this.appointmentService.findAppointmentById(appointmentId).map(appointment -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			try {
				if (!authentication.getName().equals(appointment.getClient().getUser().getUsername())) {
					model.addAttribute("message", "You cannot show another user's appointment");
					return "errors/generic";
				} else {
					model.put("appointment", appointment);
					return "appointments/details";
				}
			} catch (Exception e) {
				model.addAttribute("message", "Error: " + e.getMessage());
				return "errors/generic";
			}
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
	}

	@GetMapping(path = "/delete/{appointmentId}")
	public String deleteAppointment(@PathVariable("appointmentId") final Integer appointmentId,
			final ModelMap modelMap) {

		return this.appointmentService.findAppointmentById(appointmentId).map(appointment -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String vista = "redirect:/appointments";

			try {
				if (!authentication.getName().equals(appointment.getClient().getUser().getUsername())) {
					modelMap.addAttribute("message", "You cannot delete another user's appointment");
					return "errors/generic";
				} else {
					this.appointmentService.delete(appointment);
					modelMap.addAttribute("message", "Appointment successfully deleted");
				}
			} catch (Exception e) {
				modelMap.addAttribute("message", "Error: " + e.getMessage());
				return "errors/generic";
			}
			return vista;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
	}

	@GetMapping(value = "busy")
	@ResponseBody
	public ResponseEntity<Object> getBusyStartTimes(
			@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") final LocalDate date,
			@RequestParam(name = "professionalId", required = false) final Integer professionalId, final Model model) {

		if (date != null && professionalId != null) {
			Optional<Professional> professional = this.professionalService.findById(professionalId);
			if (professional.isPresent()) {
				Collection<LocalTime> busyStartTimes = this.appointmentService
						.findAppointmentStartTimesByProfessionalAndDate(date, professional.get());
				return new ResponseEntity<Object>(busyStartTimes, HttpStatus.OK);
			}
		}

		// Invalid request
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("error", "Invalid request");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

}
