package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
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
	@CsvSource({"Guillermo Díaz"})
	@Transactional
	public void shouldFindProfessionalById(final String name) {
		Professional pro = this.professionalService.findById(1).get();
		Assertions.assertTrue(pro.getFullName().equals(name));
	}

	@ParameterizedTest
	@CsvSource({"Guillermo Díaz"})
	@Transactional
	public void shouldNotFindProfessionalById(final String name) {
		Professional pro = this.professionalService.findById(2).get();
		Assertions.assertFalse(pro.getFullName().equals(name));
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
		Assertions.assertEquals(pros.iterator().next().getFullName(), "Guillermo Díaz");
	}

	@ParameterizedTest
	@CsvSource({
		"1, 2", "3, 1"
	})
	void shouldNotFindProfessionalBySpecialtyAndCenter(final int specialtyId, final int centerId) {
		Iterable<Professional> pros = professionalService.findProfessionalBySpecialtyAndCenter(specialtyId, centerId);
		Assertions.assertNotEquals(pros.iterator().next().getFullName(), "Guillermo Díaz");	

	}
}
