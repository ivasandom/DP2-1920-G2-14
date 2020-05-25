
package org.springframework.samples.petclinic.model;

public enum HealthInsurance {

	I_DO_NOT_HAVE_INSURANCE("I do not have insurance", null, null),
	MAPFRE("Mapfre", "Seguros Mapfre S.A.", "A111111"),
	SANITAS("Sanitas", "Seguros Sanitas S.A.", "A222222"),
	DKV("DKV", "Seguros DKV S.A.", "A333333"),
	CIGNA("Cigna", "Seguros Cigna S.A.", "A555555"),
	FIATC("Fiatc", "Seguros Miatc S.A.", "A666666"),
	AEGON("Aegon", "Seguros Aegon S.A.", "A777777"),
	AXA("Axa", "Seguros Axa S.A.", "A888888"),
	CASER("Caser", "Seguros Caser S.A.", "A999999"),
	ASISA("Asisa", "Seguros Asisa S.A.", "B1111111"),
	ADESLAS("Adeslas", "Seguros Adeslas S.A.", "B222222"),
	SANTANDER("Santander", "Seguros Santander S.A.", "B3333333");

	private String displayName;
	
	private String legalName;
	
	private String cif;

	private HealthInsurance(final String displayName, final String legalName, final String cif) {
		this.displayName = displayName;
		this.legalName = legalName;
		this.cif = cif;
	}

	public String getDisplayName() {
		return this.displayName;
	}
	
	public String getLegalName() {
		return this.legalName;
	}
	
	public String getCif() {
		return this.cif;
	}
	
	
}
