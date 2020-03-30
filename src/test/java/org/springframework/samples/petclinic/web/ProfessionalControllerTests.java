package org.springframework.samples.petclinic.web;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProfessionalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProfessionalControllerTests {

	private static final int	TEST_PROFESSIONAL_ID		= 1;

	private static final int	TEST_APPOINMENT_ID	= 1;
	
	private static final int	TEST_SPECIALTY_ID	= 1;

	@Autowired
	private ProfessionalController	professionalController;

	@MockBean
	private ProfessionalService		clinicService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AppointmentService	appointmentService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Professional				pepe;


	@BeforeEach
	void setup() {

		this.pepe = new Professional();
		this.pepe.setId(ProfessionalControllerTests.TEST_PROFESSIONAL_ID);
		this.pepe.setFirstName("Pepe");
		this.pepe.setLastName("Gotera");
		this.pepe.setEmail("pepegotera@gmail.com");
		Date birthdate = new Date(1955 - 12 - 01);
		this.pepe.setBirthDate(birthdate);
		Date registrationDate = new Date(2015 - 10 - 02);
		this.pepe.setRegistrationDate(registrationDate);
		this.pepe.setDocument("10203040T");
		this.pepe.setDocumentType(DocumentType.nif);
		Specialty sp = new Specialty();
		sp.setId(ProfessionalControllerTests.TEST_SPECIALTY_ID);
		sp.setName("Especial");
		this.pepe.setSpecialty(sp);
		BDDMockito.given(this.clinicService.findProfessionalById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(this.pepe);
		BDDMockito.given(this.appointmentService.findAppointmentById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(new Appointment());
	}

	@WithMockUser(value = "spring")
    @Test
    void testInitFindForm() throws Exception {
		mockMvc.perform(get("/professionals/find")).andExpect(status().isOk()).andExpect(model().attributeExists("professional"))
			.andExpect(view().name("professionals/find"));
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessFindFormSuccess() throws Exception {
		given(this.clinicService.findProByUsername("")).willReturn(pepe, new Professional());

		mockMvc.perform(get("/professionals")).andExpect(status().isOk()).andExpect(view().name("professionals/list"));
}
}
