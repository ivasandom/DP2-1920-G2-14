
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;

public class ProValidator {

	public boolean supports(final Class<?> paramClass) {
		return Professional.class.equals(paramClass);
	}

	public void validate(final Object obj, final Errors errors) {
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diagnosis.description", "description must not be empty");

		Professional professional = (Professional) obj;

		if (!errors.hasFieldErrors("center") && !errors.hasFieldErrors("specialty")) {
			if (professional.getCenter() == null) {
				// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
				errors.rejectValue("center", "center must not be empty");
			}
			if (professional.getSpecialty() == null) {
				errors.rejectValue("specialty", "specialty must not be empty");
			}
		}
	}
}
