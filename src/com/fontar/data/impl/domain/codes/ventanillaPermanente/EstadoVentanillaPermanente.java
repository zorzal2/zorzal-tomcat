package com.fontar.data.impl.domain.codes.ventanillaPermanente;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Ventanillas Permanentes
 */
public enum EstadoVentanillaPermanente implements Enumerable {

	ABIERTO("app.codes.ventanilla.estado.abierto"), CERRADO("app.codes.ventanilla.estado.cerrado"), ANULADO(
			"app.codes.ventanilla.estado.anulado");

	private String descripcion;

	private EstadoVentanillaPermanente(String key) {
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
