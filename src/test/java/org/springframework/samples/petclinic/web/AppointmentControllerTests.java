
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Receipt;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.DeseaseService;
import org.springframework.samples.petclinic.service.MedicineService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AppointmentControllerTests {

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@MockBean
	private MedicineService		medicineService;

	@MockBean
	private DeseaseService		deseaseService;

	@MockBean
	private ClientService		clientService;

	@MockBean
	private CenterService		centerService;

	@MockBean
	private ProfessionalService	professionalService;

	@MockBean
	private SpecialtyService	specialtyService;

	@MockBean
	private StripeService		stripeService;

	@Autowired
	private MockMvc				mockMvc;

	private Appointment			appointment;

	private Professional		professional;

	private Medicine			medicine;

	private AppointmentType		type;

	private Client				client;

	private User				user;

	private Specialty			specialty;

	private Desease				desease;

	private PaymentMethod		paymentMethod;

	private static final int	TEST_APPOINTMENT_ID	= 1;


	@BeforeEach
	void setup() throws Exception {

		this.appointment = new Appointment();
		this.appointment.setId(1);
		String par = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.appointment.setDate(LocalDate.parse(par, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		this.appointment.setReason("my head hurts");
		this.appointment.setStartTime(LocalTime.of(10, 15, 00));

		Receipt receipt = new Receipt();
		receipt.setPrice(10.);

		Diagnosis diagnosis = new Diagnosis();
		this.desease = new Desease();
		this.desease.setId(1);
		this.desease.setName("Acné");
		this.medicine = new Medicine();
		this.medicine.setId(1);
		this.medicine.setName("paracetamol");
		this.medicine.setPrice(10.);
		Set<Desease> deseases = new HashSet<Desease>();
		Set<Medicine> medicines = new HashSet<>();
		deseases.add(this.desease);
		medicines.add(this.medicine);
		diagnosis.setId(1);
		diagnosis.setDeseases(deseases);
		diagnosis.setMedicines(medicines);
		diagnosis.setDate(LocalDate.of(2020, 05, 9));
		diagnosis.setDescription("test");

		this.appointment.setReceipt(null);
		this.appointment.setDiagnosis(null);

		this.client = new Client();
		this.client.setId(1);
		this.client.setDocument("29334456");
		this.client.setDocumentType(DocumentType.nif);
		this.client.setEmail("frankcuesta@gmail.com");
		this.client.setFirstName("Frank");
		this.client.setHealthCardNumber("0000000003");
		this.client.setHealthInsurance("Adeslas");
		this.client.setLastName("Cuesta");
		Set<PaymentMethod> paymentMethods = new HashSet<>();
		this.paymentMethod = new PaymentMethod();
		this.paymentMethod.setId(1);
		this.paymentMethod.setBrand("efectivo");
		this.paymentMethod.setClient(this.appointment.getClient());
		this.paymentMethod.setToken("efective_token");
		paymentMethods.add(this.paymentMethod);
		this.client.setPaymentMethods(paymentMethods);
		this.client.setStripeId("1");
		Date registrationDate = new Date(03 / 03 / 2020);
		this.client.setRegistrationDate(registrationDate);

		Set<AppointmentType> types = Collections.emptySet();

		this.user = new User();
		this.user.setEnabled(true);
		this.user.setUsername("frankcuesta");
		this.user.setPassword("frankcuesta");
		this.client.setUser(this.user);
		this.appointment.setClient(this.client);

		AppointmentType appointmentType = new AppointmentType();
		appointmentType.setName("revision");
		this.appointment.setType(appointmentType);

		Center center = new Center();
		center.setId(1);
		center.setAddress("Sevilla");
		this.appointment.setCenter(center);

		this.specialty = new Specialty();
		this.specialty.setId(AppointmentControllerTests.TEST_APPOINTMENT_ID);
		this.specialty.setName("dermatology");
		this.appointment.setSpecialty(this.specialty);

		this.professional = new Professional();
		this.professional.setId(AppointmentControllerTests.TEST_APPOINTMENT_ID);
		this.professional.setCenter(center);
		this.professional.setSpecialty(this.specialty);
		this.professional.setFirstName("Guillermo");
		this.professional.setLastName("Díaz");
		this.professional.setEmail("mancar@gmail.com");
		this.professional.setDocument("29334485");
		this.professional.setDocumentType(DocumentType.nif);
		this.professional.setCollegiateNumber("413123122-K");

		User user2 = new User();
		this.user.setEnabled(true);
		this.user.setUsername("manucar");
		this.user.setPassword("manucar");
		this.professional.setUser(user2);
		this.appointment.setProfessional(this.professional);

		//String nombre = Mockito.mock(String.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

		BDDMockito.given(this.clientService.findClientByUsername("client")).willReturn(this.client);
		BDDMockito.given(this.professionalService.findProByUsername("professional")).willReturn(this.professional);

		BDDMockito.given(this.appointmentService.findAppointmentByUserId(this.client.getId())).willReturn(Lists.newArrayList(this.appointment));
		BDDMockito.given(this.appointmentService.findTodayPendingByProfessionalId(this.professional.getId())).willReturn(Lists.newArrayList(this.appointment));
		BDDMockito.given(this.appointmentService.findTodayCompletedByProfessionalId(this.professional.getId())).willReturn(Lists.newArrayList(this.appointment));

		BDDMockito.given(this.appointmentService.findAppointmentById(this.appointment.getId())).willReturn(new Appointment());

		BDDMockito.given(this.medicineService.findMedicines()).willReturn(Lists.newArrayList(this.medicine));

		BDDMockito.given(this.deseaseService.findAll()).willReturn(Lists.newArrayList(this.desease));

		BDDMockito.given(this.deseaseService.findAll()).willReturn(Lists.newArrayList(this.desease));

		BDDMockito.given(this.stripeService.retrievePaymentMethod(paymentMethods.stream().collect(Collectors.toList()).get(0).getToken())).willReturn(new com.stripe.model.PaymentMethod());
		//BDDMockito.given(this.appointmentService.findTodayPendingByProfessionalId(AppointmentControllerTest.TEST_APPOINTMENT_ID)).willReturn(Lists.newArrayList(this.app, new Appointment()));

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/new").param("Date", dia).param("reason", "my head hurts").with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456")
			.param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas")
			.param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp)
			.param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com")
			.param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("startTime", hora).param("client.document", "29334456").param("reason", "my head hurts")
				.param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas")
				.param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("center.address", "Sevilla")
				.param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com")
				.param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "specialty")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(value = "client")
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("appointments/list"));
	}

	@WithMockUser(value = "professional")
	@Test
	void testListAppointmentsProfessional() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/pro")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMarkAbsent() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		Set<Appointment> s = new HashSet<>();

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/absent", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
				.param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank")
				.param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta")
				.param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel")
				.param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "manucar", authorities = {
		"professional"
	})
	@Test
	void testShowVisits() throws Exception {
		String dia = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String dia2 = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();

		Medicine med = new Medicine();
		med.setName("paracetamol");
		med.setPrice(12.);
		String medName = med.getName();
		String medPrice = Double.toString(med.getPrice());
		Collection<Desease> deseases = new ArrayList<>();
		Desease des = new Desease();
		des.setName("Acné");
		deseases.add(des);
		String desName = des.getName();

		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com")
			.param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
			.param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("client.paymentMethod.token", "pm_1Ggr7GDfDQNZdQMbCcCoxzEI'").param("client.paymentMethod.brand", "visa").param("center.address", "Sevilla")
			.param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco")
			.param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K").param("diagnosis.Date", dia2)
			.param("diagnosis.description", "healthy").param("diagnosis.medicine.name", medName).param("diagnosis.medicine.price", medPrice).param("diagnosis.desease.name", desName).param("receipt.price", "10")
			.param("status", AppointmentStatus.COMPLETED.toString())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());//.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAppFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String dia2 = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();

		Medicine med = new Medicine();
		med.setName("paracetamol");
		med.setPrice(12.);
		String medName = med.getName();
		String medPrice = Double.toString(med.getPrice());
		Collection<Desease> deseases = new ArrayList<>();
		Desease des = new Desease();
		des.setName("Acné");
		deseases.add(des);
		String desName = des.getName();

		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com")
			.param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
			.param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("client.paymentMethod.token", "pm_1Ggr7GDfDQNZdQMbCcCoxzEI'").param("client.paymentMethod.brand", "visa").param("center.address", "Sevilla")
			.param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco")
			.param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K").param("diagnosis.Date", dia2)
			.param("diagnosis.description", "healthy").param("diagnosis.medicine.name", medName).param("diagnosis.medicine.price", medPrice).param("diagnosis.desease.name", desName).param("receipt.price", "10")
			.param("status", AppointmentStatus.COMPLETED.toString())).andExpect(MockMvcResultMatchers.status().is3xxRedirection());//.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAppFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String dia2 = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();

		Medicine med = new Medicine();
		med.setName("paracetamol");
		med.setPrice(12.);
		String medName = med.getName();
		String medPrice = Double.toString(med.getPrice());
		Collection<Desease> deseases = new ArrayList<>();
		Desease des = new Desease();
		des.setName("Acné");
		deseases.add(des);
		String desName = des.getName();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com")
				.param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
				.param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("client.paymentMethod.token", "pm_1Ggr7GDfDQNZdQMbCcCoxzEI'").param("client.paymentMethod.brand", "visa").param("center.address", "Sevilla")
				.param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com")
				.param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K").param("diagnosis.Date", dia2).param("diagnosis.description", "healthy")
				.param("diagnosis.medicine.name", medName).param("diagnosis.medicine.price", medPrice).param("diagnosis.desease.name", desName).param("receipt.price", "10").param("status", AppointmentStatus.COMPLETED.toString()))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "specialty"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
	}

	@WithMockUser(username = "frankcuesta", authorities = {
		"client"
	})
	@Test
	void shouldShowAppointmentDetails() throws Exception {
		this.specialty.setId(1);
		this.specialty.setName("dermatology");
		this.user.setUsername("frankcuesta");
		this.client.setUser(this.user);
		this.appointment.setSpecialty(this.specialty);
		Mockito.when(this.appointmentService.findAppointmentById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).thenReturn(this.appointment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/details", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("appointments/details"));
	}

	@WithMockUser(username = "pepegotera", authorities = {
		"client"
	})
	@Test
	void shouldNotShowAppointmentDetails() throws Exception {
		this.specialty.setId(1);
		this.specialty.setName("dermatology");
		this.user.setUsername("frankcuesta");
		this.client.setUser(this.user);
		this.appointment.setSpecialty(this.specialty);
		Mockito.when(this.appointmentService.findAppointmentById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).thenReturn(this.appointment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/details", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("message", "You cannot show another user's appointment")).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "frankcuesta", authorities = {
		"client"
	})
	@Test
	void shouldDeleteAppointment() throws Exception {
		this.user.setUsername("frankcuesta");
		this.client.setUser(this.user);
		Mockito.when(this.appointmentService.findAppointmentById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).thenReturn(this.appointment);
		Mockito.doNothing().when(this.appointmentService).delete(this.appointment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/delete/{appointmentId}", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments"));
	}

	@WithMockUser(username = "pepegotera", authorities = {
		"client"
	})
	@Test
	void shouldNotDeleteAppointmentOfOtherUser() throws Exception {
		this.user.setUsername("frankcuesta");
		this.client.setUser(this.user);
		Mockito.when(this.appointmentService.findAppointmentById(AppointmentControllerTests.TEST_APPOINTMENT_ID)).thenReturn(this.appointment);
		Mockito.doNothing().when(this.appointmentService).delete(this.appointment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/delete/{appointmentId}", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("message", "You cannot delete another user's appointment")).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

}
