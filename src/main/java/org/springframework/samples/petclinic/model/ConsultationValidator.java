
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ConsultationValidator implements Validator {

	@Override
	public boolean supports(final Class<?> paramClass) {
		return Appointment.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diagnosis.description", "description must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receipt.price", "price must not be empty");

		Appointment appointment = (Appointment) obj;

		if (!errors.hasFieldErrors("diagnosis.medicines")) {
			if (appointment.getDiagnosis().getMedicines().isEmpty()) {
				// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
				errors.rejectValue("diagnosis.medicines", "medicines must not be empty");
			}
		}

		if (!errors.hasFieldErrors("diagnosis.deseases")) {
			if (appointment.getDiagnosis().getDeseases().isEmpty()) {
				// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
				errors.rejectValue("diagnosis.deseases", "deseases must not be empty");
			}
		}

		if (appointment.getReceipt().getPrice() <= 0 || appointment.getReceipt().getPrice() == null || appointment.getReceipt().getPrice() == 0.00 || appointment.getReceipt().getPrice().toString().isEmpty()) {
			// Appointments last 15 minutes. Only XX:00, XX:15, XX:30, XX:45 are valid start times.
			errors.rejectValue("receipt.price", "price must be positive");

		}
	}
}
