package com.fontar.web.action.configuracion.fuenteFinanciamiento;

import java.sql.SQLException;
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
import com.fontar.data.api.dao.FuenteFinanciamientoDAO;
import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class FuenteFinanciamientoAction extends GenericABMAction<FuenteFinanciamientoBean> {

	public FuenteFinanciamientoAction(Class<FuenteFinanciamientoBean> type) {
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

		FuenteFinanciamientoBean ffbean = new FuenteFinanciamientoBean();

		BeanUtils.setProperty(ffbean, "id", request.getParameter("id"));
		ffbean.setIdentificador(BeanUtils.getProperty(form, "identificador"));
		ffbean.setDenominacion(BeanUtils.getProperty(form, "denominacion"));
		ffbean.setActivo(FormUtil.getBooleanValue(form, "activo"));

		// Guardo los datos en DB
		if (request.getParameter("id") == "") {
			getServicio().save(ffbean);
		}
		else {
			getServicio().update(ffbean);
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

		List<FuenteFinanciamientoBean> listIdentificador;
		List<FuenteFinanciamientoBean> listDenominacion;
		String idFuente = request.getParameter("id").equals("0") ? null : request.getParameter("id");
		String identificador = FormUtil.getStringValue(form, "identificador");
		String denominacion = FormUtil.getStringValue(form, "denominacion");

		FuenteFinanciamientoDAO financiamientoDAO = (FuenteFinanciamientoDAO) getServicio().getDao();

		if (idFuente == null || idFuente == "") {
			listIdentificador = financiamientoDAO.findByIdentificador(identificador);
			listDenominacion = financiamientoDAO.findByDenominacion(denominacion);
		}
		else {
			listIdentificador = financiamientoDAO.findByIdentificadorId(identificador, new Long(idFuente));
			listDenominacion = financiamientoDAO.findByDenominacionId(new Long(idFuente), denominacion);
		}
		if (!listIdentificador.isEmpty()) {
			addError(messages, "app.configuracion.fuentefinanciamiento.existeIdentificador");
		}
		if (!listDenominacion.isEmpty()) {
			addError(messages, "app.configuracion.fuentefinanciamiento.existeDenominacion");
		}
	}

	@Override
	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataVisualizar(mapping, form, request, response);
		// levanto el id de la entidad a visualizar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		FuenteFinanciamientoBean ffbean = (FuenteFinanciamientoBean) getServicio().load(new Long(id));

		request.setAttribute("identificador", ffbean.getIdentificador());
		request.setAttribute("denominacion", ffbean.getDenominacion());
	}



	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}

	protected List getCollectionInventario() {
		return this.getServicio().getActives(true);
	}
}