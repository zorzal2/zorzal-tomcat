package com.fontar.consultas;

import java.text.ParseException;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.util.FontarValidation;
import com.fontar.util.Util;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

public class EvaluacionPorInstitucionResolver implements Resolver {


	public void setCriteria(ConsultaFilterForm form, Session session, Criteria evaluaciones, Criteria proyecto, Criteria proyectoDatos) {
			
		String strInstrumento = (String) form.getFilter("instrumento");
		
		if (!Util.isBlank(strInstrumento)) {
			Long idInstrumento = new Long(strInstrumento);
			proyecto.add(Property.forName("idInstrumento").eq(idInstrumento));
		}
		

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EvaluacionEvaluadorBean.class);
		detachedCriteria.setProjection( Property.forName("idEvaluacion") );
		detachedCriteria.add(Property.forName("institucionData").isNotNull());
		detachedCriteria.addOrder(Order.asc("institucionData"));
		
		Collection idList = detachedCriteria.getExecutableCriteria(session).list();
		if (idList.isEmpty()) {
			idList.add(new Long(-1));
		}
		evaluaciones.add(Property.forName("id").in(idList));

//		DetachedCriteria detachedBitacora = DetachedCriteria.forClass(BitacoraBean.class);
//      detachedBitacora.setProjection( Property.forName("idEvaluacion") );
//		detachedBitacora.add(Property.forName("idSeguimiento").isNull());
//		detachedBitacora.add(Property.forName("idEvaluacion").isNotNull());
		
//		Collection idSeguimientoList = detachedBitacora.getExecutableCriteria(session).list();
//		if (idSeguimientoList.isEmpty()) {
//			idSeguimientoList.add(new Long(-1));
//		}
//		evaluaciones.add(Property.forName("id").in(idSeguimientoList));

		evaluaciones.addOrder(Order.asc("fecha"));
		
		
	}
	
	public Map getParameters(HttpServletRequest request) {
		return null;
	}

	public Collection result(HttpServletRequest request) {
		return null;
	}

	public void execute(HttpServletRequest request, Result result) {
		ConsultaFilterForm filtersForm = this.getFiltersForm(request);
		Session session = this.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(EvaluacionBean.class);
		
		//Filtro por fecha
		String before = (String) filtersForm.getFilter("before");
		String after = (String) filtersForm.getFilter("after");
		String date = (String) filtersForm.getFilter("presentacionResolucion");
		
		Criteria proyecto = criteria.createCriteria("proyecto");
		proyecto.add(Expression.sqlRestriction("TP_PROYECTO = 'P'"));
		Criteria proyectoDatosCriteria = proyecto.createCriteria("proyectoDatos");
		if(date.equals("presentacion"))
			this.setPresentacionFilter(proyectoDatosCriteria,after,before);
		else
			this.setResolucionFilter(proyecto,after,before);
		
		
		this.setCriteria(filtersForm,session,criteria,proyecto,proyectoDatosCriteria);
		
			result.init(criteria);
	}

	public void validate(InformeFilterForm form, ActionErrors errors) {
		 String before = form.getFilterAsString("before");
		 if(!FontarValidation.isDate(before,true))
			 errors.add("before", new ActionMessage("El campo desde no es válido", false));
		 
		 String after = form.getFilterAsString("after");
		 if(!FontarValidation.isDate(after,true))
			 errors.add("after", new ActionMessage("El campo hasta no es válido", false));
		 
		 if(errors.isEmpty() && StringUtil.isNotEmpty( before) && StringUtil.isNotEmpty( after)){
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
	
	protected ConsultaFilterForm getFiltersForm(HttpServletRequest request){
		return (ConsultaFilterForm) request.getAttribute("consultaFiltersForm");
	}

	public void setInitialContext(HttpServletRequest request) {
		request.setAttribute("instrumentos", this.collectionHandler.getInstrumentos());
		Collection<LabelValueBean> dateFilter = new LinkedList<LabelValueBean>();
//		dateFilter.add(new LabelValueBean(CollectionHandler.EMPTY_LABEL, CollectionHandler.EMPTY_VALUE));
		dateFilter.add(new LabelValueBean("Presentación", "presentacion"));
		dateFilter.add(new LabelValueBean("Resolución", "resolucion"));
		request.setAttribute("comboPresentacionResolucion",dateFilter);
	}
	
	protected void setPresentacionFilter(Criteria criteria, String after, String before){
		try{
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaIngreso").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after))
				criteria.add(Property.forName("fechaIngreso").le( DateTimeUtil.getDate(after)));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected void setResolucionFilter(Criteria criteria, String after, String before){
		try{
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaResolucion").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after))
				criteria.add(Property.forName("fechaResolucion").le( DateTimeUtil.getDate(after)));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
