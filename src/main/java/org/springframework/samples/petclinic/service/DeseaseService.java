
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Desease;
import org.springframework.samples.petclinic.repository.DeseaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeseaseService {

	private DeseaseRepository deseaseRepository;


	@Autowired
	public DeseaseService(final DeseaseRepository deseaseRepository) {
		this.deseaseRepository = deseaseRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Desease> findAll() throws DataAccessException {
		return this.deseaseRepository.findAll();
	}
}
