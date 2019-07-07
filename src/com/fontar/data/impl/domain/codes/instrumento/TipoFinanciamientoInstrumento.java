package com.fontar.data.impl.domain.codes.instrumento;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Financiamiento de un Intrumento
 */
public enum TipoFinanciamientoInstrumento implements Enumerable {

	POR_PROYECTO("app.codes.llamado.tipoFinanciamiento.porProyecto"), POR_BENEFICIARIO(
			"app.codes.llamado.tipoFinanciamiento.porBeneficiario");

	private String descripcion;

	private TipoFinanciamientoInstrumento(String key) {
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
