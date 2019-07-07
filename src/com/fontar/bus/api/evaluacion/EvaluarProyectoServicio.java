package com.fontar.bus.api.evaluacion;

import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.bus.impl.evaluacion.EvaluacionGeneralAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;

/**
 * Servicios relacionados con la evaluacion de los proyectos.
 */
public interface EvaluarProyectoServicio {

	/**
	 * Dado un id de Evaluacion devuelve un DTO con los datos de la evaluación.
	 * @param idEvaluacion
	 * @return EvaluacionGeneralDTO
	 */
	@SuppressWarnings("unchecked")
	EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idEvaluacion);

	/**
	 * Carga el resultado de la evaluación
	 * @param evaluacion Evaluacion
	 */
	@SuppressWarnings("unchecked")
	void cargarResultadoEvaluacion(EvaluacionGeneralDTO evaluacionDTO, boolean aprobado);

	/**
	 * Permite pasar a anulada una evaluacion de un proyecto.
	 * @param evaluacionDTO evaluacion a anular.
	 * @param observaciones descripcion del motivo de la anulacion.
	 */	@SuppressWarnings("unchecked")
	void anularEvaluacion(EvaluacionGeneralDTO evaluacionDTO, String observaciones);

	 /**
	  * Registra el resultado del proceso de autorizacion de una evaluacion.
	  * @param idEvaluacion evaluacion a procesar.
	  * @param autorizada si es TRUE pasa a estado PENDIENTE RESULTADO en caso contrario pasa a estado NO_AUTORIZADA. 
	  * @param fundamentacion motivo de la decision.
	  */
	void autorizarEvaluacion(Long idEvaluacion, boolean autorizada, String fundamentacion);
	
	/**
	 * Confirma el resultado cargado en una evaluacion. Pasa la evaluacion a estado CONFIRMADA.
	 * @param idEvaluacion
	 * @param observaciones
	 */
	@SuppressWarnings("unchecked")
	void confirmarEvaluacion(Long idEvaluacion, String observaciones);

	/**
	 * A partir de un identificador de Evaluacion retorna un DTO evaluacion general en funcion de un assembler dado. 
	 * @param idEvaluacion
	 * @param assembler
	 * @return
	 */
	EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idEvaluacion, EvaluacionGeneralAssembler assembler);

	/**
	 * A partir de un identificador de Evaluacion retorna un DTO evaluacion en funcion de un assembler dado. 
	 * @param idEvaluacion
	 * @param assembler
	 * @return
	 */
	EvaluacionDTO obtenerEvaluacionGeneral(Long idEvaluacion, EvaluacionAssembler assembler);

	/**
	 * Registra los criterios para la evaluacion relacionada con idEvaluacion.
	 * @param idEvaluacion
	 * @param idCriterios contiene el los identificadores de criterios (String[])
	 */
	void cargarCriterioEvaluacion(Long idEvaluacion, Object idCriterios);

	/**
	 * Obtiene los identificadores de evaluacion de criterios para la evaluacion definda.
	 * @param id
	 * @return
	 */
	String[] obtenerCriterioEvaluacion(Long id);
	
	/**
	 * Obtiene los datos para la cabecera de <code>Evaluacion de Seguimiento</code>.<br>
	 * @param idEvaluacion
	 * @return EvaluacionSeguimientoCabeceraDTO
	 * @author ssanchez
	 */
	public EvaluacionSeguimientoCabeceraDTO obtenerCabeceraEvaluacionSeguimiento(Long idEvaluacion);

	/**
	 * Obtiene la <code>EvaluacionGeneralBean</code>
	 * correspondiente al <i>idEvaluacion</i>.<br>
	 * @param idEvaluacion
	 * @return una EvaluacionGeneralBean
	 */
	public EvaluacionGeneralBean obtenerEvaluacionGeneralBean(Long idEvaluacion);
	/**
	 * Devuelve un DTO conteniendo los montos solicitados y aprobados por la evaluacion
	 * general con el id dado.
	 * @param idEvaluacion
	 * @return
	 */
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuesto(Long idEvaluacion);
}