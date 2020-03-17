
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Center;
import org.springframework.samples.petclinic.repository.CenterRepository;
import org.springframework.stereotype.Service;

@Service
public class CenterService {

	@Autowired
	private CenterRepository	centerRepository;
	
	@Autowired
	public CenterService(CenterRepository centerRepository) {
		this.centerRepository = centerRepository;
	}
	
	@Transactional
	public Optional<Center> findCenterById(final int id) throws DataAccessException {
		return this.centerRepository.findById(id);
	}
}
