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
 * Action para enviar una <code>Notificacion</code> al solicitante.<br>
 * Una vez creada una <code>Notificacion</code> debe ser enviada a la
 * entidad/persona que inicio el trámite.<br>
 * @author ssanchez
 */
public class EnviarNotificacionAction extends NotificacionBaseTaskAction {
	
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
	 * Guarda la fecha <i>fechaEnvio</i> en que se envió la <code>Notificacion</code>.<br>
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		NotificacionDTO notificacionDTO = populateDTO(form);
		
		wfNotificacionServicio.enviarNotificacion(idTaskInstance,DateTimeUtil.getDate(notificacionDTO.getFechaEnvio()));
	}

	/**
	 * Carga los datos del formulario al dto <code>NotificacionDTO</code>.<br>
	 */
	private NotificacionDTO populateDTO(ActionForm form) throws Exception {

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setFechaEnvio(FormUtil.getStringValue(form, "fechaEnvio"));

		return notificacionDTO;
	}
	
}