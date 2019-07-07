package com.fontar.data.impl.domain.codes.instrumentoDef;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Instrumentos Def
 */
public enum TipoInstrumentoDef implements Enumerable {

	CREDITO("app.codes.instrumentoDef.tipo.credito"),
	SUBSIDIO("app.codes.instrumentoDef.tipo.subsidio");

	private String descripcion;

	private TipoInstrumentoDef(String key) {
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
