
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "specialties")
public class Specialty extends NamedEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialty", fetch = FetchType.EAGER)
	private Set<Appointment>	appointments;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialty", fetch = FetchType.EAGER)
	private Set<Professional>	professionals;
}
