package org.springframework.samples.petclinic.service;



import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProfessionalServiceTests {

	@Autowired
	protected ProfessionalService professionalService;
	
	@Test
	public void testCountWithInitialData() {
		Collection<Professional> professionals = (Collection<Professional>) this.professionalService.findAll();
		Assertions.assertEquals(professionals.size(), 3);
	}
	
	@Test
	public void shouldFindProfessionalById() {
		Professional professional = this.professionalService.findById(1).get();
		Assertions.assertTrue(professional.getAppointments().size() == 12);
		Assertions.assertTrue(professional.getBirthDate() == null);
		Assertions.assertTrue(professional.getCenter().getId().equals(1));
		Assertions.assertTrue(professional.getCollegiateNumber().equals("123123122-F"));
		Assertions.assertTrue(professional.getDocument().equals("13232123M"));
		Assertions.assertTrue(professional.getEmail().equals("guillermodiaz@gmail.com"));
		Assertions.assertTrue(professional.getFirstName().equals("Guillermo"));
		Assertions.assertTrue(professional.getFullName().equals("Guillermo Díaz"));
		Assertions.assertTrue(professional.getLastName().equals("Díaz"));
		Assertions.assertTrue(professional.getRegistrationDate() == null);
		Assertions.assertTrue(professional.getSpecialty().getId().equals(1));
		Assertions.assertTrue(professional.getUser().getUsername().equals("professional1"));
	}
	
	@Test
	void shouldFindProfessionalBySpecialtyAndCenter() {
		Collection<Professional> professional = (Collection<Professional>) this.professionalService.findProfessionalBySpecialtyAndCenter(1, 1);
		Assertions.assertEquals(professional.iterator().next().getFullName(), "Guillermo Díaz");
	}
}
