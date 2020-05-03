
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthoritiesServiceTests {

	@Autowired
	protected AuthoritiesService authoritiesService;


	@Test
	public void testCountWithInitialData() {
		Collection<Authorities> authorities = (Collection<Authorities>) this.authoritiesService.findAll();
		org.junit.jupiter.api.Assertions.assertEquals(authorities.size(), 8);
	}

	@ParameterizedTest
	@CsvSource({
		"aitortilla, client"
	})
	@Transactional
	public void shouldSaveAuthortities(final String username, final String role) {
		Authorities authorities = new Authorities();
		authorities.setUsername(username);
		authorities.setAuthority(role);

		this.authoritiesService.saveAuthorities(authorities);

		Assertions.assertThat(AuthoritiesServiceTests.getLastElement(this.authoritiesService.findAll()).equals(authorities));
	}

	public static <T> T getLastElement(final Iterable<T> elements) {
		T lastElement = null;

		for (T element : elements) {
			lastElement = element;
		}

		return lastElement;
	}
}
