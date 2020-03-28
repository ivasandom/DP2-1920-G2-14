
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.web.AppointmentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AppointmentControllerTest {

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private ClientService		clientService;

	@Autowired
	private MockMvc				mockMvc;

	private Appointment			appointment;

	private static final int	TEST_APPOINTMENT_ID	= 1;


	@BeforeEach
	void setup() {
		this.appointment = new Appointment();
		this.appointment.setId(AppointmentControllerTest.TEST_APPOINTMENT_ID);
		this.appointment.setDate(LocalDate.of(2020, 03, 11));
		this.appointment.setStartTime(LocalTime.of(10, 15));
		this.appointment.setReason("headache");
		BDDMockito.given(this.appointmentService.findAppointmentById(AppointmentControllerTest.TEST_APPOINTMENT_ID)).willReturn(this.appointment);
	}

	@WithMockUser(value = "user")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("appointments/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
			.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(value = "user")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/new").param("date", "2020/03/12").param("startTime", "10:15").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reason", "headache"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "user")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("date", "2020/03/12").param("startTime", "10:45")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "startTime"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "reason")).andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}
}
