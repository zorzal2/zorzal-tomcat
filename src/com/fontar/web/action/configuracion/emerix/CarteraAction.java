package com.fontar.web.action.configuracion.emerix;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.CarteraBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class CarteraAction extends GenericABMAction {

	@SuppressWarnings("unchecked")
	public CarteraAction(Class type) {
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
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CarteraBean bean = new CarteraBean();

		BeanUtils.setProperty(bean, "id", request.getParameter("id"));
		bean.setCodigo(BeanUtils.getProperty(form, "codigo"));
		bean.setDenominacion(BeanUtils.getProperty(form, "denominacion"));
		bean.setEmerix(BeanUtils.getProperty(form, "emerix"));
		bean.setActivo(FormUtil.getBooleanValue(form, "activo"));

		// Guardo los datos en DB
		if (request.getParameter("id") == "") {
			getServicio().save(bean);
		}
		else {
			getServicio().update(bean);
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
		super.validateGuardar(mapping, form, request, response, messages);

//		List<RegionBean> list;
//		String nombre = FormUtil.getStringValue(form, "nombre");
//		String idRegion = request.getParameter("id");
//
//		RegionDAO regDAO = (RegionDAO) getServicio().getDao();
//
//		if (idRegion == null || idRegion == "") {
//			list = regDAO.findByNombre(nombre);
//		}
//		else {
//			list = regDAO.findByNombreID(new Long(idRegion), nombre);
//		}
//
//		if (!list.isEmpty()) {
//			addError(messages, "app.configuracion.region.existeNombre");
//		}
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		super.dataVisualizar(mapping, form, request, response);
//		// levanto el id de la entidad a visualizar
//		String id = request.getParameter("id");
//
//		if ((id == null) || (id == "")) {
//			throw new RuntimeException("Es necesario pasar el id ");
//		}
//
//		RegionBean bean = (RegionBean) getServicio().load(new Long(id));
//
//		request.setAttribute("nombre", bean.getNombre());
//	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}
}
