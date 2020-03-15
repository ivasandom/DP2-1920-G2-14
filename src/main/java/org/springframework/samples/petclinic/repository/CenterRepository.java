
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Center;

public interface CenterRepository extends CrudRepository<Center, Integer> {

}
