package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ClientValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Client generateClient() {
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

		return client;
	}

	@Test
	void shouldNotValidateWhenBirthDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date birthdate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		client.setBirthDate(birthdate);
		System.out.println(client.getBirthDate());
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Client> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenBirthDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date birthdate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();
		client.setBirthDate(birthdate);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateWhenFirstNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setFirstName("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenLastNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setLastName("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setEmail("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailDoesNotHaveCorrectFormat() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setEmail("allaroundmearefamiliarfaces.com");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		assertThat(violation.getMessage()).isEqualTo("choose the correct format");
	}
	
	@Test
	void shouldNotValidateWhenDocumentIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setDocument("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("document");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenDocumentTypeIsNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setDocumentType(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("documentType");
		assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenRegistrationDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date registrationDate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		client.setRegistrationDate(registrationDate);
		System.out.println(client.getRegistrationDate());
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Client> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("registrationDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenRegistrationDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		Date registrationDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
		client.setRegistrationDate(registrationDate);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateHealthInsuranceIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Client client = this.generateClient();
		client.setHealthInsurance("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("healthInsurance");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
}
