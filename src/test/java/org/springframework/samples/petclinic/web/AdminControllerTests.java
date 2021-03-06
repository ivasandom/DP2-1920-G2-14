
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.AppointmentType;
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.BillService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.PaymentMethodService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.samples.petclinic.service.TransactionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminControllerTests {

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private ProfessionalService professionalService;
	
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private CenterService centerService;
	
	@MockBean
	private SpecialtyService specialtyService;
	
	@MockBean
	private BillService billService;
	
	@MockBean
	private StripeService stripeService;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private PaymentMethodService paymentMethodService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Client client;
	
	private Professional professional;
	
	private Appointment appointment;
	
	private Bill bill;
	
	private Transaction transaction;
	
	private static final int TEST_CLIENT_ID = 1;
	
	private static final int TEST_PROFESSIONAL_ID = 1;
	
	private static final int TEST_APPOINTMENT_ID = 1;
	
	private static final int TEST_BILL_ID = 1;
	
	private static final int TEST_TRANSACTION_ID = 1;
	
	private static final int TEST_CENTER_ID = 1;
	
	private static final int TEST_SPECIALTY_ID = 1;
	
	
	@BeforeEach
	void setup() throws Exception {
		String par = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Date birthDate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		
		User user2 = new User();
		user2.setEnabled(true);
		user2.setUsername("manucar");
		user2.setPassword("manucar");
		
		client = new Client();
		client.setId(TEST_CLIENT_ID);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.NIF);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance(HealthInsurance.ADESLAS);
		client.setLastName("Cuesta");
		client.setStripeId("1");
		client.setUser(user);
		client.setBirthDate(birthDate);
		client.setRegistrationDate(Date.from(Instant.now()));
		client.setPaymentMethods(new HashSet<PaymentMethod>());
		
		Center center = new Center();
		center.setId(TEST_CENTER_ID);
		center.setAddress("Sevilla");

		Specialty specialty = new Specialty();
		specialty.setId(TEST_SPECIALTY_ID);
		specialty.setName("dermatology");

		professional = new Professional();
		professional.setId(TEST_PROFESSIONAL_ID);
		professional.setCenter(center);
		professional.setSpecialty(specialty);
		professional.setFirstName("Guillermo");
		professional.setLastName("Díaz");
		professional.setEmail("mancar@gmail.com");
		professional.setDocument("29334485");
		professional.setDocumentType(DocumentType.NIF);
		professional.setCollegiateNumber("413123122-K");
		professional.setUser(user2);
		
		appointment = new Appointment();
		appointment.setId(TEST_APPOINTMENT_ID);
		appointment.setDate(LocalDate.parse(par, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		appointment.setReason("my head hurts");
		appointment.setStartTime(LocalTime.of(10, 15, 00));
		appointment.setBill(null);
		appointment.setDiagnosis(null);
		appointment.setClient(client);
		appointment.setType(AppointmentType.PERIODIC_CONSULTATION);
		appointment.setCenter(center);
		appointment.setSpecialty(specialty);
		appointment.setProfessional(professional);
		appointment.setStatus(AppointmentStatus.PENDING);
		
		bill = new Bill();
		bill.setId(TEST_BILL_ID);
		bill.setCreatedAt(LocalDateTime.now());
		bill.setPrice(50.0);
		bill.setDocument(client.getDocument());
		bill.setDocumentType(client.getDocumentType());
		bill.setHealthInsurance(HealthInsurance.I_DO_NOT_HAVE_INSURANCE);
		bill.setIva(21.0);
		bill.setAppointment(appointment);
		bill.setTransactions(new HashSet<Transaction>());
		
		transaction = new Transaction();
		transaction.setId(TEST_TRANSACTION_ID);
		transaction.setBill(bill);
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setStatus("succeeded");
		transaction.setSuccess(true);
		transaction.setAmount(bill.getFinalPrice());
		transaction.setToken("CASH");
		
		
		List<Client> clientList = new ArrayList<Client>();
		clientList.add(client);
		Collection<Client> resultadoBuscarClientes = clientList;
		
		List<Professional> professionalList = new ArrayList<Professional>();
		professionalList.add(professional);
		Iterable<Professional> resultadoBuscarProfessionales = professionalList;
		
		List<Appointment> appointmentList = new ArrayList<Appointment>();
		appointmentList.add(appointment);
		Iterable<Appointment> resultadoBuscarAppointments = appointmentList;
		
		List<Bill> billList = new ArrayList<Bill>();
		billList.add(bill);
		Iterable<Bill> resultadoBuscarBills = billList;
		
		
		given(this.clientService.findClientById(TEST_CLIENT_ID)).willReturn(Optional.of(client));
		given(this.clientService.findAll()).willReturn(resultadoBuscarClientes);
		doNothing().when(this.clientService).deleteById(TEST_CLIENT_ID);
		
		given(this.professionalService.findById(TEST_CLIENT_ID)).willReturn(Optional.of(professional));
		given(this.professionalService.findAll()).willReturn(resultadoBuscarProfessionales);
		doNothing().when(this.professionalService).deleteById(TEST_PROFESSIONAL_ID);
		
		given(this.appointmentService.findAppointmentById(TEST_APPOINTMENT_ID)).willReturn(Optional.of(appointment));
		given(this.appointmentService.listAppointments()).willReturn(resultadoBuscarAppointments);
		doNothing().when(this.appointmentService).delete(Mockito.mock(Appointment.class));
		
		given(this.billService.findById(TEST_APPOINTMENT_ID)).willReturn(Optional.of(bill));
		given(this.billService.findAll()).willReturn(resultadoBuscarBills);
		
		given(this.transactionService.findById(TEST_TRANSACTION_ID)).willReturn(Optional.of(transaction));
		
		given(this.centerService.findCenterById(TEST_CENTER_ID)).willReturn(Optional.of(center));
		
		given(this.specialtyService.findSpecialtyById(TEST_SPECIALTY_ID)).willReturn(Optional.of(specialty));

	}

	@WithMockUser(value = "admin")
	@Test
	void testShowDashboard() throws Exception {
		this.mockMvc.perform(get("/admin"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/dashboard"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testClientList() throws Exception {
		this.mockMvc.perform(get("/admin/clients"))
				.andExpect(model().attributeExists("clients"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/list"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testClientDetail() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}", TEST_CLIENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("admin/clients/detail"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testClientDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}", 2))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}

	
	@WithMockUser(value = "admin")
	@Test
	void testClientEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}/edit", TEST_CLIENT_ID))
				.andExpect(model().attributeExists("client"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/form"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessClientEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/edit", TEST_CLIENT_ID)
						.with(csrf())
						.param("firstName", client.getFirstName())
						.param("lastName", client.getLastName())
						.param("birthDate", "1999-05-05")
						.param("document", client.getDocument())
						.param("documentType", client.getDocumentType().name())
						.param("healthInsurance", client.getHealthInsurance().name())
						.param("healthCardNumber", client.getHealthCardNumber())
						.param("email", client.getEmail())
						.param("user.username", client.getUser().getUsername())
						.param("user.password", client.getUser().getPassword()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients/" + TEST_CLIENT_ID));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessClientEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/edit", TEST_CLIENT_ID)
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", client.getDocumentType().name())
						.param("healthInsurance", client.getHealthInsurance().name())
						.param("healthCardNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", 
						"firstName", "lastName", "document", "healthCardNumber", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/clients/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testClientCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/clients/create"))
				.andExpect(model().attributeExists("client"))
				.andExpect(status().isOk())
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/form"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessClientCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/clients/create")
						.with(csrf())
						.param("firstName", "Manuel")
						.param("lastName", "Lopera")
						.param("birthDate", "1999-05-05")
						.param("document", "36956285F")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.I_DO_NOT_HAVE_INSURANCE.name())
						.param("healthCardNumber", "")
						.param("email", "manuel@lopera.com")
						.param("user.username", "manolo")
						.param("user.password", "loperaaa"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessClientCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/clients/create")
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.I_DO_NOT_HAVE_INSURANCE.name())
						.param("healthCardNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", 
						"firstName", "lastName", "document", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/clients/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testClientDelete() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/delete", TEST_CLIENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients"));
	}
	
	
	/**
	 * Professionals
	 */
	
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalList() throws Exception {
		this.mockMvc.perform(get("/admin/professionals"))
				.andExpect(model().attributeExists("professionals"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/list"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalDetail() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}", TEST_PROFESSIONAL_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("professional"))
				.andExpect(view().name("admin/professionals/detail"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}", 2))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID))
				.andExpect(model().attributeExists("professional"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalEditFormNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}/edit", 2))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessProfessionalEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID)
						.with(csrf())
						.param("firstName", professional.getFirstName())
						.param("lastName", professional.getLastName())
						.param("birthDate", "1999-05-05")
						.param("document", professional.getDocument())
						.param("documentType", professional.getDocumentType().name())
						.param("collegiateNumber", professional.getCollegiateNumber())
						.param("email", professional.getEmail())
						.param("user.username", professional.getUser().getUsername())
						.param("user.password", professional.getUser().getPassword())
						.param("center.id", "1")
						.param("specialty.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals/" + TEST_PROFESSIONAL_ID));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessProfessionalEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID)
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", professional.getDocumentType().name())
						.param("collegiateNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("professional"))
				.andExpect(model().attributeHasFieldErrors("professional", 
						"firstName", "lastName", "document", "collegiateNumber", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/create"))
				.andExpect(model().attributeExists("professional"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/form"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessProfessionalCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/create")
						.with(csrf())
						.param("firstName", "Julio")
						.param("lastName", "Lopera")
						.param("birthDate", "1999-05-05")
						.param("document", "95264523F")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "98565626K")
						.param("email", "julio@lopera.com")
						.param("user.username", "julio")
						.param("user.password", "loperaaa")
						.param("center.id", "1")
						.param("specialty.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessProfessionalCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/create")
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("professional"))
				.andExpect(model().attributeHasFieldErrors("professional", 
						"firstName", "lastName", "document", "collegiateNumber", "email", "user.username", "user.password", "center", "specialty"))
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProfessionalDelete() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/delete", TEST_PROFESSIONAL_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals"));
	}
	
	
	/**
	 * Appointments
	 */
	
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentList() throws Exception {
		this.mockMvc.perform(get("/admin/appointments"))
				.andExpect(model().attributeExists("appointments"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/list"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentDetail() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}", TEST_APPOINTMENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("appointment"))
				.andExpect(view().name("admin/appointments/detail"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}", 2))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID))
				.andExpect(model().attributeExists("appointment"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/form"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessAppointmentEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("date", "06/06/2020")
						.param("startTime", "08:00:00")
						.param("client.id", "1")
						.param("professional.id", "1")
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("reason", "test")
						.param("status", AppointmentStatus.COMPLETED.name()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments/" + TEST_APPOINTMENT_ID));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessAppointmentEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("date", "")
						.param("startTime", "")
						.param("client.id", "")
						.param("professional.id", "")
						.param("center.id", "")
						.param("specialty.id", "")
						.param("reason", "")
						.param("status",  AppointmentStatus.PENDING.name()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"date", "startTime", "client", "professional", "center", "specialty", "reason"))
				.andExpect(view().name("admin/appointments/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/create"))
				.andExpect(model().attributeExists("appointment"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/form"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessAppointmentCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/create")
						.with(csrf())
						.param("date", "06/06/2020")
						.param("startTime", "09:00:00")
						.param("client.id", "1")
						.param("professional.id", "1")
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("reason", "test")
						.param("status", AppointmentStatus.PENDING.name()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessAppointmentCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/create")
						.with(csrf())
						.param("date", "")
						.param("startTime", "")
						.param("client.id", "")
						.param("professional.id", "")
						.param("center.id", "")
						.param("specialty.id", "")
						.param("reason", "")
						.param("status", AppointmentStatus.PENDING.name()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"date", "startTime", "client", "professional", "center", "specialty", "reason"))
				.andExpect(view().name("admin/appointments/form"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testAppointmentDelete() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/delete", TEST_APPOINTMENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments"));
	}
	
	
	/**
	 * Bills
	 */
	
	
	@WithMockUser(value = "admin")
	@Test
	void testBillList() throws Exception {
		this.mockMvc.perform(get("/admin/bills"))
				.andExpect(model().attributeExists("bills"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/bills/list"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testBillDetail() throws Exception {
		this.mockMvc.perform(get("/admin/bills/{billId}", TEST_BILL_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("bill"))
				.andExpect(view().name("admin/bills/detail"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testBillChargeForm() throws Exception {
		this.mockMvc.perform(get("/admin/bills/{billId}/charge", TEST_BILL_ID))
				.andExpect(model().attributeExists("bill"))
				.andExpect(model().attributeExists("transaction"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/bills/chargeForm"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testProcessBillChargeFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/charge", TEST_BILL_ID)
						.with(csrf())
						.param("amount", "50")
						.param("paymentMethod.token", "CASH"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/bills/" + TEST_BILL_ID));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testProcessBillChargeFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/charge", TEST_BILL_ID)
						.with(csrf())
						.param("amount", "100000")
						.param("paymentMethod.token", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("transaction"))
				.andExpect(model().attributeHasFieldErrors("transaction", 
						"amount", "paymentMethod.token"))
				.andExpect(view().name("admin/bills/chargeForm"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testBillRefund() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/refund/{transactionId}", TEST_BILL_ID, TEST_TRANSACTION_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/bills/" + TEST_BILL_ID));
	}

}
