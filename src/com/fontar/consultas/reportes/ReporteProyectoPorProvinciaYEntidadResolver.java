package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
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
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.data.api.dao.EntidadDAO;
import com.fontar.data.api.dao.EntidadIntervinientesDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EntidadIntervinientesBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.util.FontarValidation;
import com.fontar.util.Util;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class ReporteProyectoPorProvinciaYEntidadResolver implements Resolver {

	private CollectionHandler collectionHandler = new CollectionHandler();
	
	private SessionFactory sessionFactory;
	
	public void setInitialContextImpl(HttpServletRequest request) {
	}

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

	public void setCriteria(InformeFilterForm form, Session session, Criteria bitacora, DetachedCriteria proyecto) {
		
		
		bitacora.add(Expression.eq("tipo", TipoBitacora.ENTIDAD_INTERVINIENTE));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EntidadIntervinientesBean.class);
		detachedCriteria.setProjection( Property.forName("id") );
		detachedCriteria.addOrder(Order.asc("relacion"));
		Collection idList = detachedCriteria.getExecutableCriteria(session).list();
		if (idList.isEmpty())
			idList.add(new Long(-1));
		
		bitacora.add(Property.forName("id").in(idList ));
		
		
//		DetachedCriteria proyecto = DetachedCriteria.forClass(ProyectoBean.class);
		proyecto.setProjection( Property.forName("id") );
		String jurisdiccion = (String) form.getFilter("jurisdiccion");
		DetachedCriteria pjuriCriteria = proyecto.createCriteria("proyectoJurisdiccion");
		if (!Util.isBlank(jurisdiccion)) {
			pjuriCriteria.add(Expression.eq( "idJurisdiccion", new Long(jurisdiccion)));
		}
		Collection idProyectoList = proyecto.getExecutableCriteria(session).list();
		if (idProyectoList.isEmpty())
			idProyectoList.add(new Long(-1));
		
		bitacora.add(Property.forName("idProyecto").in(idProyectoList ));
		
	}


	public void setInitialContext(HttpServletRequest request) {
		Collection<LabelValueBean> dateFilter = new LinkedList<LabelValueBean>();
		dateFilter.add(new LabelValueBean("Presentación", "presentacion"));
		dateFilter.add(new LabelValueBean("Resolución", "resolucion"));
		request.setAttribute("comboPresentacionResolucion",dateFilter);
		request.setAttribute("jurisdicciones", this.getCollectionHandler().getJurisdicciones());
//		this.setInitialContextImpl(request);
	}


	public Map getParameters(HttpServletRequest request) {
		return new HashMap();
	}


	public Collection result(HttpServletRequest request) {
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Session session = this.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(BitacoraBean.class);
		
		//Filtro por fecha
		String before = (String) filtersForm.getFilter("before");
		String after = (String) filtersForm.getFilter("after");
		String date = (String) filtersForm.getFilter("presentacionResolucion");

		DetachedCriteria proyecto = DetachedCriteria.forClass(ProyectoBean.class);
		
		DetachedCriteria proyectoDatosCriteria = proyecto.createCriteria("proyectoDatos");
		
		if(date.equals("presentacion"))
			this.setPresentacionFilter(proyectoDatosCriteria,after,before);
		else
			this.setResolucionFilter(proyecto,after,before);
		
		this.setCriteria(filtersForm,session,criteria,proyecto);
		List result = criteria.list();
		CollectionUtils.transform(result, new ProyectoEntidadIntervinientes());
		Comparator comparator2 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				ProyectoIntervinientesDTO dto1 = (ProyectoIntervinientesDTO) arg0;
				ProyectoIntervinientesDTO dto2 = (ProyectoIntervinientesDTO) arg1;
				return dto1.getDenominacion().compareToIgnoreCase(dto2.getDenominacion());
			}};
			
		Comparator comparator3 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				ProyectoIntervinientesDTO dto1 = (ProyectoIntervinientesDTO) arg0;
				ProyectoIntervinientesDTO dto2 = (ProyectoIntervinientesDTO) arg1;
				return dto1.getIdentificador().compareToIgnoreCase(dto2.getIdentificador());
			}};
				
//		List<ProyectoIntervinientesDTO> list = result;
		Collections.sort(result,comparator3);
		Collections.sort(result,comparator2);
//		list.addAll(result);
		return result;
	}


	public void execute(HttpServletRequest request, Result result) {
	}


	public void validate(InformeFilterForm form, ActionErrors errors) {
		 String before = form.getFilterAsString("before");
		 if(!FontarValidation.isDate(before,true))
			 errors.add("before", new ActionMessage("El campo desde no es válido", false));
		 
		 String after = form.getFilterAsString("after");
		 if(!FontarValidation.isDate(after,true))
			 errors.add("after", new ActionMessage("El campo hasta no es válido", false));

	}

	protected void setPresentacionFilter(DetachedCriteria criteria, String after, String before){
		try{
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaIngreso").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after))
				criteria.add(Property.forName("fechaIngreso").le( DateTimeUtil.getDate(after)));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected void setResolucionFilter(DetachedCriteria criteria, String after, String before){
		try{
			if(!Util.isBlank(before))
				criteria.add(Property.forName("fechaResolucion").ge( DateTimeUtil.getDate(before)) );
			if(!Util.isBlank(after))
				criteria.add(Property.forName("fechaResolucion").le( DateTimeUtil.getDate(after)));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	private class ProyectoEntidadIntervinientes implements Transformer {

		public Object transform(Object object) {
			BitacoraBean bitacoraBean = (BitacoraBean) object;
			ProyectoIntervinientesDTO dto = new ProyectoIntervinientesDTO();
			ProyectoBean proyecto = this.getProyectoDao().read(bitacoraBean.getIdProyecto());
			EntidadIntervinientesBean bean = this.getEntidadInterviniebtesDao().read(bitacoraBean.getId());
			dto.setDenominacion(this.getEntidadDao().read(bean.getIdEntidad()).getDenominacion());
			dto.setCodigo(proyecto.getInstrumento().getDenominacion());
			if (proyecto.getProyectoDatos().getCiiu() != null) {
				dto.setCodigoCiiu(proyecto.getProyectoDatos().getCiiu().getCodigo());
			}
			dto.setEntidadBeneficiaria(proyecto.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
			dto.setEstado(proyecto.getEstado().getDescripcion());
			dto.setIdentificador(proyecto.getCodigo());
			dto.setJurisdiccion(proyecto.getProyectoJurisdiccion().getJurisdiccion().getCodigo());
			if (proyecto.getProyectoPresupuesto() != null) {
				dto.setMontoFontar(proyecto.getProyectoPresupuesto().getMontoSolicitado());
			}
			if (proyecto.getProyectoPresupuesto() != null) {
				dto.setMontoTotal(proyecto.getProyectoPresupuesto().getMontoTotal());
			}
			dto.setTitulo(proyecto.getProyectoDatos().getTitulo());
			return dto;
		}
		
		
		private ProyectoDAO getProyectoDao(){
			return (ProyectoDAO) ContextUtil.getBean("proyectoDao");
			
		}

		private EntidadIntervinientesDAO getEntidadInterviniebtesDao(){
			return (EntidadIntervinientesDAO) ContextUtil.getBean("entidadIntervinientesDao");
			
		}

		private EntidadDAO getEntidadDao(){
			return (EntidadDAO) ContextUtil.getBean("entidadDao");
			
		}
	}

}

	
