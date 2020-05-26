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

public class ProfessionalValidatorTests {

	@Autowired
	private ProfessionalValidator professionalValidator = new ProfessionalValidator();
	
	@Autowired
	private Errors errors;
	
	private Professional generateProfessional() {
		
		Date birthdate = new GregorianCalendar(1999, Calendar.FEBRUARY, 11).getTime();
		Date registrationDate = new Date(2020-03-03);
		Set<Appointment> appointments = Collections.emptySet();
		
		User user = new User();
		user.setEnabled(true);
		user.setUsername("frankcuesta");
		user.setPassword("frankcuesta");
		
		Professional professional =new Professional();
		professional.setBirthDate(birthdate);
		professional.setDocument("29334456");
		professional.setDocumentType(DocumentType.NIF);
		professional.setEmail("frankcuesta@gmail.com");
		professional.setFirstName("Frank");
		professional.setLastName("Cuesta");
		professional.setCollegiateNumber("77777777K");
		professional.setRegistrationDate(registrationDate);
		professional.setAppointments(appointments);
		professional.setUser(user);
		Center center = new Center();
		center.setName("Sevilla");
		professional.setCenter(center);
		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		professional.setSpecialty(specialty);
		
		errors = new BeanPropertyBindingResult(professional, "");

		return professional;
	}

	@Test
	void shouldNotValidateWhenBirthDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		Date birthdate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		professional.setBirthDate(birthdate);
		
		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenBirthDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		Date birthdate = new GregorianCalendar(1950, Calendar.FEBRUARY, 11).getTime();
		professional.setBirthDate(birthdate);

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateWhenFirstNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setFirstName("");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("firstName").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenLastNameIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setLastName("");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("lastName").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setEmail("");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("email").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenEmailDoesNotHaveCorrectFormat() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setEmail("allaroundmearefamiliarfaces.com");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("email").getCode()).isEqualTo("choose the correct format");
	}
	
	@Test
	void shouldNotValidateWhenDocumentIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setDocument("");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("document").getCode()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateWhenDocumentTypeIsNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setDocumentType(null);

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("documentType").getCode()).isEqualTo("must not be null");
	}
	
	@Test
	void shouldNotValidateWhenRegistrationDateIsInFuture() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		Date registrationDate = new GregorianCalendar(2030, Calendar.FEBRUARY, 11).getTime();
		professional.setRegistrationDate(registrationDate);
		System.out.println(professional.getRegistrationDate());

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("registrationDate").getCode()).isEqualTo("the date must be in past");
	}
	
	@Test
	void shouldValidateWhenRegistrationDateIsInPast() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		Date registrationDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
		professional.setRegistrationDate(registrationDate);

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateCollegiateNumberIsEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Professional professional = this.generateProfessional();
		professional.setCollegiateNumber("");

		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("collegiateNumber").getCode()).isEqualTo("must not be empty");
	}
	
	
	/**
	 *  Validate HealthInsurance tests
	 */
	
	
	@Test
	void shouldNotValidateEmptyUsername() {
		User user = new User();
		user.setUsername(" ");
		user.setPassword("p455w0rd");
		
		Professional professional = this.generateProfessional();
		professional.setUser(user);
		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.username").getCode()).isEqualTo("username must not be empty");
	}	

	@Test
	void shouldNotValidateEmptyPassword() {
		User user = new User();
		user.setUsername("frankcuesta");
		user.setPassword(" ");
		
		Professional professional = this.generateProfessional();
		professional.setUser(user);
		
		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("user.password").getCode()).isEqualTo("password must not be empty");
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
		
		Professional professional = this.generateProfessional();
		professional.setUser(user);
		
		professionalValidator.validate(professional, errors);

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
		
		Professional professional = this.generateProfessional();
		professional.setUser(user);
		
		professionalValidator.validate(professional, errors);

		assertThat(errors.getErrorCount()).isEqualTo(0);
	}
}
