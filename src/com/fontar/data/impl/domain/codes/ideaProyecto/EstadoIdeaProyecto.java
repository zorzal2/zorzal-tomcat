package com.fontar.data.impl.domain.codes.ideaProyecto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Estados de Idea Proyecto
 */
public enum EstadoIdeaProyecto implements Enumerable {

	INICIADO("app.codes.ideaProyecto.estado.iniciada"), 
	EVALUACION("app.codes.ideaProyecto.estado.enEvaluacion"),
	CERRADO("app.codes.ideaProyecto.estado.cerrada"),
	FINALIZADO("app.codes.ideaProyecto.estado.finalizada"), 
	ANULADO("app.codes.ideaProyecto.estado.anulada"),
	PENDIENTE("app.codes.ideaProyecto.estado.pendiente"),
	ELEGIBLE("app.codes.ideaProyecto.estado.elegible"),
	NO_ELEGIBLE("app.codes.ideaProyecto.estado.noElegible");
	
	private String descripcion;

	private EstadoIdeaProyecto(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
