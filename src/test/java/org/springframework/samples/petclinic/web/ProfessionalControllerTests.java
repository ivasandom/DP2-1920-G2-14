package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = ProfessionalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProfessionalControllerTests {

	private static final int	TEST_PROFESSIONAL_ID		= 1;

	private static final int	TEST_APPOINMENT_ID	= 1;
	
	private static final int	TEST_SPECIALTY_ID	= 1;

	@Autowired
	private ProfessionalController	professionalController;

	@MockBean
	private ProfessionalService		clinicService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private SpecialtyService	specialtyService;
	
	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Professional				pepe;


	@BeforeEach
	void setup() {

		this.pepe = new Professional();
		this.pepe.setId(ProfessionalControllerTests.TEST_PROFESSIONAL_ID);
		this.pepe.setFirstName("Pepe");
		this.pepe.setLastName("Gotera");
		this.pepe.setEmail("pepegotera@gmail.com");
		Date birthdate = new Date(1955-12-01);
		this.pepe.setBirthDate(birthdate);
		Date registrationDate = new Date(2015-10-02);
		this.pepe.setRegistrationDate(registrationDate);
		this.pepe.setDocument("10203040T");
		this.pepe.setDocumentType(DocumentType.nif);
		BDDMockito.given(this.clinicService.findProfessionalById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(this.pepe);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("client"))
			.andExpect(MockMvcResultMatchers.view().name("users/createClientForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/clients/new")
			.param("firstName", "Pepe")
			.param("lastName", "Gotera").with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("email", "pepegotera@gmail.com")
			.param("birthdate", "1955-12-4")
			.param("registrationDate", "2015-07-23")
			.param("document", "10203040T")
			.param("documentType", "nif")
			.param("healthInsurance", "Mafre")
			.param("healthCardNumber", "1234567890")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/clients/new")
							.with(csrf())
							.param("firstName", "George")
							.param("lastName", "Franklin")
							.param("registrationDate", "2015-07-23")
							.param("document", "10223040T"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "email"))
				.andExpect(model().attributeHasFieldErrors("client", "birthdate"))
				.andExpect(model().attributeHasFieldErrors("client", "registrationDate"))
				.andExpect(model().attributeHasFieldErrors("client", "documentType"))
				.andExpect(model().attributeHasFieldErrors("client", "healthInsurance"))
				.andExpect(view().name("users/createClientForm"));
	}
}