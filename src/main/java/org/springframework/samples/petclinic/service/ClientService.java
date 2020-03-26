
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
		clientRepository.save(client);		
		//creating user
		userService.saveUser(client.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(client.getUser().getUsername(), "client");
	}		
}
