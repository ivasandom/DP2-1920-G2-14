
package org.springframework.samples.petclinic.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class BillTransactionValidator implements org.springframework.validation.Validator {

	private static final String AMOUNT = "amount";
	
	private static final String PAYMENTMETHODTOKEN = "paymentMethod.token";

	
	@Override
	public boolean supports(final Class<?> paramClass) {
		return Transaction.class.equals(paramClass);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "bill", "bill must no be empty");
		ValidationUtils.rejectIfEmpty(errors, AMOUNT, "amount must no be empty");

		Transaction transaction = (Transaction) obj;
		
		
		if (!errors.hasFieldErrors("bill") && !errors.hasFieldErrors(AMOUNT)) {
			if (transaction.getAmount().compareTo(0.0) < 1) {
				errors.rejectValue(AMOUNT, "amount must be bigger than zero");
			}
			if (transaction.getAmount() > transaction.getBill().getFinalPrice() - transaction.getBill().getTotalPaid()) {
				errors.rejectValue(AMOUNT, "amount cannot be bigger than pending amount");
			}
		}

		if (!errors.hasFieldErrors("bill") && !errors.hasFieldErrors("paymentMethod")) {
			if (transaction.getPaymentMethod() == null || transaction.getPaymentMethod().getToken() == null
					|| transaction.getPaymentMethod().getToken().isEmpty()) {
				errors.rejectValue(PAYMENTMETHODTOKEN, "payment method must be valid");
			}
			
			if (!errors.hasFieldErrors(PAYMENTMETHODTOKEN)) {
				if (!transaction.getBill().getHealthInsurance().equals(HealthInsurance.I_DO_NOT_HAVE_INSURANCE) &&
					!transaction.getPaymentMethod().getToken().equals("CASH") && !transaction.getPaymentMethod().getToken().equals("BANKTRANSFER")) {
					errors.rejectValue(PAYMENTMETHODTOKEN, "only cash and banktransfer methods are allowed");
				}
			}
		}

	}

}
