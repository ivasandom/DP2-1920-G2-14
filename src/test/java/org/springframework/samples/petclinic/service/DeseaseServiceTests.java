
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DeseaseServiceTests {

	@Autowired
	protected DeseaseService deseaseService;


	@Test
	public void testCountWithInitialData() {
		Collection<Desease> deseases = (Collection<Desease>) this.deseaseService.findAll();
		org.junit.jupiter.api.Assertions.assertEquals(deseases.size(), 144);
	}
}
