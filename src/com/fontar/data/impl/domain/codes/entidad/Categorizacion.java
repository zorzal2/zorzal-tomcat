package com.fontar.data.impl.domain.codes.entidad;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Paquete
 */
public enum Categorizacion implements Enumerable {


	PYME_MICRO("app.codes.entidad.tipo.micro"),
	PYME_PEQ("app.codes.entidad.tipo.peque"),
	PYME_MED("app.codes.entidad.tipo.mediana"),
	GRANDE("app.codes.entidad.tipo.grande");

	private String descripcion;

	private Categorizacion(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
