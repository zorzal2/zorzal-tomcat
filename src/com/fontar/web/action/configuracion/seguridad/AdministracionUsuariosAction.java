package com.fontar.web.action.configuracion.seguridad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.data.impl.assembler.BasicUsuarioDTOAssembler;
import com.fontar.data.impl.domain.dto.PersonaDTO;
import com.fontar.seguridad.NoValidoComoPasswordException;
import com.fontar.seguridad.cripto.PasswordInvalidaException;
import com.pragma.bus.BusinessException;
import com.pragma.util.FormUtil;
import com.pragma.web.NavigationManager;
import com.pragma.web.action.BaseMappingDispatchAction;
import com.pragma.web.messages.ErrorMessage;

public class AdministracionUsuariosAction extends BaseMappingDispatchAction {
	
	private UsuarioService usuarioService;
	
	
	
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}


	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}


	public ActionForward agregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//TODO cargar datos
		return mapping.findForward("success");
	}
	
	
	
	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = FormUtil.getStringValue(form,"userId");
		if(userId!=null){
			return this.actualizar(mapping, form,request,response);
		}else{
			String appPassword = FormUtil.getStringValue(form,"appPassword");
			String userName = FormUtil.getStringValue(form,"userName");
			String userLastName = FormUtil.getStringValue(form,"userLastName");
			String user = FormUtil.getStringValue(form,"user");
			Long idPersona = FormUtil.getLongValue(form,"idPersona");
			String userPassowrd = FormUtil.getStringValue(form,"password");
			String[] groups = FormUtil.getStringArray(form,"groups");
			try{
				this.usuarioService.create(userName,userLastName,user,userPassowrd , groups, appPassword, idPersona);
			}catch (BusinessException e) {
				ActionMessages errors = this.getErrors( request );
				errors.add("business", new ErrorMessage(e.getMessage(), false));
				return mapping.findForward("invalid");
			}
			return mapping.findForward("success");
		}
	} 
	
	public ActionForward actualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Datos del usuario
		String userId = FormUtil.getStringValue(form,"userId");
		String userName =  FormUtil.getStringValue(form,"userName");
		String userLastName =  FormUtil.getStringValue(form,"userLastName");
		Long idPersona =  FormUtil.getLongValue(form,"idPersona");
		String[] groups = FormUtil.getStringArray(form,"groups");
		
		//Update
		this.usuarioService.update(userId, userName, userLastName, groups, idPersona);
		
		return mapping.findForward("success");
	} 
	
	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String  usuario = request.getParameter("id");
		if( usuario!=null)
			this.usuarioService.delete( usuario );
		return mapping.findForward("inventario");
				
	}

	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = this.getErrors( request );
		if(messages.isEmpty()){
			String  id = request.getParameter("id");
			UsuarioDTO usuario = this.usuarioService.getUsuarioDTO( id );
			
			BeanUtils.setProperty(form, "userId" , usuario.getUserId());
			BeanUtils.setProperty(form, "userName" , usuario.getNombre());
			BeanUtils.setProperty(form, "userLastName" , usuario.getApellido());
			PersonaDTO personaVinculada = usuario.getPersona();
			if(personaVinculada==null) {
				BeanUtils.setProperty(form, "idPersona" , null);
				BeanUtils.setProperty(form, "txtPersona" , "");
			} else {
				BeanUtils.setProperty(form, "idPersona" , personaVinculada.getId());
				BeanUtils.setProperty(form, "txtPersona" , personaVinculada.getNombre());			
			}
			
			UsuarioDTO dto = usuarioService.getUsuarioDTO(usuario.getUserId());
			request.setAttribute("usuario", dto );
			request.setAttribute("grupos",dto.getGrupos());
			return mapping.findForward("success");
		}else
			return this.editarInput(mapping,form,request,response);
	}
	
	public ActionForward editarInput(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String  id = request.getParameter("userId");
		UsuarioDTO usuario = this.usuarioService.getUsuarioDTO( id );

		String[] groups = FormUtil.getStringArray(form,"groups");
		
		request.setAttribute("usuario", usuario );
		request.setAttribute("grupos", this.usuarioService.getGroupDTOSet( groups));
		
		return mapping.findForward("success");
	}

	
	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String  usuario = request.getParameter("id");
		request.setAttribute("usuario", usuarioService.getUsuarioDTO(usuario));
		return mapping.findForward("visualizar");
		
				
	}
	
	public ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("usuarios", this.usuarioService.findAll(BasicUsuarioDTOAssembler.getInstance()));
		return mapping.findForward("inventario");
	}

	
	public ActionForward registrarClavePrivada(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String password = FormUtil.getStringValue(form, "password");
		this.usuarioService.registrarClavePrivada( password);
		return mapping.findForward("success");
	}
	
	
	public ActionForward registracionClavePrivada(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("success");
	}
	
	public ActionForward resetClaveAutenticacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("id");
		if(userId!=null){
			String password = FormUtil.getStringValue(form, "password");
			this.usuarioService.resetearClaveAutenticacion(userId,password);
			return mapping.findForward("success");
		}else{
			throw new RuntimeException("@userdI requerido");
		}
	} 
	
	public ActionForward resetClaveAutenticacionInput(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("id");
		UsuarioDTO usuario = this.usuarioService.getUsuarioDTO(userId);
		request.setAttribute("usuario",usuario);
		return mapping.findForward("success");
	} 
	
	public ActionForward cambiarClaveAutenticacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		try {
			this.usuarioService.cambiarPassword(
					dynaForm.getString("user"), 
					dynaForm.getString("passwordAnterior"), 
					dynaForm.getString("password"));
			//deja un mensaje ok
			ActionMessages actionMessages = getMessages(request);
			addInformationMessage(actionMessages, "app.msj.cambioDeClaveOk");
			saveMessages(request,actionMessages);
			ActionForward previous = NavigationManager.getPreviousAction(request);
			
			// si la pila no sabe me vuelvo a la bandeja de entrada
			if (previous == null)
				return previous = mapping.findForward(FORWARD_BANDEJA_ENTRADA);
			return previous;
		} catch(PasswordInvalidaException exception) {
			ActionMessages actionMessages = getErrors(request);
			addError(actionMessages, "app.changePassword.invalidPassword");
			saveErrors(request,actionMessages);
			return mapping.getInputForward();
		} catch(NoValidoComoPasswordException exception) {
			ActionMessages actionMessages = getErrors(request);
			addError(actionMessages, "app.changePassword.noValidoComoContrasenia");
			saveErrors(request,actionMessages);
			return mapping.getInputForward();
		}
	} 
	
	public ActionForward cambiarClaveAutenticacionInput(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String user = request.getParameter("user");
		UsuarioDTO usuario = this.usuarioService.getUsuarioDTO(user);
		dynaForm.set("user", usuario.getUsername());
		dynaForm.set("passwordAnterior", "");
		dynaForm.set("password", "");
		dynaForm.set("confPassword", "");
		request.setAttribute("usuario",usuario.getNombreCompleto());
		return mapping.findForward("success");
	} 

	public ActionForward cancelar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionForward previous = NavigationManager.getPreviousAction(request);
		
		// si la pila no sabe me vuelvo a la bandeja de entrada
		if (previous == null)
			return previous = mapping.findForward(FORWARD_BANDEJA_ENTRADA);
		previous.setRedirect(true);
		return previous;
	} 
}
