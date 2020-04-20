
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

	@Query("SELECT client FROM Client client WHERE client.user.username = :username")
	Client findClientByUsername(@Param("username") String username);

	@Query("SELECT client FROM Client client WHERE client.id = :id")
	Client findById(int id) throws DataAccessException;

	@Override
	Collection<Client> findAll() throws DataAccessException;
}
