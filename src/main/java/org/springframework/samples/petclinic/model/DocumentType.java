
package org.springframework.samples.petclinic.model;

public enum DocumentType {
	nif("Nif"), nie("Nie"), passport("Passport");
	
	private String displayName;

	private DocumentType(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
}
