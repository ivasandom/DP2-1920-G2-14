
package org.springframework.samples.petclinic.model;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ClientValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(final Class<?> paramClass) {
		return Client.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "healthInsurance", "health insurance must not be empty. In case you don't have any, write 'I do not have insurance'");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "username must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "password must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "document", "document must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "documentType", "must not be null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "must not be empty");

		Client client = (Client) obj;

		if (client.getHealthCardNumber() == null) {
			// HealthCardNumber can be empty but not null.
			errors.rejectValue("healthCardNumber", "cannot be null");
		}

		if (!errors.hasFieldErrors("healthInsurance") && !errors.hasFieldErrors("healthCardNumber")) {
			if (client.getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) && !client.getHealthCardNumber().isEmpty()) {
				errors.rejectValue("healthCardNumber", "you cannot write a health card number if you don't have health insurance", new Object[] {
					"'healthCardNumber'"
				}, "you cannot write a health card number if you don't have health insurance");
			} else if (!client.getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) && client.getHealthCardNumber().isEmpty()) {
				errors.rejectValue("healthCardNumber", "you must write your health card number", new Object[] {
					"'healthCardNumber'"
				}, "you must write your health card number");
			}

		}

		if (!errors.hasFieldErrors("user.password") && (client.getUser().getPassword().length() < 6 || client.getUser().getPassword().length() > 15)) {
			errors.rejectValue("user.password", "password must be between 6 and 15 characters", new Object[] {
				"'user.password'"
			}, "password must be between 6 and 15 characters");
		}

		if (!errors.hasFieldErrors("documentType") && client.getDocumentType().equals(DocumentType.CIF)) {
			errors.rejectValue("documentType", "invalid natural person document type");
		}

		if (!errors.hasFieldErrors("birthDate") && client.getBirthDate().after(Calendar.getInstance().getTime())) {
			errors.rejectValue("birthDate", "the date must be in past", new Object[] {
				"'birthDate'"
			}, "the date must be in past");
		}

		if (!errors.hasFieldErrors("email") && !client.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
			errors.rejectValue("email", "choose the correct format", new Object[] {
				"'email'"
			}, "choose the correct format");
		}
	}

}
