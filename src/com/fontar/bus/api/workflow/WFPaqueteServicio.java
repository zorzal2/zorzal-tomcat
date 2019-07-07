package com.fontar.bus.api.workflow;

import java.util.List;

import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;

/**
 * Servicios relacionados con el circuito de Workflow de Paquetes. 
 * Los Paquetes báscamente son conjuntos de proyectos que se evaluan en forma conjunta.  
 */
public interface WFPaqueteServicio {

	/**
	 * Servicio para cargar el resultado de evaluación del paquete.
	 * @param idPaquete Identificador del paquete.
	 * @param codigoActa Número del acta de evauación.
	 * @param observacion Comentarios del usuario relacionados con la tarea.
	 * @param idTaskInstance Tarea del paquete que invocó al servicio.
	 */
	public void cargarEvaluacion(Long idPaquete, String codigoActa, String observacion, Long idTaskInstance);

	/**
	 * Servicio para confirmar el resultado de evaluación del paquete. 
	 * Este servicio permite modificar los datos de la evaluación.
	 * @param idPaquete Identificador del paquete.
	 * @param userName Usuario que realiza la acción (no utlizado actualmente).
	 * @param idTaskInstance Tarea del paquete que invocó al servicio.
	 * @param codigoActa Número del acta de evauación.
	 * @param observacion Comentarios del usuario relacionados con la tarea.  
	 */
	public void confirmarEvaluacion(Long idPaquete, String userName, Long idTaskInstance, String codigoActa, String observacion);

	/**
	 * Servicio para definir un nuevo paquete.
	 * @param proyectoArray Indentificadores de proyectos a conformar el nuevo paquete.
	 * @param idInstrumento instrumento de Beneficio de los proyectos.
	 * @param tratamiento puede ser Evaluación, Reconsideración, o Adjudicación. Para mayor información ver <code>TratamientoPaquete<code>.
	 * @param tipoPaquete puede ser Comision, Secretaria o Directorio. Para mayor información ver <code>TipoPaquete<code>.
	 */
	public void armarPaquete(String[] proyectoArray, Long idInstrumento, String tratamiento, String tipoPaquete);

	/**
	 * Tarea que permite modificar los proyectos que componen un paquete.
	 * @param idPaquete Identificador del paquete.
	 * @param proyectosSeleccionados proyectos que conformar el paquete.
	 * @param idTaskInstance Tarea de workflow que invocó al servicio.
	 */
	public void modificarPaquete(Long idPaquete, String[] proyectosSeleccionados, Long idTaskInstance);

	/**
	 * Servicio para realizar la tarea de controlar Paquete. 
	 * Los proyectos seleccionados se conservan en el paquete, el resto son descartados.
	 * El paquete pasa a estado CONTROLADO. 
	 * @param idPaquete Identificador del paquete.
	 * @param proyectosSeleccionados códigos de proyectos a conservar en el paquete.
	 * @param idTaskInstance tarea del paquete que invocó al servicio.
	 */
	public void controlarPaquete(Long idPaquete, String[] proyectosSeleccionados, Long idTaskInstance);

	/**
	 * Servicio para, a partir de una tarea de paquete, obtener un DTO con los datos del paquete. 
	 * @param idTaskInstance
	 * @return
	 */
	public PaqueteDTO obtenerPaquete(Long idTaskInstance);

	/**
	 * Devuelve un listado de elementos disponibles para definir un paquete correspondiente a un cierto tipo y un tratamiento 
	 * para un cierto instrumento. 
	 * @param instrumento instrumento de Beneficio. 
	 * @param tratamiento puede ser Evaluación, Reconsideración, o Adjudicación. Para mayor información ver <code>TratamientoPaquete<code>.
	 * @param tipoPaquete puede ser Comision, Secretaria o Directorio. Para mayor información ver <code>TipoPaquete<code>.
	 * @return
	 */
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectos(Long instrumento, String tratamiento, String tipoPaquete);

	
	/**
	 * Devuelve un listado de elementos del paquete.
	 * @param idTaskInstance Tarea del paquete que invocó al servicio.
	 * @param tipoPaquete tipo de paquete.
	 * @return
	 */
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectosPaquete(Long idTaskInstance, String tipoPaquete);

	/**
	 * Este serivicio se emplea àra anular un paquete. El paquete pasa a estado ANULADO y 
	 * los proyectos que tenian quedan libres para poder ser tomados por nuevos paquetes.
	 * @param idPaquete Identificador del paquete.
	 * @param observaciones motivo de la anulación.
	 * @param idTaskInstance Tarea del paquete que invocó al servicio. 
	 */
	public void anularPaquete(Long idPaquete, String observaciones, Long idTaskInstance);
	
	
	/**
	 * Obtiene un DTO con los datos de paquete en función de un determinado assembler.  
	 * @param idTaskInstance Tarea del paquete que invocó al servicio.
	 * @param assembler
	 * @return
	 */
	public DTO getPaqueteDTO(Long idTaskInstance, PaqueteGeneralAssembler assembler);

	/**
	 * Obtiene una lista con los Proyectos pertenecientes a un Paquete que no tienen 
	 * cargada una Evaluacion de Paquete.
	 * El servicio devuelve un listado con los códigos de estos Proyectos.
	 * @param Tarea del paquete que invocó al servicio.
	 * @return listado con códigos de proyectos.
	 */
	public List<String> obtenerProyectoPaqueteSinEval(Long idTaskInstance);
}
