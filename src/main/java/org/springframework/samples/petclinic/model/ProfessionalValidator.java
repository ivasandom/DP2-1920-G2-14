
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ProfessionalValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(final Class<?> paramClass) {
		return Professional.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "center", "center must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "specialty", "specialty must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "username must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "password must not be empty");

		Professional professional = (Professional) obj;
		
		if (!errors.hasFieldErrors("user.password")) {
			if (professional.getUser().getPassword().length() < 6 || professional.getUser().getPassword().length() > 15) {
				errors.rejectValue("user.password", "password must be between 6 and 15 characters",
						new Object[] { "'user.password'" }, "password must be between 6 and 15 characters");

			}
		}
	}

}
