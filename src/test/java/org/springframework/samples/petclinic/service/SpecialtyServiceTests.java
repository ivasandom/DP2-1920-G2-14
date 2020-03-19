package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SpecialtyServiceTests {
	
	@Autowired
	protected SpecialtyService specialtyService;
	
	@Test
	public void testCountWithInitialData() {
		Collection<Specialty> specialties = (Collection<Specialty>) this.specialtyService.findAll();
		Assertions.assertEquals(specialties.size(), 3);
	}

	@Test
	public void shouldFindSpecialtyById() {
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		Assertions.assertTrue(specialty.getName().equals("dermatology"));
	}
}
