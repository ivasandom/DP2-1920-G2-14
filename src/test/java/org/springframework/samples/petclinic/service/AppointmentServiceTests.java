
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.AppointmentStatus;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.projections.ListAppointmentsClient;
import org.springframework.samples.petclinic.service.exceptions.ProfessionalBusyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

	@Value("${STRIPE_PUBLIC_KEY}")
	private String					API_PUBLIC_KEY;


	@Test
	void shouldFindAllAppointments() {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(130);
	}

	@Test
	void shouldFindAppointmentsStartTimeByProfessionalAndDate() {
		Professional professional = this.professionalService.findById(1).get();
		LocalDate date = LocalDate.of(2020, 05, 04);
		Collection<LocalTime> startTimes = this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional);

		Assertions.assertThat(startTimes.iterator().next().getHour()).isEqualTo(8);
		Assertions.assertThat(startTimes.iterator().next().getMinute()).isEqualTo(00);
	}

	@ParameterizedTest
	@CsvSource({
		"1, 2020-04-05", "2, 2020-04-06", "3, 2030-04-07"
	})
	void shouldNotFindAppointmentsStartTimeByWrongProfessionalAndDate(final int professionalId, final LocalDate date) {
		Professional professional = this.professionalService.findById(professionalId).get();
		Collection<LocalTime> startTimes = this.appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional);

		Assertions.assertThat(startTimes.size()).isEqualTo(0);

	}

	@Test
	void shouldFindAppointmentsByClientId() {

		Collection<ListAppointmentsClient> appointments = this.appointmentService.findAppointmentByClientId(1);
		Assertions.assertThat(appointments.size()).isEqualTo(124);

		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.of(2020, 02, 02));
		Assertions.assertThat(appointments.iterator().next().getStartTime()).isEqualTo(LocalTime.of(8, 00));

		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getDate()).isEqualTo(LocalDate.of(2020, 02, 20));
		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getStartTime()).isEqualTo(LocalTime.of(8, 30));
	}

	@Test
	void shouldFindAppointmentsByProfessionalId() {

		Collection<Appointment> appointments = this.appointmentService.findAppointmentByProfessionalId(3);
		Assertions.assertThat(appointments.size()).isEqualTo(6);
		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.of(2020, 12, 12));
		Assertions.assertThat(appointments.iterator().next().getStartTime()).isEqualTo(LocalTime.of(8, 45));

		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getDate()).isEqualTo(LocalDate.of(2020, 02, 12));
		Assertions.assertThat(appointments.stream().skip(1).collect(Collectors.toList()).get(0).getStartTime()).isEqualTo(LocalTime.of(8, 15));

	}

	@Test
	@Transactional
	public void shouldFindTodayPendingAppointmentsByProfessionalId() throws DataAccessException, ProfessionalBusyException {
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
		Assertions.assertThat(appointments.size()).isEqualTo(2);
		Assertions.assertThat(appointments.iterator().next().getDate()).isEqualTo(LocalDate.now());
	}

	@Test
	@Transactional
	public void shouldFindTodayCompletedAppointmentsByProfessionalId() throws DataAccessException, ProfessionalBusyException {
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
		"pepegotera, 2020-05-04, 08:00, test"
	})
	void shouldFindAppointmentById(final String username, final LocalDate date, final LocalTime startTime, final String reason) {
		Appointment appointmentFromQuery = this.appointmentService.findAppointmentById(1).get();

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
		appointment.setStatus(AppointmentStatus.COMPLETED);
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
		Assertions.assertThat(this.appointmentService.findAppointmentById(id).isPresent()).isEqualTo(false);
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
	public void shouldSaveAppointment(final String username, final LocalDate date, final LocalTime startTime) throws DataAccessException, ProfessionalBusyException {
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

	@Test
	@Transactional
	public void shouldNotSaveAppointment() throws DataAccessException, ProfessionalBusyException {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		int found = appointments.size();

		Optional<Appointment> a = this.appointmentService.findAppointmentById(1);

		Appointment appointment = new Appointment();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(a.get().getProfessional());
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setDate(a.get().getDate());
		appointment.setSpecialty(specialty);
		appointment.setStartTime(a.get().getStartTime());

		org.junit.jupiter.api.Assertions.assertThrows(ProfessionalBusyException.class, () -> this.appointmentService.saveAppointment(appointment));

		appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(found);
	}

	@ParameterizedTest
	@CsvSource({
		"pepegotera, 2020-11-11, 10:15, 2020-11-11, Diagnosis test"
	})
	@Transactional
	public void shouldSaveAppointmentPlusDiagnosis(final String username, final LocalDate date, final LocalTime startTime, final LocalDate diagnosisDate, final String diagnosisDescription) throws DataAccessException, ProfessionalBusyException {
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

		// Now the service will call the save method of diagnosis service to save this
		// diagnosis
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
		"123, pepegotera", "122, pepegotera"
	})
	@Transactional
	void shouldDeleteAppointment(final int id, final String username) throws Exception {

		Client client = this.clientService.findClientByUsername(username);

		Collection<ListAppointmentsClient> appointments = this.appointmentService.findAppointmentByClientId(client.getId());
		Optional<Appointment> appointment = this.appointmentService.findAppointmentById(id);

		int count = appointments.size();

		org.assertj.core.api.Assertions.assertThat(appointment).isPresent();

		this.appointmentService.delete(appointment.get());

		appointments = this.appointmentService.findAppointmentByClientId(client.getId());
		org.assertj.core.api.Assertions.assertThat(appointments.size()).isEqualTo(count - 1);

	}

	@Test
	@Transactional
	void shouldNotDeleteAppointment() {

		org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
			this.appointmentService.delete(null);
		});

	}

	@ParameterizedTest
	@CsvSource({
		"124", "126"
	})
	@Transactional
	void shouldNotDeletePassedApplication(final int id) throws Exception {

		Optional<Appointment> app = this.appointmentService.findAppointmentById(id);

		org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
			this.appointmentService.delete(app.get());
		}, "You cannot delete a passed appointment");
	}

	@Test
	@Transactional
	void testGetNumberOfPendingAppointments() throws DataAccessException, ProfessionalBusyException {
		Long pendingAppointments = this.appointmentService.getNumberOfPendingAppointments();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);

		appointment.setStatus(AppointmentStatus.PENDING);
		this.appointmentService.saveAppointment(appointment);

		Assertions.assertThat(pendingAppointments).isEqualTo(127L);
		Long newPendingAppointments = this.appointmentService.getNumberOfPendingAppointments();
		Assertions.assertThat(newPendingAppointments).isEqualTo(pendingAppointments + 1L);
	}

	@Test
	@Transactional
	void testGetNumberOfAbsentAppointments() throws DataAccessException, ProfessionalBusyException {
		Long absentAppointments = this.appointmentService.getNumberOfAbsentAppointments();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);

		appointment.setStatus(AppointmentStatus.ABSENT);
		this.appointmentService.saveAppointment(appointment);

		Assertions.assertThat(absentAppointments).isEqualTo(0L);
		Long newAbsentAppointments = this.appointmentService.getNumberOfAbsentAppointments();
		Assertions.assertThat(newAbsentAppointments).isEqualTo(absentAppointments + 1L);
	}

	@Test
	@Transactional
	void testGetNumberOfCompletedAppointments() throws DataAccessException, ProfessionalBusyException {
		Long completedAppointments = this.appointmentService.getNumberOfCompletedAppointments();

		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = this.centerService.findCenterById(1).get();
		Client client = this.clientService.findClientByUsername("pepegotera");
		Specialty specialty = this.specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setSpecialty(specialty);

		appointment.setStatus(AppointmentStatus.COMPLETED);
		this.appointmentService.saveAppointment(appointment);

		Assertions.assertThat(completedAppointments).isEqualTo(3L);
		Long newCompletedAppointments = this.appointmentService.getNumberOfCompletedAppointments();
		Assertions.assertThat(newCompletedAppointments).isEqualTo(completedAppointments + 1L);

	}

	@Test
	@Transactional
	public void shouldFindAppointmentByDateTimeAndProfessional() throws DataAccessException, ProfessionalBusyException {
		Appointment appointment = this.appointmentService.findAppointmentById(1).get();

		Optional<Appointment> a = this.appointmentService.findAppointmentByDateTimeAndProfessional(appointment.getDate(), appointment.getStartTime(), appointment.getProfessional());

		Assertions.assertThat(a).isPresent();
		Assertions.assertThat(a.get()).isEqualTo(appointment);

	}

}
