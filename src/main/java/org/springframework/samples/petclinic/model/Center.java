
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
@Table(name = "center")
public class Center extends NamedEntity {

	@Column(name = "adress")
	@NotEmpty
	private String adress;


	public String getAdress() {
		return this.adress;
	}

	public void setAdress(final String adress) {
		this.adress = adress;
	}


	//Relations
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "center", fetch = FetchType.EAGER)
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


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "center", fetch = FetchType.EAGER)
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