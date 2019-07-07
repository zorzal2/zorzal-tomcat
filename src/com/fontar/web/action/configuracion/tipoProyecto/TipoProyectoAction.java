package com.fontar.web.action.configuracion.tipoProyecto;

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
import com.fontar.data.api.dao.TipoProyectoDAO;
import com.fontar.data.impl.assembler.TipoProyectoInventarioAssembler;
import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

/**
 * 
 * @author ttoth
 * 
 */
public class TipoProyectoAction extends GenericABMAction {

	@SuppressWarnings("unchecked")
	public TipoProyectoAction(Class type) {
		super(type);
		// TODO Auto-generated constructor stub
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

		TipoProyectoBean bean = new TipoProyectoBean();

		BeanUtils.setProperty(bean, "id", request.getParameter("id"));
		bean.setNombre(BeanUtils.getProperty(form, "nombre"));
		bean.setNombreCorto(BeanUtils.getProperty(form, "nombreCorto"));
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

		String nombre = FormUtil.getStringValue(form, "nombre");
		String idTipoProyecto = request.getParameter("id");
		String nombreCorto = FormUtil.getStringValue(form, "nombreCorto");

		TipoProyectoDAO tipoDAO = (TipoProyectoDAO) getServicio().getDao();

		List<TipoProyectoBean> listNombre;
		List<TipoProyectoBean> listNombreCorto;
		if (idTipoProyecto == null || idTipoProyecto == "") {
			listNombre = tipoDAO.findByNombre(nombre);
			listNombreCorto = tipoDAO.findByNombreCorto(nombreCorto);
		}
		else {
			listNombre = tipoDAO.findByNombreTipo(new Long(idTipoProyecto), nombre);
			listNombreCorto = tipoDAO.findByNombreCortoId(new Long(idTipoProyecto), nombreCorto);
		}
		if (!listNombre.isEmpty()) {
			addError(messages, "app.configuracion.tipoProyecto.existeNombre");
		}
		if (!listNombreCorto.isEmpty()) {
			addError(messages, "app.configuracion.tipoProyecto.existeNombreCorto");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataVisualizar(mapping, form, request, response);
		// levanto el id de la entidad a visualizar
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}

		TipoProyectoBean bean = (TipoProyectoBean) getServicio().load(new Long(id));

		request.setAttribute("nombre", bean.getNombre());
		request.setAttribute("nombreCorto", bean.getNombreCorto());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List getCollectionInventario() {

		return TipoProyectoInventarioAssembler.getInstance().buildDto(getServicio().getAll());
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}

}
