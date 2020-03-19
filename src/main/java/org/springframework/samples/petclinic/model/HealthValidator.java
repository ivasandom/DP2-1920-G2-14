
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class HealthValidator implements org.springframework.validation.Validator {

	//which objects can be validated by this validator
	@Override
	public boolean supports(final Class<?> paramClass) {
		return Client.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "healthCardNumber", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "healthInsurance", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "required");

		Client cli = (Client) obj;
		if (cli.getHealthInsurance().equals("I do not have insurance") && !cli.getHealthCardNumber().isEmpty()) {
			errors.rejectValue("healthCardNumber", "mustEmpty", new Object[] {
				"'healthCardNumber'"
			}, "mustEmpty");
		} else if (!cli.getHealthInsurance().equals("I do not have insurance") && cli.getHealthCardNumber().isEmpty() || cli.getHealthInsurance().isEmpty()) {
			errors.rejectValue("healthCardNumber", "required", new Object[] {
				"'healthCardNumber'"
			}, "required");
		}
		if (!cli.getUser().getPassword().isEmpty() && (cli.getUser().getPassword().length() < 6 || cli.getUser().getPassword().length() > 15)) {
			errors.rejectValue("user.password", "length", new Object[] {
				"'user.password'"
			}, "length");

		}

		//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "role.required");
	}

}
