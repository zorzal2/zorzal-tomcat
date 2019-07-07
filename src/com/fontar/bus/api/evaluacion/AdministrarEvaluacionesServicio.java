package com.fontar.bus.api.evaluacion;

import java.text.ParseException;
import java.util.Collection;

import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.Evaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;

/**
 * Servicios básicos para la administración de evaluaciones. 
 */
public interface AdministrarEvaluacionesServicio {

	/**
	 * Dado un id de Evaluacion devuelve una Bean con los datos de una Evaluacion
	 * @param idEvaluacion
	 * @return EvaluacionBean
	 */
	@SuppressWarnings("unchecked")
	EvaluacionBean obtenerEvaluacion(Long idEvaluacion);

	/**
	 * A partir de un identificador de evaluación obtiene un DTO de evaluación de acuerdo al assembler especificado. 
	 * @param idEvaluacion
	 * @param assembler
	 * @return
	 */
	public EvaluacionDTO getEvaluacionDTO(Long idEvaluacion, EvaluacionAssembler assembler);

	/**
	 * A partir de un identificador de evaluación obtiene el DTO de evaluación (assembler predeterminado).
	 * @param idEvaluacion
	 * @return
	 */
	public EvaluacionDTO getEvaluacionDTO(Long idEvaluacion);

	/**
	 * A partir de un identificador de proyecto obtiene el Bean del proyecto.
	 * @param idProyecto
	 * @return
	 */
	public ProyectoBean obtenerProyecto(Long idProyecto);
	
	/**
	 * A partir de un identificador de proyecto obtiene todas las evaluaciones del proyecto.
	 * Estas evaluaciones son las de tipo 'Evaluar Resultado'.
	 * @param idProyecto
	 * @return
	 */
	public Collection obtenerEvaluaciones(Long idProyecto);
	
	/**
	 * A partir de un identificador de proyecto obtiene todas las evaluaciones de tipo 'Finalizar Control'.
	 * @param idProyecto
	 * @return
	 */
	public Collection obtenerEvaluacionesFinCtrl(Long idProyecto);
	
	/**
	 * A partir de un proyectoBean obtiene todas las evaluaciones de un determinado tipo.
	 * @param proyecto
	 * @param tipo
	 * @return
	 */
	public Collection<Evaluacion> getEvaluaciones(ProyectoBean proyecto, String tipo);
	
	/**
	 * A partir de un identificador de proyectoRaiz obtiene todas las evaluaciones de un determinado tipo.
	 * @param idProyectoRaiz
	 * @param tipo
	 * @return
	 */
	public Collection obtenerEvaluaciones(Long idProyectoRaiz, String tipo);
	
	/**
	 * Realaciona una evaluacion con un presupuesto. 
	 * Persiste en la evaluacion que corresponde al idEvaluacion el identificador de presupuesto. 
	 * @param idEvaluacion
	 * @param idPresupueso
	 */
	public void savePresupuestoId(Long idEvaluacion, Long idPresupueso);
	
	/**
	 * Permite modifica datos del evaluacionDTO en relacion a la evaluacion identicada por idEvaluacion.<br>
	 * @param idEvaluacion
	 * @param evaluacionDTO
	 * @author ssanchez
	 */
	public Long modificarEvaluacion(Long idEvaluacion, EvaluacionGeneralDTO evaluacionDTO) throws ParseException;
}