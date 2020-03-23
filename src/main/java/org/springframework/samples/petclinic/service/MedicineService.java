
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Medicine;
import org.springframework.samples.petclinic.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicineService {

	private MedicineRepository medicineRepository;


	@Autowired
	public MedicineService(final MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Medicine> findMedicines() throws DataAccessException {
		return this.medicineRepository.findMedicines();
	}
}
