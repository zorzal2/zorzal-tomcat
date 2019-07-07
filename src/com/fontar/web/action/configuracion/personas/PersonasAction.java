package com.fontar.web.action.configuracion.personas;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.api.dao.PersonasDAO;

public class PersonasAction extends MappingDispatchAction {

	private PersonasDAO personasDAO;

	public void setPersonasDAO(PersonasDAO personasDAO) {
		this.personasDAO = personasDAO;
	}

	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection personas = personasDAO.getPersonas();
		request.setAttribute("personas", personas);

		return mapping.findForward("inventario");
	}

}
