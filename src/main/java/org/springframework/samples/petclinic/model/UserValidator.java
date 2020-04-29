
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	//which objects can be validated by this validator
	@Override
	public boolean supports(final Class<?> paramClass) {
		return User.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {

		User use = (User) obj;
		if (use.getPassword().length() < 6 || use.getPassword().length() > 15) {
			errors.rejectValue("password", "length", new Object[] {
				"'password'"
			}, "length");
		}
	}
}
