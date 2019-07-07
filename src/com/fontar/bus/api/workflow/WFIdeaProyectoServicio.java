package com.fontar.bus.api.workflow;

import java.util.Date;

import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;
import com.fontar.data.impl.domain.dto.ProyectoCargarDTO;

/**
 * Servicios para las Ideas Proyecto relacionados con las tareas de workflow. 
 * 
 */
public interface WFIdeaProyectoServicio {
	
	/**
	 *  Define una nueva Idea Proyecto con su respectivo workflow.  
	 * @param datosIdeaProyecto datos para la nueva entidad Idea Proyecto.
	 */public void cargarIdeaProyecto(IdeaProyectoDTO datosIdeaProyecto);

	 /**
	  * Permite actualizar los datos de una Idea Proyecto. 
	  * @param idIdeaProyecto Identificador de Idea Proyecto.
	  * @param datosIdeaProyecto nuevos datos para la Idea Proyecto. 
	  */
	public void modificarIdeaProyecto(String idIdeaProyecto, IdeaProyectoDTO datosIdeaProyecto);

	/**
	 * Servicio para la carga del resultado de la evaluaci�n por Junta de Elegiblidad para una Idea Proyecto.
	 * @param idProyecto identificador de Idea Proyecto
	 * @param fechaEvaluacion Fecha de Evaluaci�n
	 * @param recomendacion descripci�n de la recomendaci�n 
	 * @param aceptaProyecto Resultado de evaluaci�n. 
	 * @param fundamentacion argumentaci�n de la decisi�n.
	 * @param idTaskInstance Tarea de workflow asociada con este servicio. 
	 */
	public void cargarEvaluacionPorJunta(String idProyecto, Date fechaEvaluacion, String recomendacion,
			OpcionDeEvaluacionPorJunta aceptaProyecto, String fundamentacion, Long idTaskInstance);

	/**
	 * Devuelve un DTO con los datos necesarios para la pantalla de Evaluaci�n
	 * por Junta, incluyendo las opciones que pueden elegirse como resultado de la evaluaci�n.
	 * @param idTaskInstance
	 * @return
	 */
	public IdeaProyectoEvaluarPorJuntaDTO obtenerDatosEvaluacionPorJunta(Long idTaskInstance);
	
	/**
	 * Obtiene un DTO con los datos de la Idea Proyecto para una tarea en funci�n de un Assembler determinado.
	 * @param idTaskInstance Tarea de Idea Proyecto.
	 * @param assembler assembler a utilizar.
	 * @return
	 */
	public DTO getIdeaProyectoDTO(Long idTaskInstance, IdeaProyectoGeneralAssembler assembler);
	
	/**
	 * A partir de un Identificador de Idea Proyecto obtiene el estado de la Idea Proyecto. 
	 * @param idIdeaProyecto
	 * @return
	 */
	public EstadoIdeaProyecto obtenerEstadoIdeaProyecto(Long idIdeaProyecto);
	
	/**
	 *	Este servicio permite cargar un nuevo proyecto a partir de una Idea Proyecto que resulto elegible. 
	 *  Con el DTO del proyecto a cargar se define un nuevo proyecto al cual se le instancia su correspondiente circuito de workflow.
	 * Adem�s finaliza la tarea que invoc� este servicio.
	 * @param proyectoCargarDTO Datos para cargar el proyecto resultante de la Idea Proyecto.
	 * @param vieneDePresentacion Indica si el proyecto es de presentaci�n a convocatoria o no. 
	 * Esta informaci�n determina por ejemplo si se debe realizar o no la tarea de admisi�n del proyecto. 
	 * @param idTaskInstance identificador de la tarea de workflow asociada con este servicio.
	 */
	public void cargarProyecto(ProyectoCargarDTO proyectoCargarDTO,Boolean vieneDePresentacion, Long idTaskInstance);	
}
