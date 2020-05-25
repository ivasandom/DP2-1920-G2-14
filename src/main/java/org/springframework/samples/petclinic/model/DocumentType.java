
package org.springframework.samples.petclinic.model;

public enum DocumentType {
	NIF("Nif"), NIE("Nie"), PASSPORT("Passport"), CIF("Cif");
	
	private String displayName;

	private DocumentType(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}
	
	public static DocumentType[] getNaturalPersonValues() {
		DocumentType[] res = {NIF, NIE, PASSPORT};
		return res;
	}
	
	public static DocumentType[] getLegalPersonValues() {
		DocumentType[] res = {CIF};
		return res;
	}
}
