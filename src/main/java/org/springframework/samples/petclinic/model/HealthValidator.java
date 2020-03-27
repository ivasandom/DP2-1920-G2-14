
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "healthInsurance", "health insurance must not be empty. In case you don't have any, write 'I do not have insurance'");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "username must not be empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "password must not be empty");


		Client cli = (Client) obj;
		if (cli.getHealthInsurance().equals("I do not have insurance") && !cli.getHealthCardNumber().isEmpty()) {
			errors.rejectValue("healthCardNumber", "you cannot write a health card number if you don't have health insurance", new Object[] {
					"'healthCardNumber'"
			}, "you cannot write a health card number if you don't have health insurance");
		} else if (!cli.getHealthInsurance().equals("I do not have insurance") && cli.getHealthCardNumber().isEmpty()) {
			errors.rejectValue("healthCardNumber", "you must write your health card number", new Object[] {
					"'healthCardNumber'"
			}, "you must write your health card number");
		}
		if (!errors.hasFieldErrors("user.password")) {
			if (cli.getUser().getPassword().length() < 6 || cli.getUser().getPassword().length() > 15) {
				errors.rejectValue("user.password", "password must be between 6 and 15 characters", new Object[] {
						"'user.password'"
				}, "password must be between 6 and 15 characters");

			}
		}
		//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "role.required");
	}

}
