package com.fontar.bus.impl.workflow;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jbpm.graph.def.Transition;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.bus.api.ventanilla.EvaluarPorJuntaIdeaProyectoServicio;
import com.fontar.bus.api.workflow.OpcionDeEvaluacionPorJunta;
import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.api.assembler.IdeaProyectoGeneralAssembler;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoCargarDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.jbpm.manager.IdeaProyectoProcessManager;
import com.fontar.jbpm.manager.IdeaProyectoTaskInstanceManager;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;

public class WFIdeaProyectoServicioImpl implements WFIdeaProyectoServicio {

	private static final String DECISION_NODE_NAME = "¿Elegible?";
	
	private AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;
	private EvaluarPorJuntaIdeaProyectoServicio evaluarPorJuntaIdeaProyectoServicio;
	private WFProyectoServicio wfProyectoServicio;
	private WFNotificacionServicio wfNotificacionServicio;
	
	DecimalFormat formato = new DecimalFormat("00000000");

	/* SETTERS de SERVICIOS */
	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}

	public void setEvaluarPorJuntaIdeaProyectoServicio(
			EvaluarPorJuntaIdeaProyectoServicio evaluarPorJuntaIdeaProyectoServicio) {
		this.evaluarPorJuntaIdeaProyectoServicio = evaluarPorJuntaIdeaProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	/* SERVICIOS de TAREAS */
	public DTO getIdeaProyectoDTO(Long idTaskInstance, IdeaProyectoGeneralAssembler assembler) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idIdeaProyecto = taskHelper.getIdProyecto();

		DTO ideaProyectoDTO = administrarIdeaProyectoServicio.getIdeaProyectoDTO(idIdeaProyecto, assembler);
		return ideaProyectoDTO;
	}	
	
	/**
	 * Carga a una <code>IdeaProyecto</code> el resultado de la Evaluación por Junta.<br>
	 * También crea una <code>Notificacion</code> con su respectivo workflow, esta requiere
	 * o no acuse de recibo según el resultado de la Evaluación por junta.<br>
	 * @param ideaProyecto
	 * @param fechaEvaluacion
	 * @param recomendacion
	 * @param aceptaProyecto: resultado de la evaluación
	 * @param fundamentacion
	 * @param idTaskInstance
	 * @author ssanchez 
	 */
	public void cargarEvaluacionPorJunta(String idProyecto, Date fechaEvaluacion, String recomendacion,
			OpcionDeEvaluacionPorJunta aceptaProyecto, String fundamentacion, Long idTaskInstance) {
		evaluarPorJuntaIdeaProyectoServicio.cargarEvaluacionPorJunta(idProyecto, fechaEvaluacion, recomendacion, aceptaProyecto, fundamentacion);
		
		IdeaProyectoTaskInstanceManager instanceManager = new IdeaProyectoTaskInstanceManager(idTaskInstance);
		IdeaProyectoDAO ideaProyectoDAO = (IdeaProyectoDAO) ContextUtil.getBean("ideaProyectoDao");
		IdeaProyectoBean ideaProyecto = ideaProyectoDAO.read(instanceManager.getIdIdeaProyecto());

		if (aceptaProyecto.requiereNotificacion()) {
			NotificacionDTO notificacionDTO = cargarNotificacionDTO(ideaProyecto, aceptaProyecto.getTipoDeNotificacion()); 
			wfNotificacionServicio.cargarNotificacion(notificacionDTO);
		}
		
		
		instanceManager.finalizarTarea();
	}
	
	/**
	 * Carga los datos al dto <code>NotificacionDTO</code> necesarios
	 * para crear una nueva <code>Notificacion</code>.<br>
	 * @param ideaProyecto
	 * @param tipoNotificacion
	 * @return NotificacionDTO
	 * @author ssanchez
	 */ 
	private NotificacionDTO cargarNotificacionDTO(IdeaProyectoBean ideaProyecto, TipoNotificacion tipoNotificacion) {	

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setTipoNotificacion(tipoNotificacion);
		notificacionDTO.setIdProyecto(ideaProyecto.getId());
		
		return notificacionDTO;
	}
	
	

	/* SERVICIOS */

	public void cargarIdeaProyecto(IdeaProyectoDTO datosIdeaProyecto) {
		IdeaProyectoDAO ideaProyectoDao = (IdeaProyectoDAO) ContextUtil.getBean("ideaProyectoDao");

		// alta de idea proyecto
		IdeaProyectoBean ideaProyecto = administrarIdeaProyectoServicio.cargarIdeaProyecto(datosIdeaProyecto);

		IdeaProyectoProcessManager processManager = new IdeaProyectoProcessManager();

		// FIXME: FF / arreglar cuando tengamos el instrumento dummy de idea proyecto 
		Long idProcessInstance = processManager.nuevoProcessInstance(ideaProyecto.getId(),0L);

		// intercambio de ids
		ideaProyecto.setIdWorkFlow(idProcessInstance);
		
		ideaProyectoDao.update(ideaProyecto);

	}

	public void modificarIdeaProyecto(String idIdeaProyecto, IdeaProyectoDTO datosIdeaProyecto) {
		administrarIdeaProyectoServicio.modificarIdeaProyecto(idIdeaProyecto, datosIdeaProyecto);
	}

	@SuppressWarnings("unchecked")
	public IdeaProyectoEvaluarPorJuntaDTO obtenerDatosEvaluacionPorJunta(Long idTaskInstance) {
		IdeaProyectoTaskInstanceManager instanceManager = new IdeaProyectoTaskInstanceManager(idTaskInstance);
		
		Collection<OpcionDeEvaluacionPorJunta> opcionesDisponibles = opcionesDeEvaluacionPorJunta(instanceManager);
		
		IdeaProyectoEvaluarPorJuntaDTO datosDeEvaluacionPorJunta = evaluarPorJuntaIdeaProyectoServicio.obtenerDatos(instanceManager.getIdIdeaProyecto());
		
		datosDeEvaluacionPorJunta.setOpcionesDeEvaluacion(opcionesDisponibles);
		
		return datosDeEvaluacionPorJunta;
	}

	private Collection<OpcionDeEvaluacionPorJunta> opcionesDeEvaluacionPorJunta(IdeaProyectoTaskInstanceManager instanceManager) {
		List<Transition> leavingTransitions = instanceManager.getCurrentTaskInstance().getTask().getProcessDefinition().getNode(DECISION_NODE_NAME).getLeavingTransitions();
		
		Collection<OpcionDeEvaluacionPorJunta> opcionesDisponibles = OpcionDeEvaluacionPorJunta.porTransiciones(leavingTransitions);
		return opcionesDisponibles;
	}
	
	/**
	 * Devuelve el estado de la idea proyecto
	 * @author ssanchez
	 */
	public EstadoIdeaProyecto obtenerEstadoIdeaProyecto(Long idIdeaProyecto) {
		IdeaProyectoDAO ideaProyectoDAO = (IdeaProyectoDAO) ContextUtil.getBean("ideaProyectoDao");
		IdeaProyectoBean ideaProyecto = ideaProyectoDAO.read(idIdeaProyecto);
		return ideaProyecto.getEstado();
	}
	
	/**
	 * Guarda los datos del <code>Proyecto</code>, crea una instancia del
	 * workflow de <code>Proyecto</code>, finaliza el workflow de 
	 * <code>IdeaProyecto</code>.<br>
	 * @author ssanchez
	 */
	public void cargarProyecto(ProyectoCargarDTO proyectoCargarDTO,Boolean vieneDePresentacion, Long idTaskInstance) {
		
		wfProyectoServicio.cargarProyecto((ProyectoEdicionDTO) proyectoCargarDTO, vieneDePresentacion, 
				proyectoCargarDTO.getIdInstrumento(), proyectoCargarDTO.getIdPresentacion(), proyectoCargarDTO.getIdProyectoPitec());

		IdeaProyectoTaskInstanceManager instanceManager = new IdeaProyectoTaskInstanceManager(idTaskInstance);

		instanceManager.finalizarTarea();
	}

	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}
}