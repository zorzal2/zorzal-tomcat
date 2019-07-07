package com.fontar.data.impl.assembler;

import com.fontar.data.impl.domain.bean.NotificacionBean;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizDTO;
import com.pragma.util.DateTimeUtil;

/**
 * Clase para copiar los datos desde una <code>NotificacionBean</code>
 * a los DTO's que pueda tener el objeto <code>Notificacion</code>.<br> 
 * @author ssanchez
 */
public class NotificacionDTOAssembler {

	/**
	 * Llena una <code>NotificacionDTO</code> con los datos
	 * del bean <code>NotificacionBean</code> que es recibido como
	 * parámetro.<br>
	 * @param bean
	 * @return dto
	 */
	public static NotificacionDTO buildDto(NotificacionBean bean) {
		NotificacionDTO dto = new NotificacionDTO();

		dto.setDescripcion(bean.getDescripcion());
		dto.setEstado(bean.getEstado());
		dto.setFechaAcuse(DateTimeUtil.formatDate(bean.getFechaAcuse()));
		dto.setFechaCreacion(DateTimeUtil.formatDate(bean.getFechaCreacion()));
		dto.setFechaEnvio(DateTimeUtil.formatDate(bean.getFechaEnvio()));
		dto.setId(bean.getId());
		dto.setIdProyecto(bean.getIdProyecto());
		dto.setRequiereAcuse(bean.getRequiereAcuse());
		dto.setTipoNotificacion(bean.getTipoNotificacion());
		dto.setObservacion(bean.getObservacion());
		ProyectoRaizAssembler assembler = new ProyectoRaizAssembler();
		dto.setProyectoRaiz((ProyectoRaizDTO)assembler.buildDTO(bean.getProyecto()));

		return dto;
	}
}
