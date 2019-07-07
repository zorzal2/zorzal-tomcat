package com.fontar.bus.impl.workflow;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.proyecto.AdmisibilidadProyectoServicio;
import com.fontar.bus.api.proyecto.EvaluacionProyectoServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.assembler.ProyectoGeneralAssembler;
import com.fontar.data.api.dao.InstrumentoDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompletarDatosInicialesDTO;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.ExpedienteMovimientoDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.PresentacionCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoAgregarDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.jbpm.manager.ProyectoHistoricoProcessManager;
import com.fontar.jbpm.manager.ProyectoProcessManager;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;

public class WFProyectoServicioImpl implements WFProyectoServicio {

	protected EvaluacionProyectoServicio evaluacionProyectoServicio;

	protected AdministrarProyectoServicio administrarProyectoServicio;

	protected AdministrarProyectoRaizServicio administrarProyectoRaizServicio;

	protected AdmisibilidadProyectoServicio admisibilidadProyectoServicio;
	
	protected WFNotificacionServicio wfNotificacionServicio;
	
	protected WFSeguimientoServicio wfSeguimientoServicio;

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}

	public AdministrarProyectoServicio getAdministrarProyectoServicio() {
		return administrarProyectoServicio;
	}

	public AdmisibilidadProyectoServicio getAdmisibilidadProyectoServicio() {
		return admisibilidadProyectoServicio;
	}

	public EvaluacionProyectoServicio getEvaluacionProyectoServicio() {
		return evaluacionProyectoServicio;
	}

	public DTO getProyectoDTO(Long idTaskInstance, ProyectoGeneralAssembler assembler) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		return administrarProyectoServicio.getProyectoDTO(idProyecto, assembler);
	}

	public void setEvaluacionProyectoServicio(EvaluacionProyectoServicio evaluacionProyectoServicio) {
		this.evaluacionProyectoServicio = evaluacionProyectoServicio;
	}

	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}

	public void setAdmisibilidadProyectoServicio(AdmisibilidadProyectoServicio admisibilidadProyectoServicio) {
		this.admisibilidadProyectoServicio = admisibilidadProyectoServicio;
	}

	/**
	 * Pasa a la próxima etapa evitando la etapa de Evaluación para el proyecto
	 */
	public void pasarAProximaEtapaSinEvaluacion(String fundamentacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto(); 
		evaluacionProyectoServicio.pasarAProximaEtapaSinEvaluacion( idProyecto, fundamentacion);
		taskHelper.finalizarTarea();
	}

	/**
	 * 
	 */
	public void finalizarControlEvaluacion(ProyectoPresupuestoDTO presupuesto, EvaluacionGeneralDTO evaluacion, Long idProyecto, ResultadoEvaluacion resultado,
			Long idTaskInstance) {
		if(presupuesto!=null) actualizarPresupuestoActual(idProyecto, presupuesto);
		evaluacionProyectoServicio.finalizarControlEvaluacion(evaluacion, idProyecto, resultado);
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		taskHelper.finalizarTarea();
	}

	/**
	 * 
	 */
	public void cargarAlicuota(BigDecimal porcentaje, String observaciones, Long idTaskInstance) {
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		administrarProyectoServicio.cargarAlicuota(idProyecto, porcentaje, observaciones);
		taskHelper.finalizarTarea();
	}
	/**
	 * Obtiene el presupuesto mediante el idProyecto
	 */
	public ProyectoPresupuestoDTO obtenerPresupuesto(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		return administrarProyectoServicio.obtenerPresupuesto(taskHelper.getIdProyecto(), null);
	}

	/**
	 * Guarda el presupuesto del proyecto
	 */
	public ProyectoPresupuestoDTO cargarPresupuesto(ProyectoPresupuestoDTO presupuesto, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		return administrarProyectoServicio.cargarPresupuesto(presupuesto, taskHelper.getIdProyecto(), null);

	}

	/**
	 * Finaliza la posibilidad de reconsideracion de un proyecto =
	 * finalizarPosibilidadReconsideracion
	 */
	public void finalizarPosibilidadReconsideracion(BitacoraDTO bitacora, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		administrarProyectoServicio.finalizarPosibilidadReconsideracion(bitacora, taskHelper.getIdProyecto());

		taskHelper.finalizarTarea();
	}

	/**
	 * 
	 */
	public ProyectoDTO obtenerProyecto(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		return evaluacionProyectoServicio.obtenerProyecto(idProyecto);
	}

	/**
	 * Devuelve el estado del proyecto, se utliza para Decisiones del WorkFlow
	 */
	public EstadoProyecto obtenerEstadoProyecto(Long idProyecto) {
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyecto = proyectoDao.read(idProyecto);
		return proyecto.getEstado();
	}
	
	/**
	 * Devuelve la recomendación actual del proyecto, se utliza para Decisiones del WorkFlow
	 */
	public Recomendacion obtenerRecomendacionProyecto(Long idProyecto) {
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyecto = proyectoDao.read(idProyecto);
		return proyecto.getRecomendacion();
	}

	public BigDecimal obtenerMontoSolicitadoProyecto(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskManager = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskManager.getIdProyecto();
		
		return administrarProyectoServicio.obtenerMontoSolicitadoProyecto(idProyecto);		
	}
		
	
	public void cargarAdmisionAlProyecto(Date fecha, String fundamentacion, String disposicion,
			String resultado, String observacion, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		admisibilidadProyectoServicio.cargarAdmisionAlProyecto(taskHelper.getIdProyecto(), fecha, fundamentacion, disposicion, resultado, observacion);
		
		ProyectoDAO proyectoDAO  = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDAO.read(taskHelper.getIdProyecto());
		
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		if (resultado.equals(Constant.ProyAdmisionResultado.APROBADO)) {
			notificacionDTO = cargarNotificacionDTO(proyectoBean, TipoNotificacion.ADMISION_APROBADA);
			notificacionDTO.setDescripcion("Notificación del resultado de la admisión del proyecto. Proyecto admitido.");
		} else {
			notificacionDTO = cargarNotificacionDTO(proyectoBean, TipoNotificacion.ADMISION_RECHAZADA);
			notificacionDTO.setDescripcion("Notificación del resultado de la admisión del proyecto. Proyecto no admitido, con posibilidad de solicitar readmisión.");
		}
		
		wfNotificacionServicio.cargarNotificacion(notificacionDTO);
		
		taskHelper.finalizarTarea();
	}
	
	/**
	 * Carga los datos al dto <code>NotificacionDTO</code> necesarios
	 * para crear una nueva <code>Notificacion</code>.<br>
	 * @param proyecto
	 * @param tipoNotificacion
	 * @return NotificacionDTO
	 * @author ssanchez
	 */ 	
	private NotificacionDTO cargarNotificacionDTO(ProyectoBean proyecto, TipoNotificacion tipoNotificacion) {

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setIdProyecto(proyecto.getId());
		notificacionDTO.setTipoNotificacion(tipoNotificacion);
		
		return notificacionDTO;
	}
	
	public void solicitarReadmisionAlProyecto(Date fecha, String observacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto= taskHelper.getIdProyecto();
		admisibilidadProyectoServicio.solicitarReadmisionAlProyecto(idProyecto, fecha, observacion);
		taskHelper.finalizarTarea();
	}
	
	public Boolean enPaquete(Long idProyecto) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  Llama al servicio de administración para cargar el proyecto 
	 *  y crea la instancia de workflow que se le va a asociar.
	 */
	public void cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento, Long idPresentacion, Long idProyectoPitec) {

		// cargo el proyecto
		Long idProyecto = administrarProyectoServicio.cargarProyecto(datos,vieneDePresentacion,idInstrumento,idPresentacion,idProyectoPitec);
		
		crearInstanciaWF(idProyecto, vieneDePresentacion);				
	}
	
	/**
	 * Crea una nueva instancia de workflow de <code>Proyecto</code>
	 * o <code>ProyectoHistorico</code> dependiendo de si el instrumento
	 * es o no <i>paraProyectoHistorico</i>.  
	 * @author ttoth
	 * @author ssanchez
	 */
	public void crearInstanciaWF(Long idProyecto, boolean vieneDePresentacion) {
		
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyecto = proyectoDao.read(idProyecto);

		Long idProcessInstance;

		InstrumentoDAO instrumentoDAO = (InstrumentoDAO) ContextUtil.getBean("instrumentoDao");
		InstrumentoBean instrumento = instrumentoDAO.read(proyecto.getIdInstrumento());
		if(instrumento.getParaProyectoHistorico()) {
			
			ProyectoHistoricoProcessManager proyectoHistoricoManager = new ProyectoHistoricoProcessManager();
			idProcessInstance = proyectoHistoricoManager.nuevoProcessInstance(idProyecto,!vieneDePresentacion,proyecto.getIdInstrumento());
		} else {
			
			ProyectoProcessManager proyectoManager = new ProyectoProcessManager();
			idProcessInstance = proyectoManager.nuevoProcessInstance(idProyecto, !vieneDePresentacion, proyecto.getIdInstrumento());
		}
		
		proyecto.setIdWorkFlow(idProcessInstance);
		proyectoDao.update(proyecto);
	}
	
	public ProyectoAgregarDTO obtenerDatosAgregarProyecto(Long idPresentacion) {
		return administrarProyectoServicio.obtenerDatosAgregarProyecto(idPresentacion);
	}

	public PresentacionCabeceraDTO obtenerDatosCabeceraProyecto(Long idPresentacion) {
		return administrarProyectoServicio.obtenerDatosCabeceraProyecto(idPresentacion);
	}

	public ProyectoVisualizacionDTO obtenerDatosVisualizacionProyecto(Long idProyecto) {
		return administrarProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
	}

	public void guardarDatosEdicionProyecto(ProyectoEdicionDTO datos) {
		administrarProyectoServicio.guardarDatosEdicionProyecto(datos);
	}

	public void cargarReadmisionAlProyecto(Date fecha, String fundamentacion, String resultado, 
			String resolucion, String observacion, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		admisibilidadProyectoServicio.cargarReadmisionAlProyecto(taskHelper.getIdProyecto(), fecha, fundamentacion, resultado, resolucion, observacion);
		
		ProyectoDAO proyectoDAO  = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDAO.read(taskHelper.getIdProyecto());
		
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		if (resultado.equals(Constant.ProyAdmisionResultado.APROBADO)) {
			notificacionDTO = cargarNotificacionDTO(proyectoBean, TipoNotificacion.READMISION_APROBADA);
			notificacionDTO.setDescripcion("Notificación del resultado de la readmisión del proyecto. Proyecto admitido en segunda instancia.");
		} else {
			notificacionDTO = cargarNotificacionDTO(proyectoBean, TipoNotificacion.READMISION_RECHAZADA);
			notificacionDTO.setDescripcion("Notificación del resultado de la readmisión del proyecto. Proyecto no admitido.");
		}
		
		wfNotificacionServicio.cargarNotificacion(notificacionDTO);
		
		taskHelper.finalizarTarea();
	}

	/**
	 * Firmar Contrato de un Proyecto
	 */
	public void guardarFirmaContrato(Long idResponsableLegal, String txtResponsableLegal, Date fechaFirma, String observaciones, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto= taskHelper.getIdProyecto();
		administrarProyectoServicio.guardarFirmaContrato(idResponsableLegal, txtResponsableLegal, fechaFirma, observaciones, idProyecto);

		taskHelper.finalizarTarea();
	}

	public void analizarReconsideracionAlProyecto(Date fecha, String fundamentacion, String resultado, String resolucion, String observacion, Long idTaskInstance, String dictamen) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto= taskHelper.getIdProyecto();
		admisibilidadProyectoServicio.analizarReconsideracionAlProyecto(idProyecto, fecha, fundamentacion, resultado, resolucion, observacion, dictamen);
		taskHelper.finalizarTarea();
	}

	public void reconsiderarProyecto(Date fecha, String paso, String observacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto= taskHelper.getIdProyecto();
		admisibilidadProyectoServicio.reconsiderarProyecto(idProyecto, fecha, paso, observacion);
		taskHelper.finalizarTarea();
	}

	public void guardarMovimiento(Date fecha, String ubicacion, Date fechaDevolucion, String observaciones, Long idPersona, Long idProyecto) {
		administrarProyectoServicio.guardarMovimiento(fecha,ubicacion,fechaDevolucion,observaciones,idPersona,idProyecto);
	}

	public void guardarExpediente(String[] cuerpo, String[] folioDesde, String[] folioHasta, Long idProyecto) {
		administrarProyectoServicio.guardarExpediente(cuerpo,folioDesde,folioHasta,idProyecto);
	}

	public ExpedienteMovimientoDTO obtenerDatosExpedienteMov(Long idProyecto) {
		return administrarProyectoServicio.obtenerExpedienteMov(idProyecto);
	}

	public void actualizarPresupuestoActual(Long proyectoId, ProyectoPresupuestoDTO presupuesto) {
		getAdministrarProyectoRaizServicio().actualizarPresupuestoActual(proyectoId, presupuesto);
	}

	public AdministrarProyectoRaizServicio getAdministrarProyectoRaizServicio() {
		return administrarProyectoRaizServicio;
	}

	public void setAdministrarProyectoRaizServicio(AdministrarProyectoRaizServicio administrarProyectoRaizServicio) {
		this.administrarProyectoRaizServicio = administrarProyectoRaizServicio;
	}

	/**
	 * @author ssanchez
	 */
	public void finalizarProyecto(Long idTaskInstance, String observacion) {
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		administrarProyectoServicio.finalizarProyecto(taskHelper.getIdProyecto(), observacion);

		taskHelper.finalizarTarea();
	}
	
	/**
	 * Obtiene del <code>ProyectoBean</code> en
	 * base al <i>idTaskInstance</i>.<br>
	 * @param idTaskInstance
	 * @return el <code>ProyectoBean</code>
	 */
	public ProyectoBean obtenerProyectoBean(Long idTaskInstance) {
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		
		return administrarProyectoServicio.obtenerProyectoBean(taskHelper.getIdProyecto());
	}
	
	/**
	 * Ejecuta la acción <i>Completar Datos Iniciales</i>
	 * del workflow de <code>ProyectoHistorico</code>.<br>
	 * Persiste los datos ingresados y patea el token a la siguiente
	 * tarea de workflow.<br>
	 * @param idTaskInstance
	 * @param datosInicialesDTO
	 */
	public void completarDatosIniciales(Long idTaskInstance, CompletarDatosInicialesDTO datosInicialesDTO) {
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		
		//creo el seguimiento
		Long idSeguimiento = wfSeguimientoServicio.cargarSeguimiento(idTaskInstance,true,true,datosInicialesDTO.getObservacion(),datosInicialesDTO.getObservacion());

		//persisto los datos iniciales
		administrarProyectoServicio.completarDatosIniciales(taskHelper.getIdProyecto(),idSeguimiento,datosInicialesDTO);
		
		wfSeguimientoServicio.finalizarWorkflowSeguimiento(idSeguimiento);
	}

	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuestoParaFinalizarControl(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		
		return administrarProyectoRaizServicio.obtenerResumenDePresupuestoParaFinalizarControl(idProyecto);
	}
}