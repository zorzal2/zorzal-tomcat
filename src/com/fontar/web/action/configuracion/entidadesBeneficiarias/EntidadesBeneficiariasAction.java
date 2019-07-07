package com.fontar.web.action.configuracion.entidadesBeneficiarias;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.api.dao.EntidadesBeneficiariasDAO;

/**
 * 
 * @author gboaglio
 * @deprecated
 */
public class EntidadesBeneficiariasAction extends MappingDispatchAction {

	private EntidadesBeneficiariasDAO entidadesBeneficiariasDAO;

	public void setEntidadesBeneficiariasDAO(EntidadesBeneficiariasDAO entidadesBeneficiariasDAO) {
		this.entidadesBeneficiariasDAO = entidadesBeneficiariasDAO;
	}

	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection entidadesBeneficiarias = entidadesBeneficiariasDAO.getEntidadesBeneficiarias();
		request.setAttribute("entidadesBeneficiarias", entidadesBeneficiarias);

		return mapping.findForward("inventario");
	}

}
