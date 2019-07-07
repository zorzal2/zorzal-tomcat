package com.fontar.bus.impl;

import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.pragma.PragmaControlledException;

public class PersonaDatosInsuficientesException extends PragmaControlledException {

	private static final long serialVersionUID = 1L;
	
	
	public PersonaDatosInsuficientesException(PersonaDTO persona) {
		super();
		if(persona.getNombre()==null || persona.getNombre().equals(""))
			setBundleKey("app.configuracion.persona.faltanDatosDeLaPersonaSinNombre");
		else
			setBundleKey("app.configuracion.persona.faltanDatosDeLaPersona", persona.getNombre());
	}

}
