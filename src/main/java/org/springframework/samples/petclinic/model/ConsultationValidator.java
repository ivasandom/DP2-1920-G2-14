
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diagnosis.deseases", "deseases must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "diagnosis.medicines", "medicines must no be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bill", "bill must not be empty");

		Appointment appointment = (Appointment) obj;

		if (!errors.hasFieldErrors("bill")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bill.price", "price must not be empty");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bill.iva", "iva must not be empty");
		
			if (!errors.hasFieldErrors("bill.price")) {
				if(appointment.getBill().getPrice() <= 0.) {
					errors.rejectValue("bill.price", "price must be positive");
				}
			}
			
			if (!errors.hasFieldErrors("bill.iva")) {
				if (appointment.getBill().getIva() < 0. || appointment.getBill().getIva() > 100.) {
					errors.rejectValue("bill.iva", "iva must be between 0 and 100");
				}
			}
			
		}
		
		if (!errors.hasFieldErrors("diagnosis.medicines")) {
			if (appointment.getDiagnosis().getMedicines().isEmpty()) {
				errors.rejectValue("diagnosis.medicines", "medicines must no be empty");
			}
		}
		
		if (!errors.hasFieldErrors("diagnosis.deseases")) {
			if (appointment.getDiagnosis().getDeseases().isEmpty()) {
				errors.rejectValue("diagnosis.deseases", "deseases must no be empty");
			}
		}
	}
}
