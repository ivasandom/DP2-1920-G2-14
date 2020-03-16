
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;

public enum HealthInsurance {

	I_DO_NOT_HAVE_INSURANCE("I do not have insurance"), MAPFRE("Mapfre"), SANITAS("Sanitas"), DKV("DKV"), CIGNA("Cigna"), FIATC("Fiatc"), AEGON("Aegon"), AXA("Axa"), CASER("Caser"), ASISA("Asisa"), ADESLAS("Adeslas");

	private String displayName;


	private HealthInsurance(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	// Optionally and/or additionally, toString.
	@Override
	public String toString() {
		return this.getDisplayName();
	}

	public static void main(final String args[]) {

		java.util.List<HealthInsurance> lista = new ArrayList<>();
		HealthInsurance[] h = HealthInsurance.values();
		for (HealthInsurance hi : h) {
			System.out.println(hi.toString());
		}
		//}
		//		for (HealthInsurance hi : h) {
		//			System.out.println(hi);
		//		}
	}
}
