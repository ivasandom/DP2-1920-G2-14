
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends Person {

	
	@Column(name = "health_insurance")
	@NotEmpty(message = "must not be empty")
	private String	healthInsurance;

	@Column(name = "health_card_number")
	//@NotEmpty(message = "*")
	private String	healthCardNumber;
	
	@Column(name = "stripe_id")
	private String stripeId;

	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

	//Relations

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
	private Set<Appointment> appointments;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
	private Set<PaymentMethod> paymentMethods;
	
	
}
