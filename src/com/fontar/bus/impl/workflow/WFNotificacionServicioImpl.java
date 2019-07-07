package com.fontar.bus.impl.workflow;

import java.util.Date;

import com.fontar.bus.api.notificacion.AdministrarNotificacionServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.data.api.dao.NotificacionDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.domain.bean.NotificacionBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.jbpm.manager.NotificacionProcessManager;
import com.fontar.jbpm.manager.NotificacionTaskInstanceManager;
import com.pragma.util.ContextUtil;

/**
 * Servicios generales de workflow para <code>Notificacion</code>.<br> 
 */
public class WFNotificacionServicioImpl implements WFNotificacionServicio {

	private AdministrarNotificacionServicio administrarNotificacionServicio;

	//Setters
	public void setAdministrarNotificacionServicio(AdministrarNotificacionServicio administrarNotificacionServicio) {
		this.administrarNotificacionServicio = administrarNotificacionServicio;
	}

	//Métodos
	/**
	 * Crea una <code>Notificacion</code> nueva y genera una
	 * instancia de workflow para ésta.<br>
	 * La <code>Notificacion</code> se crea en base a los datos
	 * obtenidos desde el dto <code>notificacionDTO</code>.<br>
	 * @param notificacionDTO
	 */	
	public void cargarNotificacion(NotificacionDTO notificacionDTO) {
		
		Long idNotificacion = administrarNotificacionServicio.cargarNotificacion(notificacionDTO);
		
		crearInstanciaWF(idNotificacion);
	}

	/**
	 * Crea una instancia de Workflow de <code>Notificacion</code>.<br>
	 * El <code>Process Instance</code> es creado con la variable <i>ID_NOTIFICACION</i>
	 * cuyo valor es obtenido desde el parámetro <i>idNotificacion</i>.
	 */
	private void crearInstanciaWF(Long idNotificacion) {
		
		NotificacionProcessManager processManager = new NotificacionProcessManager();
		


		NotificacionDAO notificacionDAO = (NotificacionDAO) ContextUtil.getBean("notificacionDao");
		NotificacionBean notificacionBean = notificacionDAO.read(idNotificacion);
		
		//FIXME no deberia hacer falta, ver porque getProyecto de notificacion retorna null
		ProyectoRaizDAO proyectoRaizDAO = (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
		ProyectoRaizBean proyecto = proyectoRaizDAO.read( notificacionBean.getIdProyecto());
		
		Long idProcessInstance = processManager.nuevoProcessInstance(idNotificacion, proyecto.getIdInstrumento());
		notificacionBean.setIdWorkFlow(idProcessInstance);
		
		notificacionDAO.update(notificacionBean);
	}	

	/**
	 * Obtiene los datos de una <code>Notificacion</code> en base al
	 * <i>idTaskInstance</i> de una tarea.<br>
	 * Devuelve los datos en el dto <code>NotificacionDTO</code>
	 * @param idTaskInstance
	 * @return NotificacionDTO
	 */
	public NotificacionDTO obtenerNotificacion(Long idTaskInstance) {
		
		NotificacionTaskInstanceManager taskManager = new NotificacionTaskInstanceManager(idTaskInstance);
		
		NotificacionDTO notificacionDTO = administrarNotificacionServicio.obtenerNotificacion(taskManager.getIdNotificacion());
		
		return notificacionDTO;
	}

	/**
	 * Finaliza la tarea de Workflow del envío de <code>Notificacion</code>
	 * y registra la fecha de envío.<br>
	 * @param idTaskInstance
	 * @param fechaEnvio
	 */
	public void enviarNotificacion(Long idTaskInstance, Date fechaEnvio) {
		
		NotificacionTaskInstanceManager taskManager = new NotificacionTaskInstanceManager(idTaskInstance);
		
		administrarNotificacionServicio.enviarNotificacion(taskManager.getIdNotificacion(),fechaEnvio);
		
		taskManager.finalizarTarea();
	}
	
	/**
	 * Obtiene el <code>EstadoNotificacion</code> de la <code>Notificacion</code>
	 * correspondiente al parámetro <i>idNotificacion</i>.<br>
	 * @param idNotificacion
	 * @return EstadoNotificacion
	 */
	public EstadoNotificacion obtenerEstadoNotificacion(Long idNotificacion) {
		
		return administrarNotificacionServicio.obtenerEstadoNotificacion(idNotificacion);
	}
	
	/**
	 * Obtiene si la <code>Notificacion</code> necesita recibir el acuse
	 * de recibo por parte del solicitante.<br>
	 * @param idNotificacion
	 * @return Boolean RequiereAcuse
	 */
	public Boolean requiereAcuseNotificacion(Long idNotificacion) {
		
		return administrarNotificacionServicio.requiereAcuseNotificacion(idNotificacion);
	}
	
	/**
	 * Finaliza la tarea de Workflow de recepción de acuse de <code>Notificacion</code>
	 * y registra la fecha de acuse.<br>
	 * @param idTaskInstance
	 * @param fechaAcuse
	 */
	public void recibirAcuseNotificacion(Long idTaskInstance, Date fechaAcuse) {
		
		NotificacionTaskInstanceManager taskManager = new NotificacionTaskInstanceManager(idTaskInstance);
		
		administrarNotificacionServicio.recibirAcuseNotificacion(taskManager.getIdNotificacion(),fechaAcuse);
		
		taskManager.finalizarTarea();
	}
	
	/**
	 * Finaliza la tarea de Workflow <i>Cerrar Notificacion</i>
	 * y guarda el motivo de cierre <i>observacion</i>.<br>
	 * @param idTaskInstance
	 * @param observacion
	 */
	public void cerrarNotificacion(Long idTaskInstance, String observacion) {
		
		NotificacionTaskInstanceManager taskManager = new NotificacionTaskInstanceManager(idTaskInstance);
		
		administrarNotificacionServicio.cerrarNotificacion(taskManager.getIdNotificacion(),observacion);
		
		taskManager.finalizarTarea();
	}	

	/**
	 * Finaliza la tarea de Workflow <i>Anular Notificacion</i>
	 * y guarda el motivo de cierre <i>observacion</i>.<br>
	 * @param idTaskInstance
	 * @param observacion
	 */
	public void anularNotificacion(Long idTaskInstance, String observacion) {
		
		NotificacionTaskInstanceManager taskManager = new NotificacionTaskInstanceManager(idTaskInstance);
		
		administrarNotificacionServicio.anularNotificacion(taskManager.getIdNotificacion(),observacion);
		
		taskManager.finalizarTarea();
	}	
}
