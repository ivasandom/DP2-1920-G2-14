
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Diagnosis;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {

}
