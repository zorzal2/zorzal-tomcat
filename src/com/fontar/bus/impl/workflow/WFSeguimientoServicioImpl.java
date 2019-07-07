package com.fontar.bus.impl.workflow;

import java.util.Collection;
import java.util.List;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.api.dao.EvaluacionSeguimientoDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.jbpm.manager.EvaluacionSeguimientoProcessManager;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.jbpm.manager.SeguimientoProcessManager;
import com.fontar.jbpm.manager.SeguimientoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

/**
 * @author gboaglio
 * 
 */
public class WFSeguimientoServicioImpl implements WFSeguimientoServicio {

	private AdministrarSeguimientoServicio administrarSeguimientoServicio;
	private PersonaDAO personaDao;
	private SeguridadObjetoServicio seguridadObjetoServicio;
	
	public void setPersonaDao(PersonaDAO personaDao) {
		this.personaDao = personaDao;
	}

	/**
	 * Carga un nuevo seguimiento, crea la instancia de WF que se le asignará al mismo.
	 * y hace intercambio de ids.
	 * 
	 */
	public Long cargarSeguimiento(Long idTaskInstance, Boolean esFinanciero, Boolean esTecnico, String descripcion, String observaciones) {		

		SeguimientoDAO seguimientoDao = (SeguimientoDAO) ContextUtil.getBean("seguimientoDao");
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto(); 
		
		// Alta de seguimiento				
		Long idSeguimiento = administrarSeguimientoServicio.cargarSeguimiento(idProyecto, esFinanciero, esTecnico, descripcion, observaciones);		
		
		// Creo la instancia de WF asociada al nuevo seguimiento
		ProyectoRaizDAO proyectoRaizDAO = (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
		ProyectoRaizBean proyectoRaizBean = proyectoRaizDAO.read(idProyecto);

		SeguimientoProcessManager processManager = new SeguimientoProcessManager();
		Long idProcessInstance = processManager.nuevoProcessInstance(idSeguimiento, proyectoRaizBean.getIdInstrumento());
		

		// Intercambio de id's y actualización del seguimiento creado		
		SeguimientoBean seguimiento = seguimientoDao.read(idSeguimiento);
		seguimiento.setIdWorkFlow(idProcessInstance);
		
		seguimientoDao.update(seguimiento);
		
		// Finalizo la tarea
		taskHelper.finalizarTarea();
		
		return idSeguimiento;
	}

	/**
	 *  Anula un Seguimiento. Finaliza la tarea correspondiente en el WF.
	 */
	public void anularSeguimiento(String observacion, Long idTaskInstance) {
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		administrarSeguimientoServicio.anularSeguimiento(idSeguimiento, observacion);

		taskHelper.finalizarTarea();
	}
	
	/**
	 * Devuelve un DTO con los datos para la cabecera de Alta de un Seguimiento
	 */
	public ProyectoCabeceraDTO obtenerCabeceraAltaSeguimiento(Long idTaskInstance) {		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		
		return administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoAlta(idProyecto);
	}

	public SeguimientoVisualizacionCabeceraDTO obtenerDatosCabeceraSeguimientoVisualizacion(Long idTaskInstance) {
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento= taskHelper.getIdSeguimiento();
		
		return administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);
	}
	
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	public void cargarEvaluacionASeguimiento(EvaluacionSeguimientoDTO evaluacion, Long idTaskInstance) {
		EvaluacionSeguimientoDAO evaluacionSeguimientoDao = (EvaluacionSeguimientoDAO) ContextUtil.getBean("evaluacionSeguimientoDao");
		EvaluacionSeguimientoProcessManager evaluacionProcessManager = new EvaluacionSeguimientoProcessManager();

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		evaluacion.setIdSeguimiento(idSeguimiento);

		EvaluacionSeguimientoBean evaluacionBean = administrarSeguimientoServicio.cargarEvaluacionASeguimiento(evaluacion, idSeguimiento);
		
		evaluacionBean.setIdWorkFlow(evaluacionProcessManager.nuevoProcessInstance(evaluacionBean.getId(), evaluacionBean.getProyecto().getIdInstrumento()));
		evaluacionBean.setFecha(DateTimeUtil.getDate());
		
		/*
		 * Actualizo los permisos de los evaluadores
		 */
		// Grabo los evaluadores
		Collection<EvaluacionEvaluadorDTO> evaluadorListDTO = evaluacion.getEvaluadores();
		if (evaluadorListDTO != null) {
			for(EvaluacionEvaluadorDTO evaluadorDTO : evaluadorListDTO) {
				if(!StringUtil.isEmpty(evaluadorDTO.getIdEvaluador())) {
					String userId = personaDao.read(new Long(evaluadorDTO.getIdEvaluador())).getUserId();
					if(userId!=null) {
						seguridadObjetoServicio.permitir(userId, "WF-EVALUACION-SEGUIMIENTO-CARGAR-RESULTADO", evaluacionBean);
					}
				}
			}
		}

		// actualizo la evaluación con el idWorkFlow
		evaluacionSeguimientoDao.update(evaluacionBean);

		taskHelper.finalizarTarea();
		
	}

	public void finalizarControlEvaluacion(EvaluacionSeguimientoDTO evaluacion, ResultadoEvaluacion resultado, Long idTaskInstance) {
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		evaluacion.setIdSeguimiento(idSeguimiento);
		administrarSeguimientoServicio.finalizarControlEvaluacion(evaluacion, resultado);
		taskHelper.finalizarTarea();
	}

	/**
	 * Devuelve el estado del proyecto, se utliza para Decisiones del WorkFlow
	 */
	public EstadoSeguimiento obtenerEstadoSeguimiento(Long idSeguimiento) {
		SeguimientoDAO seguimientoDAO = (SeguimientoDAO) ContextUtil.getBean("seguimientoDao");
		SeguimientoBean seguimientoBean = seguimientoDAO.read(idSeguimiento);
		return seguimientoBean.getEstado();
	}

