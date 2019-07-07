package com.fontar.web.action.seguridad.logon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.data.impl.domain.ldap.Usuario;
import com.pragma.util.ContextUtil;

/**
 * 
 * @author ssanchez Manejo de autenticación del usuario
 */
public class LogonAction extends MappingDispatchAction {

	/**
	 * Elimina la sesión del usuario y sale de la aplicación
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return mapping.findForward("success");
	}
	
	public ActionForward loginSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//FIXME cambiar por authentication service
		UsuarioService usuarioService = (UsuarioService) ContextUtil.getBean("usuarioService");
		Usuario usuario = usuarioService.getCurrentUser();
		String forward = usuario.isInitialized()? "success" : "privateKeyRequired";
		return mapping.findForward(forward);
	}
}
