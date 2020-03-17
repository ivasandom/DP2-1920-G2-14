
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.repository.AppointmentRepository;
import org.springframework.samples.petclinic.repository.CenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private CenterRepository centerRepository;
	
	@Autowired
	public AppointmentService(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Center> listCenters() throws DataAccessException {
		return centerRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Appointment> listAppointments() throws DataAccessException {
		return appointmentRepository.findAll();
	}
	@Transactional
	public void saveAppointment(Appointment appointment) throws DataAccessException {
		appointmentRepository.save(appointment);
	}		
}
