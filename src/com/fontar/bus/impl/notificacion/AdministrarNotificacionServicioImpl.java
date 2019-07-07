package com.fontar.bus.impl.notificacion;

import static com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion.ANULADA;

import java.util.Date;
import java.util.List;

import com.fontar.bus.api.notificacion.AdministrarNotificacionServicio;
import com.fontar.data.api.dao.NotificacionDAO;
import com.fontar.data.impl.assembler.NotificacionDTOAssembler;
import com.fontar.data.impl.domain.bean.NotificacionBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.pragma.util.DateTimeUtil;

/**
 * Servicios generales para <code>Notificacion</code>.<br> 
 * 
 * @author ssanchez
 */
public class AdministrarNotificacionServicioImpl implements AdministrarNotificacionServicio {
	
	private NotificacionDAO notificacionDAO;
	
	//Setters
	public void setNotificacionDAO(NotificacionDAO notificacionDAO) {
		this.notificacionDAO = notificacionDAO;
	}

	//M�todos
	/**
	 * Obtiene una lista de <code>ProyectoRaizBean</code> de todos los
	 * proyectos que tienen alguna <code>Notificacion</code>.<br>
	 */
	public List<ProyectoRaizBean> obtenerProyectosConNotificaciones() {
		return notificacionDAO.findByNotificacionesConProyectos();
	}
	
	/**
	 * Persiste una <code>Notificacion</code>.<br>
	 * Los datos son obtenidos desde el dto <code>NotificacionDTO</code>
	 * recibido como par�metro.<br>
	 * @param notificacionDTO
	 * @return id
	 */
	public Long cargarNotificacion(NotificacionDTO notificacionDTO) {
		
		NotificacionBean notificacionBean = new NotificacionBean();
		notificacionBean.setTipoNotificacion(notificacionDTO.getTipoNotificacion());
		notificacionBean.setDescripcion(notificacionDTO.getDescripcion());
		
		notificacionBean.setFechaCreacion(DateTimeUtil.getDate());
		notificacionBean.setRequiereAcuse(notificacionDTO.getTipoNotificacion().getRequiereAcuse());
		notificacionBean.setEstado(EstadoNotificacion.PENDIENTE_ENVIO);
		notificacionBean.setIdProyecto(notificacionDTO.getIdProyecto());
		
		notificacionDAO.save(notificacionBean);
		
		return notificacionBean.getId();
	}
	
	/**
	 * Obtiene los datos de una <code>Notificacion</code> mediante
	 * el <i>idNotificacion</i>.<br>
	 * @param idNotificacion
	 * @return NotificacionDTO
	 */
	public NotificacionDTO obtenerNotificacion(Long idNotificacion) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		NotificacionDTO notificacionDTO = NotificacionDTOAssembler.buildDto(notificacionBean);
		
		return notificacionDTO;
	}

	/**
	 * Registra la fecha de env�o de la <code>Notificacion</code> al solicitante.<br>
	 * La <code>Notificacion</code> modificada es determinada por el par�metro <i>idNotificacion</i>
	 * y la fecha a registrar se obtiene desde el par�metro <i>fechaEnvio</i>.<br>
	 * @param idNotificacion
	 * @param fechaEnvio
	 */
	public void enviarNotificacion(Long idNotificacion, Date fechaEnvio) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		notificacionBean.setFechaEnvio(fechaEnvio);
		
		if (notificacionBean.getRequiereAcuse()) {
			notificacionBean.setEstado(EstadoNotificacion.PENDIENTE_ACUSE);
		} else {
			notificacionBean.setEstado(EstadoNotificacion.FINALIZADA);
		}
		
		notificacionDAO.update(notificacionBean);
	}
	
	/**
	 * Obtiene el <code>EstadoNotificacion</code> de la <code>Notificacion</code>
	 * correspondiente al par�metro <i>idNotificacion</i>.<br>
	 * @param idNotificacion
	 * @return EstadoNotificacion
	 */
	public EstadoNotificacion obtenerEstadoNotificacion(Long idNotificacion) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		
		return notificacionBean.getEstado(); 
	}
	
	/**
	 * Obtiene si la <code>Notificacion</code> necesita recibir el acuse
	 * de recibo por parte del solicitante.<br>
	 * @param idNotificacion
	 * @return Boolean RequiereAcuse
	 */
	public Boolean requiereAcuseNotificacion(Long idNotificacion) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		
		return notificacionBean.getRequiereAcuse();
	}
	
	/**
	 * Registra la fecha en la que se recibi� el acuse de la <code>Notificacion</code>.<br> 
	 * La <code>Notificacion</code> modificada es determinada por el par�metro <i>idNotificacion</i>
	 * y la fecha a registrar se obtiene desde el par�metro <i>fechaAcuse</i>.<br>
	 * @param idNotificacion
	 * @param fechaAcuse
	 */
	public void recibirAcuseNotificacion(Long idNotificacion, Date fechaAcuse) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		notificacionBean.setFechaAcuse(fechaAcuse);
		
		notificacionBean.setEstado(EstadoNotificacion.FINALIZADA);
		
		notificacionDAO.update(notificacionBean);
	}

	/**
	 * Registra el cierre de una notificaci�n guardando el motivo
	 * de cierre dado por el par�metro <i>observacion</i>.<br>
	 * @param idNotificacion
	 * @param observacion
	 */
	public void cerrarNotificacion(Long idNotificacion, String observacion) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		notificacionBean.setObservacion(observacion);
		
		notificacionBean.setEstado(EstadoNotificacion.CERRADA);
		
		notificacionDAO.update(notificacionBean);
	}	

	/**
	 * Registra la anulaci�n de una notificaci�n guardando el motivo
	 * de anulaci�n dado por el par�metro <i>observacion</i>.<br>
	 * @param idNotificacion
	 * @param observacion
	 */
	public void anularNotificacion(Long idNotificacion, String observacion) {
		
		NotificacionBean notificacionBean = notificacionDAO.findByID(idNotificacion).get(0);
		notificacionBean.setObservacion(observacion);
		
		notificacionBean.setEstado(ANULADA);
		
		notificacionDAO.update(notificacionBean);
	}	
	
	
	
}