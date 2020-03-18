package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AppointmentServiceTests {

	@Autowired
	protected AppointmentService appointmentService;
	
	@Autowired
	protected ProfessionalService professionalService;
	
	@Autowired
	protected CenterService centerService;
	
	@Autowired
	protected ClientService clientService;
	
	@Autowired
	protected SpecialtyService specialtyService;
	
	@Test
	void shouldFindAllAppointments() {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(12);
	}
	
	@Test
	void shouldFindAppointmentsStartTimeByProfessionalAndDate() {
		Professional professional = this.professionalService.findById(1).get();
		LocalDate date = LocalDate.of(2020, 12, 12);
		Collection<LocalTime> startTimes = appointmentService.findAppointmentStartTimesByProfessionalAndDate(date, professional);
		Assertions.assertThat(startTimes.size()).isEqualTo(12);
	}
	
	@Test
	@Transactional
	public void shouldSaveAppointment() {
		Collection<Appointment> appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		int found = appointments.size();
		
		Appointment appointment = new Appointment();
		Professional professional = this.professionalService.findById(1).get();
		Center center = centerService.findCenterById(1).get();
		Client client = clientService.findClientByUsername("pepegotera");
		Specialty specialty = specialtyService.findSpecialtyById(1).get();
		appointment.setProfessional(professional);
		appointment.setCenter(center);
		appointment.setClient(client);
		appointment.setDate(LocalDate.of(2020, 11, 11));
		appointment.setSpecialty(specialty);
		appointment.setStartTime(LocalTime.of(10, 15));
		appointment.setId(13);
		
		this.appointmentService.saveAppointment(appointment);
		Assertions.assertThat(appointment.getId().longValue()).isNotEqualTo(0);
		
		appointments = (Collection<Appointment>) this.appointmentService.listAppointments();
		Assertions.assertThat(appointments.size()).isEqualTo(found + 1);
	}
}
