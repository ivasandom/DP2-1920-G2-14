
package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProfessionalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProfessionalControllerTests {

	private static final int	TEST_PROFESSIONAL_ID	= 1;

	private static final int	TEST_SPECIALTY_ID		= 1;

	private static final int	TEST_CENTER_ID			= 1;

	@MockBean
	private ProfessionalService	professionalService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@MockBean
	private SpecialtyService	specialtyService;

	@MockBean
	private CenterService		centerService;

	@MockBean
	private ClientService		clientService;

	@Autowired
	private MockMvc				mockMvc;

	private Professional		professional;

	private Specialty			specialty;

	private Center				center;

	private Client				client;

	private static final int	TEST_CLIENT_ID			= 1;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");

		User user2 = new User();
		user2.setEnabled(true);
		user2.setUsername("manucar");
		user2.setPassword("manucar");

		this.specialty = new Specialty();
		this.specialty.setId(TEST_SPECIALTY_ID);
		this.specialty.setName("Especial");

		this.center = new Center();
		this.center.setId(TEST_CENTER_ID);
		this.center.setName("SEVILLA");
		this.center.setAddress("REINA MERCEDES");

		Date birthdate = new Date(1955 - 12 - 01);
		Date registrationDate = new Date(2015 - 10 - 02);

		Date birthDate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();

		this.professional = new Professional();
		this.professional.setId(TEST_PROFESSIONAL_ID);
		this.professional.setFirstName("Pepe");
		this.professional.setLastName("Gotera");
		this.professional.setEmail("pepegotera@gmail.com");
		this.professional.setBirthDate(birthdate);
		this.professional.setRegistrationDate(registrationDate);
		this.professional.setDocument("10203040T");
		this.professional.setDocumentType(DocumentType.NIF);
		this.professional.setSpecialty(this.specialty);
		this.professional.setCenter(this.center);
		this.professional.setUser(user);

		this.client = new Client();
		this.client.setId(ProfessionalControllerTests.TEST_CLIENT_ID);
		this.client.setDocument("29334456");
		this.client.setDocumentType(DocumentType.NIF);
		this.client.setEmail("frankcuesta@gmail.com");
		this.client.setFirstName("Frank");
		this.client.setHealthCardNumber("0000000003");
		this.client.setHealthInsurance(HealthInsurance.ADESLAS);
		this.client.setLastName("Cuesta");
		this.client.setStripeId("1");
		this.client.setUser(user2);
		this.client.setBirthDate(birthDate);
		this.client.setRegistrationDate(Date.from(Instant.now()));
		this.client.setPaymentMethods(new HashSet<PaymentMethod>());

		List<Professional> professionalList = new ArrayList<Professional>();
		professionalList.add(this.professional);
		Iterable<Professional> resultadoBuscarProfessionales = professionalList;

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(this.client);
		Collection<Client> resultadoBuscarClientes = clientList;

		BDDMockito.given(this.professionalService.findProfessionalById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(this.professional);
		BDDMockito.given(this.professionalService.findProfessionalBySpecialtyAndCenter(ProfessionalControllerTests.TEST_SPECIALTY_ID, ProfessionalControllerTests.TEST_CENTER_ID)).willReturn(resultadoBuscarProfessionales);
		BDDMockito.given(this.professionalService.findProfessionalById(TEST_PROFESSIONAL_ID)).willReturn(this.professional);
		BDDMockito.given(this.professionalService.findProfessionalBySpecialtyAndCenter(TEST_SPECIALTY_ID, TEST_CENTER_ID)).willReturn(resultadoBuscarProfessionales);

		BDDMockito.given(this.clientService.findClientById(ProfessionalControllerTests.TEST_CLIENT_ID)).willReturn(Optional.of(this.client));
		BDDMockito.given(this.clientService.findAll()).willReturn(resultadoBuscarClientes);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(get("/professionals/find"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("professional"))
				.andExpect(view().name("professionals/find"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		this.mockMvc.perform(get("/professionals")
							.queryParam("center.id", "1")
							.queryParam("specialty.id", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("selections"))
				.andExpect(view().name("professionals/list"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNotFound() throws Exception {
		this.mockMvc.perform(get("/professionals")
							.queryParam("center.id", "1")
							.queryParam("specialty.id", "2"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("professional", "specialty"))
				.andExpect(view().name("professionals/find"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormHasErrors() throws Exception {
		this.mockMvc.perform(get("/professionals")
							.queryParam("center.id", "")
							.queryParam("specialty.id", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("professional", "center", "specialty"))
				.andExpect(view().name("professionals/find"));
	}
		
	@WithMockUser(value = "spring")
	@Test	
	void testShowClient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/clients/{clientId}", TEST_CLIENT_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("client"))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("lastName", Matchers.is("Cuesta"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("firstName", Matchers.is("Frank"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("document", Matchers.is("29334456"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("documentType", Matchers.is(DocumentType.NIF))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("email", Matchers.is("frankcuesta@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthCardNumber", Matchers.is("0000000003"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthInsurance", Matchers.is(HealthInsurance.ADESLAS))))
			.andExpect(MockMvcResultMatchers.view().name("professionals/clientShow"));
		
	}	
	
	@WithMockUser(value = "spring")
	@Test
	void testClientShowNotFound() throws Exception {
		this.mockMvc.perform(get("/professionals/clients/{clientId}", 999))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testClientList() throws Exception {
		this.mockMvc.perform(get("/professionals/clients"))
				.andExpect(model().attributeExists("clients"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("professionals/clientList"));
	}
}
