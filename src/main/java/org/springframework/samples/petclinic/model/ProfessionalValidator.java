
package org.springframework.samples.petclinic.model;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ProfessionalValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(final Class<?> paramClass) {
		return Professional.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "center", "center must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialty", "specialty must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "username must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "password must not be empty");
		ValidationUtils.rejectIfEmpty(errors, "collegiateNumber", "must not be empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "documentType", "must not be null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "document", "must not be empty");


		Professional professional = (Professional) obj;
		
		if (!errors.hasFieldErrors("user.password")) {
			if (professional.getUser().getPassword().length() < 6 || professional.getUser().getPassword().length() > 15) {
				errors.rejectValue("user.password", "password must be between 6 and 15 characters",
						new Object[] { "'user.password'" }, "password must be between 6 and 15 characters");

			}
		}
		
		if (!errors.hasFieldErrors("documentType")) {
			if (professional.getDocumentType().equals(DocumentType.CIF)) {
				errors.rejectValue("documentType", "invalid natural person document type");
			}
		}
		
//		if (!errors.hasFieldErrors("registrationDate")) {
//			if (professional.getRegistrationDate().after(Calendar.getInstance().getTime())) {
//				errors.rejectValue("registrationDate", "the date must be in past",
//						new Object[] { "'registrationDate'" }, "the date must be in past");
//
//			}
//		}
		
		if (!errors.hasFieldErrors("email")) {
			if (!professional.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
				errors.rejectValue("email", "choose the correct format",
						new Object[] { "'email'" }, "choose the correct format");

			}
		}
		
//		if (!errors.hasFieldErrors("birthDate")) {
//			if (professional.getBirthDate().after(Calendar.getInstance().getTime())) {
//				errors.rejectValue("birthDate", "the date must be in past",
//						new Object[] { "'birthDate'" }, "the date must be in past");
//
//			}
//		}
	}

}
