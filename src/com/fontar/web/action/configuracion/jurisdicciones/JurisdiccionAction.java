package com.fontar.web.action.configuracion.jurisdicciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class JurisdiccionAction extends GenericABMAction<JurisdiccionBean> {

	public JurisdiccionAction(Class<JurisdiccionBean> type) {
		super(type);
	}
	
	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setCollections(request);
	}
	
	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		super.dataEditar(mapping, form, request, response);
		
		setCollections(request);
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JurisdiccionBean jurisdiccion = new JurisdiccionBean();
		jurisdiccion.setId(FormUtil.getLongValue(form,"id"));
		jurisdiccion.setCodigo(FormUtil.getStringValue(form,"codigo"));
		jurisdiccion.setDescripcion(FormUtil.getStringValue(form,"descripcion"));
		jurisdiccion.setActivo(FormUtil.getBooleanValue(form,"activo"));
		jurisdiccion.setIdRegion(FormUtil.getLongValue(form,"idRegion"));
		jurisdiccion.setEmerix(FormUtil.getStringValue(form,"emerix"));
		
		String id = request.getParameter("id");
		if ((id == null) || (id == "")) {
			getServicio().save(jurisdiccion);
		}
		else {
			getServicio().update(jurisdiccion);
		}		
	}

	@Override
	protected void initGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (request.getParameter("id") == null || request.getParameter("id").equals("")) {
			BeanUtils.setProperty(form, "activo", true);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {

		List<JurisdiccionBean> list;
		String idJurisdiccion = request.getParameter("id");
		String codigo = FormUtil.getStringValue(form, "codigo");

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) getServicio().getDao();

		if (idJurisdiccion == null || idJurisdiccion == "") {
			list = jurisdiccionDAO.findByCodigo(codigo);
		}
		else {
			list = jurisdiccionDAO.findByCodigoId(codigo,new Long(idJurisdiccion));
		}
		if (!list.isEmpty()) {
			addError(messages, "app.configuracion.jurisdiccion.existeCodigo");
		}
		if (codigo.startsWith(" "))
			addError(messages, "app.configuracion.jurisdiccion.primerCharBlanco");
	}
	
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection regiones = new ArrayList();
		regiones = collectionHandler.getRegiones();
		request.setAttribute("regiones", regiones);

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}	
}
