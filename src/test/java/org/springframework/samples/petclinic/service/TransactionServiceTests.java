
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Transaction;
import org.springframework.samples.petclinic.model.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransactionServiceTests {

	@Autowired
	protected TransactionService transactionService;


	@ParameterizedTest
	@CsvSource({
		"10.0, tok_visa, finished"
	})
	@Transactional
	public void shouldSaveTransaction(final Double amount, final String token, final String status) {
		Collection<Transaction> transactions = (Collection<Transaction>) this.transactionService.listTransactions();
		int found = transactions.size();

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setType(TransactionType.REFUND);
		transaction.setStatus(status);
		transaction.setToken(token);

		this.transactionService.saveTransaction(transaction);

		transactions = (Collection<Transaction>) this.transactionService.listTransactions();
		org.assertj.core.api.Assertions.assertThat(transactions.size()).isEqualTo(found + 1);
	}

	@Test
	public void testCountWithInitialData() {
		Collection<Transaction> transactions = (Collection<Transaction>) this.transactionService.listTransactions();
		Assertions.assertEquals(transactions.size(), 0);
	}

}
