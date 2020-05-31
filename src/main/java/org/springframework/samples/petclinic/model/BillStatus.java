
package org.springframework.samples.petclinic.model;

public enum BillStatus {

	PENDING("Pending"), PARTIALLY_REFUNDED("Partially refunded"), REFUNDED("Refunded"),
	PARTIALLY_PAID("Partially paid"), PAID("Paid");

	private String displayName;

	private BillStatus(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
