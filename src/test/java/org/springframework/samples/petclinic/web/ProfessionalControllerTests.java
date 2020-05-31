
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.samples.petclinic.model.DocumentType;
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


	@BeforeEach
	void setup() {

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");

		this.specialty = new Specialty();
		this.specialty.setId(ProfessionalControllerTests.TEST_SPECIALTY_ID);
		this.specialty.setName("Especial");

		this.center = new Center();
		this.center.setId(ProfessionalControllerTests.TEST_CENTER_ID);
		this.center.setName("SEVILLA");
		this.center.setAddress("REINA MERCEDES");

		Date birthdate = new Date(1955 - 12 - 01);
		Date registrationDate = new Date(2015 - 10 - 02);

		this.professional = new Professional();
		this.professional.setId(ProfessionalControllerTests.TEST_PROFESSIONAL_ID);
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

		List<Professional> professionalList = new ArrayList<Professional>();
		professionalList.add(this.professional);
		Iterable<Professional> resultadoBuscarProfessionales = professionalList;

		BDDMockito.given(this.professionalService.findProfessionalById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(this.professional);
		BDDMockito.given(this.professionalService.findProfessionalBySpecialtyAndCenter(ProfessionalControllerTests.TEST_SPECIALTY_ID, ProfessionalControllerTests.TEST_CENTER_ID)).willReturn(resultadoBuscarProfessionales);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("professional"))
			.andExpect(MockMvcResultMatchers.view().name("professionals/find"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals").queryParam("center.id", "1").queryParam("specialty.id", "1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("professionals/list"));
	}

	@WithMockUser(value = "professional1")
	@Test
	void testShowClient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/{clientId}", 1))//
			.andExpect(MockMvcResultMatchers.status().isOk())//
			.andExpect(MockMvcResultMatchers.model().attributeExists("client")).andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("lastName", Matchers.is("Gotera"))))//
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("firstName", Matchers.is("Pepe")))).andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("document", Matchers.is("28334456"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("birthDate", Matchers.is("1992-02-02")))).andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("documentType", Matchers.is("1"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("email", Matchers.is("pepegotera@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("registrationDate", Matchers.is("2020-03-12"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthCardNumber", Matchers.is("00001"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthInsurance", Matchers.is("I_DO_NOT_HAVE_INSURANCE"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("username", Matchers.is("pepegotera"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("stripe_id", Matchers.is("cus_HFLSuDf4wEoVn7"))))
			.andExpect(MockMvcResultMatchers.view().name("/professionals/1"));
	}
}
