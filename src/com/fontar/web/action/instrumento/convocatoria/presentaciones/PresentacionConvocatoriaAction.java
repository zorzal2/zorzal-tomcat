package com.fontar.web.action.instrumento.convocatoria.presentaciones;

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
import com.fontar.data.api.dao.LlamadoConvocatoriaDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.codes.presentacionConvocatoria.EstadoPresentacion;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;

/**
 * 
 * @author fferrara
 * 
 */
public class PresentacionConvocatoriaAction extends GenericABMAction<PresentacionConvocatoriaBean> {

	public PresentacionConvocatoriaAction(Class<PresentacionConvocatoriaBean> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void dataInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// List<PresentacionConvocatoriaBean> collection =
		// ((PresentacionConvocatoriaDAO)this.getServicio().getDao()).findByEstado("Presentada");
		List<PresentacionConvocatoriaBean> collection = ((PresentacionConvocatoriaDAO) this.getServicio().getDao()).getAll();
		// guardo la collection en el request
		request.setAttribute("collection", collection);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
		request.setAttribute("estado", EstadoPresentacion.INICIADA.name());
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataEditar(mapping, form, request, response);
		setCollections(request);
	}

	protected void dataGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// creo una instancia
		PresentacionConvocatoriaBean bean = new PresentacionConvocatoriaBean();

		// copio los datos del Form al Bean
		bean.setCodigo(BeanUtils.getProperty(form, "codigo"));
		bean.setFechaIngreso(FormUtil.getDateValue(form, "fechaIngreso"));

		bean.setIdInstrumento(FormUtil.getLongValue(form, "idInstrumento"));
		bean.setIdJurisdiccion(FormUtil.getLongValue(form, "idJurisdiccion"));
		bean.setNombreEntidad(BeanUtils.getProperty(form, "nombreEntidad"));
		bean.setObservaciones(BeanUtils.getProperty(form, "observaciones"));
		bean.setEstado(EstadoPresentacion.INICIADA);

		BeanUtils.setProperty(bean, "id", request.getParameter("id"));// TODO:
																		// esto
																		// se
																		// puede
																		// reempñazar
																		// con
																		// un
																		// campo
																		// con
																		// nombre
																		// ID y
																		// hidden

		bean.setEstado(EstadoPresentacion.INICIADA);

		// Guardo los datos en DB
		if (request.getParameter("id") == "") {
			getServicio().save(bean);
		}
		else {
			getServicio().update(bean);
		}
	}

	@Override
	/**
	 * Valida que el código no exista cuando se crea una nueva presentación
	 * convocatoria o cuando se edita
	 */
	protected void validateGuardar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages) throws Exception {
		super.validateGuardar(mapping, form, request, response, messages);

		List<PresentacionConvocatoriaBean> list;
		String codigo = FormUtil.getStringValue(form, "codigo");
		String idPresentacion = FormUtil.getStringValue(form, "id");

		PresentacionConvocatoriaDAO convocatoriaDAO = (PresentacionConvocatoriaDAO) getServicio().getDao();

		if (idPresentacion == null || idPresentacion == "") {
			list = convocatoriaDAO.findByCodigo(codigo);
		}
		else {
			list = convocatoriaDAO.findByCodigoId(new Long(idPresentacion), codigo);
		}
		boolean blnExisteCodigo = false;
		if(!list.isEmpty()){
			for(PresentacionConvocatoriaBean presentacionBean : list){
				if (presentacionBean.getEstado().getDescripcion() != EstadoPresentacion.ANULADA.getDescripcion())
					blnExisteCodigo = true;
			}
		}
		
		if (blnExisteCodigo) {
			addError(messages, "app.convocatoria.presentacion.existeCodigo");
		}
		
	}

	/*
	 * 
	 * Lleno las collections para Agregar y Edición
	 */
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		LlamadoConvocatoriaDAO convocatoriasDao = (LlamadoConvocatoriaDAO) WebContextUtil.getBeanFactory().getBean("llamadoConvocatoriaDao");
		JurisdiccionDAO jurisdiccionDAO = (JurisdiccionDAO) WebContextUtil.getBeanFactory().getBean("jurisdiccionDao");

		Collection convocatorias = new ArrayList();
		Collection jurisdicciones = new ArrayList();

		convocatorias.addAll(collectionHandler.getConvocatorias(convocatoriasDao));
		jurisdicciones.addAll(collectionHandler.getJurisdicciones(jurisdiccionDAO));

		request.setAttribute("convocatorias", convocatorias);
		request.setAttribute("jurisdicciones", jurisdicciones);
	}

}
