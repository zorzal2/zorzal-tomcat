package com.fontar.web.action.configuracion.instrumentos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.configuracion.InstrumentoDefServicio;
import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.FuenteFinanciamientoDAO;
import com.fontar.data.api.dao.InstrumentoDefDAO;
import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoDefBean;
import com.fontar.data.impl.domain.codes.instrumentoDef.TipoInstrumentoDef;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.GenericABMAction;

public class InstrumentoDefAction extends GenericABMAction<InstrumentoDefBean> {
	
	AdjuntoServicio adjuntoServicio;
	
	

	public AdjuntoServicio getAdjuntoServicio() {
		return adjuntoServicio;
	}

	public void setAdjuntoServicio(AdjuntoServicio adjuntoServicio) {
		this.adjuntoServicio = adjuntoServicio;
	}

	
	
	

	public InstrumentoDefAction(Class<InstrumentoDefBean> type) {
		super(type);
	}

	@Override
	protected void dataAgregar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCollections(request);
//		request.setAttribute("permiteAdjudicacion", tipos);
	}

	@Override
	protected void dataEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.dataEditar(mapping, form, request, response);
		setCollections(request);
		setSelectors(form, request);
//		List listaAdjuntos = (List) getAdjuntoServicio().getAdjuntos();
//		request.setAttribute("listaAdjuntos", listaAdjuntos);

	}

	private void setSelectors(ActionForm form, HttpServletRequest request) throws Exception {

		FuenteFinanciamientoDAO fuenteFinanciamientoDAO = (FuenteFinanciamientoDAO) WebContextUtil.getBeanFactory().getBean("fuenteFinanciamientoDao");
		String idFuenteFinanciamiento = BeanUtils.getProperty(form, "idFuenteFinanciamiento");

		if (idFuenteFinanciamiento != null && !idFuenteFinanciamiento.equals("")) {
			FuenteFinanciamientoBean bean = (FuenteFinanciamientoBean) fuenteFinanciamientoDAO.read(new Long(idFuenteFinanciamiento));
			BeanUtils.copyProperty(form, "txtFuenteFinanciamiento", bean.getDenominacion());
		}
	}

	/*
	 * 
	 * Lleno las collections para Agregar y Edición
	 */
	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection tipos = new ArrayList();
		tipos = collectionHandler.getComboFormulario(TipoInstrumentoDef.class, false);
		request.setAttribute("tipos", tipos);

		Collection estadosEntidad = new ArrayList();
		estadosEntidad.addAll(collectionHandler.getEstadosEntidad());
		request.setAttribute("estadosEntidad", estadosEntidad);
		
		request.setAttribute("fuentesFinanciamiento", collectionHandler.getFuenteFinanciamiento() );

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

		List<InstrumentoDefBean> list;
		String idInstrumentoDef = request.getParameter("id");
		String identificador = FormUtil.getStringValue(form, "identificador");

		InstrumentoDefDAO instrumentoDefDAO = (InstrumentoDefDAO) getServicio().getDao();

		if (idInstrumentoDef == null || idInstrumentoDef == "") {
			list = instrumentoDefDAO.findByIdentificador(identificador);
		}
		else {
			list = instrumentoDefDAO.findByIdentificadorId(new Long(idInstrumentoDef), identificador);
		}
		if (!list.isEmpty()) {
			addError(messages, "app.configuracion.instrumento.existeIdentificador");
		}
		if (identificador.startsWith(" "))
			addError(messages, "app.configuracion.instrumento.primerCharBlanco");
		BigDecimal monto = FormUtil.getBigDecimalValue(form, "monto");
		if (monto == null) {
			BeanUtils.setProperty(form, "monto", BigDecimal.ZERO);
		}
		
		BigDecimal proporcionApoyo = FormUtil.getBigDecimalValue(form, "proporcionApoyo");
		if (proporcionApoyo == null) {
			BeanUtils.setProperty(form, "proporcionApoyo", BigDecimal.ZERO);
		}
	}

	@Override
	protected void dataVisualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		if ((id == null) || (id == "")) {
			throw new RuntimeException("Es necesario pasar el id ");
		}
		
		InstrumentoDefServicio instrumentoDefServicio = (InstrumentoDefServicio) this.getServicio();
		request.setAttribute("instrumento" , instrumentoDefServicio.getVisualizarDTO(new Long(id)));
	}
	
	
	
}
