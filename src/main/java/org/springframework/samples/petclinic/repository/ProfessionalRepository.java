
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Professional;

public interface ProfessionalRepository extends CrudRepository<Professional, Integer> {

	@Query("SELECT professional FROM Professional professional WHERE professional.center.id = :centerId and professional.specialty.id = :specialtyId")
	Collection<Professional> findBySpecialtyAndCenter(@Param("specialtyId") int specialtyId, @Param("centerId") int centerId);

	@Query("SELECT professional FROM Professional professional WHERE professional.id = :id")
	Professional findProfessionalById(@Param("id") int id);

	@Query("SELECT professional FROM Professional professional WHERE professional.user.username =:username")
	Professional findProByUsername(@Param("username") String username);

	//	@Query("SELECT DISTINCT appointment FROM Appointment appointment WHERE appointment.professional.user.username LIKE :username%")
	//	Collection<Appointment> findByUsername(@Param("username") String username);

}
