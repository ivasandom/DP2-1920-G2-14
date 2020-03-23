
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Desease;

public interface DeseaseRepository extends CrudRepository<Desease, Integer> {

}
