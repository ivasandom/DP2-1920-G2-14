
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client extends Person {

	@Column(name = "health_insurance")
	@NotEmpty(message = "*")
	private String	healthInsurance;

	@Column(name = "health_card_number")
	@NotEmpty(message = "*")
	private String	healthCardNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

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
