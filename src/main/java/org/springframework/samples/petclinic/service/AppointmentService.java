
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.projections.ListAppointmentsClient;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.samples.petclinic.service.exceptions.ProfessionalBusyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	@Autowired
	private DiagnosisService		diagnosisService;

	@Autowired
	private AppointmentRepository	appointmentRepository;


	public int appointmentCount() {
		return (int) this.appointmentRepository.count();
	}

	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return this.appointmentRepository.findAll();
	}

	public Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(final LocalDate date, final Professional professional) {
		return this.appointmentRepository.findAppointmentStartTimesByProfessionalAndDate(date, professional);
	}

	public Collection<ListAppointmentsClient> findAppointmentByClientId(final int id) {
		return this.appointmentRepository.findAppointmentByClientId(id);
	}

	public Collection<Appointment> findAppointmentByProfessionalId(final int id) {
		return this.appointmentRepository.findAppointmentByProfessionalId(id);
	}

	public Collection<Appointment> findTodayPendingByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayPendingByProfessionalId(id);
	}

	public Collection<Appointment> findTodayCompletedByProfessionalId(final int id) {
		return this.appointmentRepository.findTodayCompletedByProfessionalId(id);
	}

	public Optional<Appointment> findAppointmentById(final int id) {
		return this.appointmentRepository.findById(id);
	}

	public Collection<Medicine> findMedicines(final int id) {
		return this.appointmentRepository.findMedicines(id);
	}

	public Collection<Desease> findDeseases(final int id) {
		return this.appointmentRepository.findDeseases(id);
	}
	
	public Optional<Appointment> findAppointmentByDateTimeAndProfessional(LocalDate date, LocalTime startTime, 
			Professional professional) {
		return this.appointmentRepository.findAppointmentByDateTimeAndProfessional(date, startTime, professional);
	}

	@Transactional
	public void saveAppointment(final Appointment appointment) throws DataAccessException, ProfessionalBusyException {
		Optional<Appointment> existAppointment = this.findAppointmentByDateTimeAndProfessional(appointment.getDate(), appointment.getStartTime(), 
				appointment.getProfessional());
		
		if (existAppointment.isPresent() && !existAppointment.get().getId().equals(appointment.getId())) {
			throw new ProfessionalBusyException();
		} else {
			this.appointmentRepository.save(appointment);
			if (appointment.getDiagnosis() != null) {
				this.diagnosisService.saveDiagnosis(appointment.getDiagnosis());
			}
		}

	}


	@Transactional
	public void delete(final Appointment appointment) throws Exception {

		if (appointment.getDate().isBefore(LocalDate.now())) {
			throw new Exception("You cannot delete a passed appointment");
		} else if (appointment.getStartTime().isBefore(LocalTime.now()) && appointment.getDate().isEqual(LocalDate.now())) {
			throw new Exception("You cannot delete an appointment that was today");
		}
		this.appointmentRepository.delete(appointment);
	}

	public Long getNumberOfPendingAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfPendingAppointmentsByStatus();
	}

	public Long getNumberOfAbsentAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfAbsentAppointmentsByStatus();
	}

	public Long getNumberOfCompletedAppointments() throws DataAccessException {
		return this.appointmentRepository.getNumberOfCompletedAppointmentsByStatus();
	}

}
