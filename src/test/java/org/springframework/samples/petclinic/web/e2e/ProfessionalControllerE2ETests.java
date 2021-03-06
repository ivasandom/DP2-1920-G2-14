
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class ProfessionalControllerE2ETests {
	
	private static final int	TEST_CLIENT_ID			= 1;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/find"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("professional"))
				.andExpect(MockMvcResultMatchers.view().name("professionals/find"));
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testProcessFindFormSuccess() throws Exception {

		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/professionals").queryParam("center", "1").queryParam("specialty", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
			    .andExpect(MockMvcResultMatchers.view().name("professionals/list"));
	}
	
	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testProcessFindFormNotFound() throws Exception {
		this.mockMvc.perform(get("/professionals")
							.queryParam("center.id", "1")
							.queryParam("specialty.id", "2"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("professional", "specialty"))
				.andExpect(view().name("professionals/find"));
	}
	
	
	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testProcessFindFormHasErrors() throws Exception {
		this.mockMvc.perform(get("/professionals")
							.queryParam("center.id", "")
							.queryParam("specialty.id", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("professional", "center", "specialty"))
				.andExpect(view().name("professionals/find"));
	}

	@WithMockUser(value = "guillermodiaz", authorities = {"professional"})
	@Test
	void testShowClient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/clients/{clientId}", TEST_CLIENT_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("client"))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("lastName", Matchers.is("Gotera"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("firstName", Matchers.is("Pepe"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("document", Matchers.is("28334456"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("documentType", Matchers.is(DocumentType.NIE))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("email", Matchers.is("pepegotera@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthCardNumber", Matchers.is("00001"))))
			.andExpect(MockMvcResultMatchers.model().attribute("client", Matchers.hasProperty("healthInsurance", Matchers.is(HealthInsurance.I_DO_NOT_HAVE_INSURANCE))))
			.andExpect(MockMvcResultMatchers.view().name("professionals/clientShow"));
		
	}
	
	@WithMockUser(value = "guillermodiaz", authorities = {"professional"})
	@Test
	void testClientShowNotFound() throws Exception {
		this.mockMvc.perform(get("/professionals/clients/{clientId}", 999))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "guillermodiaz", authorities = {"professional"})
	@Test
	void testClientList() throws Exception {
		this.mockMvc.perform(get("/professionals/clients"))
				.andExpect(model().attributeExists("clients"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("professionals/clientList"));
	}
	
	@WithMockUser(value = "pepegotera", authorities = {"client"})
	@Test
	void testFilterJSON() throws Exception {
		this.mockMvc.perform(get("/professionals/filter")
							.queryParam("centerId", "1")
							.queryParam("specialtyId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].fullName", Matchers.is("Guillermo Diaz")));
	}
}
