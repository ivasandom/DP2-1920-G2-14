
package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProfessionalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProfessionalControllerTests {

	private static final int TEST_PROFESSIONAL_ID = 1;

	private static final int TEST_SPECIALTY_ID = 1;

	private static final int TEST_CENTER_ID = 1;

	@MockBean
	private ProfessionalService professionalService;

	@MockBean
	private UserService userService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private SpecialtyService specialtyService;

	@MockBean
	private CenterService centerService;

	@MockBean
	private ClientService clientService;

	@Autowired
	private MockMvc mockMvc;

	private Professional professional;

	private Specialty specialty;

	private Center center;

	@BeforeEach
	void setup() {

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		
		specialty = new Specialty();
		specialty.setId(TEST_SPECIALTY_ID);
		specialty.setName("Especial");

		center = new Center();
		center.setId(TEST_CENTER_ID);
		center.setName("SEVILLA");
		center.setAddress("REINA MERCEDES");
		
		Date birthdate = new Date(1955 - 12 - 01);
		Date registrationDate = new Date(2015 - 10 - 02);
		
		professional = new Professional();
		professional.setId(TEST_PROFESSIONAL_ID);
		professional.setFirstName("Pepe");
		professional.setLastName("Gotera");
		professional.setEmail("pepegotera@gmail.com");
		professional.setBirthDate(birthdate);
		professional.setRegistrationDate(registrationDate);
		professional.setDocument("10203040T");
		professional.setDocumentType(DocumentType.NIF);
		professional.setSpecialty(specialty);
		professional.setCenter(center);
		professional.setUser(user);

		List<Professional> professionalList = new ArrayList<Professional>();
		professionalList.add(professional);
		Iterable<Professional> resultadoBuscarProfessionales = professionalList;

		given(this.professionalService.findProfessionalById(TEST_PROFESSIONAL_ID))
				.willReturn(professional);
		given(this.professionalService.findProfessionalBySpecialtyAndCenter(TEST_SPECIALTY_ID, TEST_CENTER_ID))
				.willReturn(resultadoBuscarProfessionales);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(get("/professionals/find"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("professional"))
				.andExpect(view().name("professionals/find"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {

		this.mockMvc.perform(get("/professionals")
							.queryParam("center", "1")
							.queryParam("specialty", "1"))
				.andExpect(status().isOk())
				.andExpect(view().name("professionals/list"));
	}
}
