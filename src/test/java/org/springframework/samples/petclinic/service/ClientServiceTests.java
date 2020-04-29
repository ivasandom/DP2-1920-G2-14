
package org.springframework.samples.petclinic.service;


import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.DocumentType;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientServiceTests {

	@Autowired
	protected ClientService clientService;


	@Test
	public void testCountWithInitialData() {
		int count = this.clientService.clientCount();
		Assertions.assertEquals(count, 2);
	}

	@ParameterizedTest
	@CsvSource({"pepegotera", "elenanito"})
	@Transactional
	void shouldFindClientByUsername(final String username) {
		Client client = this.clientService.findClientByUsername(username);
		Collection<Client> clients = (Collection<Client>) this.clientService.findAll();
		Assertions.assertTrue(clients.contains(client));
	}
	
	@Test
	@Transactional
	public void shouldSaveClient() {
		Collection<Client> clients = (Collection<Client>) this.clientService.findAll();
		int found = clients.size();

		Client client = new Client();
		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		client.setBirthDate(birthdate);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.nif);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance("Adeslas");
		client.setLastName("Cuesta");
		Date registrationDate = new Date(2020-03-03);
		client.setRegistrationDate(registrationDate);
		
		Set<Appointment> appointments = Collections.emptySet();
		client.setAppointments(appointments);
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		client.setUser(user);

		this.clientService.saveClient(client);
		org.assertj.core.api.Assertions.assertThat(client.getId().longValue()).isNotEqualTo(0);

		clients = (Collection<Client>) this.clientService.findAll();
		org.assertj.core.api.Assertions.assertThat(clients.size()).isEqualTo(found + 1);
	}
}
