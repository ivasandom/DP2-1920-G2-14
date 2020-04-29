
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
