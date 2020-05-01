package org.springframework.samples.petclinic.service;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CenterServiceTests {


	@Autowired
	protected CenterService centerService;
	
	@ParameterizedTest
	@CsvSource({"Sevilla"})
	@Transactional
	void shouldFindCenter(final String name) {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		List<String> addresses = centers.stream().map(x-> x.getAddress()).collect(Collectors.toList());
		assertThat(addresses, hasItem(name));
	}
	
	@ParameterizedTest
	@CsvSource({"Córdoba"})
	@Transactional
	void shouldNotFindCenter(final String name) {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		List<String> addresses = centers.stream().map(x-> x.getAddress()).collect(Collectors.toList());
		assertThat(addresses, not(hasItem(name)));
	}
	
	@Test
	public void testCountWithInitialData() {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		Assertions.assertEquals(centers.size(), 2);
	}
	
	@ParameterizedTest
	@CsvSource({"Sevilla"})
	@Transactional
	public void shouldFindCenterById(final String name) {
		Center center = this.centerService.findCenterById(1).get();
		Assertions.assertTrue(center.getAddress().equals(name));
		Assertions.assertTrue(center.getSchedules().isEmpty());
	}
}
