
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "professionals")
public class Professional extends Person {

	@Column(name = "collegiate_number")
	@NotEmpty(message = "*")
	private String				collegiateNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				user;

	//Relations

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professional", fetch = FetchType.EAGER)
	private Set<Schedule>		schedules;

	@ManyToOne
	@JoinColumn(name = "center_id")
	private Center				center;

	@ManyToOne
	@JoinColumn(name = "specialty_id")
	private Specialty			specialty;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professional", fetch = FetchType.EAGER)
	private Set<Appointment>	appointments;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "professionals_medicines", joinColumns = @JoinColumn(name = "professional_id"), inverseJoinColumns = @JoinColumn(name = "medicine_id"))
	private Set<Medicine>		medicines;


	protected Set<Medicine> getMedicinesInternal() {
		if (this.medicines == null) {
			this.medicines = new HashSet<>();
		}
		return this.medicines;
	}

	public void setMedicines(final Set<Medicine> medicines) {
		this.medicines = medicines;
	}

	public List<Medicine> getMedicines() {
		List<Medicine> sortedMedicines = new ArrayList<>(this.getMedicinesInternal());
		PropertyComparator.sort(sortedMedicines, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedMedicines);
	}

}
