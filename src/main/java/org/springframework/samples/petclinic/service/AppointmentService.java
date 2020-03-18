
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
	private AppointmentRepository appointmentRepository;

	
	@Autowired
	public AppointmentService(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return appointmentRepository.findAll();
	}
	
	@Transactional
	public  Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(LocalDate date, Professional professional) {
		return appointmentRepository.findAppointmentStartTimesByProfessionalAndDate(date, professional);
	}
	
	@Transactional
	public void saveAppointment(Appointment appointment) throws DataAccessException {
		appointmentRepository.save(appointment);
	}
	
}
