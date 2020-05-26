
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.PaymentMethod;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Integer> {

	@Query("SELECT pm FROM PaymentMethod pm WHERE pm.client = :client")
	Collection<PaymentMethod> findByClient(@Param("client") Client client);
	
	@Query("SELECT pm FROM PaymentMethod pm WHERE pm.token = :token and pm.client = :client")
	PaymentMethod findByTokenAndClient(@Param("token") String token, @Param("client") Client client);
	
}
