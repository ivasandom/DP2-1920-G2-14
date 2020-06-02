
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ConsultationValidatorTests {

	@Autowired
	private ConsultationValidator consultationValidator = new ConsultationValidator();
	
	@Autowired
	private Appointment appointment;

	@Autowired
	private Errors errors;

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
		
		// Consultation data
		Desease desease = new Desease();
		desease.setId(1);
		desease.setName("Desease name");
		Set<Desease> deseases = new HashSet<Desease>();
		deseases.add(desease);
		
		Medicine medicine = new Medicine();
		medicine.setName("Medicine name");
		Set<Medicine> medicines = new HashSet<Medicine>();
		medicines.add(medicine);
		
		Bill bill = new Bill();
		bill.setIva(21.0);
		bill.setPrice(20.0);
		
		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setDescription("Diagnosis description");
		diagnosis.setDeseases(deseases);
		diagnosis.setMedicines(medicines);
		
		this.appointment.setBill(bill);
		this.appointment.setDiagnosis(diagnosis);

		this.errors = new BeanPropertyBindingResult(this.appointment, "");
	}

	@Test
	void shouldValidateCorrectConsultationData() {
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);
	}
	
	@Test
	void shouldNotValidateEmptyDiagnosis() {
		this.appointment.setDiagnosis(null);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(3);
		Assertions.assertThat(this.errors.getFieldError("diagnosis.description").getCode()).isEqualTo("description must no be empty");
		Assertions.assertThat(this.errors.getFieldError("diagnosis.deseases").getCode()).isEqualTo("deseases must no be empty");
		Assertions.assertThat(this.errors.getFieldError("diagnosis.medicines").getCode()).isEqualTo("medicines must no be empty");
	}
	
	@Test
	void shouldNotValidateEmptyBill() {
		this.appointment.setBill(null);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill").getCode()).isEqualTo("bill must not be empty");
	}

	@Test
	void shouldNotValidateEmptyDiagnosisDescription() {
		this.appointment.getDiagnosis().setDescription("");
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("diagnosis.description").getCode()).isEqualTo("description must no be empty");
	}

	@Test
	void shouldNotValidateEmptyDiagnosisDeseases() {
		this.appointment.getDiagnosis().setDeseases(new HashSet<Desease>());
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("diagnosis.deseases").getCode()).isEqualTo("deseases must no be empty");
	}
	
	@Test
	void shouldNotValidateEmptyDiagnosisMedicines() {
		this.appointment.getDiagnosis().setMedicines(new HashSet<Medicine>());
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("diagnosis.medicines").getCode()).isEqualTo("medicines must no be empty");
	}
	
	@Test
	void shouldNotValidateEmptyPrice() {
		this.appointment.getBill().setPrice(null);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill.price").getCode()).isEqualTo("price must not be empty");
	}
	
	@Test
	void shouldNotValidateEmptyIva() {
		this.appointment.getBill().setIva(null);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill.iva").getCode()).isEqualTo("iva must not be empty");
	}

	@ParameterizedTest
	@CsvSource({
		"-1.",
		"0."
	})
	void shouldNotValidateNegativePrice(final Double price) {
		this.appointment.getBill().setPrice(price);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill.price").getCode()).isEqualTo("price must be positive");
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1.",
		"101."
	})
	void shouldNotValidateInvalidIva(final Double iva) {
		this.appointment.getBill().setIva(iva);
		this.consultationValidator.validate(this.appointment, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill.iva").getCode()).isEqualTo("iva must be between 0 and 100");
	}

	

}
