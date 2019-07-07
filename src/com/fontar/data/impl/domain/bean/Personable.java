package com.fontar.data.impl.domain.bean;

public class Personable {

	private PersonaBean persona;

	public PersonaBean getPersona() {
		if (persona == null) {
			persona = new PersonaBean();
		}
		return persona;
	}

	public void setPersona(PersonaBean persona) {
		this.persona = persona;
	}
}
