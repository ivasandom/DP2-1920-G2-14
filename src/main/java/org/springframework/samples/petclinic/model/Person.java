
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@MappedSuperclass
public class Person extends BaseEntity {

	// Attributes -------------------------------------------
	
	@NotEmpty(message = "*")
	private String			firstName;

	@NotEmpty(message = "*")
	private String			lastName;

	@NotEmpty(message = "*")
	@Email(message = "Enter a valid email address.")
	private String			email;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	@NotNull(message = "*")
	@Past
	private Date			birthDate;

	@Past
//	@NotNull(message = "*")
	private Date			registrationDate;

	@NotEmpty(message = "*")
	private String			document;

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

}
