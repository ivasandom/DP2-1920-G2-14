
package org.springframework.samples.petclinic.service;

import java.util.Optional;

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
	public TransactionService(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public void saveTransaction(final Transaction transaction) throws DataAccessException {
		this.transactionRepository.save(transaction);
	}

	@Transactional(readOnly = true)
	public Iterable<Transaction> listTransactions() throws DataAccessException {
		return this.transactionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Transaction findById(int id) throws DataAccessException {
		Optional<Transaction> optional = this.transactionRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	

}
