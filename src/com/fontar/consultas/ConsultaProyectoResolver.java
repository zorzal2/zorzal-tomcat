package com.fontar.consultas;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.util.FontarValidation;
import com.fontar.util.Util;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.DateTimeUtil;

public abstract class ConsultaProyectoResolver {

	private CollectionHandler collectionHandler = new CollectionHandler();
	
	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public CollectionHandler getCollectionHandler() {
		return collectionHandler;
	}

	public void setCollectionHandler(CollectionHandler collectionHandler) {
		this.collectionHandler = collectionHandler;
	}

	public abstract void setInitialContextImpl(HttpServletRequest request);
	
	public void setInitialContext(HttpServletRequest request) {
		Collection<LabelValueBean> dateFilter = new LinkedList<LabelValueBean>();
//		dateFilter.add(new LabelValueBean(CollectionHandler.EMPTY_LABEL, CollectionHandler.EMPTY_VALUE));
		dateFilter.add(new LabelValueBean("Presentación", "presentacion"));
		dateFilter.add(new LabelValueBean("Resolución", "resolucion"));
		request.setAttribute("comboPresentacionResolucion",dateFilter);
		request.setAttribute("instrumentos", this.collectionHandler.getInstrumentos());
		
		this.setInitialContextImpl(request);
	}
	
	public Map getParameters(HttpServletRequest request) {
		return null;
	}

	
	protected void setPresentacionFilter(Criteria criteria, String after, String before){
		try{
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaIngreso").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after)){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateTimeUtil.getDate(after)  );
				calendar.add(Calendar.DATE,1);
				criteria.add(Property.forName("fechaIngreso").lt(calendar.getTime()));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected void setResolucionFilter(Criteria criteria, String after, String before){
		try{
			 
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaResolucion").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after)){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateTimeUtil.getDate(after)  );
				calendar.add(Calendar.DATE,1);
				criteria.add(Property.forName("fechaResolucion").lt( calendar.getTime()));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public abstract void setCriteria(InformeFilterForm form ,Session session, Criteria proyecto, Criteria proyectoDatos); 
	
	public Collection result(HttpServletRequest request) {
		return null;
	}
	
	
	public void execute(HttpServletRequest request, Result result) {
				
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("consultaFiltersForm");
		Session session = this.getSessionFactory().openSession();
		
		
		Criteria criteria = session.createCriteria(ProyectoBean.class);
		
		//Filtro por fecha
		String before = (String) filtersForm.getFilter("before");
		String after = (String) filtersForm.getFilter("after");
		String strInstrumento = (String) filtersForm.getFilter("finstrumento");
		String date = (String) filtersForm.getFilter("presentacionResolucion");
		
		if (!Util.isBlank(strInstrumento)) {
			Long idInstrumento = new Long(strInstrumento);
			criteria.add(Property.forName("idInstrumento").eq(idInstrumento));
		}
		Criteria proyectoDatosCriteria = criteria.createCriteria("proyectoDatos");
		
		
		if(date.equals("presentacion"))
			this.setPresentacionFilter(proyectoDatosCriteria,after,before);
		else
			this.setResolucionFilter(criteria,after,before);
		
		
		this.setCriteria(filtersForm,session,criteria,proyectoDatosCriteria);
		
		result.init(criteria);
	}
	
	
	public void validate(InformeFilterForm form, ActionErrors errors) {
		 String before = form.getFilterAsString("before");
		 if(!FontarValidation.isDate(before,true))
			 errors.add("before", new ActionMessage("El campo desde no es válido", false));
		 
		 String after = form.getFilterAsString("after");
		 if(!FontarValidation.isDate(after,true))
			 errors.add("after", new ActionMessage("El campo hasta no es válido", false));
		 
		 if(errors.isEmpty() && !Util.isBlank(before) && !Util.isBlank(after)){
			 try{
				 Date firstDate = DateTimeUtil.getDate(before);
				 Date seconDate = DateTimeUtil.getDate(after);
				 if (!seconDate.after(firstDate)) 
					 errors.add("after", new ActionMessage("El campo hasta es anterior a desde", false));
			 }catch(ParseException e){
				 throw new RuntimeException(e);
			 }
			 
		 }

	}
	
}
