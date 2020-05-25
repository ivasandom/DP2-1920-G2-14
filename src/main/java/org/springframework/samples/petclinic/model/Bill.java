
package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "document")
	private String document;

	@Column(name = "document_type")
	private DocumentType documentType;

	@Column(name = "price")
	@NotNull
	private Double price;

	@Column(name = "iva")
	@NotNull
	private Double iva = 0.21;

	// If no health insurance, then only cash and bank transfer methods allowed.
	@Column(name = "health_insurance")
	@Enumerated(value = EnumType.STRING)
	private HealthInsurance healthInsurance = HealthInsurance.I_DO_NOT_HAVE_INSURANCE; 

	// Relations

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill", fetch = FetchType.EAGER)
	private Set<Transaction> transactions;

	@Transient
	public Double getFinalPrice() {
		return (1 + this.iva) * this.price;
	}

	@Transient
	public Double getTotalPaid() {
		Double totalCharged = 0.0;
		Double totalRefunded = 0.0;

		for (Transaction transaction : this.getTransactions()) {
			if (transaction.getSuccess()) {
				if (transaction.getType().equals(TransactionType.CHARGE)) {
					totalCharged += transaction.getAmount();
				} else {
					totalRefunded += transaction.getAmount();
				}
			}
		}

		return totalCharged - totalRefunded;
	}

	@Transient
	public BillStatus getStatus() {
		Long successfullRefundedTransactions = transactions.stream()
				.filter(t -> t.getSuccess() && t.getType().equals(TransactionType.REFUND)).count();
		Double totalPaid = getTotalPaid();
		Double finalPrice = getFinalPrice();

		if (totalPaid.equals(finalPrice)) {
			return BillStatus.PAID;
		} else if (successfullRefundedTransactions > 0 && totalPaid > 0.0) {
			return BillStatus.PARTIALLY_REFUNDED;
		} else if (totalPaid > 0.0) {
			return BillStatus.PARTIALLY_PAID;
		} else if (successfullRefundedTransactions > 0) {
			return BillStatus.REFUNDED;
		} else {
			return BillStatus.PENDING;
		}
	}

}
