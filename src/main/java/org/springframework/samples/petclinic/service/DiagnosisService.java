package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Diagnosis;
import org.springframework.samples.petclinic.repository.DiagnosisRepository;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisService {

	@Autowired
	private DiagnosisRepository diagnosisRepository;
	
	@Transactional
	public Optional<Diagnosis> findDiagnosisById(final int id) throws DataAccessException {
		return this.diagnosisRepository.findById(id);
	}
}
