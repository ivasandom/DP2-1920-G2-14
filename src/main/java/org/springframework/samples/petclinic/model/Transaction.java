
package org.springframework.samples.petclinic.model;

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

	//Relations

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receipt_id")
	private Receipt			receipt;

}
