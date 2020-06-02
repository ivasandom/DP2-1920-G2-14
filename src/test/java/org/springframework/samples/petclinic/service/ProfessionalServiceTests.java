package org.springframework.samples.petclinic.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProfessionalServiceTests {

	@Autowired
	protected ProfessionalService 	professionalService;
	
	@Autowired
	protected CenterService 		centerService;

	@Autowired
	protected SpecialtyService 		specialtyService;

	@Test
	public void testCountWithInitialDate() {
		Collection<Professional> professionals = (Collection<Professional>)this.professionalService.findAll();
		Assertions.assertEquals(professionals.size(), 3);
	}
	
	@ParameterizedTest
	@CsvSource({"1, Guillermo Diaz"})
	@Transactional
	public void shouldFindProfessionalById(final int id, final String name) {
		Optional<Professional> pro = this.professionalService.findById(1);
		Assertions.assertTrue(pro.isPresent());
		Assertions.assertTrue(pro.get().getFullName().equals(name));
	}

	@Transactional
	public void shouldNotFindProfessionalById() {
		Optional<Professional> pro = this.professionalService.findById(100);
		Assertions.assertFalse(pro.isPresent());
	}

	@ParameterizedTest
	@CsvSource({"professional1", "professional2", "professional3"})
	@Transactional
	void shouldFindProfessionalByUsername(final String username) {
		Professional pro = this.professionalService.findProByUsername(username);
		Collection<Professional> pros = (Collection<Professional>) this.professionalService.findAll();
		Assertions.assertTrue(pros.contains(pro));
	}
	
	@ParameterizedTest
	@CsvSource({"pepegotera", "elenanito"})
	@Transactional
	void shouldNotFindProfessionalByUsername(final String username) {
		Professional pro = this.professionalService.findProByUsername(username);
		Collection<Professional> pros = (Collection<Professional>) this.professionalService.findAll();
		Assertions.assertFalse(pros.contains(pro));
	}
	
	@Test
	void shouldFindProfessionalBySpecialtyAndCenter() {
		Specialty spe = specialtyService.findSpecialtyById(1).get();
		Center cen = centerService.findCenterById(1).get();
		Iterable<Professional> pros = professionalService.findProfessionalBySpecialtyAndCenter(spe.getId(), cen.getId());
		Assertions.assertEquals(pros.iterator().next().getFullName(), "Guillermo Diaz");
	}

	@ParameterizedTest
	@CsvSource({
		"1, 2", "3, 1"
	})
	void shouldNotFindProfessionalBySpecialtyAndCenter(final int specialtyId, final int centerId) {
		Iterable<Professional> pros = professionalService.findProfessionalBySpecialtyAndCenter(specialtyId, centerId);
		Assertions.assertNotEquals(pros.iterator().next().getFullName(), "Guillermo Díaz");	

	}
	
	@Test
	@Transactional
	public void shouldSaveProfessional() throws DataAccessException, DuplicatedUsernameException {
		Collection<Professional> professionals = (Collection<Professional>)this.professionalService.findAll();
		int found = professionals.size();

		
		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		Date registrationDate = new Date(2020-03-03);
		Set<Appointment> appointments = Collections.emptySet();
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("professional4");
		user.setPassword("professional4");

		Professional professional =new Professional();
		professional.setBirthDate(birthdate);
		professional.setDocument("29334456");
		professional.setDocumentType(DocumentType.NIF);
		professional.setEmail("frankcuesta@gmail.com");
		professional.setFirstName("Frank");
		professional.setLastName("Cuesta");
		professional.setCollegiateNumber("77777777K");
		professional.setRegistrationDate(registrationDate);
		professional.setAppointments(appointments);
		professional.setUser(user);
		
		this.professionalService.saveProfessional(professional);
		org.assertj.core.api.Assertions.assertThat(professional.getId().longValue()).isNotEqualTo(0);

		professionals = (Collection<Professional>)this.professionalService.findAll();
		org.assertj.core.api.Assertions.assertThat(professionals.size()).isEqualTo(found + 1);
	}
	
	@ParameterizedTest
	@CsvSource({"professional1", "professional2", "professional3"})
	@Transactional
	public void shouldNotSaveProfessional() throws DataAccessException, DuplicatedUsernameException {
		Collection<Professional> professionals = (Collection<Professional>)this.professionalService.findAll();
		int found = professionals.size();

		
		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		Date registrationDate = new Date(2020-03-03);
		Set<Appointment> appointments = Collections.emptySet();
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("professional1");
		user.setPassword("professional1");

		Professional professional =new Professional();
		professional.setBirthDate(birthdate);
		professional.setDocument("29334456");
		professional.setDocumentType(DocumentType.NIF);
		professional.setEmail("frankcuesta@gmail.com");
		professional.setFirstName("Frank");
		professional.setLastName("Cuesta");
		professional.setCollegiateNumber("77777777K");
		professional.setRegistrationDate(registrationDate);
		professional.setAppointments(appointments);
		professional.setUser(user);
		
		Assertions.assertThrows(DuplicatedUsernameException.class, () -> this.professionalService.saveProfessional(professional));

		professionals = (Collection<Professional>)this.professionalService.findAll();
		org.assertj.core.api.Assertions.assertThat(professionals.size()).isEqualTo(found);
	}
	
	@Test
	@Transactional
	public void shouldDeleteProfessionalById() throws DataAccessException, DuplicatedUsernameException {
		Collection<Professional> professionals = (Collection<Professional>)this.professionalService.findAll();
		int found = professionals.size();
		
		this.professionalService.deleteById(1);
		
		professionals = (Collection<Professional>)this.professionalService.findAll();
		org.assertj.core.api.Assertions.assertThat(professionals.size()).isEqualTo(found - 1);
	}
}
