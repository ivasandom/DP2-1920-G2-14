
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

	@Column(name = "brand")
	private String	brand;
	
	@Column(name = "last4")
	private String last4;

	@Column(name = "token")
	@NotBlank
	private String	token;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client	client;

}
