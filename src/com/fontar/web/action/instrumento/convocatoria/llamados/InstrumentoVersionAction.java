package com.fontar.web.action.instrumento.convocatoria.llamados;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.data.api.dao.InstrumentoVersionDAO;
import com.fontar.data.impl.domain.bean.InstrumentoVersionBean;
import com.pragma.web.action.GenericAction;

public class InstrumentoVersionAction extends GenericAction {

	private static final String ID_INSTRUMENTO_PARAMETER = "idInstrumento";

	private static final String INSTRUMENTO_VERSION_LIST = "instrumentoVersionList";

	protected void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String idInstrumento = (String) request.getParameter(ID_INSTRUMENTO_PARAMETER);

		BeanUtils.setProperty(form, "idInstrumento", idInstrumento);
		List version = ((InstrumentoVersionDAO) getServicio().getDao(InstrumentoVersionBean.class)).findByInstrumento(new Long(idInstrumento));
		request.setAttribute(INSTRUMENTO_VERSION_LIST, version);
	}

	protected void validateCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@SuppressWarnings("unchecked")
	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
