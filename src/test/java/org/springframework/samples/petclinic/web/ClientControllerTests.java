
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.stripe.model.Customer;

@WebMvcTest(controllers = ClientController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ClientControllerTests {

	private static final int TEST_CLIENT_ID = 1;

	private static final int TEST_APPOINMENT_ID = 1;


	@MockBean
	private ClientService clinicService;

	@MockBean
	private UserService userService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private StripeService stripeService;

	@Autowired
	private MockMvc mockMvc;

	private Client pepe;

	@BeforeEach
	void setup() throws Exception {
		
		Date birthdate = new Date(1955 - 12 - 01);
		Date registrationDate = new Date(2015 - 10 - 02);
		
		pepe = new Client();
		pepe.setId(ClientControllerTests.TEST_CLIENT_ID);
		pepe.setFirstName("Pepe");
		pepe.setLastName("Gotera");
		pepe.setEmail("pepegotera@gmail.com");	
		pepe.setBirthDate(birthdate);
		pepe.setRegistrationDate(registrationDate);
		pepe.setDocument("10203040T");
		pepe.setDocumentType(DocumentType.NIF);
		pepe.setHealthInsurance(HealthInsurance.MAPFRE);
		pepe.setHealthCardNumber("1234567890");
		pepe.setStripeId("1");
		
		given(this.clinicService.findClientById(ClientControllerTests.TEST_CLIENT_ID)).willReturn(Optional.of(this.pepe));
		given(this.appointmentService.findAppointmentById(ClientControllerTests.TEST_APPOINMENT_ID))
				.willReturn(Optional.of(new Appointment()));
		given(this.stripeService.createCustomer(this.pepe.getEmail())).willReturn(new Customer());

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(get("/clients/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("users/createClientForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
				.perform(post("/clients/new")
						.with(csrf())
						.param("firstName", "Pepe")
						.param("lastName", "Gotera")
						.param("email", "pepegotera@gmail.com")
						.param("birthDate", "1955-12-4")
						.param("document", "10203040T")
						.param("documentType", "NIF")
						.param("healthInsurance", HealthInsurance.MAPFRE.name())
						.param("healthCardNumber", "1234567890")
						.param("user.username", "1234567890")
						.param("user.password", "1234567890"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
				.perform(post("/clients/new")
						.with(csrf())
						// Unicos parametros que recibe
						.param("firstName", "Pepe").param("lastName", "Gotera")
						.param("email", "pepegoteragmail.com")
						.param("birthdate", "1955-12-4")
						.param("document", "")
						.param("healthInsurance", "")
						.param("healthCardNumber", "123456789")
						.param("user.username", "1234567890")
						.param("user.password", "1234"))
				.andExpect(status().isOk())
				// Comprobamos error de validacion
				.andExpect(model().attributeHasErrors("client"))
				// Parametros dentro de los errores del modelo
				.andExpect(model().attributeHasFieldErrors("client", "email"))
				.andExpect(model().attributeHasFieldErrors("client", "document"))
				.andExpect(model().attributeHasFieldErrors("client", "documentType"))
				.andExpect(model().attributeHasFieldErrors("client", "user.password"))
				.andExpect(model().attributeHasFieldErrors("client", "healthInsurance"))
				.andExpect(view().name("users/createClientForm"));
	}
}
