
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
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTests {

	@Autowired
	protected UserService userService;


	@ParameterizedTest
	@CsvSource({
		"antonio, pass1234"
	})
	@Transactional
	public void shouldSaveUser(final String username, final String password) {
		Collection<User> users = (Collection<User>) this.userService.listUsers();
		int found = users.size();

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		this.userService.saveUser(user);

		users = (Collection<User>) this.userService.listUsers();
		org.assertj.core.api.Assertions.assertThat(users.size()).isEqualTo(found + 1);
	}

	@Test
	public void testCountWithInitialData() {
		Collection<User> users = (Collection<User>) this.userService.listUsers();
		Assertions.assertEquals(users.size(), 9);
	}

}
