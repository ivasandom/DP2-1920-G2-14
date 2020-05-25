package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Integer>{
	
	@Query("SELECT sum(bill.price * bill.iva / 100 + bill.price) FROM Bill bill")
	Double getTotalBilled();
	
	@Query("SELECT sum(bill.price * (1+bill.iva/100)), CAST(bill.createdAt AS date) FROM Bill bill "
			+ "GROUP BY CAST(bill.createdAt AS date)")
	Object[] getBilledPerDay();

}
