
package org.springframework.samples.petclinic.web.e2e;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AppointmentControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	private static final int TEST_APPOINTMENT_ID = 28;

	@BeforeEach
	void setup() {
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testInitCreationForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
				.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/appointments/new").param("Date", dia)
						.param("reason", "my head hurts").with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime", hora).param("client.document", "29334456")
						.param("client.documentType", DocumentType.NIF.toString())
						.param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank")
						.param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas")
						.param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
						.param("client.user.password", "frankcuesta").param("client.type.name", "revision")
						.param("center.address", "Sevilla").param("specialty.name", sp)
						.param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2)
						.param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco")
						.param("professional.email", "mancar@gmail.com").param("professional.document", "29334485")
						.param("professional.documentType", "NIF").param("professional.collegiateNumber", "413123122K"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "elenanito", authorities = { "client" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/appointments/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("startTime", hora)
						.param("client.document", "29334456").param("reason", "my head hurts")
						.param("client.documentType", DocumentType.NIF.toString())
						.param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank")
						.param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas")
						.param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
						.param("client.user.password", "frankcuesta").param("client.type.name", "revision")
						.param("center.address", "Sevilla").param("professional.center.address", "Sevilla")
						.param("professional.specialty.name", sp2).param("professional.firstName", "Manuel")
						.param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com")
						.param("professional.document", "29334485").param("professional.documentType", "NIF")
						.param("professional.collegiateNumber", "413123122K"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "specialty"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("appointments/new"));
	}

	@WithMockUser(username = "pepegotera", authorities = { "client" })
	@Test
	void testlistAppointments() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("appointments/list"));
	}

	@WithMockUser(username = "professional1", authorities = { "professional" })
	@Test
	void testListAppointmentsProfessional() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/appointments/pro"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("appointments/pro"));
	}

	@WithMockUser(username = "professional1", authorities = { "professional" })
	@Test
	void testMarkAbsent() throws Exception {
		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		Set<Appointment> s = new HashSet<>();

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		String sp = specialty.getName();

		Specialty specialty2 = new Specialty();
		specialty2.setName("dermatology");
		String sp2 = specialty2.getName();
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/appointments/{appointmentId}/absent", AppointmentControllerE2ETests.TEST_APPOINTMENT_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia)
						.param("reason", "my head hurts").param("startTime", hora).param("client.document", "29334456")
						.param("client.documentType", DocumentType.NIF.toString())
						.param("client.email", "frankcuesta@gmail.com").param("client.firstName", "Frank")
						.param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas")
						.param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
						.param("client.user.password", "frankcuesta").param("client.type.name", "revision")
						.param("center.address", "Sevilla").param("specialty.name", sp)
						.param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2)
						.param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco")
						.param("professional.email", "mancar@gmail.com").param("professional.document", "29334485")
						.param("professional.documentType", "NIF").param("professional.collegiateNumber", "413123122K"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
	}

	@WithMockUser(username = "professional1", authorities = { "professional" })
	@Test
	void testShowVisits() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}/consultation",
						AppointmentControllerE2ETests.TEST_APPOINTMENT_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
				.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
	}

//	@WithMockUser(username="professional1",authorities= {"professional"})
//	@Test
//	void testProcessUpdateAppFormSuccess() throws Exception {
//		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//		Specialty specialty = new Specialty();
//		specialty.setName("dermatology");
//		String sp = specialty.getName();
//
//		Specialty specialty2 = new Specialty();
//		specialty2.setName("dermatology");
//		String sp2 = specialty2.getName();
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerE2ETests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", dia).param("reason", "my head hurts")
//			.with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.NIF.toString()).param("client.email", "frankcuesta@gmail.com")
//			.param("client.firstName", "Frank").param("client.healthCardNumber", "0000000003").param("client.healthInsurance", "Adeslas").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta")
//			.param("client.user.password", "frankcuesta").param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2)
//			.param("professional.firstName", "Manuel").param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "NIF")
//			.param("professional.collegiateNumber", "413123122K")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/appointments/pro"));
//	}

//	@WithMockUser(username="professional2",authorities= {"professional"})
//	@Test
//	void testProcessUpdateAppFormHasErrors() throws Exception {
//		String dia = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		String hora = LocalTime.of(10, 15, 00).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//		Specialty specialty = new Specialty();
//		specialty.setName("dermatology");
//		String sp = specialty.getName();
//
//		Specialty specialty2 = new Specialty();
//		specialty2.setName("dermatology");
//		String sp2 = specialty2.getName();
//		this.mockMvc
//			.perform(MockMvcRequestBuilders.post("/appointments/{appointmentId}/consultation", AppointmentControllerE2ETests.TEST_APPOINTMENT_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("Date", "test").param("reason", "my head hurts")
//				.param("startTime", hora).param("client.document", "29334456").param("client.documentType", DocumentType.NIF.toString()).param("client.email", "frankcuesta@gmail.com").param("client.healthCardNumber", "0000000003")
//				.param("client.healthInsurance", "Adeslas").param("client.firstName", "Frank").param("client.lastName", "Cuesta").param("client.user.username", "frankcuesta").param("client.user.password", "frankcuesta")
//				.param("client.type.name", "revision").param("center.address", "Sevilla").param("specialty.name", sp).param("professional.center.address", "Sevilla").param("professional.specialty.name", sp2).param("professional.firstName", "Manuel")
//				.param("professional.lastName", "Carrasco").param("professional.email", "mancar@gmail.com").param("professional.document", "29334485").param("professional.documentType", "NIF").param("professional.collegiateNumber", "413123122K"))
//			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("appointment")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("appointment", "Date"))
//			.andExpect(MockMvcResultMatchers.view().name("appointments/consultationPro"));
//	}

}
