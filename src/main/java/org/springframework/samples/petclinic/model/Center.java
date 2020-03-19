
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
@Entity
@Table(name = "centers")
public class Center extends NamedEntity {
	
	@Column(name = "address")
	@NotEmpty
	private String address;


	//Relations
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "center", fetch = FetchType.EAGER)
	private Set<Schedule> schedules;

	
}
