
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class ClientControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {	}

	@WithMockUser(username="pepegotera",authorities= {"client"})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clients/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("client"))
		.andExpect(MockMvcResultMatchers.view().name("users/createClientForm"));
	}

	@WithAnonymousUser
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

	@WithAnonymousUser
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
