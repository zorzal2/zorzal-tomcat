package com.fontar.data.impl.domain.codes.general;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Evaluacion
 */
public enum EstadoEntidad implements Enumerable {

	TRUE("app.codes.general.estado.activo"), FALSE("app.codes.general.estado.inactivo");

	private String descripcion;

	private EstadoEntidad(String key) {
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
		// TODO Auto-generated method stub
		return super.toString();
	}
}
