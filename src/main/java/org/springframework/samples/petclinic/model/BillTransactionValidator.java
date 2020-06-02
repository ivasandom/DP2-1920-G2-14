
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class BillTransactionValidator implements org.springframework.validation.Validator {

	@Override
	public boolean supports(final Class<?> paramClass) {
		return Transaction.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "bill", "bill must no be empty");
		ValidationUtils.rejectIfEmpty(errors, "amount", "amount must no be empty");

		Transaction transaction = (Transaction) obj;
		
		if (!errors.hasFieldErrors("amount")) {
			if (transaction.getAmount().compareTo(0.0) < 1) {
				errors.rejectValue("amount", "amount must be bigger than zero");
			}
			if (transaction.getAmount() > transaction.getBill().getFinalPrice() - transaction.getBill().getTotalPaid()) {
				errors.rejectValue("amount", "amount cannot be bigger than pending amount");
			}
		}

		if (!errors.hasFieldErrors("paymentMethod")) {
			if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().getToken() == null
					|| transaction.getPaymentMethod().getToken().isEmpty()) {
				errors.rejectValue("paymentMethod.token", "payment method must be valid");
			}
			
			if (!errors.hasFieldErrors("paymentMethod.token")) {
				if (!transaction.getBill().getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) &&
					!transaction.getPaymentMethod().getToken().equals("CASH") && !transaction.getPaymentMethod().getToken().equals("BANKTRANSFER")) {
					errors.rejectValue("paymentMethod.token", "only cash and banktransfer methods are allowed");
				}
			}
		}

	}

}
