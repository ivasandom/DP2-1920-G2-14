
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "centers")
public class Center extends NamedEntity {
	
	@Column(name = "address")
	@NotEmpty
	private String address;
	
}
