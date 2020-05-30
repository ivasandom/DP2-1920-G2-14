
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Bill;
import org.springframework.samples.petclinic.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillService {

	private BillRepository billRepository;


	@Autowired
	public BillService(final BillRepository billRepository) {
		this.billRepository = billRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Bill> findAll() throws DataAccessException {
		return this.billRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Bill> findById(int id) throws DataAccessException {
		return this.billRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Double getTotalBilled() throws DataAccessException {
		return this.billRepository.getTotalBilled();
	}
	
	@Transactional(readOnly = true)
	public Object[] getBilledPerDay() throws DataAccessException {
		return this.billRepository.getBilledPerDay();
	}
	
	
	@Transactional
	public void saveBill(final Bill bill) throws DataAccessException {
		this.billRepository.save(bill);
	}
}
