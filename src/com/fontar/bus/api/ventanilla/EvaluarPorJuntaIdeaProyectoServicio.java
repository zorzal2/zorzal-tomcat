package com.fontar.bus.api.ventanilla;

import java.util.Date;

import com.fontar.bus.api.workflow.OpcionDeEvaluacionPorJunta;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;

/**
 * Servicios para Evaluacion por Junta de Ideas Proyectos
 */
public interface EvaluarPorJuntaIdeaProyectoServicio {
	/**
	 * Registra el resultado de la evaluacion realizada por la junta de elegiblidad. 
	 * @param idProyecto identificador de la Idea Proyecto evaluada
	 * @param fechaEvaluacion fecha de evaluacion 
	 * @param recomendacion descripcion del resultado
	 * @param aceptaProyecto resultado de la evaluacion  
	 * @param fundamentacion motivo del resultado
	 */
	public abstract void cargarEvaluacionPorJunta(String idProyecto, Date fechaEvaluacion, String recomendacion,
			OpcionDeEvaluacionPorJunta aceptaProyecto, String fundamentacion);

	/**
	 * Obtiene un DTO con los datos de la evaluacion por Junta de elegiblidad para una Idea Proyecto
	 * @param idIdeaProyecto
	 * @return
	 */
	public abstract IdeaProyectoEvaluarPorJuntaDTO obtenerDatos(Long idIdeaProyecto);

}
