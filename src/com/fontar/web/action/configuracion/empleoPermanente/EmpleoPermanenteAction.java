package com.fontar.web.action.configuracion.empleoPermanente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.impl.domain.bean.EmpleoPermanenteBean;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.GenericAction;

public class EmpleoPermanenteAction extends GenericAction {
	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.dataAgregar(mapping, form, request, response);
	}

	@Override
	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataCargar(mapping, form, request, response);
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EmpleoPermanenteBean bean = (EmpleoPermanenteBean) SessionHelper.getEmpleoPermanente(request);
		if (bean != null) {

			BeanUtils.copyProperties(form, bean);
		}
	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EmpleoPermanenteBean bean = new EmpleoPermanenteBean();

		BeanUtils.copyProperties(bean, form);
		SessionHelper.setEmpleoPermanente(request, bean);

		BeanUtils.setProperty(form, "windowClose", "true");
	}
}