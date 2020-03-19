
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
	String			username;

	@NotEmpty(message = "*")
	@Length(min = 5, max = 60)
	private String	password;

	boolean			enabled;

	//	public User getUserWithIdDifferent(String username, final Integer id) {
	//		username = username.toLowerCase();
	//		Users users = new Users();
	//		for (User user : users.getUserList()) {
	//			String compName = user.getUsername();
	//			compName = compName.toLowerCase();
	//			if (compName.equals(username) && user.getId() != id) {
	//				return user;
	//			}
	//		}
	//		return null;
	//	}

}
