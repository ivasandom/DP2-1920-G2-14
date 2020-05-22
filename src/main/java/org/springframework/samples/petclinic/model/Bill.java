
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill extends NamedEntity {
	
	@Column(name = "status")
	@Enumerated
	private BillStatus			status;

	@Column(name = "last_name")
	@NotEmpty
	protected String	lastName;

	@Column(name = "document")
	@NotEmpty
	protected String	document;

	@Column(name = "type_document")
	@NotEmpty
	protected String	typeDocument;

	@Column(name = "price")
	@NotNull
	protected Double	price;

	@Column(name = "iva")
	@NotNull
	protected Double	iva;

	@Column(name = "final_price")
	@NotNull
	protected Double	finalPrice;

	// Relations

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_id")
	private Appointment			appointment;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill", fetch = FetchType.EAGER)
	private Set<Transaction>	transactions;

	public Double getFinalPrice() {
		return this.iva / 100 * this.price + this.price;
	}
}
