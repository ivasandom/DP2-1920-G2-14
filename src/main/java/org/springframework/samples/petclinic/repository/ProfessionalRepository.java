
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Professional;

public interface ProfessionalRepository extends CrudRepository<Professional, Integer> {

	@Query("SELECT professional FROM Professional professional WHERE professional.specialty = :specialty")
	public Collection<Professional> findBySpecialty(@Param("specialty") String specialty);

}
