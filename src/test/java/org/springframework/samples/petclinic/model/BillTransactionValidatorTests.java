
package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class BillTransactionValidatorTests {

	@Autowired
	private BillTransactionValidator billTransactionValidator = new BillTransactionValidator();

	@Autowired
	private Bill bill;
	
	@Autowired
	private Transaction transaction;

	@Autowired
	private Errors errors;

	@BeforeEach
	private void generateBillAndTransactions() {
		PaymentMethod paymentMethod = PaymentMethod.cash();
		
		this.transaction = new Transaction();
		this.transaction.setAmount(50.0);
		this.transaction.setToken("CASH");
		this.transaction.setSuccess(false);
		this.transaction.setPaymentMethod(paymentMethod);
		this.transaction.setType(TransactionType.CHARGE);
		
		Transaction transaction2 = new Transaction();
		transaction2.setAmount(50.0);
		transaction2.setToken("CASH");
		transaction2.setSuccess(true);
		transaction2.setType(TransactionType.CHARGE);
		
		Set<Transaction> transactions = new HashSet<Transaction>();
		transactions.add(this.transaction);
		transactions.add(transaction2);
		
		
		this.bill = new Bill();
		bill.setIva(21.0);
		bill.setPrice(100.0); // total price 121
		bill.setTransactions(transactions);
		
		this.transaction.setBill(bill);

		this.errors = new BeanPropertyBindingResult(this.transaction, "");
	}

	@Test
	void shouldValidateCorrectTransaction() {
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateEmptyBill() {
		this.transaction.setBill(null);
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("bill").getCode()).isEqualTo("bill must no be empty");
	}
	
	@Test
	void shouldNotValidateEmptyAmount() {
		this.transaction.setAmount(null);
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("amount").getCode()).isEqualTo("amount must no be empty");
	}
	
	@Test
	void shouldNotValidateNegativeAmount() {
		this.transaction.setAmount(-1.);
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("amount").getCode()).isEqualTo("amount must be bigger than zero");
	}
	
	@Test
	void shouldNotValidateBiggerAmount() {
		// Should not validate when transaction is bigger than the amount remaining to be paid.
		// 50.0 + 70.0 > 121.0
		
		this.transaction.setAmount(80.0);
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("amount").getCode()).isEqualTo("amount cannot be bigger than pending amount");
	}
	
	@Test
	void shouldNotValidateCreditCardIfHealthInsurance() {
		this.bill.setHealthInsurance(HealthInsurance.ADESLAS);
		this.transaction.getPaymentMethod().setToken("payment method token");
		
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("paymentMethod.token").getCode()).isEqualTo("only cash and banktransfer methods are allowed");
	}
	
	@Test
	void shouldNotValidateEmptyPaymentMethod() {
		this.transaction.setPaymentMethod(null);;
		
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("paymentMethod.token").getCode()).isEqualTo("payment method must be valid");
	}
	
	@Test
	void shouldNotValidateEmptyPaymentMethodToken() {
		this.transaction.getPaymentMethod().setToken("");
		
		this.billTransactionValidator.validate(this.transaction, this.errors);

		Assertions.assertThat(this.errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(this.errors.getFieldError("paymentMethod.token").getCode()).isEqualTo("payment method must be valid");
	}
	
}
