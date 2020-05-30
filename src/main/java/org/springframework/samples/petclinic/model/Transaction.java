
package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "type")
	@Enumerated
	private TransactionType	type;

	@Column(name = "amount")
	@NotNull
	private Double			amount;

	@Column(name = "token")
	@NotEmpty
	private String			token;

	@Column(name = "success")
	private Boolean			success;

	@Column(name = "status")
	@NotEmpty
	private String			status;
	
	@Column(name = "refunded")
	private Boolean			refunded = false;

	//Relations

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bill_id")
	private Bill			bill;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_method_id")
	private PaymentMethod		paymentMethod;
	
	
	@Transient
	public String getSource() {
		switch (token) {
		case "CASH":
			return "Cash";
		case "BANKTRANSFER":
			return "Bank Transfer";
		default:
			return String.format("Stripe: %s", token);
		}
	}
	

}
