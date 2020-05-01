
package org.springframework.samples.petclinic.web.e2e;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
class ProfessionalControllerE2ETests {

	@Autowired
	private MockMvc					mockMvc;

	@BeforeEach
	void setup() { }

	@WithMockUser(username="pepegotera",authorities= {"client"})
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/find"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("professional"))
			.andExpect(MockMvcResultMatchers.view().name("professionals/find"));
	}

	@WithMockUser(username="pepegotera",authorities= {"client"})
	@Test
	void testProcessFindFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals")
				.queryParam("center", "1")
				.queryParam("specialty", "1"))
			.andExpect(MockMvcResultMatchers.status().isOk());
//			.andExpect(MockMvcResultMatchers.view().name("professionals/list"));
	}
}
