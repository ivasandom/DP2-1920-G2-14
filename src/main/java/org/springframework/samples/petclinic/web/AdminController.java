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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.AppointmentValidator;
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.model.BillTransactionValidator;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.ClientValidator;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.ProfessionalValidator;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.BillService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.PaymentMethodService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final AppointmentService appointmentService;
	private final ProfessionalService professionalService;
	private final ClientService clientService;
	private final CenterService centerService;
	private final SpecialtyService specialtyService;
	private final BillService billService;
	private final StripeService stripeService;
	private final TransactionService transactionService;
	private final PaymentMethodService paymentMethodService;

	@Autowired
	public AdminController(final AppointmentService appointmentService, final ProfessionalService professionalService,
			final ClientService clientService, final CenterService centerService,
			final SpecialtyService specialtyService, final BillService billService, final StripeService stripeService,
			final TransactionService transactionService, final PaymentMethodService paymentMethodService) {
		this.appointmentService = appointmentService;
		this.professionalService = professionalService;
		this.clientService = clientService;
		this.centerService = centerService;
		this.specialtyService = specialtyService;
		this.billService = billService;
		this.stripeService = stripeService;
		this.transactionService = transactionService;
		this.paymentMethodService = paymentMethodService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping()
	public String dashboard(Map<String, Object> model) {
		Integer numClients = this.clientService.clientCount();
		Integer numProfessionals = this.professionalService.professionalCount();
		Integer numAppointments = this.appointmentService.appointmentCount();
		Double totalBilled = this.billService.getTotalBilled();
		Object[] billedPerDay = this.billService.getBilledPerDay();
		Long numCompletedAppointments = this.appointmentService.getNumberOfCompletedAppointments();
		Long numPendingAppointments = this.appointmentService.getNumberOfPendingAppointments();
		Long numAbsentAppointments = this.appointmentService.getNumberOfAbsentAppointments();
		System.out.println(Arrays.toString(billedPerDay));
		model.put("numClients", numClients);
		model.put("numProfessionals", numProfessionals);
		model.put("numAppointments", numAppointments);
		model.put("totalBilled", totalBilled);
		model.put("billedPerDay", billedPerDay);
		model.put("numCompletedAppointments", numCompletedAppointments);
		model.put("numPendingAppointments", numPendingAppointments);
		model.put("numAbsentAppointments", numAbsentAppointments);
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
		model.put("client", client);
		model.put("documentTypes", DocumentType.getNaturalPersonValues());
		model.put("healthInsurances", HealthInsurance.values());
		return "admin/clients/form";
	}

	@PostMapping("/clients/{clientId}/edit")
	public String processClientEditForm(@Valid final Client client, final BindingResult result,
			@PathVariable("clientId") final int clientId, final ModelMap model) throws Exception {
		ClientValidator clientValidator = new ClientValidator();
		clientValidator.validate(client, result);

		if (result.hasErrors()) {
			model.put("client", client);
			model.put("documentTypes", DocumentType.getNaturalPersonValues());
			model.put("healthInsurances", HealthInsurance.values());
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
		model.put("client", client);
		model.put("documentTypes", DocumentType.getNaturalPersonValues());
		model.put("healthInsurances", HealthInsurance.values());
		return "admin/clients/form";
	}

	@PostMapping("/clients/create")
	public String processClientCreateForm(@Valid final Client client, final BindingResult result,
			final ModelMap model) {
		ClientValidator clientValidator = new ClientValidator();
		clientValidator.validate(client, result);

		if (result.hasErrors()) {
			model.put("client", client);
			model.put("documentTypes", DocumentType.getNaturalPersonValues());
			model.put("healthInsurances", HealthInsurance.values());
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
		model.put("documentTypes", DocumentType.getNaturalPersonValues());
		return "admin/professionals/form";
	}

	@PostMapping("/professionals/{professionalId}/edit")
	public String processProfessionalEditForm(@Valid final Professional professional, final BindingResult result,
			@PathVariable("professionalId") final int professionalId, final ModelMap model) {
		System.out.println("errores");
		ProfessionalValidator professionalValidator = new ProfessionalValidator();
		professionalValidator.validate(professional, result);

		if (result.hasErrors()) {
			System.out.println("errores" + result.getFieldErrors());
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();
			

			model.put("professional", professional);
			model.put("centers", centers);
			model.put("specialties", specialties);
			model.put("documentTypes", DocumentType.getNaturalPersonValues());
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
		model.put("documentTypes", DocumentType.getNaturalPersonValues());
		return "admin/professionals/form";
	}

	@PostMapping("/professionals/create")
	public String processProfessionalCreateForm(@Valid final Professional professional, final BindingResult result,
			final ModelMap model) {
		ProfessionalValidator professionalValidator = new ProfessionalValidator();
		professionalValidator.validate(professional, result);

		if (result.hasErrors()) {
			Iterable<Center> centers = this.centerService.findAll();
			Iterable<Specialty> specialties = this.specialtyService.findAll();

			model.put("professional", professional);
			model.put("centers", centers);
			model.put("specialties", specialties);
			model.put("documentTypes", DocumentType.getNaturalPersonValues());
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
	public String processAppointmentEditForm(@Valid final Appointment appointment, final BindingResult result,
			@PathVariable("appointmentId") final int appointmentId, final ModelMap model) {
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
	public String processAppointmentCreateForm(@Valid final Appointment appointment, final BindingResult result,
			final ModelMap model) {
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

	@PostMapping("/appointments/{appointmentId}/delete")
	public String appointmentDelete(@PathVariable("appointmentId") final int appointmentId, final ModelMap model)
			throws Exception {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		if (appointment != null && !appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
			this.appointmentService.delete(appointment);
		}
		return "redirect:/admin/appointments";
	}

	/**
	 * BILLS
	 */

	@GetMapping("/bills")
	public String billList(Map<String, Object> model) {
		Iterable<Bill> bills = this.billService.findAll();
		model.put("bills", bills);
		return "admin/bills/list";
	}

	@GetMapping("/bills/{billId}")
	public String billDetail(@PathVariable("billId") final int billId, final ModelMap model) {
		Bill bill = this.billService.findById(billId);
		model.put("bill", bill);
		return "admin/bills/detail";
	}

	@GetMapping("/bills/{billId}/charge")
	public String billChargeForm(@PathVariable("billId") final int billId, final ModelMap model) {
		Bill bill = this.billService.findById(billId);

		Transaction transaction = new Transaction();
		transaction.setAmount(bill.getFinalPrice() - bill.getTotalPaid());
		Client client = bill.getAppointment().getClient();

		List<PaymentMethod> availablePaymentMethods = new ArrayList<>();
		availablePaymentMethods.add(PaymentMethod.cash());
		availablePaymentMethods.add(PaymentMethod.bankTransfer());

		if (bill.getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) && client != null) {
			Set<PaymentMethod> clientPaymentMethods = client.getPaymentMethods();
			availablePaymentMethods.addAll(clientPaymentMethods);
		}

		model.put("bill", bill);
		model.put("transaction", transaction);
		model.put("paymentMethods", availablePaymentMethods);
		return "admin/bills/chargeForm";
	}

	@PostMapping("/bills/{billId}/charge")
	public String billChargeForm(@ModelAttribute Transaction transaction, final BindingResult result,
			@PathVariable("billId") final int billId, final ModelMap model) throws Exception {

		Bill bill = this.billService.findById(billId);
		transaction.setBill(bill);

		PaymentMethod paymentMethod = transaction.getPaymentMethod();
		if (paymentMethod != null
				&& !(paymentMethod.getToken().equals("CASH") || paymentMethod.getToken().equals("BANKTRANSFER"))) {

			transaction.setPaymentMethod(this.paymentMethodService.findByTokenAndClient(paymentMethod.getToken(),
					bill.getAppointment().getClient()));
		}

		BillTransactionValidator billTransactionValidator = new BillTransactionValidator();
		billTransactionValidator.validate(transaction, result);

		if (result.hasErrors()) {
			Client client = bill.getAppointment().getClient();

			List<PaymentMethod> availablePaymentMethods = new ArrayList<>();
			availablePaymentMethods.add(PaymentMethod.cash());
			availablePaymentMethods.add(PaymentMethod.bankTransfer());

			if (bill.getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) && client != null) {
				Set<PaymentMethod> clientPaymentMethods = client.getPaymentMethods();
				availablePaymentMethods.addAll(clientPaymentMethods);
			}

			model.put("bill", bill);
			model.put("transaction", transaction);
			model.put("paymentMethods", availablePaymentMethods);
			return "admin/bills/chargeForm";
		} else {
			transaction.setBill(bill);
			transaction.setCreatedAt(LocalDateTime.now());
			transaction.setType(TransactionType.CHARGE);

			if (paymentMethod.getToken().equals("CASH") || paymentMethod.getToken().equals("BANKTRANSFER")) {
				transaction.setSuccess(true);
				transaction.setStatus("succeeded");
				transaction.setToken(paymentMethod.getToken());
			} else {
				PaymentIntent paymentIntent = this.stripeService.charge(transaction.getPaymentMethod().getToken(),
						transaction.getAmount(), bill.getAppointment().getClient().getStripeId());

				transaction.setAmount(paymentIntent.getAmount() * 0.01);
				transaction.setToken(paymentIntent.getId());
				transaction.setStatus(paymentIntent.getStatus());
				transaction.setSuccess(paymentIntent.getStatus().equals("succeeded"));
			}

			this.transactionService.saveTransaction(transaction);

			return "redirect:/admin/bills/" + billId;
		}

	}

	@PostMapping("/bills/{billId}/refund/{transactionId}")
	public String billRefund(@PathVariable("billId") final int billId,
			@PathVariable("transactionId") final int transactionId, final ModelMap model) throws Exception {

		Transaction transaction = this.transactionService.findById(transactionId);

		if (transaction != null && transaction.getType() == TransactionType.CHARGE && transaction.getSuccess()
				&& !transaction.getRefunded()) {

			Transaction transactionRefund = new Transaction();
			transactionRefund.setBill(transaction.getBill());
			transactionRefund.setType(TransactionType.REFUND);
			transactionRefund.setCreatedAt(LocalDateTime.now());

			if (transaction.getToken().equals("CASH") || transaction.getToken().equals("BANKTRANSFER")) {
				transactionRefund.setAmount(transaction.getAmount());
				transactionRefund.setToken(transaction.getToken());
				transactionRefund.setSuccess(true);
				transactionRefund.setStatus("succeeded");
			} else {
				Refund stripeRefund = this.stripeService.refund(transaction.getToken());
				transactionRefund.setAmount(stripeRefund.getAmount() * 0.01);
				transactionRefund.setToken(stripeRefund.getId());
				transactionRefund.setSuccess(stripeRefund.getStatus() == "succeeded");
				transactionRefund.setStatus(stripeRefund.getStatus());
			}

			transaction.setRefunded(true);
			this.transactionService.saveTransaction(transaction);
			this.transactionService.saveTransaction(transactionRefund);
		}

		return "redirect:/admin/bills/" + billId;

	}

}
