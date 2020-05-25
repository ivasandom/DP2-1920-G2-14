
package org.springframework.samples.petclinic.model;

public enum BillStatus {

	PENDING("Pending"), REFUNDED("Refunded"), PARTIALLY_PAID("Partially paid"), PAID("Paid");
	
	private String displayName;

	private BillStatus(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
