
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ClientController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ClientControllerTests {

	private static final int	TEST_CLIENT_ID		= 1;

	private static final int	TEST_APPOINMENT_ID	= 1;

	@Autowired
	private ClientController	clientController;

	@MockBean
	private ClientService		clinicService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private AuthoritiesService	authoritiesService;
	
	@MockBean
	private StripeService stripeService;

	@Autowired
	private MockMvc				mockMvc;

	private Client				pepe;


	@BeforeEach
	void setup() {

		this.pepe = new Client();
		this.pepe.setId(ClientControllerTests.TEST_CLIENT_ID);
		this.pepe.setFirstName("Pepe");
		this.pepe.setLastName("Gotera");
		this.pepe.setEmail("pepegotera@gmail.com");
		Date birthdate = new Date(1955 - 12 - 01);
		this.pepe.setBirthDate(birthdate);
		Date registrationDate = new Date(2015 - 10 - 02);
		this.pepe.setRegistrationDate(registrationDate);
		this.pepe.setDocument("10203040T");
		this.pepe.setDocumentType(DocumentType.nif);
		this.pepe.setHealthInsurance("Mafre");
		this.pepe.setHealthCardNumber("1234567890");
		BDDMockito.given(this.clinicService.findClientById(ClientControllerTests.TEST_CLIENT_ID)).willReturn(this.pepe);
		BDDMockito.given(this.appointmentService.findAppointmentById(ClientControllerTests.TEST_APPOINMENT_ID)).willReturn(new Appointment());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("client"))
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
//			.param("registrationDate", "2015-07-23")
			.param("document", "10203040T")
			.param("documentType", "nif")
			.param("healthInsurance", "Mafre")
			.param("healthCardNumber", "1234567890")
			.param("user.username", "1234567890")
			.param("user.password", "1234567890"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/clients/new").with(SecurityMockMvcRequestPostProcessors.csrf())
			//Unicos parametros que recibe
			.param("firstName", "Pepe")
			.param("lastName", "Gotera").with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("email", "pepegoteragmail.com")
			.param("birthdate", "1955-12-4")
//			.param("registrationDate", "2015-07-23")
			.param("document", "")
			.param("healthInsurance", "")
			.param("healthCardNumber", "123456789")
			.param("user.username", "1234567890")
			.param("user.password", "1234"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			//Comprobamos error de validacion
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("client"))
			//Parametros dentro de los errores del modelo
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("client", "email"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("client", "document"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("client", "documentType"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("client", "user.password"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("client", "healthInsurance"))
			.andExpect(MockMvcResultMatchers.view().name("users/createClientForm"));
	}
}
