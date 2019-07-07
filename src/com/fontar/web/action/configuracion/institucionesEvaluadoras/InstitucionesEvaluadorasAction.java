package com.fontar.web.action.configuracion.institucionesEvaluadoras;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.data.api.dao.InstitucionesEvaluadorasDAO;

public class InstitucionesEvaluadorasAction extends MappingDispatchAction {

	private InstitucionesEvaluadorasDAO institucionesEvaluadorasDAO;

	public void setInstitucionesEvaluadorasDAO(InstitucionesEvaluadorasDAO institucionesEvaluadorasDAO) {
		this.institucionesEvaluadorasDAO = institucionesEvaluadorasDAO;
	}

	public ActionForward verInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection institucionesEvaluadoras = institucionesEvaluadorasDAO.getInstitucionesEvaluadoras();
		request.setAttribute("institucionesEvaluadoras", institucionesEvaluadoras);

		return mapping.findForward("inventario");
	}

}
