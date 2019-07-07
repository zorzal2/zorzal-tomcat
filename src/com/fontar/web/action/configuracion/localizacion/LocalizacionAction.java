package com.fontar.web.action.configuracion.localizacion;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.JurisdiccionDAO;
import com.fontar.data.impl.domain.dto.LocalizacionDTO;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericAction;

/**
 * 
 * @deprecated
 */
public class LocalizacionAction extends GenericAction {

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.dataAgregar(mapping, form, request, response);
		setCollections(request);
	}

	@Override
	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataCargar(mapping, form, request, response);
		setCollections(request);
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");
		Collection jurisdicciones = new ArrayList();

		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));
		request.setAttribute("jurisdicciones", jurisdicciones);
	}

	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LocalizacionDTO dto = new LocalizacionDTO();

		dto.setId(FormUtil.getLongValue(form, "id"));
		dto.setCodigoPostal(FormUtil.getStringValue(form, "codigoPostal"));
		dto.setDepartamento(FormUtil.getStringValue(form, "departamento"));
		dto.setDireccion(FormUtil.getStringValue(form, "direccion"));
		dto.setEmail(FormUtil.getStringValue(form, "email"));
		dto.setFax(FormUtil.getStringValue(form, "fax"));

		dto.setIdJurisdiccion(FormUtil.getLongValue(form, "idJurisdiccion"));

		dto.setLocalidad(FormUtil.getStringValue(form, "localidad"));
		dto.setPaginaWeb(FormUtil.getStringValue(form, "paginaWeb"));
		dto.setPais(FormUtil.getStringValue(form, "pais"));
		dto.setTelefono(FormUtil.getStringValue(form, "telefono"));

		// SessionHelper.SetLocalizacion(request, dto);

		BeanUtils.setProperty(form, "windowClose", "true");
	}

}