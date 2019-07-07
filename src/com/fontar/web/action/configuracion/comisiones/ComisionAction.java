package com.fontar.web.action.configuracion.comisiones;

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

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.ComisionDAO;
import com.fontar.data.impl.domain.bean.ComisionBean;
import com.fontar.data.impl.domain.dto.ComisionDTO;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericABMAction;

public class ComisionAction extends GenericABMAction<ComisionBean> {

	ConfiguracionServicio configuracionService;

	public void setConfiguracionService(ConfiguracionServicio configuracionService) {
		this.configuracionService = configuracionService;
	}

	public ComisionAction(Class<ComisionBean> type) {
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
		setCollections(request);

		String id = request.getParameter("id");
		ComisionDTO comisionDTO = configuracionService.obtenerComision(new Long(id));
		BeanUtils.setProperty(form, "id", comisionDTO.getId());
		BeanUtils.setProperty(form, "denominacion", comisionDTO.getDenominacion());
		BeanUtils.setProperty(form, "resolucion", comisionDTO.getResolucion());
		BeanUtils.setProperty(form, "fechaBaja", comisionDTO.getFechaBaja());
		BeanUtils.setProperty(form, "fechaAlta", comisionDTO.getFechaAlta());
		BeanUtils.setProperty(form, "observacion", comisionDTO.getObservacion());
		BeanUtils.setProperty(form, "descripcion", comisionDTO.getDescripcion());
		BeanUtils.setProperty(form, "activo", comisionDTO.getActivo());
	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ComisionDTO dto = new ComisionDTO();
		BeanUtils.setProperty(dto, "id", request.getParameter("id"));
		dto.setId(FormUtil.getLongValue(form, "id"));
		dto.setDenominacion(FormUtil.getStringValue(form, "denominacion"));
		dto.setResolucion(FormUtil.getStringValue(form, "resolucion"));
		dto.setFechaBaja(FormUtil.getStringValue(form, "fechaBaja"));
		dto.setFechaAlta(FormUtil.getStringValue(form, "fechaAlta"));
		dto.setObservacion(FormUtil.getStringValue(form, "observacion"));
		dto.setDescripcion(FormUtil.getStringValue(form, "descripcion"));
		dto.setActivo((FormUtil.getStringValue(form, "activo") == null || FormUtil.getStringValue(form, "activo").equals("")) ? "true"
				: FormUtil.getStringValue(form, "activo"));

		configuracionService.saveComision(dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);

		List<ComisionBean> list;
		String idComision   = request.getParameter("id");
		String denominacion = FormUtil.getStringValue(form, "denominacion");

		ComisionDAO comisionDAO = (ComisionDAO) getServicio().getDao();

		// alta
		if (idComision == null || idComision == "") {
			list = comisionDAO.findByDenominacion(denominacion);
		}
		else {
			list = comisionDAO.findByDenominacionId(new Long(idComision), denominacion);
		}
		if (!list.isEmpty()) {
			addError(messages, "app.configuracion.comision.existeComision");
		}
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws SQLException {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
	}
}