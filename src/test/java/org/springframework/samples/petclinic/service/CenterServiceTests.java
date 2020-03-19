package org.springframework.samples.petclinic.service;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CenterServiceTests {


	@Autowired
	protected CenterService centerService;
	
	@Test
	void shouldFindCenter() {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		String name = "Sevilla";
		List<String> addresses = centers.stream().map(x-> x.getAddress()).collect(Collectors.toList());
		assertThat(addresses, hasItem(name));
	}
	
	@Test
	void shouldNotFindCenter() {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		String name = "Córdoba";
		List<String> addresses = centers.stream().map(x-> x.getAddress()).collect(Collectors.toList());
		assertThat(addresses, not(hasItem(name)));
	}
	
	@Test
	public void testCountWithInitialData() {
		Collection<Center> centers = (Collection<Center>) this.centerService.findAll();
		Assertions.assertEquals(centers.size(), 2);
	}
	
	@Test
	public void shouldFindCenterById() {
		Center center = this.centerService.findCenterById(1).get();
		Assertions.assertTrue(center.getAddress().equals("Sevilla"));
		Assertions.assertTrue(center.getSchedules().isEmpty());
	}
}
