
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AppointmentService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CenterService;
import org.springframework.samples.petclinic.service.ProfessionalService;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProfessionalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProfessionalControllerTests {

	private static final int		TEST_PROFESSIONAL_ID	= 1;

	private static final int		TEST_APPOINMENT_ID		= 1;

	private static final int		TEST_SPECIALTY_ID		= 1;

	private static final int		TEST_CENTER_ID			= 1;

	@Autowired
	private ProfessionalController	professionalController;

	@MockBean
	private ProfessionalService		professionalService;

	@MockBean
	private UserService				userService;

	@MockBean
	private AppointmentService		appointmentService;

	@MockBean
	private AuthoritiesService		authoritiesService;

	@MockBean
	private SpecialtyService		specialtyService;

	@MockBean
	private CenterService			centerService;

	@Autowired
	private MockMvc					mockMvc;

	private Professional			pepe;

	private Appointment				app;

	private Specialty				spe;

	private Center					center;


	@BeforeEach
	void setup() {

		this.spe = new Specialty();

		this.spe.setId(ProfessionalControllerTests.TEST_SPECIALTY_ID);
		this.spe.setName("Especial");

		Center center = new Center();
		center.setId(ProfessionalControllerTests.TEST_CENTER_ID);
		center.setName("SEVILLA");
		center.setAddress("REINA MERCEDES");
		this.center = center;

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

		this.pepe.setSpecialty(this.spe);
		this.pepe.setCenter(this.center);

		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		this.pepe.setUser(user);

		this.app = new Appointment();
		this.app.setId(1);
		String par = LocalDate.of(2020, 12, 03).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.app.setDate(LocalDate.parse(par, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		this.app.setReason("my head hurts");
		this.app.setStartTime(LocalTime.of(10, 15, 00));

		this.app.setReceipt(null);
		this.app.setDiagnosis(null);
		this.app.setClient(null);

		List<Professional> professionalList = new ArrayList<Professional>();
		professionalList.add(this.pepe);
		Iterable<Professional> resultadoBuscarProfessionales = professionalList;

		BDDMockito.given(this.professionalService.findProfessionalById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(this.pepe);
		BDDMockito.given(this.appointmentService.findAppointmentById(ProfessionalControllerTests.TEST_PROFESSIONAL_ID)).willReturn(new Appointment());
		BDDMockito.given(this.professionalService.findProfessionalBySpecialtyAndCenter(ProfessionalControllerTests.TEST_SPECIALTY_ID, ProfessionalControllerTests.TEST_CENTER_ID)).willReturn(resultadoBuscarProfessionales);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("professional"))
			.andExpect(MockMvcResultMatchers.view().name("professionals/find"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/professionals").queryParam("center", "1").queryParam("specialty", "1")).andExpect(MockMvcResultMatchers.status().isOk());
		//			.andExpect(MockMvcResultMatchers.view().name("professionals/list"));
	}
}
