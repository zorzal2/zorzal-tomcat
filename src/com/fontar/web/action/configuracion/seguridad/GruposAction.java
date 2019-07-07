package com.fontar.web.action.configuracion.seguridad;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.impl.dao.ldap.GrupoDao;

public class GruposAction extends MappingDispatchAction {

	private GrupoDao grupoDAO;

	public void setGrupoDAO(GrupoDao grupoDAO) {
		this.grupoDAO = grupoDAO;
	}

	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection grupos = grupoDAO.findAll();
		request.setAttribute("grupos", grupos);

		return mapping.findForward("inventario");
	}

}
