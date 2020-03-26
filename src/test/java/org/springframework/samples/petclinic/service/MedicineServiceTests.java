package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MedicineServiceTests {

	@Autowired
	protected MedicineService medicineService;
	
	@Test
	public void testCountWithInitialData() {
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		org.junit.jupiter.api.Assertions.assertEquals(medicines.size(), 42);
	}
}
