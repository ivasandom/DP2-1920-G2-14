/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.repository.ProfessionalRepository;
import org.springframework.samples.petclinic.web.DuplicatedUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalService {

	private ProfessionalRepository professionalRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ProfessionalService(final ProfessionalRepository professionalRepository) {
		this.professionalRepository = professionalRepository;
	}

	@Transactional
	public int professionalCount() {
		return (int) this.professionalRepository.count();
	}

	@Transactional(readOnly = true)
	public Iterable<Professional> findAll() throws DataAccessException {
		return this.professionalRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Professional> findById(final int id) throws DataAccessException {
		return this.professionalRepository.findById(id);
	}

	@Transactional
	public Professional findProByUsername(final String username) throws DataAccessException {
		return this.professionalRepository.findProByUsername(username);
	}

	@Transactional(readOnly = true)
	public Iterable<Professional> findProfessionalBySpecialtyAndCenter(final int specialtyId, final int centerId)
			throws DataAccessException {
		return this.professionalRepository.findBySpecialtyAndCenter(specialtyId, centerId);
	}

	@Transactional(readOnly = true)
	public Object findProfessionalById(final int id) throws DataAccessException {
		return this.professionalRepository.findById(id);
	}

	@Transactional
	public void deleteById(final Integer id) throws DataAccessException {
		this.professionalRepository.deleteById(id);
	}

	@Transactional
	public void saveProfessional(final Professional professional)
			throws DataAccessException, DuplicatedUsernameException {
		Professional professionalWithSameUsername = this.findProByUsername(professional.getUser().getUsername());
		Client clientWithSameUsername = this.clientService.findClientByUsername(professional.getUser().getUsername());

		if (clientWithSameUsername != null || professionalWithSameUsername != null
				&& !professionalWithSameUsername.getId().equals(professional.getId())) {
			throw new DuplicatedUsernameException();
		} else {
			// creating professional
			this.professionalRepository.save(professional);
			// creating user
			this.userService.saveUser(professional.getUser());
			// creating authorities
			this.authoritiesService.saveAuthorities(professional.getUser().getUsername(), "professional");
		}

	}

}
