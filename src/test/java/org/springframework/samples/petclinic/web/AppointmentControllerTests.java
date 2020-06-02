
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AppointmentControllerTests {

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private MedicineService medicineService;

	@MockBean
	private DeseaseService deseaseService;

	@MockBean
	private ClientService clientService;

	@MockBean
	private CenterService centerService;

	@MockBean
	private ProfessionalService professionalService;

	@MockBean
	private SpecialtyService specialtyService;

	@MockBean
	private StripeService stripeService;
	
	@MockBean
	private BillService billService;

	@Autowired
	private MockMvc mockMvc;

	private Appointment appointment;

	private Professional professional;

	private Medicine medicine;

	private Client client;

	private User user;

	private Specialty specialty;

	private Desease desease;

	private PaymentMethod paymentMethod;

	private static final int TEST_APPOINTMENT_ID = 1;
	
	private static final int TEST_PROFESSIONAL_ID = 1;
	
	private static final int TEST_SPECIALTY_ID = 1;	
	
	private static final int TEST_CENTER_ID = 1;

	@BeforeEach
	void setup() throws Exception {
		String par = LocalDate.of(2020, 05, 9).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");

		User user2 = new User();
		user2.setEnabled(true);
		user2.setUsername("manucar");
		user2.setPassword("manucar");

		Date registrationDate = new Date(03 / 03 / 2020);
		client = new Client();
		client.setId(1);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.NIF);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance(HealthInsurance.ADESLAS);
		client.setLastName("Cuesta");
		client.setStripeId("1");
		client.setUser(this.user);
		client.setRegistrationDate(registrationDate);

		paymentMethod = new PaymentMethod();
		paymentMethod.setId(1);
		paymentMethod.setBrand("efectivo");
		paymentMethod.setClient(client);
		paymentMethod.setToken("efective_token");

		Set<PaymentMethod> paymentMethods = new HashSet<>();
		paymentMethods.add(this.paymentMethod);
		client.setPaymentMethods(paymentMethods);

		Bill bill = new Bill();
		bill.setPrice(10.);

		desease = new Desease();
		desease.setId(1);
		desease.setName("Acné");

		medicine = new Medicine();
		medicine.setId(1);
		medicine.setName("paracetamol");
		medicine.setPrice(10.);

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

		Center center = new Center();
		center.setId(TEST_CENTER_ID);
		center.setAddress("Sevilla");

		specialty = new Specialty();
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

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
		
		given(this.clientService.findClientByUsername("frankcuesta")).willReturn(this.client);
		
		given(this.professionalService.findProByUsername("professional")).willReturn(this.professional);
		given(this.professionalService.findById(TEST_PROFESSIONAL_ID)).willReturn(Optional.of(this.professional));
		
		given(this.appointmentService.findAppointmentByUserId(this.client.getId()))
				.willReturn(Lists.newArrayList(this.appointment));
		given(this.appointmentService.findTodayPendingByProfessionalId(this.professional.getId()))
				.willReturn(Lists.newArrayList(this.appointment));
		given(this.appointmentService.findTodayCompletedByProfessionalId(this.professional.getId()))
				.willReturn(Lists.newArrayList(this.appointment));
		given(this.appointmentService.findAppointmentById(this.appointment.getId())).willReturn(Optional.of(appointment));
		given(this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(appointment.getDate(), appointment.getProfessional())).willReturn(Lists.newArrayList(appointment.getStartTime()));
		
		given(this.medicineService.findMedicines()).willReturn(Lists.newArrayList(this.medicine));
		
		given(this.deseaseService.findAll()).willReturn(Lists.newArrayList(this.desease));

		
		given(this.centerService.findCenterById(TEST_CENTER_ID)).willReturn(Optional.of(center));
		
		given(this.specialtyService.findSpecialtyById(TEST_SPECIALTY_ID)).willReturn(Optional.of(specialty));
		
	}
	
	@WithMockUser(value = "frankcuesta")
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(get("/appointments"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("appointments"))
				.andExpect(view().name("appointments/list"));
	}
	
	@WithMockUser(value = "professional")
	@Test
	void testListAppointmentsProfessional() throws Exception {

		this.mockMvc.perform(get("/appointments/pro"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("nextAppointment"))
				.andExpect(model().attributeExists("pendingAppointments"))
				.andExpect(model().attributeExists("completedAppointments"))
				.andExpect(view().name("appointments/pro"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {

		this.mockMvc.perform(get("/appointments/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("appointment"))
				.andExpect(view().name("appointments/new"));
	}

	@WithMockUser(value = "frankcuesta")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		this.mockMvc.perform(post("/appointments/new")
						.with(csrf())
						.param("date", dia)
						.param("reason", "my head hurts")
						.param("startTime", hora)
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("professional.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		this.mockMvc.perform(post("/appointments/new")
						.with(csrf())
						.param("date", dia)
						.param("startTime", hora)
						.param("reason", "my head hurts")
						.param("center.id", "")
						.param("professional.id", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", "center", "professional", "specialty"))
				.andExpect(view().name("appointments/new"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testMarkAbsent() throws Exception {
		this.mockMvc
				.perform(post("/appointments/{appointmentId}/absent", TEST_APPOINTMENT_ID)
						.with(csrf()))					
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "manucar")
	@Test
	void testAppointmentConsultationForm() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/consultation", TEST_APPOINTMENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("medicineList", "appointment", "deseaseList"))
				.andExpect(view().name("appointments/consultationPro"));

	}
	
	@WithMockUser(username = "manucar")
	@Test
	void testAppointmentConsultationFormCompletedRedirect() throws Exception {
		this.appointment.setStatus(AppointmentStatus.COMPLETED);
		
		this.mockMvc.perform(get("/appointments/{appointmentId}/consultation", TEST_APPOINTMENT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation",TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("diagnosis.description", "healthy")
						.param("diagnosis.medicines.id", "1")
						.param("diagnosis.deseases.id", "1")
						.param("bill.price", "10")
						.param("bill.iva", "21"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation",TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("bill.price", "-10")
						.param("bill.iva", "500"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"diagnosis.description", "diagnosis.medicines", "diagnosis.deseases", "bill.price", "bill.iva"))
				.andExpect(view().name("appointments/consultationPro"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessAppointmentConsultationFormCompletedRedirect() throws Exception {
		appointment.setStatus(AppointmentStatus.COMPLETED);
		
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation",TEST_APPOINTMENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "frankcuesta")
	@Test
	void testShouldShowAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/details", TEST_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("appointments/details"));
	}

	@WithMockUser(username = "pepegotera")
	@Test
	void testShouldNotShowAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/details", TEST_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "You cannot show another user's appointment"))
				.andExpect(view().name("errors/generic"));
	}

	@WithMockUser(username = "frankcuesta")
	@Test
	void testShouldDeleteAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/delete/{appointmentId}", TEST_APPOINTMENT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments"));
	}

	@WithMockUser(username = "pepegotera")
	@Test
	void testShouldNotDeleteAppointmentOfOtherUser() throws Exception {
		this.mockMvc.perform(get("/appointments/delete/{appointmentId}", TEST_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "You cannot delete another user's appointment"))
				.andExpect(view().name("errors/generic"));
	}
	
	@WithMockUser(username = "spring")
	@Test
	void testBusyStartTimes() throws Exception {
		this.mockMvc.perform(get("/appointments/busy")
							.queryParam("date", "09/05/2020")
							.queryParam("professionalId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]", Matchers.is("10:15:00")));
	}

}
