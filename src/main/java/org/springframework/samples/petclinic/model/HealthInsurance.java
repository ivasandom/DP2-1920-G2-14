
package org.springframework.samples.petclinic.model;

public enum HealthInsurance {

	I_DO_NOT_HAVE_INSURANCE("I do not have insurance"), MAPFRE("Mapfre"), SANITAS("Sanitas"), DKV("DKV"), CIGNA("Cigna"), FIATC("Fiatc"), AEGON("Aegon"), AXA("Axa"), CASER("Caser"), ASISA("Asisa"), ADESLAS("Adeslas");

	private String displayName;

	private HealthInsurance(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
	
}
