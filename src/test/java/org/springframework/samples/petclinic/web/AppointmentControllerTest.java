
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.AppointmentType;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.Professional;
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
public class AppointmentControllerTest {

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

	@Autowired
	private MockMvc				mockMvc;

	private Appointment			app;

	private Professional		professional;

	private Medicine			medicine;

	private AppointmentType		type;

	private Desease				desease;

	private static final int	TEST_APPOINTMENT_ID	= 1;


	@BeforeEach
	void setup() {

		//		this.app = new Appointment();
		//		Professional professional = this.professionalService.findProByUsername("professional1");
		//		Center center = this.centerService.findCenterByAddress("Sevilla");
		//		Client client = new Client();
		//		client.setId(4);
		//		client.set
		//			Specialty specialty = this.specialtyService.findSpecialtyByName("dermatology");
		//		this.app.setProfessional(professional);
		//		this.app.setCenter(center);
		//		this.app.setClient(client);
		//		this.app.setId(AppointmentControllerTest.TEST_APPOINTMENT_ID);
		//		this.app.setDate(LocalDate.of(2020, 03, 11));
		//		this.app.setSpecialty(specialty);
		//		this.app.setStartTime(LocalTime.of(10, 15));
		//		this.app.setReason("headache");
		this.app = new Appointment();
		this.app.setId(1);
		String par = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.app.setDate(LocalDate.parse(par, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		this.app.setReason("my head hurts");
		this.app.setStartTime(LocalTime.of(10, 15, 00));

		this.app.setReceipt(null);
		this.app.setDiagnosis(null);
		this.app.setClient(null);

		Client client = new Client();
		client.setId(1);
		//		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		//		client.setBirthDate(birthdate);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.nif);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance("Adeslas");
		client.setLastName("Cuesta");
		//		Date registrationDate = new Date(03 / 03 / 2020);
		//		client.setRegistrationDate(registrationDate);

		Set<AppointmentType> types = Collections.emptySet();
		//		Set<Appointment> appointments = Collections.emptySet();
		//client.setAppointments(appointments);

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		client.setUser(user);
		this.app.setClient(client);

		AppointmentType appointmentType = new AppointmentType();
		appointmentType.setName("revision");
		this.app.setType(appointmentType);

		Center center = new Center();
		center.setId(1);
		center.setAddress("Sevilla");
		//center.setSchedules(null);
		this.app.setCenter(center);

		Medicine medicine = new Medicine();
		medicine.setId(AppointmentControllerTest.TEST_APPOINTMENT_ID);
		medicine.setName("ibuprofeno");
		medicine.setPrice(10.0);
		//	appointment.setMedicine(medicine);

		Specialty specialty = new Specialty();
		specialty.setId(AppointmentControllerTest.TEST_APPOINTMENT_ID);
		specialty.setName("dermatology");
		this.app.setSpecialty(specialty);

		Professional professional = new Professional();
		professional.setId(AppointmentControllerTest.TEST_APPOINTMENT_ID);
		professional.setCenter(center);
		professional.setSpecialty(specialty);
		professional.setFirstName("Manuel");
		professional.setLastName("Carrasco");
		professional.setEmail("mancar@gmail.com");
		professional.setDocument("29334485");
		professional.setDocumentType(DocumentType.nif);
		professional.setCollegiateNumber("413123122-K");
		this.app.setProfessional(professional);

		//String nombre = Mockito.mock(String.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

		BDDMockito.given(this.clientService.findClientByUsername(authentication.getName())).willReturn(new Client());
		BDDMockito.given(this.professionalService.findProByUsername(authentication.getName())).willReturn(new Professional());

		BDDMockito.given(this.appointmentService.findAppointmentByUserId(client.getId())).willReturn(Lists.newArrayList(this.app));
		BDDMockito.given(this.appointmentService.findTodayPendingByProfessionalId(professional.getId())).willReturn(Lists.newArrayList(this.app));
		BDDMockito.given(this.appointmentService.findTodayCompletedByProfessionalId(professional.getId())).willReturn(Lists.newArrayList(this.app));
		//BDDMockito.given(this.appointmentService.findTodayPendingByProfessionalId(professional.getId()).iterator().next()).willReturn(new Appointment());

		BDDMockito.given(this.appointmentService.findAppointmentById(this.app.getId())).willReturn(new Appointment());

		BDDMockito.given(this.medicineService.findMedicines()).willReturn(Lists.newArrayList(this.medicine));

		//BDDMockito.given(this.appointmentService.findAppointmentByTypes()).willReturn(Lists.newArrayList(this.type));

		BDDMockito.given(this.deseaseService.findAll()).willReturn(Lists.newArrayList(this.desease));

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

	@WithMockUser(value = "spring")
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/oups"));
		//		.andExpect(MockMvcResultMatchers.status().isOk())
		//		.andExpect(MockMvcResultMatchers.view().name("appointments/list"));
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
			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/absent", AppointmentControllerTest.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
				.param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank")
				.param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta")
				.param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel")
				.param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVisits() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/consultation", AppointmentControllerTest.TEST_APPOINTMENT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("appointment")).andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAppFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTest.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com")
			.param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
			.param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2)
			.param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif")
			.param("professional.collegiateNumber", "413123122K")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAppFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerTest.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", "test").param("reason", "my head hurts")
				.param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.nif.toString()).param("client.email", "frankcuesta@gmail.com").param("client.healthCardNumber", "0000000003")
				.param("client.healthInsurance", "Adeslas").param("client.firstName", "Frank").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta")
				.param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel")
				.param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "nif").param("professional.collegiateNumber", "413123122K"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "Date"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
	}

}
