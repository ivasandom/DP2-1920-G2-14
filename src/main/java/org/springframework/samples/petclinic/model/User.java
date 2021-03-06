
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@NotEmpty(message = "must not be empty")
	String			username;

	@NotEmpty(message = "must not be empty")
	@Length(min = 5, max = 60)
	private String	password;

	boolean			enabled;

}
