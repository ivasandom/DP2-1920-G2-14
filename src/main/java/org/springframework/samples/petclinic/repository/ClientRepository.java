
package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
	
	@Query("SELECT client FROM Client client WHERE client.user.username = :username")
	public Client findClientByUsername(@Param("username") String username);
}
