package com.fontar.data.impl.domain.codes.seguimiento;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Seguimiento
 */
public enum TipoSeguimiento implements Enumerable {

	FINANCIERO("app.codes.seguimiento.tipo.contable"),	
	TECNICO("app.codes.seguimiento.tipo.tecnico");	

	private String descripcion;

	private TipoSeguimiento(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
