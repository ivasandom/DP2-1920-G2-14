package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ClientValidatorTests {

	@Autowired
	private ClientValidator clientValidator = new ClientValidator();
	
	@Autowired
	private Errors errors;
	
	private Client generateClient() {
		
		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		Date registrationDate = new Date(2020-03-03);
		Set<Appointment> appointments = Collections.emptySet();
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		
		Client client = new Client();
		client.setBirthDate(birthdate);
		client.setDocument("29334456");
		client.setDocumentType(DocumentType.nif);
		client.setEmail("frankcuesta@gmail.com");
		client.setFirstName("Frank");
		client.setHealthCardNumber("0000000003");
		client.setHealthInsurance(HealthInsurance.ADESLAS);
		client.setLastName("Cuesta");
		client.setRegistrationDate(registrationDate);
		client.setAppointments(appointments);
		client.setUser(user);
		
		errors = new BeanPropertyBindingResult(client, "");

		return client;
	}

	@Test
	void shouldNotValidateWhenBirthDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date birthdate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		client.setBirthDate(birthdate);
		
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenBirthDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date birthdate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();
		client.setBirthDate(birthdate);

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateWhenFirstNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setFirstName("");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("firstName").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenLastNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setLastName("");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("lastName").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setEmail("");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("email").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailDoesNotHaveCorrectFormat() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setEmail("allaroundmearefamiliarfaces.com");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("email").getCode()).isEqualTo("choose the correct format");
	}
	
	@Test
	void shouldNotValidateWhenDocumentIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setDocument("");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("document").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenDocumentTypeIsNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setDocumentType(null);

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("documentType").getCode()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenRegistrationDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date registrationDate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		client.setRegistrationDate(registrationDate);
		System.out.println(client.getRegistrationDate());
		
		clientValidator.validate(client, errors);
		System.out.println(errors.getAllErrors());
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("registrationDate").getCode()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenRegistrationDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date registrationDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
		client.setRegistrationDate(registrationDate);

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateHealthInsuranceIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setHealthInsurance(null);

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthInsurance").getCode()).isEqualTo("must not be empty");
	}
	
	
	@Test
	void shouldNotValidateEmptyUsername() {
		User user = new User();
		user.setUsername(" ");
		user.setPassword("p455w0rd");
		
		Client client = this.generateClient();
		client.setUser(user);
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.username").getCode()).isEqualTo("username must not be empty");
	}
	
	
	/**
	 *  Validate HealthInsurance tests
	 */

	
	@Test
	void shouldValidateCorrectHealth() {
		Client client = this.generateClient();
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateEmptyHealthInsurance() {
		Client client = this.generateClient();
		client.setHealthInsurance(null); // check
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthInsurance").getCode()).isEqualTo("health insurance must not be empty. In case you don't have any, write 'I do not have insurance'");
	}	

	@Test
	void shouldNotValidateEmptyPassword() {
		User user = new User();
		user.setUsername("frankcuesta");
		user.setPassword(" ");
		
		Client client = this.generateClient();
		client.setUser(user);
		
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.password").getCode()).isEqualTo("password must not be empty");
	}


	@Test
	void shouldNotValidateHealthCardNumberWhenYouDoNotHaveHealthInsurance() {
		Client client = this.generateClient();
		client.setHealthInsurance(HealthInsurance.I_DO_NOT_HAVE_INSURANCE);
		client.setHealthCardNumber("0290239");

		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("healthCardNumber").getCode()).isEqualTo("you cannot write a health card number if you don't have health insurance");
	}
	
	@Test
	void shouldNotValidateHealthCardNumberEmptyWhenYouHaveHealthInsurance() {
		Client client = this.generateClient();
		client.setHealthInsurance(HealthInsurance.MAPFRE);
		client.setHealthCardNumber(null);

		clientValidator.validate(client, errors);

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
		
		Client client = this.generateClient();
		client.setUser(user);
		
		clientValidator.validate(client, errors);

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
		
		Client client = this.generateClient();
		client.setUser(user);
		
		clientValidator.validate(client, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
}