	public List<String> getEvaluacionesAbiertas(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		
		return administrarSeguimientoServicio.getEvaluacionesAbiertas(idSeguimiento);
	}

	/**
	 * Obtiene una lista de Evaluaciones para un
	 * Seguimiento que estan abiertas y son de tipo técnica.
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesTecnicasAbiertas(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();		

		return administrarSeguimientoServicio.evaluacionesTecnicasAbiertas(idSeguimiento);
	}
	
	/**
	 * Obtiene una lista de Evaluaciones para un 
	 * Seguimiento que estan abiertas y son de tipo contable.
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */	
	public List<EvaluacionSeguimientoBean> evaluacionesContablesAbiertas(Long idTaskInstance) {

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();	
		
		return administrarSeguimientoServicio.evaluacionesContablesAbiertas(idSeguimiento);
	}
	
	public Collection getEvaluaciones(SeguimientoBean seguimientoBean, String finalizar_control) {
		return administrarSeguimientoServicio.getEvaluaciones(seguimientoBean, finalizar_control);
	}

	public void autorizarPago(boolean autorizada, EvaluacionSeguimientoDTO evaluacion, Long idTaskInstance) {
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		evaluacion.setIdSeguimiento(idSeguimiento);
		administrarSeguimientoServicio.autorizarPago(idSeguimiento, autorizada, evaluacion);
		
		taskHelper.finalizarTarea();
	}

	/**
	 *  Cierra un <code>Seguimiento</code> finalizando el workflow.
	 *  Guarda la observación ingresada.<br>
	 *  @author ssanchez
	 */
	public void cerrarSeguimiento(String observacion, Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);

		Long idSeguimiento = taskHelper.getIdSeguimiento();
		administrarSeguimientoServicio.cerrarSeguimiento(idSeguimiento, observacion);

		taskHelper.finalizarTarea();
	}

	/**
	 * Obtiene todas las Evaluaciones de un
	 * Seguimiento que coninciden con el <i>estado</i>.<br>
	 * @param idSeguimiento
	 * @param estado
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesPorEstado(Long idTaskInstance, EstadoEvaluacion estado) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.evaluacionesPorEstado(idSeguimiento,estado);
	}

	
	/**
	 * Verifica si el Seguimiento tiene al menos
	 * una Evaluación Técnica y una Contable en
	 * estado confirmada.
	 * @param idTaskInstance
	 * @return Boolean
	 * @author ssanchez
	 */
	public Boolean tieneEvaluacionesTecnicaYContableConfirmadas(Long idTaskInstance) {

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.tieneEvaluacionesTecnicaYContableConfirmadas(idSeguimiento);
	}
	
	/**
	 * Obtiene el SeguimientoBean 
	 * correspondiente al <i>idTaskInstance</i>.<br>
	 */
	public SeguimientoBean obtenerSeguimiento(Long idTaskInstance) {

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.obtenerSeguimiento(idSeguimiento);
	}

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
	public void cargarGestionPago(Long idTaskInstance, EstadoSeguimiento estado, String observacion) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		administrarSeguimientoServicio.cargarGestionPago(idSeguimiento,estado,observacion);
		
		taskHelper.finalizarTarea();
	}	
	
	/**
	 * Fuerza el workflow de <code>Seguimiento</code>
	 * para finalizase mediante la Anulación y deja
	 * el estado del mismo en <code>Gestionado</code>.<br>
	 * @param idSeguimiento
	 */
	public void finalizarWorkflowSeguimiento(Long idSeguimiento) {
		
		SeguimientoDAO seguimientoDAO = (SeguimientoDAO) ContextUtil.getBean("seguimientoDao");
		SeguimientoBean seguimiento = seguimientoDAO.read(idSeguimiento);
		
		SeguimientoTaskInstanceManager taskInstanceManager = new SeguimientoTaskInstanceManager();
		taskInstanceManager.anularSeguimiento(seguimiento.getIdWorkFlow());
		
		administrarSeguimientoServicio.finalizarGestionarSeguimiento(idSeguimiento);
	}	
	
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>técnica</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionTecnicaAbierta(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.tieneEvaluacionTecnicaAbierta(idSeguimiento);
	}

	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>visita técnica</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionVisitaTecnicaAbierta(Long idTaskInstance) {

		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.tieneEvaluacionVisitaTecnicaAbierta(idSeguimiento);
	}
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>contable</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionContableAbierta(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.tieneEvaluacionContableAbierta(idSeguimiento);
	}
	
	/**
	 * Devuelve <i>true</i> si el <code>Seguimiento</code>
	 * tiene una evaluación <i>auditoría contable</i> abierta.
	 * @param idTaskInstance
	 * @return true o false
	 * @author ssanchez
	 */	
	public Boolean tieneEvaluacionAuditoriaContableAbierta(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.tieneEvaluacionAuditoriaContableAbierta(idSeguimiento);
	}
	
	/**
	 * Obtiene una lista de Evaluaciones abiertas
	 * correspondientes a un <code>Seguimiento</code>.<br> 
	 * @param idTaskInstance
	 * @return List<EvaluacionSeguimientoBean>
	 * @author ssanchez
	 */
	public List<EvaluacionSeguimientoBean> evaluacionesAbiertas(Long idTaskInstance) {
		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();

		return administrarSeguimientoServicio.evaluacionesAbiertas(idSeguimiento);
	}

	public void setSeguridadObjetoServicio(SeguridadObjetoServicio seguridadObjetoServicio) {
		this.seguridadObjetoServicio = seguridadObjetoServicio;
	}
}
