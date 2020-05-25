package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Integer>{
	
	@Query("SELECT sum(bill.price * bill.iva / 100 + bill.price) FROM Bill bill")
	Double getTotalBilled();

}
