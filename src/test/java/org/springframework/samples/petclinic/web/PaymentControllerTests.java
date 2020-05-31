
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.PaymentMethodService;
import org.springframework.samples.petclinic.service.StripeService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.stripe.model.SetupIntent;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PaymentControllerTests {

	@MockBean
	private PaymentMethodService paymentMethodService;

	@MockBean
	private StripeService stripeService;

	@MockBean
	private ClientService clientService;
	
	@Autowired
	private MockMvc mockMvc;

	private Client client;
	
	private PaymentMethod paymentMethod;
	
	private PaymentMethod duplicatedPaymentMethod;

	private static final int TEST_CLIENT_ID = 1;

	@BeforeEach
	void setup() throws Exception {
		Date birthDate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");

		client = new Client();
		client.setId(TEST_CLIENT_ID);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.NIF);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance(HealthInsurance.ADESLAS);
		client.setLastName("Cuesta");
		client.setStripeId("1");
		client.setUser(user);
		client.setBirthDate(birthDate);
		client.setRegistrationDate(Date.from(Instant.now()));
		client.setPaymentMethods(new HashSet<PaymentMethod>());
		
		paymentMethod = new PaymentMethod();
		paymentMethod.setClient(client);
		paymentMethod.setToken("token");
		
		duplicatedPaymentMethod = new PaymentMethod();
		duplicatedPaymentMethod.setClient(client);
		duplicatedPaymentMethod.setToken("duplicated");
		
		com.stripe.model.PaymentMethod.Card stripeCard = new com.stripe.model.PaymentMethod.Card();
		stripeCard.setBrand("visa");
		stripeCard.setLast4("4242");
		
		com.stripe.model.PaymentMethod stripePaymentMethod = new com.stripe.model.PaymentMethod();
		stripePaymentMethod.setCard(stripeCard);
		
		com.stripe.model.PaymentMethod duplicatedStripePaymentMethod = new com.stripe.model.PaymentMethod();
		stripePaymentMethod.setCard(stripeCard);
		
		
		List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
		paymentMethods.add(paymentMethod);	
		
		SetupIntent setupIntent = new SetupIntent();
		setupIntent.setClientSecret("intentClientSecret");
	
		given(this.clientService.findClientByUsername("client")).willReturn(this.client);
		given(this.paymentMethodService.findByClient(this.client)).willReturn(paymentMethods);
		given(this.stripeService.setupIntent(client.getStripeId())).willReturn(setupIntent);
		given(this.stripeService.retrievePaymentMethod(paymentMethod.getToken())).willReturn(stripePaymentMethod);
		given(this.stripeService.retrievePaymentMethod(duplicatedPaymentMethod.getToken())).willReturn(duplicatedStripePaymentMethod);
		given(this.paymentMethodService.isDuplicated(client, stripePaymentMethod)).willReturn(false);
		given(this.paymentMethodService.isDuplicated(client, duplicatedStripePaymentMethod)).willReturn(true);

	}

	@WithMockUser(value = "client", authorities = { "client" })
	@Test
	void testPaymentMethodList() throws Exception {
		this.mockMvc.perform(get("/payments/methods"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("paymentMethods"))
				.andExpect(view().name("payments/paymentMethodList"));
	}

	@WithMockUser(value = "client", authorities = { "client" })
	@Test
	void testPaymentMethodForm() throws Exception {
		this.mockMvc.perform(get("/payments/new-method"))
				.andExpect(model().attributeExists("intentClientSecret", "paymentMethod", "apiKey"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("payments/paymentMethodForm"));
	}

	@WithMockUser(value = "client", authorities = { "client" })
	@Test
	void testProcessPaymentMethodFormSuccess() throws Exception {
		this.mockMvc.perform(post("/payments/new-method", TEST_CLIENT_ID)
							.with(csrf())
							.param("token", paymentMethod.getToken()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/payments/methods"));
	}
	
	@WithMockUser(value = "client", authorities = { "client" })
	@Test
	void testProcessPaymentMethodFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/payments/new-method", TEST_CLIENT_ID)
							.with(csrf())
							.param("token", "notAValidToken"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("paymentMethod", "token"))
				.andExpect(model().attributeHasFieldErrorCode("paymentMethod", "token", Matchers.is("invalid")))
				.andExpect(view().name("payments/paymentMethodForm"));
	}
	
	@WithMockUser(value = "client", authorities = { "client" })
	@Test
	void testProcessPaymentMethodDuplicated() throws Exception {
		this.mockMvc.perform(post("/payments/new-method", TEST_CLIENT_ID)
							.with(csrf())
							.param("token", duplicatedPaymentMethod.getToken()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("paymentMethod", "token"))
				.andExpect(model().attributeHasFieldErrorCode("paymentMethod", "token", Matchers.is("duplicated")))
				.andExpect(view().name("payments/paymentMethodForm"));
	}
	
}
