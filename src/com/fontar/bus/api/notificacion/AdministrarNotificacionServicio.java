package com.fontar.bus.api.notificacion;

import java.util.Date;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.dto.NotificacionDTO;

/**
 * Interfaz de servicios generales de <code>Notificacion</code>.<br>
 */
public interface AdministrarNotificacionServicio {

	/**
	 * Obtiene una lista de <code>ProyectoRaizBean</code> de todos los
	 * proyectos que tienen alguna <code>Notificacion</code>.<br>
	 */
	public List<ProyectoRaizBean> obtenerProyectosConNotificaciones();
	
	/**
	 * Persiste una <code>Notificacion</code>.<br>
	 * Los datos son obtenidos desde el dto <code>NotificacionDTO</code>
	 * recibido como parámetro.<br>
	 * @param notificacionDTO
	 * @return id
	 */
	public Long cargarNotificacion(NotificacionDTO notificacionDTO);
	
	/**
	 * Obtiene los datos de una <code>Notificacion</code> mediante
	 * el <i>idNotificacion</i>.<br>
	 * @param idNotificacion
	 * @return NotificacionDTO
	 */
	public NotificacionDTO obtenerNotificacion(Long idNotificacion);	

	/**
	 * Registra la fecha de envío de la <code>Notificacion</code> al solicitante.<br>
	 * La <code>Notificacion</code> modificada es determinada por el parámetro <i>idNotificacion</i>
	 * y la fecha a registrar se obtiene desde el parámetro <i>fechaEnvio</i>.<br>
	 * @param idNotificacion
	 * @param fechaEnvio
	 */
	public void enviarNotificacion(Long idNotificacion, Date fechaEnvio);
	
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
	 * Registra la fecha en la que se recibió el acuse de la <code>Notificacion</code>.<br> 
	 * La <code>Notificacion</code> modificada es determinada por el parámetro <i>idNotificacion</i>
	 * y la fecha a registrar se obtiene desde el parámetro <i>fechaAcuse</i>.<br>
	 * @param idNotificacion
	 * @param fechaAcuse
	 */
	public void recibirAcuseNotificacion(Long idNotificacion, Date fechaAcuse);
	
	/**
	 * Registra el cierre de una notificación guardando el motivo
	 * de cierre dado por el parámetro <i>observacion</i>.<br>
	 * @param idNotificacion
	 * @param observacion
	 */
	public void cerrarNotificacion(Long idNotificacion, String observacion);

	/**
	 * Registra la anulación de una notificación guardando el motivo
	 * de anulación dado por el parámetro <i>observacion</i>.<br>
	 * @param idNotificacion
	 * @param observacion
	 */
	public void anularNotificacion(Long idNotificacion, String observacion);
	
}