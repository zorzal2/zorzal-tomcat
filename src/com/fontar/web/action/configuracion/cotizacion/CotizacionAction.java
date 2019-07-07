package com.fontar.web.action.configuracion.cotizacion;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.bus.api.configuracion.CotizacionService;
import com.fontar.bus.api.configuracion.MonedaService;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.dto.CotizacionDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.toolbar.Toolbar;
import com.pragma.toolbar.util.ToolbarRegister;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class CotizacionAction extends BaseMappingDispatchAction {

	private final String ID_TOOLBAR = "cotizaciones";
	private final String hqlQueryString = "from CotizacionBean o where o.id<>"+MonedaService.PESO_ARGENTINO_ID;

	protected Toolbar toolbar;
	
	private CotizacionService cotizacionService;

	public void setCotizacionService(CotizacionService cotizacionService) {
		this.cotizacionService = cotizacionService;
	}

	public final ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initToolbar(request, (ToolbarFiltersForm) form);
		initInventario(request);
		
		return mapping.findForward(FORWARD_SUCCESS); 
	}

	protected ActionForward forwardFiltrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS); 
	}


	private void initToolbar(HttpServletRequest request, ToolbarFiltersForm form) {
		Session session = ((SessionFactory) ContextUtil.getBean("sessionFactory")).openSession();
		WebContextUtil.setHibernateSession(session);
		
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		
		Toolbar toolbar = new Toolbar(request, form, ID_TOOLBAR, authenticationService.getUserId(), hqlQueryString, session);
		
		ToolbarRegister.registerDefault(request, toolbar);
	}

	protected void initInventario(HttpServletRequest request) throws Exception {
		Collection<LabelValueBean> monedas = monedasCotizablesConTodas();
		request.setAttribute("monedas", monedas);
	}
	
	private Collection<LabelValueBean> monedasCotizablesConTodas() {
		return monedasCotizables(CollectionHandler.LABEL_TODAS);
	}
	
	private Collection<LabelValueBean> monedasCotizablesConSeleccionar() {
		return monedasCotizables(CollectionHandler.EMPTY_LABEL);
	}

	private Collection<LabelValueBean> monedasCotizables(String emptyOption) {
		return CollectionHandler.defaultInstance().buildSortedLabelValue(cotizacionService.getMonedasCotizables(), "descripcion", "id", emptyOption);
	}

	public ActionForward agregar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		request.setAttribute("monedas", monedasCotizablesConSeleccionar());
		return mapping.findForward("success");
	}

	public ActionForward editar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id = (Long) dynaForm.get("id");
		
		CotizacionDTO cotizacion = cotizacionService.load(id);
		
		dynaForm.set("idMoneda", cotizacion.getIdMoneda());
		dynaForm.set("fecha", StringUtil.formatDate(cotizacion.getFecha()));
		dynaForm.set("monto", cotizacion.getMonto().toString());
		
		request.setAttribute("monedas", monedasCotizablesConSeleccionar());
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		CotizacionDTO cotizacion = new CotizacionDTO();
		cotizacion.setId((Long) dynaForm.get("id"));
		cotizacion.setIdMoneda((Long)dynaForm.get("idMoneda"));
		cotizacion.setFecha(FormUtil.getDateValue(form, "fecha"));
		cotizacion.setMonto(FormUtil.getBigDecimalValue(form, "monto"));
		
		cotizacionService.update(cotizacion);
		
		return mapping.findForward("success");
	}

	public ActionForward crear(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		CotizacionDTO cotizacion = new CotizacionDTO();
		cotizacion.setIdMoneda((Long)dynaForm.get("idMoneda"));
		cotizacion.setFecha(FormUtil.getDateValue(form, "fecha"));
		cotizacion.setMonto(FormUtil.getBigDecimalValue(form, "monto"));
		
		cotizacionService.create(cotizacion);
		
		return mapping.findForward("success");
	}
	
	public ActionForward eliminar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		Long id =  (Long) dynaForm.get("id");
		
		cotizacionService.delete(id);
		
		return mapping.findForward("success");
	}

}
