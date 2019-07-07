package com.fontar.data.impl.domain.codes.instrumento;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Financiamiento de Asignacion de un Intrumento
 */
public enum TipoFinanciamientoAsignacionInstrumento implements Enumerable {

	MONTO("app.codes.llamado.tipoAsignacion.monto"), PORCENTAJE("app.codes.llamado.tipoAsignacion.porcentaje");

	private String descripcion;

	private TipoFinanciamientoAsignacionInstrumento(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
}
