package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Receipt;

public interface ReceiptRepository extends CrudRepository<Receipt, Integer>{

}
