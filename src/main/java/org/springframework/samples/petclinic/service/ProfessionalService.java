/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalService {

	private ProfessionalRepository professionalRepository;	
	
	
	@Autowired
	public ProfessionalService(ProfessionalRepository professionalRepository) {
		this.professionalRepository = professionalRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Professional> findProfessionalBySpecialty(String specialty) throws DataAccessException {
		if (specialty.equals("")) {
			return professionalRepository.findAll();
		}
		return professionalRepository.findBySpecialty(specialty);
	}

}
