package com.fontar.web.action.configuracion.seguridad;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.impl.dao.ldap.UsuarioDao;

public class UsuariosAction extends MappingDispatchAction {

	private UsuarioDao usuarioDAO;


	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection usuarios = usuarioDAO.findAll();
		request.setAttribute("usuarios", usuarios);

		return mapping.findForward("inventario");
	}


	public UsuarioDao getUsuarioDAO() {
		return usuarioDAO;
	}


	public void setUsuarioDAO(UsuarioDao usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
