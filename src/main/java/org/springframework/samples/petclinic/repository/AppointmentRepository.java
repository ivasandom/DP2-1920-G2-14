
package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Appointment;
import org.springframework.samples.petclinic.model.Professional;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
	
	@Query("SELECT a.startTime FROM Appointment a WHERE a.date = :date and a.professional = :professional")
	public Collection<LocalTime> findAppointmentStartTimesByProfessionalAndDate(@Param("date") LocalDate date, @Param("professional") Professional professional);

	@Query("SELECT a FROM Appointment a WHERE a.client.id = :id")
	public Collection<Appointment> findAppointmentByClientId(@Param("id") int clientId);

}
