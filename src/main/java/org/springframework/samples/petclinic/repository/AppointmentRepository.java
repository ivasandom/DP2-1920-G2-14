
package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Professional;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	@Query("SELECT a.startTime FROM Appointment a WHERE a.date = :date and a.professional = :professional")
	Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(@Param("date") LocalDate date, @Param("professional") Professional professional);

	@Query("SELECT a FROM Appointment a WHERE a.client.id = :id")
	Collection<Appointment> findAppointmentByClientId(@Param("id") int clientId);

	@Query("SELECT a FROM Appointment a WHERE a.professional.id = :id")
	Collection<Appointment> findAppointmentByProfessionalId(@Param("id") int professionalId);
	
	@Query("SELECT a FROM Appointment a WHERE a.professional.id = :id AND a.date = current_date() AND a.status != 'COMPLETED' ORDER BY a.status DESC,  a.startTime ASC")
	Collection<Appointment> findTodayPendingByProfessionalId(@Param("id") int professionalId);
	
	@Query("SELECT a FROM Appointment a WHERE a.professional.id = :id AND a.date = current_date() AND a.status = 'COMPLETED'")
	Collection<Appointment> findTodayCompletedByProfessionalId(@Param("id") int professionalId);

	@Query("SELECT DISTINCT type.name FROM AppointmentType type ORDER BY type.name")
	List<String> findAppointmentTypes() throws DataAccessException;
}
