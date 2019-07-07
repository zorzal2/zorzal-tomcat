package com.fontar.data.impl.domain.codes.seguimiento;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de los posibles Estados de un Seguimiento
 */
public enum EstadoSeguimiento implements Enumerable {

	INICIADO("app.codes.seguimiento.estado.iniciado"),
	EVALUACION("app.codes.seguimiento.estado.evaluacion"),
	CONTROLADO("app.codes.seguimiento.estado.controlado"),
	AUTORIZADO("app.codes.seguimiento.estado.autorizado"),
	NO_AUTORIZADO("app.codes.seguimiento.estado.noAutorizado"),
	ANULADO("app.codes.seguimiento.estado.anulado"),
	CERRADO("app.codes.seguimiento.estado.cerrado"),
	GESTIONADO("app.codes.seguimiento.estado.gestionado"),
	NO_GESTIONADO("app.codes.seguimiento.estado.noGestionado"),
	RECHAZADO("app.codes.seguimiento.estado.rechazado"),
	FINALIZADO("app.codes.seguimiento.estado.finalizado");
	
	private String descripcion;

	private EstadoSeguimiento(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
