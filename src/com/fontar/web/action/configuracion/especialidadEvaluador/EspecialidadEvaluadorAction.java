package com.fontar.web.action.configuracion.especialidadEvaluador;

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
import com.fontar.data.api.dao.EspecialidadEvaluadorDAO;
import com.fontar.data.impl.domain.bean.EspecialidadEvaluadorBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class EspecialidadEvaluadorAction extends GenericABMAction<EspecialidadEvaluadorBean> {

	public EspecialidadEvaluadorAction(Class<EspecialidadEvaluadorBean> type) {
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

		EspecialidadEvaluadorBean eebean = new EspecialidadEvaluadorBean();

		BeanUtils.setProperty(eebean, "id", request.getParameter("id"));
		eebean.setCodigo(BeanUtils.getProperty(form, "codigo"));
		eebean.setNombre(BeanUtils.getProperty(form, "nombre"));
		eebean.setActivo(FormUtil.getBooleanValue(form, "activo"));

		// Guardo los datos en DB
		if (request.getParameter("id") == "") {
			getServicio().save(eebean);
		}
		else {
			getServicio().update(eebean);
		}

		request.setAttribute("id", eebean.getId());
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

		List<EspecialidadEvaluadorBean> listCodigo;
		List<EspecialidadEvaluadorBean> listNombre;
		String idEspecialidad = request.getParameter("id").equals("0") ? null : request.getParameter("id");
		String codigo = FormUtil.getStringValue(form, "codigo");
		String nombre = FormUtil.getStringValue(form, "nombre");

		EspecialidadEvaluadorDAO evaluadorDAO = (EspecialidadEvaluadorDAO) getServicio().getDao();

		if (idEspecialidad == null || idEspecialidad == "") {
			listCodigo = evaluadorDAO.findByCodigo(codigo);
			listNombre = evaluadorDAO.findByNombre(nombre);
		}
		else {
			listCodigo = evaluadorDAO.findByCodigoID(codigo, new Long(idEspecialidad));
			listNombre = evaluadorDAO.findByNombreID(nombre, new Long(idEspecialidad));
		}
		if (!listCodigo.isEmpty()) {
			addError(messages, "app.configuracion.especialidadEvaluador.existeCodigo");
		}
		if (!listNombre.isEmpty()) {
			addError(messages, "app.configuracion.especialidadEvaluador.existeNombre");
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

		EspecialidadEvaluadorBean eebean = (EspecialidadEvaluadorBean) getServicio().load(new Long(id));

		request.setAttribute("id", eebean.getId());
		request.setAttribute("codigo", eebean.getCodigo());
		request.setAttribute("nombre", eebean.getNombre());
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}

}