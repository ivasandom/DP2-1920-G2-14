
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professional")
	private Set<Appointment>	appointments;

}
