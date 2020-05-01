
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class MedicineServiceTests {

	@Autowired
	protected MedicineService medicineService;


	@Test
	public void testCountWithInitialData() {
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		org.junit.jupiter.api.Assertions.assertEquals(medicines.size(), 42);
	}

	@ParameterizedTest
	@CsvSource({
		"ibuprofeno, 10.0"
	})
	@Transactional
	public void shouldFindMedicineById(final String name, final Double price) {
		Medicine medicine = this.medicineService.findMedicineById(1).get();
		Assertions.assertTrue(medicine.getName().equals(name));
		Assertions.assertTrue(medicine.getPrice().equals(price));
	}

	@ParameterizedTest
	@CsvSource({
		"talquistina, 6.0"
	})
	@Transactional
	public void shouldSaveMedicine(final String name, final Double price) {
		Collection<Medicine> medicines = this.medicineService.findMedicines();
		int found = medicines.size();

		Medicine medicine = new Medicine();
		medicine.setName(name);
		medicine.setPrice(price);

		this.medicineService.saveMedicine(medicine);
		org.assertj.core.api.Assertions.assertThat(medicine.getId().longValue()).isNotEqualTo(0);

		medicines = this.medicineService.findMedicines();
		org.assertj.core.api.Assertions.assertThat(medicines.size()).isEqualTo(found + 1);
	}
}
