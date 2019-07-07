package com.fontar.util;

public enum Sexo {
	FEMENINO("app.codes.sexo.femenino.short", "app.codes.sexo.femenino.full"),
	MASCULINO("app.codes.sexo.masculino.short", "app.codes.sexo.masculino.full");
	
	private String shortDescription;
	private String fullDescription;
	
	private Sexo(String shortDescription, String fullDescription) {
		this.shortDescription = ResourceManager.getCodesResource(shortDescription);
		this.fullDescription = ResourceManager.getCodesResource(fullDescription);
	}

	public String getDescripcion() {
		return fullDescription;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
	
	public static Sexo fromString(String sexo) {
		if(sexo.equalsIgnoreCase(MASCULINO.shortDescription) || sexo.equalsIgnoreCase(MASCULINO.fullDescription)) return MASCULINO;
		if(sexo.equalsIgnoreCase(FEMENINO.shortDescription) || sexo.equalsIgnoreCase(FEMENINO.fullDescription)) return FEMENINO;
		return null;
	}
}
