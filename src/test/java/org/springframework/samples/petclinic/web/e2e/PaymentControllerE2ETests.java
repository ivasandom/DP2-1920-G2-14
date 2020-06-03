
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	@BeforeEach
	void setup() {
	}

	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testPaymentMethodList() throws Exception {
		this.mockMvc.perform(get("/payments/methods"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("paymentMethods"))
				.andExpect(view().name("payments/paymentMethodList"));
	}

	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testPaymentMethodForm() throws Exception {
		this.mockMvc.perform(get("/payments/new-method"))
				.andExpect(model().attributeExists("intentClientSecret", "paymentMethod", "apiKey"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("payments/paymentMethodForm"));
	}

	@WithMockUser(value = "elenanito", authorities = { "client" })
	@Test
	void testProcessPaymentMethodFormSuccess() throws Exception {
		this.mockMvc.perform(post("/payments/new-method")
							.with(csrf())
							.param("token", "pm_1Ggr7GDfDQNZdQMbCcCoxzEI"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/payments/methods"));
	}
	
	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testProcessPaymentMethodFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/payments/new-method")
							.with(csrf())
							.param("token", "notAValidToken"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("paymentMethod", "token"))
				.andExpect(model().attributeHasFieldErrorCode("paymentMethod", "token", Matchers.is("invalid")))
				.andExpect(view().name("payments/paymentMethodForm"));
	}
	
	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testProcessPaymentMethodDuplicated() throws Exception {
		this.mockMvc.perform(post("/payments/new-method")
							.with(csrf())
							.param("token", "pm_1Ggr7GDfDQNZdQMbCcCoxzEI"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("paymentMethod", "token"))
				.andExpect(model().attributeHasFieldErrorCode("paymentMethod", "token", Matchers.is("duplicated")))
				.andExpect(view().name("payments/paymentMethodForm"));
	}

}
