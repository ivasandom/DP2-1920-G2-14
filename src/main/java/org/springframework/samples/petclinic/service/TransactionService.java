package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
	
	
	private TransactionRepository transactionRepository;
	
	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	@Transactional
	public void saveTransaction(Transaction transaction) throws DataAccessException {
		transactionRepository.save(transaction);		
	}

}
