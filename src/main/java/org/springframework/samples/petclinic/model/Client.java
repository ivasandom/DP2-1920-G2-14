
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "clients")
public class Client extends User {

	//	@Column(name = "is_health_insurance")
	//	private Boolean			isHealthInsurance;

	@Column(name = "health_insurance")
	@NotEmpty(message = "*")
	private String	healthInsurance;

	@Column(name = "health_card_number")
	//@NotEmpty(message = "*")
	private String	healthCardNumber;


	public String getHealthInsurance() {
		return this.healthInsurance;
	}

	public void setHealthInsurance(final String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public String getHealthCardNumber() {
		return this.healthCardNumber;
	}

	public void setHealthCardNumber(final String healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}

	//	public Boolean getIsHealthInsurance() {
	//		return this.isHealthInsurance;
	//	}
	//
	//	public void setIsHealthInsurance(final Boolean isHealthInsurance) {
	//		this.isHealthInsurance = isHealthInsurance;
	//	}
	//Relations


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
	private Set<Appointment> appointments;


	protected Set<Appointment> getAppointmentsInternal() {
		if (this.appointments == null) {
			this.appointments = new HashSet<>();
		}
		return this.appointments;
	}

	public void setAppointments(final Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Appointment> getAppointments() {
		List<Appointment> sortedAppointments = new ArrayList<>(this.getAppointmentsInternal());
		PropertyComparator.sort(sortedAppointments, new MutableSortDefinition("type", true, true));
		return Collections.unmodifiableList(sortedAppointments);
	}
}
