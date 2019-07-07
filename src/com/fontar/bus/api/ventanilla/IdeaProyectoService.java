package com.fontar.bus.api.ventanilla;

import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.pragma.bus.api.GenericService;


/**
 * Servicios para la administración basica (sin tareas de workflow) de Ideas proyecto.
 */
public interface IdeaProyectoService extends GenericService {
	/**
	 * Devuelve la un
	 * @param id
	 * @return
	 */
	public EvaluacionBean getUltimaEvaluacionPorJunta(Long id);
	
	/**
	 * Actualiza el estado de la Idea Proyecto.
	 * @param idIdeaProyecto identificador de la Idea Proyecto.
	 * @param estado nuevo estado.
	 */
	public void updateEstado(Long idIdeaProyecto, EstadoIdeaProyecto estado);
}
