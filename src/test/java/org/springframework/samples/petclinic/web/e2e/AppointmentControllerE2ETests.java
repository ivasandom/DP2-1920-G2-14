
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
public class AppointmentControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	
	private static final int	TEST_COMPLETED_APPOINTMENT_ID	= 1;
	private static final int	TEST_APPOINTMENT_ID	= 5;
	private static final int	TEST_ELENANITO_APPOINTMENT_ID	= 4;


	@BeforeEach
	void setup() {
	}

	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(get("/appointments"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("appointments"))
				.andExpect(view().name("appointments/list"));
	}
	
	@WithMockUser(value = "professional1", authorities = { "professional" })
	@Test
	void testListAppointmentsProfessional() throws Exception {

		this.mockMvc.perform(get("/appointments/pro"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("nextAppointment"))
				.andExpect(model().attributeExists("pendingAppointments"))
				.andExpect(model().attributeExists("completedAppointments"))
				.andExpect(view().name("appointments/pro"));
	}
	
	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testInitCreationForm() throws Exception {

		this.mockMvc.perform(get("/appointments/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("appointment"))
				.andExpect(view().name("appointments/new"));
	}

	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		this.mockMvc.perform(post("/appointments/new")
						.with(csrf())
						.param("date", dia)
						.param("reason", "my head hurts")
						.param("startTime", hora)
						.param("center.id", "1")
						.param("specialty.id", "1")
						.param("professional.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments"));
	}

	@WithMockUser(value = "pepegotera", authorities = { "client" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		this.mockMvc.perform(post("/appointments/new")
						.with(csrf())
						.param("date", dia)
						.param("startTime", hora)
						.param("reason", "my head hurts")
						.param("center.id", "")
						.param("professional.id", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("appointment"))
				.andExpect(model().attributeHasFieldErrors("appointment", "center", "professional", "specialty"))
				.andExpect(view().name("appointments/new"));
	}

	@WithMockUser(value = "professional1", authorities = { "professional" })
	@Test
	void testMarkAbsent() throws Exception {
		this.mockMvc
				.perform(post("/appointments/{appointmentId}/absent", TEST_APPOINTMENT_ID)
						.with(csrf()))					
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "professional1", authorities = { "professional" })
	@Test
	void testAppointmentConsultationForm() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/consultation", TEST_APPOINTMENT_ID))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("medicineList", "appointment", "deseaseList"))
				.andExpect(view().name("appointments/consultationPro"));

	}
	
	@WithMockUser(username = "professional1", authorities = { "professional" })
	@Test
	void testAppointmentConsultationFormCompletedRedirect() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/consultation", TEST_COMPLETED_APPOINTMENT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));

	}

	@WithMockUser(value = "professional1", authorities = { "professional" })
	@Test
	void testProcessAppointmentConsultationFormSuccess() throws Exception {
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation",TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("diagnosis.description", "healthy")
						.param("diagnosis.medicines", "1")
						.param("diagnosis.deseases", "1")
						.param("bill.price", "10")
						.param("bill.iva", "21"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}
	
	@WithMockUser(value = "professional1", authorities = { "professional" })
	@Test
	void testProcessAppointmentConsultationFormHasErrors() throws Exception {
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation",TEST_APPOINTMENT_ID)
						.with(csrf())
						.param("bill.price", "-10")
						.param("bill.iva", "500"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeHasFieldErrors("appointment", 
						"diagnosis.description", "diagnosis.medicines", "diagnosis.deseases", "bill.price", "bill.iva"))
				.andExpect(view().name("appointments/consultationPro"));
	}
	
	@WithMockUser(value = "professional1", authorities = { "professional" })
	@Test
	void testProcessAppointmentConsultationFormCompletedRedirect() throws Exception {
		
		this.mockMvc.perform(post("/appointments/{appointmentId}/consultation", TEST_COMPLETED_APPOINTMENT_ID)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "elenanito", authorities = { "client" })
	@Test
	void testShouldShowAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/details", TEST_ELENANITO_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("appointments/details"));
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testShouldNotShowAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/{appointmentId}/details", TEST_ELENANITO_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "You cannot show another user's appointment"))
				.andExpect(view().name("errors/generic"));
	}

	@WithMockUser(username = "elenanito", authorities = { "client" })
	@Test
	void testShouldDeleteAppointment() throws Exception {
		this.mockMvc.perform(get("/appointments/delete/{appointmentId}", TEST_ELENANITO_APPOINTMENT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/appointments"));
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testShouldNotDeleteAppointmentOfOtherUser() throws Exception {
		this.mockMvc.perform(get("/appointments/delete/{appointmentId}", TEST_ELENANITO_APPOINTMENT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "You cannot delete another user's appointment"))
				.andExpect(view().name("errors/generic"));
	}
	
	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testBusyStartTimes() throws Exception {
		this.mockMvc.perform(get("/appointments/busy")
							.queryParam("date", "05/05/2020")
							.queryParam("professionalId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]", Matchers.is("08:00:00")));
	}
}
