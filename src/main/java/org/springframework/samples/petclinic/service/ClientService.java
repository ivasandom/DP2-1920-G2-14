
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

	@Autowired
	private ClientRepository	clientRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Transactional
	public int clientCount() {
		return (int) this.clientRepository.count();
	}
	@Transactional(readOnly = true)
	public Client findClientById(final int id) throws DataAccessException {
		return this.clientRepository.findById(id);
	}

	@Transactional
	public Client findClientByUsername(String username) throws DataAccessException {
		return clientRepository.findClientByUsername(username);
	}	
	
	@Transactional
	public Iterable<Client> findAll() throws DataAccessException {
		return clientRepository.findAll();
	}
	
	@Transactional
	public void saveClient(Client client) throws DataAccessException {
		//creating client
		this.clientRepository.save(client);
		//creating user
		this.userService.saveUser(client.getUser());
		//creating authorities
		this.authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");
	}
}
