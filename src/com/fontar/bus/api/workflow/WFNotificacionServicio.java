package com.fontar.bus.api.workflow;

import java.util.Date;

import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.dto.NotificacionDTO;

/**
 * Interfaz para los servicios de Workflow de <code>Notificacion</code>.<br> 
 * 
 * @author ssanchez
 *
 */
public interface WFNotificacionServicio {
	
	/**
	 * Crea una <code>Notificacion</code> nueva y genera una
	 * instancia de workflow para ésta.<br>
	 * La <code>Notificacion</code> se crea en base a los datos
	 * obtenidos desde el dto <code>notificacionDTO</code>.<br>
	 * @param notificacionDTO
	 */	
	public void cargarNotificacion(NotificacionDTO notificacionDTO);

	/**
	 * Obtiene los datos de una <code>Notificacion</code> en base al
	 * <i>idTaskInstance</i> de una tarea.<br>
	 * Devuelve los datos en el dto <code>NotificacionDTO</code>
	 * @param idTaskInstance
	 * @return NotificacionDTO
	 */
	public NotificacionDTO obtenerNotificacion(Long idTaskInstance);

	/**
	 * Finaliza la tarea de Workflow del envío de <code>Notificacion</code>
	 * y registra la fecha de envío.<br>
	 * @param idTaskInstance
	 * @param fechaEnvio
	 */
	public void enviarNotificacion(Long idTaskInstance, Date fechaEnvio);
	
	/**
	 * Obtiene el <code>EstadoNotificacion</code> de la <code>Notificacion</code>
	 * correspondiente al parámetro <i>idNotificacion</i>.<br>
	 * @param idNotificacion
	 * @return EstadoNotificacion
	 */
	public EstadoNotificacion obtenerEstadoNotificacion(Long idNotificacion);

	
	/**
	 * Obtiene si la <code>Notificacion</code> necesita recibir el acuse
	 * de recibo por parte del solicitante.<br>
	 * @param idNotificacion
	 * @return Boolean RequiereAcuse
	 */
	public Boolean requiereAcuseNotificacion(Long idNotificacion);
	
	/**
	 * Finaliza la tarea de Workflow de recepción de acuse de <code>Notificacion</code>
	 * y registra la fecha de acuse.<br>
	 * @param idTaskInstance
	 * @param fechaAcuse
	 */
	public void recibirAcuseNotificacion(Long idTaskInstance, Date fechaAcuse);
	
	/**
	 * Finaliza la tarea de Workflow <i>Cerrar Notificacion</i>
	 * y guarda el motivo de cierre <i>observacion</i>.<br>
	 * @param idTaskInstance
	 * @param observacion
	 */
	public void cerrarNotificacion(Long idTaskInstance, String observacion);
	
	/**
	 * Finaliza la tarea de Workflow <i>Anular Notificacion</i>
	 * y guarda el motivo de cierre <i>observacion</i>.<br>
	 * @param idTaskInstance
	 * @param observacion
	 */
	public void anularNotificacion(Long idTaskInstance, String observacion);

}
