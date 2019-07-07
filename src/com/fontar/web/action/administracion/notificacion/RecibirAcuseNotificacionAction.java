package com.fontar.web.action.administracion.notificacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;

/**
 * Action para registrar la recepeción del acuse de una <code>Notificacion</code> 
 * desde el solicitante.<br>
 * @author ssanchez
 */
public class RecibirAcuseNotificacionAction extends NotificacionBaseTaskAction {
	
	/**
	 * Carga el dto de <code>NotificacionDTO</code> y el dto para la cabecera
	 * de <code>Proyecto</code> o <code>IdeaProyecto</code>.<br>
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.executeCargarTarea(mapping,form,request,response,messages,idTaskInstance);

	}

	/**
	 * Se registra la recepción del acuse de recibo de la notificación.<br> 
	 * Guarda la fecha <i>fechaAcuse</i> en que se recibió la <code>Notificacion</code>.
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		NotificacionDTO notificacionDTO = populateDTO(form);
		
		wfNotificacionServicio.recibirAcuseNotificacion(idTaskInstance,DateTimeUtil.getDate(notificacionDTO.getFechaAcuse()));
	}

	/**
	 * Carga los datos del formulario al dto <code>NotificacionDTO</code>.<br>
	 */
	private NotificacionDTO populateDTO(ActionForm form) throws Exception {

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setFechaAcuse(FormUtil.getStringValue(form, "fechaAcuse"));

		return notificacionDTO;
	}
	
}