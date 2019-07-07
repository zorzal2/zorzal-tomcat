package com.fontar.bus.api.workflow;

import java.util.List;

import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;

/**
 * Servicios relacionados con el circuito de Workflow de Paquetes. 
 * Los Paquetes b�scamente son conjuntos de proyectos que se evaluan en forma conjunta.  
 */
public interface WFPaqueteServicio {

	/**
	 * Servicio para cargar el resultado de evaluaci�n del paquete.
	 * @param idPaquete Identificador del paquete.
	 * @param codigoActa N�mero del acta de evauaci�n.
	 * @param observacion Comentarios del usuario relacionados con la tarea.
	 * @param idTaskInstance Tarea del paquete que invoc� al servicio.
	 */
	public void cargarEvaluacion(Long idPaquete, String codigoActa, String observacion, Long idTaskInstance);

	/**
	 * Servicio para confirmar el resultado de evaluaci�n del paquete. 
	 * Este servicio permite modificar los datos de la evaluaci�n.
	 * @param idPaquete Identificador del paquete.
	 * @param userName Usuario que realiza la acci�n (no utlizado actualmente).
	 * @param idTaskInstance Tarea del paquete que invoc� al servicio.
	 * @param codigoActa N�mero del acta de evauaci�n.
	 * @param observacion Comentarios del usuario relacionados con la tarea.  
	 */
	public void confirmarEvaluacion(Long idPaquete, String userName, Long idTaskInstance, String codigoActa, String observacion);

	/**
	 * Servicio para definir un nuevo paquete.
	 * @param proyectoArray Indentificadores de proyectos a conformar el nuevo paquete.
	 * @param idInstrumento instrumento de Beneficio de los proyectos.
	 * @param tratamiento puede ser Evaluaci�n, Reconsideraci�n, o Adjudicaci�n. Para mayor informaci�n ver <code>TratamientoPaquete<code>.
	 * @param tipoPaquete puede ser Comision, Secretaria o Directorio. Para mayor informaci�n ver <code>TipoPaquete<code>.
	 */
	public void armarPaquete(String[] proyectoArray, Long idInstrumento, String tratamiento, String tipoPaquete);

	/**
	 * Tarea que permite modificar los proyectos que componen un paquete.
	 * @param idPaquete Identificador del paquete.
	 * @param proyectosSeleccionados proyectos que conformar el paquete.
	 * @param idTaskInstance Tarea de workflow que invoc� al servicio.
	 */
	public void modificarPaquete(Long idPaquete, String[] proyectosSeleccionados, Long idTaskInstance);

	/**
	 * Servicio para realizar la tarea de controlar Paquete. 
	 * Los proyectos seleccionados se conservan en el paquete, el resto son descartados.
	 * El paquete pasa a estado CONTROLADO. 
	 * @param idPaquete Identificador del paquete.
	 * @param proyectosSeleccionados c�digos de proyectos a conservar en el paquete.
	 * @param idTaskInstance tarea del paquete que invoc� al servicio.
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
	 * @param tratamiento puede ser Evaluaci�n, Reconsideraci�n, o Adjudicaci�n. Para mayor informaci�n ver <code>TratamientoPaquete<code>.
	 * @param tipoPaquete puede ser Comision, Secretaria o Directorio. Para mayor informaci�n ver <code>TipoPaquete<code>.
	 * @return
	 */
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectos(Long instrumento, String tratamiento, String tipoPaquete);

	
	/**
	 * Devuelve un listado de elementos del paquete.
	 * @param idTaskInstance Tarea del paquete que invoc� al servicio.
	 * @param tipoPaquete tipo de paquete.
	 * @return
	 */
	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectosPaquete(Long idTaskInstance, String tipoPaquete);

	/**
	 * Este serivicio se emplea �ra anular un paquete. El paquete pasa a estado ANULADO y 
	 * los proyectos que tenian quedan libres para poder ser tomados por nuevos paquetes.
	 * @param idPaquete Identificador del paquete.
	 * @param observaciones motivo de la anulaci�n.
	 * @param idTaskInstance Tarea del paquete que invoc� al servicio. 
	 */
	public void anularPaquete(Long idPaquete, String observaciones, Long idTaskInstance);
	
	
	/**
	 * Obtiene un DTO con los datos de paquete en funci�n de un determinado assembler.  
	 * @param idTaskInstance Tarea del paquete que invoc� al servicio.
	 * @param assembler
	 * @return
	 */
	public DTO getPaqueteDTO(Long idTaskInstance, PaqueteGeneralAssembler assembler);

	/**
	 * Obtiene una lista con los Proyectos pertenecientes a un Paquete que no tienen 
	 * cargada una Evaluacion de Paquete.
	 * El servicio devuelve un listado con los c�digos de estos Proyectos.
	 * @param Tarea del paquete que invoc� al servicio.
	 * @return listado con c�digos de proyectos.
	 */
	public List<String> obtenerProyectoPaqueteSinEval(Long idTaskInstance);
}
