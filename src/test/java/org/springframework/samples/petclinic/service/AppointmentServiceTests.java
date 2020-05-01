
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Receipt;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.model.PaymentIntent;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentServiceTests {

	@Autowired
	protected AppointmentService	appointmentService;

	@Autowired
	protected ProfessionalService	professionalService;

	@Autowired
	protected CenterService			centerService;

	@Autowired
	protected ClientService			clientService;

	@Autowired
	protected SpecialtyService		specialtyService;

	@Autowired
	protected MedicineService		medicineService;

	@Autowired
	protected DeseaseService		deseaseService;

	@Autowired
	protected DiagnosisService		diagnosisService;

	@Autowired
	protected StripeService			stripeService;

	@Autowired
	protected TransactionService	transactionService;


	@Test
	void shouldFindAllAppointments() {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(11);
	}

	@Test
	void shouldFindAppointmentsStartTimeByProfessionalAndDate() {
		Professional professional = this.professionalService.findById(1).get();
		LocalDate date = LocalDate.of(2020, 12, 12);
		Collection<LocalTime> startTimes = this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional);

		Assertions.assertThat(startTimes.size()).isEqualTo(2);
	}

	@ParameterizedTest
	@CsvSource({
		"1, 2020-05-04", "2, 2020-12-11", "3, 2030-04-02"
	})
	void shouldNotFindAppointmentsStartTimeByWrongProfessionalAndDate(final int professionalId, final LocalDate date) {
		Professional professional = this.professionalService.findById(professionalId).get();
		Collection<LocalTime> startTimes = this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional);

		Assertions.assertThat(startTimes.size()).isEqualTo(0);

	}

	@Test
	void shouldFindAppointmentsByClientId() {

		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.findAppointmentByUserId(1);
		Assertions.assertThat(appointments.size()).isEqualTo(6);

		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.of(2020, 12, 12));
		Assertions.assertThat(appointments.iterator().next().getStartTime()).isEqualTo(LocalTime.of(8, 00));

		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getDate()).isEqualTo(LocalDate.of(2020, 12, 12));
		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getStartTime()).isEqualTo(LocalTime.of(8, 15));
	}

	@Test
	void shouldFindAppointmentsByProfessionalId() {
		
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.findAppointmentByProfessionalId(3);
		Assertions.assertThat(appointments.size()).isEqualTo(7);
		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.of(2020, 12, 12));
		Assertions.assertThat(appointments.iterator().next().getStartTime()).isEqualTo(LocalTime.of(8, 15));

		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getDate()).isEqualTo(LocalDate.of(2020, 12, 12));
		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getStartTime()).isEqualTo(LocalTime.of(8, 45));

	}

	@Test
	void shouldFindAppointmentTypes() {
		Collection<String> types = this.appointmentService.findAppointmentByTypes();

		// Create list of types ordered by name, as the query says
		List<String> items = new ArrayList<>();
		items.add("analisis");
		items.add("another case");
		items.add("checking");
		items.add("consultation for prescription issuance");
		items.add("illness consultation");
		items.add("periodic consultation");
		items.add("vaccination");

		Collection<String> expected = new ArrayList<>();
		expected.addAll(items);

		Assertions.assertThat(types.size()).isEqualTo(7);
		Assertions.assertThat(types).containsExactlyElementsOf(expected);
	}

	@Test
	void shouldFindTodayPendingAppointmentsByProfessionalId() {
		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);
		// Set date to today
		appointment.setDate(LocalDate.now());
		// Set status to pending
		appointment.setStatus(AppointmentStatus.PENDING);
		appointment.setStartTime(LocalTime.of(10, 15));

		this.appointmentService.saveAppointment(appointment);
		Collection<Appointment> appointments = this.appointmentService.findTodayPendingByProfessionalId(1);
		Assertions.assertThat(appointments.size()).isEqualTo(1);
		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.now());
	}

	@Test
	void shouldFindTodayCompletedAppointmentsByProfessionalId() {
		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);
		// Set date to today
		appointment.setDate(LocalDate.now());
		// Set status to completed
		appointment.setStatus(AppointmentStatus.COMPLETED);
		appointment.setStartTime(LocalTime.of(10, 15));

		this.appointmentService.saveAppointment(appointment);
		Collection<Appointment> appointments = this.appointmentService.findTodayCompletedByProfessionalId(1);
		Assertions.assertThat(appointments.size()).isEqualTo(1);
		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.now());
	}

	@ParameterizedTest
	@CsvSource({
		"pepegotera, 2020-12-12, 08:00, test"
	})
	void shouldFindAppointmentById(final String username, final LocalDate date, final LocalTime startTime, final String reason) {
		Appointment appointmentFromQuery = this.appointmentService.findAppointmentById(1);

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername(username);
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);
		appointment.setDate(date);
		appointment.setStatus(AppointmentStatus.PENDING);
		appointment.setStartTime(startTime);
		appointment.setReason(reason);

		Assertions.assertThat(appointmentFromQuery.getProfessional()).isEqualTo(appointment.getProfessional());
		Assertions.assertThat(appointmentFromQuery.getCenter()).isEqualTo(appointment.getCenter());
		Assertions.assertThat(appointmentFromQuery.getClient()).isEqualTo(appointment.getClient());
		Assertions.assertThat(appointmentFromQuery.getSpecialty()).isEqualTo(appointment.getSpecialty());
		Assertions.assertThat(appointmentFromQuery.getDate()).isEqualTo(appointment.getDate());
		Assertions.assertThat(appointmentFromQuery.getStatus()).isEqualTo(appointment.getStatus());
		Assertions.assertThat(appointmentFromQuery.getStartTime()).isEqualTo(appointment.getStartTime());
		Assertions.assertThat(appointmentFromQuery.getReason()).isEqualTo(appointment.getReason());
	}

	@ParameterizedTest
	@CsvSource({
		"-1", "1000", "1000000000"
	})
	void shouldNotFindAppointmentWithWrongId(final int id) {
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.appointmentService.findAppointmentById(id);
		});
	}

	@Test
	void shouldfindMedicines() {
		Collection<Medicine> medicines = this.appointmentService.findMedicines(1);
		Assertions.assertThat(medicines.size()).isEqualTo(2);
	}

	@Test
	void shouldFindDeseases() {
		Collection<Desease> deseasees = this.appointmentService.findDeseases(1);
		Assertions.assertThat(deseasees.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@CsvSource({
		"pepegotera, 2020-11-11, 10:15"
	})
	@Transactional
	public void shouldSaveAppointment(final String username, final LocalDate date, final LocalTime startTime) {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		int found = appointments.size();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername(username);
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setDate(date);
		appointment.setSpecialty(specialty);
		appointment.setStartTime(startTime);

		this.appointmentService.saveAppointment(appointment);
		Assertions.assertThat(appointment.getId().longValue()).isNotEqualTo(0);

		appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(found + 1);
	}

	@ParameterizedTest
	@CsvSource({
		"pepegotera, 2020-11-11, 10:15, 2020-11-11, Diagnosis test"
	})
	@Transactional
	public void shouldSaveAppointmentPlusDiagnosis(final String username, final LocalDate date, final LocalTime startTime, final LocalDate diagnosisDate, final String diagnosisDescription) {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		int found = appointments.size();
		Collection<Diagnosis> diagnosisCollection = (Collection<Diagnosis>) this.diagnosisService.findAll();
		int found2 = diagnosisCollection.size();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername(username);
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setDate(date);
		appointment.setSpecialty(specialty);
		appointment.setStartTime(startTime);

		//Now the service will call the save method of diagnosis service to save this diagnosis
		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setDate(diagnosisDate);
		diagnosis.setDescription(diagnosisDescription);
		appointment.setDiagnosis(diagnosis);

		this.appointmentService.saveAppointment(appointment);
		Assertions.assertThat(appointment.getId().longValue()).isNotEqualTo(0);

		appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(found + 1);

		this.diagnosisService.saveDiagnosis(appointment.getDiagnosis());
		diagnosisCollection = (Collection<Diagnosis>) this.diagnosisService.findAll();
		Assertions.assertThat(diagnosisCollection.size()).isEqualTo(found2 + 1);
	}

	@ParameterizedTest
	@CsvSource({
		"pepegotera, 2020-11-11, 10:15"
	})
	@Transactional
	public void shouldChargeAppointment(final String username, final LocalDate date, final LocalTime startTime) throws Exception {
		Collection<Transaction> transactions = (Collection<Transaction>) this.transactionService.listTransactions();
		int found = transactions.size();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername(username);
		Collection<PaymentMethod> paymentMethods = client.getPaymentMethods();
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		System.out.println("================================================" + paymentMethods.stream().collect(Collectors.toList()).get(0));
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setDate(date);
		appointment.setSpecialty(specialty);
		appointment.setStartTime(startTime);

		//Receipt must be created for the method
		Receipt receipt = new Receipt();
		receipt.setPrice(100.);
		appointment.setReceipt(receipt);

		//We obtain the paymentIntent
		PaymentMethod primary = paymentMethods.iterator().next();
		PaymentIntent paymentIntent = this.stripeService.charge(primary.getToken(), appointment.getReceipt().getPrice(), appointment.getClient().getStripeId());

		//Later, we create a transaction
		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.CHARGE);
		transaction.setReceipt(appointment.getReceipt());
		transaction.setToken(paymentIntent.getId());
		transaction.setAmount((double) paymentIntent.getAmount() / 100);
		transaction.setStatus(paymentIntent.getStatus());
		transaction.setSuccess(paymentIntent.getStatus().equals("succeeded"));
		this.transactionService.saveTransaction(transaction);

		transactions = (Collection<Transaction>) this.transactionService.listTransactions();
		Assertions.assertThat(transactions.size()).isEqualTo(found + 1);
	}
}
