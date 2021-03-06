
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class AppointmentValidatorTests {

	@Autowired
	private AppointmentValidator	appointmentValidator	= new AppointmentValidator();

	@Autowired
	private Appointment				appointment;

	@Autowired
	private Errors					errors;

	private static final String					STARTTIME				= "startTime";

	private static final String					CENTER					= "center";

	private static final String					SPECIALTY				= "specialty";

	private static final String					PROFESSIONAL			= "professional";

	private static final String					DATE					= "date";


	@BeforeEach
	private void generateAppointment() {
		this.appointment = new Appointment();
		this.appointment.setDate(LocalDate.of(2020, 12, 03));
		this.appointment.setReason("my head hurts");
		this.appointment.setStartTime(LocalTime.of(10, 15));

		this.appointment.setBill(null);
		this.appointment.setDiagnosis(null);
		this.appointment.setClient(null);
		this.appointment.setType(AppointmentType.PERIODIC_CONSULTATION);

		this.appointment.setStatus(AppointmentStatus.COMPLETED);

		Center center = new Center();
		center.setAddress("Sevilla");
		this.appointment.setCenter(center);

		Specialty specialty = new Specialty();
		specialty.setName("dermatology");
		this.appointment.setSpecialty(specialty);

		Client client = new Client();
		client.setEmail("client@gmail.com");
		this.appointment.setClient(client);

		Professional professional = new Professional();
		professional.setCenter(center);
		professional.setSpecialty(specialty);
		professional.setFirstName("Manuel");
		professional.setLastName("Carrasco");
		professional.setEmail("mancar@gmail.com");
		professional.setDocument("29334485");
		professional.setDocumentType(DocumentType.NIF);
		professional.setCollegiateNumber("413123122-K");
		this.appointment.setProfessional(professional);

		this.errors = new BeanPropertyBindingResult(this.appointment, "");
	}

	@Test
	void shouldValidateCorrectAppointment() {
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateEmptyDate() {
		this.appointment.setDate(null);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(DATE).getCode()).isEqualTo("date must no be empty");
	}

	@Test
	void shouldNotValidateEmptyStartTime() {
		this.appointment.setStartTime(null);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(STARTTIME).getCode()).isEqualTo("start time must no be empty");
	}

	@Test
	void shouldNotValidateEmptyCenter() {
		this.appointment.setCenter(null);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(CENTER).getCode()).isEqualTo("center must no be empty");
	}

	@Test
	void shouldNotValidateEmptySpecialty() {
		this.appointment.setSpecialty(null);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(SPECIALTY).getCode()).isEqualTo("specialty must no be empty");
	}

	@Test
	void shouldNotValidateEmptyProfessional() {
		this.appointment.setProfessional(null);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(PROFESSIONAL).getCode()).isEqualTo("professional must no be empty");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"10:11",//
		"12:25",//
		"15:38",//
		"18:54"
	})
	void shouldNotValidateStartTimeIsNotEvery15Minutes(final LocalTime stTime) {
		this.appointment.setStartTime(stTime);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(STARTTIME).getCode()).isEqualTo("appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"10:15",//
		"12:30",//
		"15:45",//
		"18:00"
	})
	void shouldValidateStartTimeIsEvery15Minutes(final LocalTime stTime) {
		this.appointment.setStartTime(stTime);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"06:45",//
		"20:30",//
		"20:00"
	})
	void shouldNotValidateStartTimeIsNotInSchedule(final LocalTime stTime) {
		this.appointment.setStartTime(stTime);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(STARTTIME).getCode()).isEqualTo("our clinics are open from 8 a.m to 8 p.m");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"08:00",//
		"12:30",//
		"15:45",//
		"19:45"
	})
	void shouldValidateStartTimeIsInSchedule(final LocalTime stTime) {
		this.appointment.setStartTime(stTime);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateWhenDateIsInPast() {
		this.appointment.setDate(LocalDate.of(2020, 03, 03));
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(DATE).getCode()).isEqualTo("the date must be in future");

		// Yesterday
		Date date1 = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
		this.appointment.setDate(date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(DATE).getCode()).isEqualTo("the date must be in future");

		// One second before now
		Date date2 = new Date(System.currentTimeMillis() - 1);
		this.appointment.setDate(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(DATE).getCode()).isEqualTo("the date must be in future");

		// Min date
		this.appointment.setDate(LocalDate.MIN);
		this.appointmentValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(DATE).getCode()).isEqualTo("the date must be in future");
	}

	@Test
	void shouldNotValidateWhenAppointmentCenterIsNotEqualsToProfessionalCenter() {
		Center center1 = new Center();
		center1.setAddress("Sevilla");
		this.appointment.setCenter(center1);

		Center center2 = new Center();
		center2.setAddress("Cádiz");
		this.appointment.getProfessional().setCenter(center2);

		this.appointmentValidator.validate(this.appointment, this.errors);
		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(CENTER).getCode()).isEqualTo("appointment center must be equal to professional center");
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"dermatology",//
		"dentistry"
	})
	void shouldNotValidateWhenAppointmentSpecialtyIsNotEqualsToProfessionalSpecialty() {

		Specialty specialty1 = new Specialty();
		specialty1.setName("dermatology");
		this.appointment.setSpecialty(specialty1);

		Specialty specialty2 = new Specialty();
		specialty2.setName("surgery");
		this.appointment.getProfessional().setSpecialty(specialty2);

		System.out.println(this.appointment.getSpecialty().getName());
		System.out.println(this.appointment.getProfessional().getSpecialty().getName());
		this.appointmentValidator.validate(this.appointment, this.errors);
		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError(SPECIALTY).getCode()).isEqualTo("appointment specialty must be equal to professional specialty");

	}
}
