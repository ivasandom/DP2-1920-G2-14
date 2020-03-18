package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Receipt;
import org.springframework.samples.petclinic.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;
	
	@Transactional
	public Optional<Receipt> findReceiptById(final int id) throws DataAccessException {
		return this.receiptRepository.findById(id);
	}
}
