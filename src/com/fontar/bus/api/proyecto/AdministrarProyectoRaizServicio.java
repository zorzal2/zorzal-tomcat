package com.fontar.bus.api.proyecto;

import java.util.Date;

import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
/**
 * Servicios para la administracion de Proyectos raiz (idea proyecto y Proyectos). 
 */
public interface AdministrarProyectoRaizServicio {
	/**
	 * Finaliza un proyecto. 
	 * @param idProyecto identificador del proyecto afectado.
	 * @param motivo aclaracion del motivo de cierre.
	 * @param observacion descripcion en relacion al motivo de cierre.
	 */
	public abstract void cerrarProyecto(Long idProyecto, MotivoCierre motivo, String observacion);
	
	/**
	 * Pasa un proyecto a estado anulado. 
	 * @param idProyecto identificador del proyecto afectado.
	 * @param observacion aclaracion del motivo anulacion.
	 */
	public abstract void anularProyecto(Long idProyecto, String observacion);
	
	/**
	 * Define una nueva evaluacion para un cierto proyecto.
	 * @param evaluacionDTO
	 * @param idProyecto
	 * @return
	 */
	public Long cargarEvaluacionAProyecto(EvaluacionGeneralDTO evaluacionDTO, Long idProyecto);

	/**
	 * Devuelve un DTO de ProyectoRaizEvaluar los datos 
	 * @param idProyecto
	 * @return
	 */
	public ProyectoRaizEvaluarDTO obtenerDatosEvaluacion(Long idProyecto);

	/**
	 * Devuelve un DTO con informacion del cierre de proyecto.
	 * @param idProyecto
	 * @return
	 */
	public ProyectoRaizCerrarDTO obtenerDatosCierre(Long idProyecto);

	/**
	 * Retorna un DTO con la informacion del proyecto en funcion del assembler especificado
	 * @param idProyecto
	 * @param assembler
	 * @return
	 */
	public DTO getProyectoRaizDTO(Long idProyecto, ProyectoRaizGeneralAssembler assembler);
	
	/**
	 * Retorna un DTO de ProyectoRaiz con la informacion del proyecto.
	 * @param idProyecto
	 * @return
	 */
	public ProyectoRaizDTO getProyectoRaizDTO(Long idProyecto);
	
	/**
	 * Retorna un DTO de ProyectoRaiz con la informacion necesaria para la evaluacion del ProyectoRaiz (Proyecto/Idea Proyecto).
	 * @param idProyectoRaiz
	 * @return
	 */
	public ProyectoRaizEvaluarDTO obtenerClaseProyectoRaiz(Long idProyectoRaiz);
	
	/**
	 * Define que el proyecto Raiz debe ser reconsiderado.  
	 * @param idProyectoRaiz
	 * @param fecha
	 * @param observacion
	 */
	public void solicitarReconsideracionDeProyectoRaiz(Long idProyectoRaiz, Date fecha, String observacion);
	
	/**
	 * Registra una modificacion de presupuesto de un proyecto Raiz
	 * @param proyectoId
	 * @param presupuesto
	 */
	public void actualizarPresupuestoActual(Long proyectoId, ProyectoPresupuestoDTO presupuesto);
	/**
	 * Devuelve un DTO con los montos solicitados y aprobados previstos al
	 * finalizar el control de las evaluaciones del seguimiento.
	 * @param idProyecto
	 * @return
	 */
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuestoParaFinalizarControl(Long idProyecto);
}
