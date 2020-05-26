
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends Person {

	@Column(name = "health_insurance")
	@Enumerated(value = EnumType.STRING)
	private HealthInsurance		healthInsurance;

	@Column(name = "health_card_number")
	//@NotEmpty(message = "*")
	private String				healthCardNumber;

	@Column(name = "stripe_id")
	private String				stripeId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				user;

	//Relations

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private Set<Appointment>	appointments;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private Set<PaymentMethod>	paymentMethods;

}
