
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.HealthInsurance;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdminControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	private static final int TEST_CLIENT_ID = 1;
	
	private static final int TEST_PROFESSIONAL_ID = 1;
	
	private static final int TEST_APPOINTMENT_ID = 1;
	
	private static final int TEST_BILL_ID = 1;
	
	private static final int TEST_TRANSACTION_ID = 1;


	@BeforeEach
	void setup() {
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testShowDashboard() throws Exception {
		this.mockMvc.perform(get("/admin"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/dashboard"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientList() throws Exception {
		this.mockMvc.perform(get("/admin/clients"))
				.andExpect(model().attributeExists("clients"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/list"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientDetail() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}", TEST_CLIENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("client"))
				.andExpect(view().name("admin/clients/detail"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}", 2000))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}

	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/clients/{clientId}/edit", TEST_CLIENT_ID))
				.andExpect(model().attributeExists("client"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/form"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessClientEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/edit", TEST_CLIENT_ID)
						.with(csrf())
						.param("firstName", "Frank")
						.param("lastName", "Cuesta")
						.param("birthDate", "1999-05-05")
						.param("document", "29334456")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.ADESLAS.name())
						.param("healthCardNumber", "0000000003")
						.param("email", "frankcuesta@gmail.com")
						.param("user.username", "frankcuesta")
						.param("user.password", "frankcuesta"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients/" + TEST_CLIENT_ID));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessClientEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/edit", TEST_CLIENT_ID)
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.ADESLAS.name())
						.param("healthCardNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", 
						"firstName", "lastName", "document", "healthCardNumber", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/clients/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/clients/create"))
				.andExpect(model().attributeExists("client"))
				.andExpect(status().isOk())
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/clients/form"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessClientCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/clients/create")
						.with(csrf())
						.param("firstName", "Manuel")
						.param("lastName", "Lopera")
						.param("birthDate", "1999-05-05")
						.param("document", "36956285F")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.I_DO_NOT_HAVE_INSURANCE.name())
						.param("healthCardNumber", "")
						.param("email", "manuel@lopera.com")
						.param("user.username", "manolo")
						.param("user.password", "loperaaa"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessClientCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/clients/create")
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("healthInsurance", HealthInsurance.I_DO_NOT_HAVE_INSURANCE.name())
						.param("healthCardNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", 
						"firstName", "lastName", "document", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/clients/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testClientDelete() throws Exception {
		this.mockMvc.perform(post("/admin/clients/{clientId}/delete", TEST_CLIENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/clients"));
	}
	
	
	/**
	 * Professionals
	 */
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalList() throws Exception {
		this.mockMvc.perform(get("/admin/professionals"))
				.andExpect(model().attributeExists("professionals"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/list"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalDetail() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}", TEST_PROFESSIONAL_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("professional"))
				.andExpect(view().name("admin/professionals/detail"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}", 2000))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID))
				.andExpect(model().attributeExists("professional"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalEditFormNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/{professionalId}/edit", 2000))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessProfessionalEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID)
						.with(csrf())
						.param("firstName", "Guillermo")
						.param("lastName", "Díaz")
						.param("birthDate", "1999-05-05")
						.param("document", "29334485")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "413123122-K")
						.param("email", "mancar@gmail.com")
						.param("user.username", "manucar")
						.param("user.password", "manucar")
						.param("center.id", "1")
						.param("specialty.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals/" + TEST_PROFESSIONAL_ID));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessProfessionalEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/edit", TEST_PROFESSIONAL_ID)
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("professional"))
				.andExpect(model().attributeHasFieldErrors("professional", 
						"firstName", "lastName", "document", "collegiateNumber", "email", "user.username", "user.password"))
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/professionals/create"))
				.andExpect(model().attributeExists("professional"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/professionals/form"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessProfessionalCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/create")
						.with(csrf())
						.param("firstName", "Julio")
						.param("lastName", "Lopera")
						.param("birthDate", "1999-05-05")
						.param("document", "95264523F")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "98565626K")
						.param("email", "julio@lopera.com")
						.param("user.username", "julio")
						.param("user.password", "loperaaa")
						.param("center.id", "1")
						.param("specialty.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessProfessionalCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/create")
						.with(csrf())
						.param("firstName", "")
						.param("lastName", "")
						.param("birthDate", "1999-05-05")
						.param("document", "")
						.param("documentType", DocumentType.NIF.name())
						.param("collegiateNumber", "")
						.param("email", "")
						.param("user.username", "")
						.param("user.password", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("professional"))
				.andExpect(model().attributeHasFieldErrors("professional", 
						"firstName", "lastName", "document", "collegiateNumber", "email", "user.username", "user.password", "center", "specialty"))
				.andExpect(view().name("admin/professionals/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProfessionalDelete() throws Exception {
		this.mockMvc.perform(post("/admin/professionals/{professionalId}/delete", TEST_PROFESSIONAL_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/professionals"));
	}
	
	
	/**
	 * Appointments
	 */
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentList() throws Exception {
		this.mockMvc.perform(get("/admin/appointments"))
				.andExpect(model().attributeExists("appointments"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/list"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentDetail() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}", TEST_APPOINTMENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("appointment"))
				.andExpect(view().name("admin/appointments/detail"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentDetailNotFound() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}", 2000))
				.andExpect(status().isNotFound())
				.andExpect(view().name("errors/404"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentEditForm() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID))
				.andExpect(model().attributeExists("appointment"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/form"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessAppointmentEditFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("date", "12/12/2020")
						.param("startTime", "08:00:00")
						.param("client.id", "1")
						.param("professional.id", "1")
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("reason", "test")
						.param("status", AppointmentStatus.COMPLETED.name()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments/" + TEST_APPOINTMENT_ID));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessAppointmentEditFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/edit", TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("date", "")
						.param("startTime", "")
						.param("client.id", "")
						.param("professional.id", "")
						.param("center.id", "")
						.param("specialty.id", "")
						.param("reason", "")
						.param("status",  AppointmentStatus.PENDING.name()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"date", "startTime", "client", "professional", "center", "specialty", "reason"))
				.andExpect(view().name("admin/appointments/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentCreateForm() throws Exception {
		this.mockMvc.perform(get("/admin/appointments/create"))
				.andExpect(model().attributeExists("appointment"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/appointments/form"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessAppointmentCreateFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/create")
						.with(csrf())
						.param("date", "06/06/2020")
						.param("startTime", "09:00:00")
						.param("client.id", "1")
						.param("professional.id", "1")
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("reason", "test")
						.param("status", AppointmentStatus.PENDING.name()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessAppointmentCreateFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/create")
						.with(csrf())
						.param("date", "")
						.param("startTime", "")
						.param("client.id", "")
						.param("professional.id", "")
						.param("center.id", "")
						.param("specialty.id", "")
						.param("reason", "")
						.param("status", AppointmentStatus.PENDING.name()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"date", "startTime", "client", "professional", "center", "specialty", "reason"))
				.andExpect(view().name("admin/appointments/form"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testAppointmentDelete() throws Exception {
		this.mockMvc.perform(post("/admin/appointments/{appointmentId}/delete", TEST_APPOINTMENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/appointments"));
	}
	
	
	/**
	 * Bills
	 */
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testBillList() throws Exception {
		this.mockMvc.perform(get("/admin/bills"))
				.andExpect(model().attributeExists("bills"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/bills/list"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testBillDetail() throws Exception {
		this.mockMvc.perform(get("/admin/bills/{billId}", TEST_BILL_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("bill"))
				.andExpect(view().name("admin/bills/detail"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testBillChargeForm() throws Exception {
		this.mockMvc.perform(get("/admin/bills/{billId}/charge", TEST_BILL_ID))
				.andExpect(model().attributeExists("bill"))
				.andExpect(model().attributeExists("transaction"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("admin/bills/chargeForm"));
	}

	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessBillChargeFormSuccess() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/charge", TEST_BILL_ID)
						.with(csrf())
						.param("amount", "50")
						.param("paymentMethod.token", "CASH"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/bills/" + TEST_BILL_ID));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessBillChargeFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/charge", TEST_BILL_ID)
						.with(csrf())
						.param("amount", "100000")
						.param("paymentMethod.token", ""))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasErrors("transaction"))
				.andExpect(model().attributeHasFieldErrors("transaction", 
						"amount", "paymentMethod.token"))
				.andExpect(view().name("admin/bills/chargeForm"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testBillRefund() throws Exception {
		this.mockMvc.perform(post("/admin/bills/{billId}/refund/{transactionId}", TEST_BILL_ID, TEST_TRANSACTION_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/bills/" + TEST_BILL_ID));
	}

}
