
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Professional;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.samples.petclinic.web.DuplicatedUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

	@Autowired
	private ClientRepository	clientRepository;

	@Autowired
	private UserService			userService;
	
	@Autowired
	private ProfessionalService professionalService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	public int clientCount() {
		return (int) this.clientRepository.count();
	}
	public Optional<Client> findClientById(final int id) throws DataAccessException {
		return this.clientRepository.findById(id);
	}

	public Client findClientByUsername(final String username) throws DataAccessException {
		return this.clientRepository.findClientByUsername(username);
	}

	public Collection<Client> findAll() throws DataAccessException {
		return this.clientRepository.findAll();
	}

	@Transactional
	public void saveClient(final Client client) throws DataAccessException, DuplicatedUsernameException {
		Client clientWithSameUsername = this.findClientByUsername(client.getUser().getUsername());
		Professional professionalWithSameUsername = this.professionalService.findProByUsername(client.getUser().getUsername());
		
		if (professionalWithSameUsername != null || clientWithSameUsername != null
				&& !clientWithSameUsername.getId().equals(client.getId())) {
			throw new DuplicatedUsernameException();
		} else {
			//creating client
			this.clientRepository.save(client);
			//creating user
			this.userService.saveUser(client.getUser());
			//creating authorities
			this.authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");
		}
		
	}
	
	@Transactional
	public void deleteById(final Integer id) throws DataAccessException {
		this.clientRepository.deleteById(id);
	}
}
