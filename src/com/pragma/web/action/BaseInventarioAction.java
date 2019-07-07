package com.pragma.web.action;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.seguridad.AuthenticationService;
import com.pragma.toolbar.Toolbar;
import com.pragma.toolbar.util.ToolbarRegister;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.web.WebContextUtil;

/*******************************************************************************
 * 
 * @author gboaglio
 * 
 */
public class BaseInventarioAction extends BaseMappingDispatchAction {

	private SessionFactory sessionFactory;

	private String idToolbar;

	private String hqlQueryString;

	protected Toolbar toolbar;
	
	// Mapa para poder configurar los LabelValueBean para las collections de los filtros desde Spring
	private Map<String,Collection<LabelValueBean>> filtrosData; 

	// Hibernate SessionFactory
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setHqlQueryString(String hqlQueryString) {
		this.hqlQueryString = hqlQueryString;
	}

	protected String getHqlQueryString() {
		return hqlQueryString;
	}

	public void setIdToolbar(String idToolbar) {
		this.idToolbar = idToolbar;
	}
	public String getIdToolbar() {
		return idToolbar;
	}
	public final ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!(form instanceof ToolbarFiltersForm)) {
			throw new RuntimeException("El formulario debe ser de clase ToolbarFiltersForm");
		}
		addCustomFilters(request,(ToolbarFiltersForm) form);
		initToolbar(request, (ToolbarFiltersForm) form);
		initInventario(request);
		
		return forwardInventario(mapping,form,request,response);
	}


	protected ActionForward forwardFiltrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS); 
	}
	
	protected ActionForward forwardInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(FORWARD_SUCCESS); 
	}
	
	public final ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!(form instanceof ToolbarFiltersForm)) {
			throw new RuntimeException("El formulario debe ser de clase ToolbarFiltersForm");
		}
		addCustomFilters(request,(ToolbarFiltersForm) form);
		initToolbar(request, (ToolbarFiltersForm) form);
		initInventario(request);

		return forwardFiltrar(mapping,form,request,response);
	}

	protected void addCustomFilters(HttpServletRequest request,ToolbarFiltersForm form) {
		// TODO Auto-generated method stub
		
	}

	protected void initToolbar(HttpServletRequest request, ToolbarFiltersForm form) {
		Session session = sessionFactory.openSession();
		WebContextUtil.setHibernateSession(session);
		
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		
		Toolbar toolbar = new Toolbar(request, form, getIdToolbar(), authenticationService.getUserId(), this.getHqlQueryString(), session);
		// Toolbar toolbar = new Toolbar(request, form, idToolbar,
		// session.createQuery(hqlQueryString), session);
		
		ToolbarRegister.registerDefault(request, toolbar);
	}

	protected void initInventario(HttpServletRequest request) throws Exception {
		if (filtrosData != null){		
			if (!filtrosData.isEmpty()){
				for (String atributo : filtrosData.keySet()) {
					request.setAttribute(atributo,filtrosData.get(atributo));
				}
			}
		}
	}
	
	public final ActionForward selector(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.inventario(mapping,form,request,response);
	}

	public void setFiltrosData(Map<String, Collection<LabelValueBean>> filtrosData) {
		this.filtrosData = filtrosData;
	}
}

