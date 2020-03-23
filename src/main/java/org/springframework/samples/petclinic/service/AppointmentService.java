
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	
	@Autowired
	private DiagnosisService			diagnosisService;

	@Autowired
	private AppointmentRepository appointmentRepository;


	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return this.appointmentRepository.findAll();
	}

	@Transactional
	public Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(final LocalDate date, final Professional professional) {
		return this.appointmentRepository.findAppointmentStartTimesByProfessionalAndDate(date, professional);
	}

	@Transactional
	public Collection<Appointment> findAppointmentByUserId(final int id) {
		return this.appointmentRepository.findAppointmentByClientId(id);
	}

	@Transactional
	public Collection<Appointment> findAppointmentByProfessionalId(final int id) {
		return this.appointmentRepository.findAppointmentByProfessionalId(id);
	}

	@Transactional
	public Collection<String> findAppointmentByTypes() throws DataAccessException {
		return this.appointmentRepository.findAppointmentTypes();
	}

	@Transactional
	public Appointment findAppointmentById(final int id) {
		return this.appointmentRepository.findById(id).get();
	}

	@Transactional
	public void saveAppointment(final Appointment appointment) throws DataAccessException {
		appointmentRepository.save(appointment);
		
		if (appointment.getDiagnosis() != null) {
			this.diagnosisService.saveDiagnosis(appointment.getDiagnosis());
		}

		
	}

}
