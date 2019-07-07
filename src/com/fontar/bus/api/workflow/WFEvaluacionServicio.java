package com.fontar.bus.api.workflow;

import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.bus.impl.evaluacion.EvaluacionGeneralAssembler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;

/**
 * Servicio integrador entre los procesos de BPM y los procesos de Negocios
 * Fontar para el workflow de Evaluación
 * 
 */
public interface WFEvaluacionServicio {
	/**
	 * Servicio para registrar si una evaluación es o no autorizada.
	 * @param autorizada
	 * @param fundamentacion
	 * @param idTaskInstance
	 */
	public void autorizarEvaluacion(boolean autorizada, String fundamentacion, Long idTaskInstance);
	
	/**
	 * Servicio para registrar el resultado para una evaluación. 
	 * Este servicio sobreescrive cualquier resultado previo cargado en la misma evaluación. 
	 * @param evaluacionDTO
	 * @param aprobado
	 * @param idTaskInstance
	 */
	public void cargarResultadoEvaluacion(EvaluacionGeneralDTO evaluacionDTO, Boolean aprobado, Long idTaskInstance);

	/**
	 * Servicio para dejar sin efecto una evaluación.
	 */
	public void anularEvaluacion(EvaluacionGeneralDTO evaluacion, String observaciones, Long idTaskInstance);

	/**
	 * Servicio para confirmar la carga de resultado de una evaluación. 
	 * @param observaciones
	 * @param idTaskInstance
	 */
	public void confirmarEvaluacion(String observaciones, Long idTaskInstance);

	/**
	 * A partir de un identificador de tarea obtiene el DTO de una evaluacionGeneral. 
	 * @param idTaskInstance
	 * @return
	 */
	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idTaskInstance);

	/**
	 * A partir de un identificador de tarea obtiene el DTO de una evaluacionGeneral en funcion de un assembler dado. 
	 * @param idTaskInstance
	 * @param assembler
	 * @return
	 */
	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idTaskInstance, EvaluacionGeneralAssembler assembler);

	/**
	 * A partir de un identificador de tarea obtiene el DTO de una evaluacion en funcion de un assembler dado.
	 * @param idTaskInstance
	 * @param assembler
	 * @return
	 */
	public EvaluacionDTO obtenerEvaluacion(Long idTaskInstance, EvaluacionAssembler assembler);
	
	/**
	 * A partir de un identificador de tarea obtiene el identidicador de la evaluacion general.
	 * @param idTaskInstance
	 * @return
	 */
	public Long obtenerIdEvaluacionGeneral(Long idTaskInstance);
	
	/**
	 * A partir de un identificador de tarea de evaluacion obtiene el presupuesto asociado a la evaluacion.  
	 * @param idTaskInstance
	 * @return
	 * @throws Exception
	 */
	public ProyectoPresupuestoDTO obtenerPresupuesto(Long idTaskInstance) throws Exception;

	/**
	 * Servicio para cargar un presupuesto del proyecto modificado. 
	 * Esta presupuesto forma parte del resultado de la evaluación.  
	 * @param presupuesto
	 * @param idTaskInstance
	 * @return
	 * @throws Exception
	 */public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idTaskInstance)
			throws Exception;

	/**
	 * A partir de un identificador de proyecto obtiene el presupuesto vigente del mismo.
	 * @param idProyecto
	 * @return
	 * @throws Exception
	 */
	 public ProyectoPresupuestoDTO obtenerPresupuestoProyecto(Long idProyecto) throws Exception;

	
	/**
	 * Obtiene el estado actual para una evaluación.
	 * @param idEvaluacion
	 * @return
	 */
	 public EstadoEvaluacion obtenerEstadoEvaluacion(Long idEvaluacion);

	 /**
	  * Persiste el resultado de los criterios tecnicos asociados a la evaluacion. 
	  * Como parte de este resultado puede o no modificarse el tipo de proyecto.  
	  * @param idProyecto
	  * @param idEvaluacion
	  * @param proyectoTipo
	  * @param idCriterios
	  */
	public void cargarCriterioEvaluacion(Long idProyecto, Long idEvaluacion, String proyectoTipo, Object idCriterios);

	/**
	 * Otiene un listado de identificadores de criterios tecnicos registrados bajo 
	 * la evalucion que corresponde al id informado. 
	 * @param id
	 * @return
	 */
	public String[] obtenerCriterioEvaluacion(Long id);

	/***
	 * Obtiene el tipo de proyecto registrado para una evaluación.
	 * @param id
	 * @return
	 */
	public String obtenerTipoProyecto(String id);

	/**
	 * Obtiene los datos para la cabecera de <code>Evaluacion de Seguimiento</code>.<br>
	 * @param idTaskInstance
	 * @return EvaluacionSeguimientoCabeceraDTO
	 */
	public EvaluacionSeguimientoCabeceraDTO obtenerCabeceraEvaluacionSeguimiento(Long idTaskInstance);
	
	/**
	 * Devuelve un DTO conteniendo los montos solicitados y aprobados por la evaluacion
	 * general correspondiente al task instance dado.
	 * @param idEvaluacion
	 * @return
	 */
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuesto(Long idTaskInstance);
	/**
	 * Obtiene datos resumidos de las rendiciones para mostrar al confirmar la 
	 * evaluación de seguimiento.
	 * @param idEvaluacion
	 * @return
	 */
	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(Long idTaskInstance);
	/**
	 * Obtiene datos resumidos de las rendiciones para mostrar al finalizar el
	 * control de un seguimiento. 
	 * @param idSeguimiento
	 * @return
	 */
	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(Long idTaskInstance);
}
