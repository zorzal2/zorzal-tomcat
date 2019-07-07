package com.fontar.web.action.cripto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.util.Util;
import com.pragma.util.ContextUtil;

/**
 * 
 * @author gboaglio
 * 
 */
public class LogonCriptoAction extends MappingDispatchAction {

	/**
	 * Muestra el formulario para cargar un proyecto de una presentación
	 * determinada
	 */
	public ActionForward logon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// TODO: FF / hago un reset manual, habria que ver que onda
		BeanUtils.setProperty(form, "usuario", "");
		BeanUtils.setProperty(form, "criptoPassword", "");

		return mapping.findForward("success");
	}

	/**
	 * Limpia la clave de encriptación
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		service.shutDownEncryptionService( );
		return mapping.findForward("success");
	}

	/**
	 * Muestra la pantalla para modificar la contraseña de criptografía del
	 * usuario
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// TODO: FF / hago un reset manual, habria que ver que onda
		BeanUtils.setProperty(form, "oldPassword", "");
		BeanUtils.setProperty(form, "newPassword", "");
		BeanUtils.setProperty(form, "confirmNewPassword", "");

		return mapping.findForward("success");
	}

	/**
	 * Modifica la contraseña del usuario
	 */
	public ActionForward validateChangedPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Cola de mensajes de error
		ActionMessages messages = new ActionMessages();

		String oldPassword = BeanUtils.getProperty(form, "oldPassword");
		String newPassword = BeanUtils.getProperty(form, "newPassword");
		String confirmNewPassword = BeanUtils.getProperty(form, "confirmNewPassword");
		
		if (!newPassword.equals(confirmNewPassword)) {
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Las contraseñas no coinciden", false));
		}else{
			try{
				UsuarioService usuarioService = (UsuarioService) ContextUtil.getBean("usuarioService");
				usuarioService.actualizarClavePrivada( oldPassword, newPassword);
			}catch (Exception ex) {
				messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			}
		}
		if (!messages.isEmpty()){
			saveErrors(request, messages);
			return mapping.findForward("invalid");
		} else {
			return mapping.findForward("success");	
		}
		
	}

	/**
	 * Valiada el password ingresado en el formulario de logon
	 */
	public ActionForward validateLogon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String password = BeanUtils.getProperty(form, "criptoPassword");

		try {
			if (Util.isBlank( password))
				throw new Exception("Se requiere el campo Contraseña de Criptografía");

			FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
			service.startUpEncryptionService( password );
			
		}catch (Exception ex) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(request, messages);
			return mapping.findForward("invalid");
		}
		
		return mapping.findForward("success");
	}

	
}
