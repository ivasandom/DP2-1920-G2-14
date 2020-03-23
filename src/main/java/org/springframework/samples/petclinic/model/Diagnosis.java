
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "diagnosis")
public class Diagnosis extends BaseEntity {

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate		date;

	@Column(name = "description")
	@NotEmpty
	private String			description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "diagnosis_medicines", joinColumns = @JoinColumn(name = "diagnosis_id"), inverseJoinColumns = @JoinColumn(name = "medicine_id"))
	private Set<Medicine>	medicines;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "diagnosis_deseases", joinColumns = @JoinColumn(name = "diagnosis_id"), inverseJoinColumns = @JoinColumn(name = "desease_id"))
	private Set<Desease>	deseases;
}
