
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CenterServiceTests {

	@Autowired
	protected CenterService centerService;


	@ParameterizedTest
	@CsvSource({
		"Sevilla"
	})
	@Transactional
	void shouldFindCenter(final String name) {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		List<String> addresses = centers.stream().map(x -> x.getAddress()).collect(Collectors.toList());
		Assert.assertThat(addresses, Matchers.hasItem(name));
	}

	@ParameterizedTest
	@CsvSource({
		"Córdoba"
	})
	@Transactional
	void shouldNotFindCenter(final String name) {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		List<String> addresses = centers.stream().map(x -> x.getAddress()).collect(Collectors.toList());
		Assert.assertThat(addresses, IsNot.not(Matchers.hasItem(name)));
	}

	@Test
	public void testCountWithInitialData() {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		Assertions.assertEquals(centers.size(), 2);
	}

	@ParameterizedTest
	@CsvSource({
		"Sevilla"
	})
	@Transactional
	public void shouldFindCenterById(final String name) {
		Center center = this.centerService.findCenterById(1).get();
		Assertions.assertTrue(center.getAddress().equals(name));
		Assertions.assertTrue(center.getSchedules().isEmpty());
	}
}
