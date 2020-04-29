
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Medicine;

public interface MedicineRepository extends CrudRepository<Medicine, Integer> {

	@Query("SELECT medicine FROM Medicine medicine ORDER BY medicine.name")
	List<Medicine> findMedicines() throws DataAccessException;
}
