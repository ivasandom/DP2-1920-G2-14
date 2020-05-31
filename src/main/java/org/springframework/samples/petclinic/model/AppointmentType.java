
package org.springframework.samples.petclinic.model;

public enum AppointmentType {
	CHECKING("Checking"), ANALISIS("Analisis"), ILLNESS_CONSULTATION("Illness consultation"),
	PRESCRIPTION_ISSUANCE("Prescription Issuance"), VACCINATION("Vaccination"), PERIODIC_CONSULTATION("Periodic Consultation"), ANOTHER("Another");
	
	private String displayName;

	private AppointmentType(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
