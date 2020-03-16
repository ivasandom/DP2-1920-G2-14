
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

@Entity
@Table(name = "professionals")
public class Professional extends Person {


	@Column(name = "specialty")
	@NotEmpty(message = "*")
	private String	specialty;

	@Column(name = "collegiate_number")
	@NotEmpty(message = "*")
	private String	collegiateNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;


	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(final String specialty) {
		this.specialty = specialty;
	}

	public String getCollegiateNumber() {
		return this.collegiateNumber;
	}

	public void setCollegiateNumber(final String collegiateNumber) {
		this.collegiateNumber = collegiateNumber;
	}


	//Relations

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professional", fetch = FetchType.EAGER)
	private Set<Schedule> schedules;


	protected Set<Schedule> getSchedulesInternal() {
		if (this.schedules == null) {
			this.schedules = new HashSet<>();
		}
		return this.schedules;
	}

	public void setSchedules(final Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	public List<Schedule> getSchedules() {
		List<Schedule> sortedSchedules = new ArrayList<>(this.getSchedulesInternal());
		PropertyComparator.sort(sortedSchedules, new MutableSortDefinition("date", true, true));
		return Collections.unmodifiableList(sortedSchedules);
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professional", fetch = FetchType.EAGER)
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
