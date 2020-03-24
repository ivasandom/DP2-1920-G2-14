package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class HealthValidatorTests {

	@Autowired
	private HealthValidator healthValidator = new HealthValidator();

	@Autowired
	private Client client;

	@Autowired
	private Errors errors;

	@BeforeEach
	private void generateClient() {
		this.client= new Client();
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

		this.errors =  new BeanPropertyBindingResult(this.client, "");
	}

	@Test
	void shouldValidateCorrectHealth() {
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateEmptyHealthInsurance() {
		this.client.setHealthInsurance(" ");
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthInsurance").getCode()).isEqualTo("health insurance must not be empty. In case you don't have any, write 'I do not have insurance'");
	}

	@Test
	void shouldNotValidateEmptyUsername() {
		User user = new User();
		user.setUsername(" ");
		user.setPassword("p455w0rd");
		this.client.setUser(user);
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.username").getCode()).isEqualTo("username must not be empty");
	}

	@Test
	void shouldNotValidateEmptyPassword() {
		User user = new User();
		user.setUsername("frankcuesta");
		user.setPassword(" ");
		this.client.setUser(user);
		
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.password").getCode()).isEqualTo("password must not be empty");
	}


	@Test
	void shouldNotValidateHealthCardNumberWhenYouDoNotHaveHealthInsurance() {
		this.client.setHealthInsurance("I do not have insurance");
		this.client.setHealthCardNumber("0290239");

		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthCardNumber").getCode()).isEqualTo("you cannot write a health card number if you don't have health insurance");
	}
	
	@Test
	void shouldNotValidateHealthCardNumberEmptyWhenYouHaveHealthInsurance() {
		this.client.setHealthInsurance("Mapfre");
		this.client.setHealthCardNumber("");

		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthCardNumber").getCode()).isEqualTo("you must write your health card number");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"pepe",//
			"pepe1",//
			"pepepepepepepepe",//
			"pepepepepepepepe1"
	})
	void shouldNotValidateWrongPassword(final String password) {
		User user = new User();
		user.setUsername("frankcuesta");
		user.setPassword(password);
		this.client.setUser(user);
		
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.password").getCode()).isEqualTo("password must be between 6 and 15 characters");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"pepe12",//
			"pepe123",//
			"pepepepepepepe",//
			"pepepepepepepep"
	})
	void shouldValidateCorrectPassword(final String password) {
		User user = new User();
		user.setUsername("frankcuesta");
		user.setPassword(password);
		this.client.setUser(user);
		
		healthValidator.validate(this.client, this.errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

}
