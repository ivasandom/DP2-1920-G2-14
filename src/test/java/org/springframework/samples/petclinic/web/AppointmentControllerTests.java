
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
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
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
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
	private AppointmentService		appointmentService;

	@MockBean
	private AuthoritiesService		authoritiesService;

	@MockBean
	private MedicineService			medicineService;

	@MockBean
	private DeseaseService			deseaseService;

	@MockBean
	private ClientService			clientService;

	@MockBean
	private CenterService			centerService;

	@MockBean
	private ProfessionalService		professionalService;

	@MockBean
	private SpecialtyService		specialtyService;

	@MockBean
	private StripeService			stripeService;

	@MockBean
	private BillService				billService;

	@Autowired
	private MockMvc					mockMvc;

	private Appointment				appointment;

	private ListAppointmentsClient	listAppointmentsClient;

	private Professional			professional;

	private Medicine				medicine;

	private Client					client;

	private Center					center;

	private User					user;

	private Specialty				specialty;

	private Desease					desease;

	private PaymentMethod			paymentMethod;

	private static final int		TEST_APPOINTMENT_ID		= 1;

	private static final int		TEST_PROFESSIONAL_ID	= 1;

	private static final int		TEST_SPECIALTY_ID		= 1;

	private static final int		TEST_CENTER_ID			= 1;


	@BeforeEach
	void setup() throws Exception {

		this.inicializar();

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

		BDDMockito.given(this.clientService.findClientByUsername("frankcuesta")).willReturn(this.client);

		BDDMockito.given(this.professionalService.findProByUsername("professional")).willReturn(this.professional);
		BDDMockito.given(this.professionalService.findById(AppointmentControllerTests.TEST_PROFESSIONAL_ID)).willReturn(Optional.of(this.professional));

		BDDMockito.given(this.appointmentService.findAppointmentByUserId(this.client.getId())).willReturn(Lists.newArrayList(this.listAppointmentsClient));
		BDDMockito.given(this.appointmentService.findTodayPendingByProfessionalId(this.professional.getId())).willReturn(Lists.newArrayList(this.appointment));
		BDDMockito.given(this.appointmentService.findTodayCompletedByProfessionalId(this.professional.getId())).willReturn(Lists.newArrayList(this.appointment));
		BDDMockito.given(this.appointmentService.findAppointmentById(this.appointment.getId())).willReturn(Optional.of(this.appointment));
		BDDMockito.given(this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(this.appointment.getDate(), this.appointment.getProfessional())).willReturn(Lists.newArrayList(this.appointment.getStartTime()));

		BDDMockito.given(this.medicineService.findMedicines()).willReturn(Lists.newArrayList(this.medicine));

		BDDMockito.given(this.deseaseService.findAll()).willReturn(Lists.newArrayList(this.desease));

		BDDMockito.given(this.centerService.findCenterById(AppointmentControllerTests.TEST_CENTER_ID)).willReturn(Optional.of(this.center));

		BDDMockito.given(this.specialtyService.findSpecialtyById(AppointmentControllerTests.TEST_SPECIALTY_ID)).willReturn(Optional.of(this.specialty));

	}

	@WithMockUser(value = "frankcuesta")
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/list"));
	}

	@WithMockUser(value = "professional")
	@Test
	void testListAppointmentsProfessional() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/pro")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("nextAppointment"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("pendingAppointments")).andExpect(MockMvcResultMatchers.model().attributeExists("completedAppointments")).andExpect(MockMvcResultMatchers.view().name("appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(value = "frankcuesta")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("date", dia).param("reason", "my head hurts").param("startTime", hora).param("center.id", "1").param("specialty.id", "1")
			.param("professional.id", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("date", dia).param("startTime", hora).param("reason", "my head hurts").param("center.id", "").param("professional.id", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "center", "professional", "specialty"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMarkAbsent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/absent", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "manucar")
	@Test
	void testAppointmentConsultationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.model().attributeExists("medicineList", "appointment", "deseaseList")).andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));

	}

	@WithMockUser(username = "manucar")
	@Test
	void testAppointmentConsultationFormCompletedRedirect() throws Exception {
		this.appointment.setStatus(AppointmentStatus.COMPLETED);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("diagnosis.description", "healthy")
				.param("diagnosis.medicines.id", "1").param("diagnosis.deseases.id", "1").param("bill.price", "10").param("bill.iva", "21"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("bill.price", "-10").param("bill.iva", "500"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "diagnosis.description", "diagnosis.medicines", "diagnosis.deseases", "bill.price", "bill.iva"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormCompletedRedirect() throws Exception {
		this.appointment.setStatus(AppointmentStatus.COMPLETED);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "frankcuesta")
	@Test
	void testShouldShowAppointment() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/details", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("appointments/details"));
	}

	@WithMockUser(username = "pepegotera")
	@Test
	void testShouldNotShowAppointment() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/details", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("message", "You cannot show another user's appointment")).andExpect(MockMvcResultMatchers.view().name("errors/generic"));
	}

	@WithMockUser(username = "frankcuesta")
	@Test
	void testShouldDeleteAppointment() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/delete/{appointmentId}", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments"));
	}

	@WithMockUser(username = "pepegotera")
	@Test
	void testShouldNotDeleteAppointmentOfOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/delete/{appointmentId}", AppointmentControllerTests.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("message", "You cannot delete another user's appointment")).andExpect(MockMvcResultMatchers.view().name("errors/generic"));
	}

	@WithMockUser(username = "spring")
	@Test
	void testBusyStartTimes() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/busy").queryParam("date", "09/05/2020").queryParam("professionalId", "1")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("10:15:00")));
	}

	public void inicializar() {
		String par = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		this.user = new User();
		this.user.setEnabled(true);
		this.user.setUsername("frankcuesta");
		this.user.setPassword("frankcuesta");

		User user2 = new User();
		user2.setEnabled(true);
		user2.setUsername("manucar");
		user2.setPassword("manucar");

		Date registrationDate = new Date(03 / 03 / 2020);
		this.client = new Client();
		this.client.setId(1);
		this.client.setDocument("29334456");
		this.client.setDocumentType(DocumentType.NIF);
		this.client.setEmail("frankcuesta@gmail.com");
		this.client.setFirstName("Frank");
		this.client.setHealthCardNumber("0000000003");
		this.client.setHealthInsurance(HealthInsurance.ADESLAS);
		this.client.setLastName("Cuesta");
		this.client.setStripeId("1");
		this.client.setUser(this.user);
		this.client.setRegistrationDate(registrationDate);

		this.paymentMethod = new PaymentMethod();
		this.paymentMethod.setId(1);
		this.paymentMethod.setBrand("efectivo");
		this.paymentMethod.setClient(this.client);
		this.paymentMethod.setToken("efective_token");

		Set<PaymentMethod> paymentMethods = new HashSet<>();
		paymentMethods.add(this.paymentMethod);
		this.client.setPaymentMethods(paymentMethods);

		Bill bill = new Bill();
		bill.setPrice(10.);

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

		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setId(1);
		diagnosis.setDeseases(deseases);
		diagnosis.setMedicines(medicines);
		diagnosis.setDate(LocalDate.of(2020, 05, 9));
		diagnosis.setDescription("test");

		this.center = new Center();
		this.center.setId(AppointmentControllerTests.TEST_CENTER_ID);
		this.center.setAddress("Sevilla");

		this.specialty = new Specialty();
		this.specialty.setId(AppointmentControllerTests.TEST_SPECIALTY_ID);
		this.specialty.setName("dermatology");

		this.professional = new Professional();
		this.professional.setId(AppointmentControllerTests.TEST_PROFESSIONAL_ID);
		this.professional.setCenter(this.center);
		this.professional.setSpecialty(this.specialty);
		this.professional.setFirstName("Guillermo");
		this.professional.setLastName("Díaz");
		this.professional.setEmail("mancar@gmail.com");
		this.professional.setDocument("29334485");
		this.professional.setDocumentType(DocumentType.NIF);
		this.professional.setCollegiateNumber("413123122-K");
		this.professional.setUser(user2);

		this.appointment = new Appointment();
		this.appointment.setId(AppointmentControllerTests.TEST_APPOINTMENT_ID);
		this.appointment.setDate(LocalDate.parse(par, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		this.appointment.setReason("my head hurts");
		this.appointment.setStartTime(LocalTime.of(10, 15, 00));
		this.appointment.setBill(null);
		this.appointment.setDiagnosis(null);
		this.appointment.setClient(this.client);
		this.appointment.setType(AppointmentType.PERIODIC_CONSULTATION);
		this.appointment.setCenter(this.center);
		this.appointment.setSpecialty(this.specialty);
		this.appointment.setProfessional(this.professional);
	}
}
