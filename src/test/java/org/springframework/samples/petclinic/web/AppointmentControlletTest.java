
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AppointmentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AppointmentControlletTest {

	private static final int		TEST_APPOINTMENT_ID	= 1;

	@Autowired
	private AppointmentController	appointmentController;

	@MockBean
	private AppointmentService		clinicService;

	@MockBean
	private UserService				userService;

	@MockBean
	private AuthoritiesService		authoritiesService;

	@Autowired
	private MockMvc					mockMvc;

	private Appointment				app;

	private AppointmentType			tipo;


	@BeforeEach
	void setup() {

		this.tipo = new AppointmentType();
		this.tipo.setName("Tipo");
		this.app = new Appointment();
		this.app.setId(AppointmentControlletTest.TEST_APPOINTMENT_ID);
		this.app.setDate(LocalDate.of(2020, 05, 05));
		this.app.setStartTime(LocalTime.of(8, 30, 0));
		this.app.setReason("Reason");
		this.app.setType(this.tipo);
		BDDMockito.given(this.clinicService.findAppointmentById(AppointmentControlletTest.TEST_APPOINTMENT_ID)).willReturn(this.app);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
			.andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs").with(SecurityMockMvcRequestPostProcessors.csrf()).param("address", "123 Caramel Street").param("city", "London").param("telephone", "01316761638"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner")).andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {
		BDDMockito.given(this.clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(george, new Owner()));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByLastName() throws Exception {
		BDDMockito.given(this.clinicService.findOwnerByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", "Franklin")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + TEST_OWNER_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners").param("lastName", "Unknown Surname")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "lastName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("owner", "lastName", "notFound")).andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateOwnerForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("George"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("110 W. Liberty St.")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Madison"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("6085551023")))).andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("address", "123 Caramel Street")
			.param("city", "London").param("telephone", "01616291589")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", TEST_OWNER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "telephone")).andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("lastName", Matchers.is("Franklin")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("firstName", Matchers.is("George"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("address", Matchers.is("110 W. Liberty St.")))).andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("city", Matchers.is("Madison"))))
			.andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("telephone", Matchers.is("6085551023")))).andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"));
	}
}
