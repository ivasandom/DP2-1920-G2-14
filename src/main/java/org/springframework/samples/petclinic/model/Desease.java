
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deseases")
public class Desease extends NamedEntity {

	//	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "deseases")
	//	private Set<Diagnosis>	diagnosis;

}
