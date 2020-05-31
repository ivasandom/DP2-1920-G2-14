
package org.springframework.samples.petclinic.model;

public enum AppointmentStatus {
	
	PENDING("Pending"), ABSENT("Absent"), COMPLETED("Completed");

	private String displayName;

	private AppointmentStatus(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
