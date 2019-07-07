package com.fontar.bus.impl;

import com.pragma.PragmaControlledException;

public class EntidadBeneficiariaDatosInsuficientesException extends PragmaControlledException {

	private static final long serialVersionUID = 1L;
	
	
	public EntidadBeneficiariaDatosInsuficientesException(String denominacion) {
		setBundleKey("app.configuracion.entidad.faltanDatosDeLaEntidadBeneficiaria", denominacion);
	}

}
