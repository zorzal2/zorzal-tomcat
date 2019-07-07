package com.fontar.data.impl.domain.codes.entidad;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Paquete
 */
public enum TipoEmpresa implements Enumerable {


	SA("app.codes.entidad.tipo.sa"),
	SRL("app.codes.entidad.tipo.srl"),
	DE_HECHO("app.codes.entidad.tipo.dehecho"),
	UNIPERSONAL("app.codes.entidad.tipo.unipersonal"),
	OTRO("app.codes.entidad.tipo.otro");

	private String descripcion;

	private TipoEmpresa(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
