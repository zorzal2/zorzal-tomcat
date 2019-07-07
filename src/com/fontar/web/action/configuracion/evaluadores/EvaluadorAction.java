package com.fontar.web.action.configuracion.evaluadores;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.configuracion.EntidadEvaluadoraServicio;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.GenericABMAction;

public class EvaluadorAction extends GenericABMAction<EvaluadorBean> {

	public EvaluadorAction(Class<EvaluadorBean> type) {
		super(type);
	}

	@Override
	public final ActionForward selector(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Collection collection;

		String idEntidadEvaluadora = request.getParameter("idEntidadEvaluadora");
		if (idEntidadEvaluadora != null && !GenericValidator.isBlankOrNull(idEntidadEvaluadora)) {
			collection = this.filterByEntidadEvaluador(GenericTypeValidator.formatLong(idEntidadEvaluadora));
		}
		else
			collection = this.filter();

		// guardo la collection en el request
		request.setAttribute("collection", collection);
		return mapping.findForward(EvaluadorAction.FORWARD_SUCCESS);
	}

	private Collection filter() {
		return this.getServicio().getAll();
	}

	private Collection filterByEntidadEvaluador(Long id) {
		EntidadEvaluadoraServicio servicio = ContextUtil.getBean("entidadEvaluadoraService");
		return servicio.obtenerEvaluadoresDeLaEntidadEvaluadora(id);
	}

}