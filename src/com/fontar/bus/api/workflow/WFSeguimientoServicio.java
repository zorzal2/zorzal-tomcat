package com.fontar.bus.api.workflow;

import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;


public interface WFSeguimientoServicio {
	
	public Long cargarSeguimiento(Long idTaskInstance, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones);
	public void anularSeguimiento(String observacion, Long idTaskInstance);
	
	public ProyectoCabeceraDTO obtenerCabeceraAltaSeguimiento(Long idTaskInstance);
	public SeguimientoVisualizacionCabeceraDTO obtenerDatosCabeceraSeguimientoVisualizacion(Long idTaskInstance);
	public void cargarEvaluacionASeguimiento(EvaluacionSeguimientoDTO evaluacion, Long idTaskInstance);
	public void finalizarControlEvaluacion(EvaluacionSeguimientoDTO evaluacion, ResultadoEvaluacion resultado, Long idTaskInstance);
	public EstadoSeguimiento obtenerEstadoSeguimiento(Long idSeguimiento);
	public List<String> getEvaluacionesAbiertas(Long idSeguimiento);
	public Collection getEvaluaciones(SeguimientoBean seguimientoBean, String finalizar_control);
	public void autorizarPago(boolean autorizada, EvaluacionSeguimientoDTO evaluacion, Long idTaskInstance);

	/**
	 *  Cierra un <code>Seguimiento</code> finalizando el workflow.
	 *  Guarda la observación ingresada.<br>
	 */
	public void cerrarSeguimiento(String observacion, Long idTaskInstance);
	
	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas y son de tipo técnica.
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesTecnicasAbiertas(Long idTaskInstance);
	
	/**
	 * Obtiene una lista de Evaluaciones para un 
	 * Seguimiento que estan abiertas y son de tipo contable.
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesContablesAbiertas(Long idTaskInstance);
	
	/**
	 * Obtiene todas las Evaluaciones de un
	 * Seguimiento que coninciden con el <i>estado</i>.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesPorEstado(Long idTaskInstance, EstadoEvaluacion estado);
	
	/**
	 * Verifica si el Seguimiento tiene al menos
	 * una Evaluación Técnica y una Contable en
	 * estado confirmada.
	 * @param idTaskInstance
	 * @return Boolean
	 * @author ssanchez
	 */
	public Boolean tieneEvaluacionesTecnicaYContableConfirmadas(Long idTaskInstance);
	
	/**
	 * Obtiene el SeguimientoBean 
	 * correspondiente al <i>idTaskInstance</i>.<br>
	 */
	public SeguimientoBean obtenerSeguimiento(Long idTaskInstance);
	
	/**
	 * Modifica el estado del seguimiento según 
	 * el parametro <i>estado</i>.<br>
	 * Persiste la observación y finaliza
	 * la tarea de workflow.<br>
	 * @param idTaskInstance
	 * @param estado
	 * @param observación
	 * @author ssanchez
	 */
	public void cargarGestionPago(Long idTaskInstance, EstadoSeguimiento estado, String observacion);
	
	/**
	 * Fuerza el workflow de <code>Seguimiento</code>
	 * para finalizase mediante la Anulación y deja
	 * el estado del mismo en <code>Gestionado</code>.<br>
	 * @param idSeguimiento
	 */
	public void finalizarWorkflowSeguimiento(Long idSeguimiento);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>técnica</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionTecnicaAbierta(Long idTaskInstance);

	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>visita técnica</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionVisitaTecnicaAbierta(Long idTaskInstance);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>contable</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionContableAbierta(Long idTaskInstance);
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>auditoría contable</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionAuditoriaContableAbierta(Long idTaskInstance);
	
	/**
	 * Obtiene una lista de Evaluaciones abiertas
	 * correspondientes a un <code>Seguimiento</code>.<br> 
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesAbiertas(Long idTaskInstance);
	
}