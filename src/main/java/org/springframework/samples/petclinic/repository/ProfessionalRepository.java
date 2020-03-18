
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Professional;

public interface ProfessionalRepository extends CrudRepository<Professional, Integer> {
	
	@Query("SELECT professional FROM Professional professional WHERE professional.center.id = :centerId and professional.specialty.id = :specialtyId")
	public Collection<Professional> findBySpecialtyAndCenter(@Param("specialtyId") int specialtyId, @Param("centerId") int centerId);

}
