package com.fontar.web.action.configuracion.entidades.entidad;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.entidad.TipoEntidad;
import com.pragma.web.action.GenericAction;

public class EntidadIntervinientesAction extends GenericAction {

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		setCollections(request);
	}
	
	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		setCollections(request);
		super.dataEditar(mapping,form,request,response);
	}
	
	@Override
	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}

	protected void dataCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setCollections(request);
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection tiposEnt = new ArrayList();
		tiposEnt = collectionHandler.getComboFormulario(TipoEntidad.class, false);

		request.setAttribute("tipoEnt", tiposEnt);
	}

}
