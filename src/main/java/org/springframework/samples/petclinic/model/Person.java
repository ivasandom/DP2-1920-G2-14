
package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@MappedSuperclass
public class Person extends BaseEntity {

	// Attributes -------------------------------------------
	
	@Column(name = "first_name")
	@NotEmpty(message = "*")
	private String			firstName;
	
	@Column(name = "last_name")
	@NotEmpty(message = "*")
	private String			lastName;
	
	@Column(name = "email")
	@NotEmpty(message = "*")
	@Email(message = "Enter a valid email address.")
	private String			email;
	
	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private Date			birthDate;

	@Column(name = "registration_date")
	@Past
	private Date			registrationDate;
	
	@Column(name = "document")
	@NotEmpty(message = "*")
	private String			document;
	
	@Column(name = "document_type")
	@NotNull(message = "*")
	private DocumentType	documentType;

//	@NotEmpty(message = "*")
//	private String			avatar;


//	@NotEmpty(message = "*")
//	private String			address;
//
//	@NotEmpty(message = "*")
//	private String			city;
//
//	@NotEmpty(message = "*")
//	@Digits(fraction = 0, integer = 10)
//	private String			telephone;
	
//	@Column(name = "age")
//	@Formula("(TIMESTAMPDIFF(YEAR,birth_date,CURDATE()))")
//	@NotEmpty(message = "*")
//	private String			age;
	
	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}

}
