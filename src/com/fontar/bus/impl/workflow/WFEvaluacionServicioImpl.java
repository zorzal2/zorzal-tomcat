package com.fontar.bus.impl.workflow;

import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.bus.impl.evaluacion.EvaluacionAssembler;
import com.fontar.bus.impl.evaluacion.EvaluacionGeneralAssembler;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.jbpm.manager.EvaluacionTaskInstanceManager;
import com.fontar.jbpm.manager.SeguimientoTaskInstanceManager;
import com.pragma.util.ContextUtil;

public class WFEvaluacionServicioImpl implements WFEvaluacionServicio {

	private EvaluarProyectoServicio evaluarProyectoServicio;

	private AdministrarProyectoServicio administrarProyectoServicio;
	
	private AdministrarSeguimientoServicio administracionSeguimientoService;

	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}

	public void setEvaluarProyectoServicio(EvaluarProyectoServicio evaluarProyectoServicio) {
		this.evaluarProyectoServicio = evaluarProyectoServicio;
	}

	/**
	 * Carga el resultado de la evaluación
	 */
	@SuppressWarnings("unchecked")
	public void cargarResultadoEvaluacion(EvaluacionGeneralDTO evaluacionDTO, Boolean aprobado, Long idTaskInstance) {

		evaluarProyectoServicio.cargarResultadoEvaluacion(evaluacionDTO, aprobado);

		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		taskHelper.finalizarTarea();
	}

	/**
	 * Anula la evaluacion
	 */
	public void anularEvaluacion(EvaluacionGeneralDTO evaluacionDTO, String observaciones, Long idTaskInstance) {
		evaluarProyectoServicio.anularEvaluacion(evaluacionDTO, observaciones);

		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		taskHelper.finalizarTarea();
	}

	/**
	 * Autoriza o desautoriza la evaluacion
	 */
	public void autorizarEvaluacion(boolean autorizada, String fundamentacion, Long idTaskInstance) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		evaluarProyectoServicio.autorizarEvaluacion(idEvaluacion, autorizada, fundamentacion);
		
		taskHelper.finalizarTarea();
	}

	/**
	 * Confirma la evaluacion
	 */
	public void confirmarEvaluacion(String observaciones, Long idTaskInstance) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		
		evaluarProyectoServicio.confirmarEvaluacion(idEvaluacion, observaciones);

		taskHelper.finalizarTarea();
	}

	/**
	 * Obtiene una evaluacion dado un idTarea
	 */
	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idTaskInstance) {
		Long idEvaluacion = this.obtenerIdEvaluacionGeneral(idTaskInstance);
		return evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion);
	}
	
	public Long obtenerIdEvaluacionGeneral(Long idTaskInstance) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		return idEvaluacion;
	}
	
	
	/**
	 * Obtiene el presupuesto mediante el idProyecto
	 */
	public ProyectoPresupuestoDTO obtenerPresupuesto(Long idTaskInstance) throws Exception {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);

		return administrarProyectoServicio.obtenerPresupuesto(null, taskHelper.getIdEvaluacion());
	}

	/**
	 * Obtiene el presupuesto mediante el idProyecto
	 */
	public ProyectoPresupuestoDTO obtenerPresupuestoProyecto(Long idProyecto) throws Exception {
		return administrarProyectoServicio.obtenerPresupuesto(idProyecto, null);
	}

	/**
	 * Guarda el presupuesto de la evaluación
	 */
	public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idTaskInstance)
			throws Exception {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);

		return administrarProyectoServicio.cargarPresupuesto(presupuesto, null, taskHelper.getIdEvaluacion());
	}

	/**
	 * Devuelve el estado de la evaluacion, se utliza para Decisiones del
	 * WorkFlow
	 */
	public EstadoEvaluacion obtenerEstadoEvaluacion(Long idEvaluacion) {
		EvaluacionDAO evaluacionDao = (EvaluacionDAO) ContextUtil.getBean("evaluacionDao");
		EvaluacionBean evaluacion = evaluacionDao.read(idEvaluacion);
		return evaluacion.getEstado();
	}

	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idTaskInstance, EvaluacionGeneralAssembler assembler) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		return evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion, assembler);
	}


	public EvaluacionDTO obtenerEvaluacion(Long idTaskInstance, EvaluacionAssembler assembler) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		return evaluarProyectoServicio.obtenerEvaluacionGeneral(idEvaluacion, assembler);
	}

	public void cargarCriterioEvaluacion(Long idProyecto, Long idEvaluacion, String proyectoTipo, Object idCriterios) {
		administrarProyectoServicio.cargarCriterio(idProyecto,proyectoTipo);		
		evaluarProyectoServicio.cargarCriterioEvaluacion(idEvaluacion,idCriterios);
		
	}

	public String[] obtenerCriterioEvaluacion(Long id) {
		return evaluarProyectoServicio.obtenerCriterioEvaluacion(id);
	}

	public String obtenerTipoProyecto(String id) {
		return administrarProyectoServicio.obtenerTipoProyecto(id);
	}

	/**
	 * Obtiene los datos para la cabecera de <code>Evaluacion de Seguimiento</code>.<br>
	 * @param idTaskInstance
	 * @return EvaluacionSeguimientoCabeceraDTO
	 * @author ssanchez
	 */
	public EvaluacionSeguimientoCabeceraDTO obtenerCabeceraEvaluacionSeguimiento(Long idTaskInstance) {

		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();

		return evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);

	}

	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuesto(Long idTaskInstance) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		return evaluarProyectoServicio.obtenerResumenDePresupuesto(idEvaluacion);
	}

	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(Long idTaskInstance) {
		EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
		Long idEvaluacion = taskHelper.getIdEvaluacion();
		
		return administracionSeguimientoService.obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(idEvaluacion);
	}

	public ResumenDeRendicionCompactoDTO obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(Long idTaskInstance) {
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		
		return administracionSeguimientoService.obtenerTotalesRendicionesParaFinalizarControlDeSeguimiento(idSeguimiento);
	}

	public void setAdministracionSeguimientoService(AdministrarSeguimientoServicio administracionSeguimientoService) {
		this.administracionSeguimientoService = administracionSeguimientoService;
	}	
}