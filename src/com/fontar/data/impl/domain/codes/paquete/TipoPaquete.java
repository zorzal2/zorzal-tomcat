package com.fontar.data.impl.domain.codes.paquete;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Paquete
 */
public enum TipoPaquete implements Enumerable {

	COMISION("app.codes.paquete.tipo.comision"),
	SECRETARIA("app.codes.paquete.tipo.secretaria"),
	DIRECTORIO("app.codes.paquete.tipo.directorio");

	private String descripcion;

	private TipoPaquete(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
