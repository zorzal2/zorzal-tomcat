package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.util.FontarValidation;
import com.fontar.util.Util;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

public class ReporteEstadoSituacionIdeaProyectoResolver implements Resolver {

	public void setInitialContextImpl(HttpServletRequest request) {
	}


	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {

		proyecto.add(Expression.ne("codigoEstado",EstadoIdeaProyecto.ANULADO.getName()));
		proyecto.addOrder(Order.asc("codigo"));
		proyectoDatos.addOrder(Order.asc("titulo"));
	}


	public void setInitialContext(HttpServletRequest request) {
		this.setInitialContextImpl(request);
	}


	public Map getParameters(HttpServletRequest request) {
		return new HashMap();
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
	
	public Collection result(HttpServletRequest request) {
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Session session = this.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(IdeaProyectoBean.class);
		
		//Filtro por fecha
		String before = (String) filtersForm.getFilter("before");
		String after = (String) filtersForm.getFilter("after");
		
		Criteria proyectoDatosCriteria = criteria.createCriteria("proyectoDatos");
		
		this.setPresentacionFilter(proyectoDatosCriteria,after,before);
		
		
		this.setCriteria(filtersForm,session,criteria,proyectoDatosCriteria);
		
		List result = criteria.list();
		CollectionUtils.transform(result, new IdeaProyectoListado());
		Comparator comparator2 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IdeaProyectoListadoDTO dto1 = (IdeaProyectoListadoDTO) arg0;
				IdeaProyectoListadoDTO dto2 = (IdeaProyectoListadoDTO) arg1;
				return dto1.getNroIdeaProyecto().compareToIgnoreCase(dto2.getNroIdeaProyecto());
			}};
			
		Comparator comparatorTitulo = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IdeaProyectoListadoDTO dto1 = (IdeaProyectoListadoDTO) arg0;
				IdeaProyectoListadoDTO dto2 = (IdeaProyectoListadoDTO) arg1;
				if (dto1.getTitulo() == null) {
					return -1;
				}
				if (dto2.getTitulo() == null) {
					return 1;
				}
				return dto1.getTitulo().compareToIgnoreCase(dto2.getTitulo());
			}};
				
		Collections.sort(result,comparatorTitulo);
		Collections.sort(result,comparator2);
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

	private class IdeaProyectoListado implements Transformer {

		public Object transform(Object object) {
			IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) object;
			IdeaProyectoListadoDTO dto = new IdeaProyectoListadoDTO();
			List<ProyectoBean> list = this.getProyectoDao().findByProyectoOriginal(ideaProyectoBean.getId());
			if (!list.isEmpty()) {
				ProyectoBean proyecto = list.get(0);
				if (proyecto.getFechaResolucion() != null ){
					dto.setFechaResolucion(StringUtil.formatDate(proyecto.getFechaResolucion()));
				}
				dto.setInstrumentoAs(proyecto.getInstrumento().getDenominacion());
				if (proyecto.getCodigoResolucion() != null) {
					dto.setResolucion(proyecto.getCodigoResolucion());
				}
			}
			dto.setEntidadBeneficiaria(ideaProyectoBean.getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
			dto.setEstado(ideaProyectoBean.getEstado().getDescripcion());
			String fecha = StringUtil.formatDate(ideaProyectoBean.getProyectoDatos().getFechaIngreso());
			dto.setFecha(fecha);
			if (ideaProyectoBean.getProyectoDatos().getInstrumentoSolicitado() != null) {
				dto.setInstrumentoSolicitado(ideaProyectoBean.getProyectoDatos().getInstrumentoSolicitado());
			}
			
			if (ideaProyectoBean.getProyectoJurisdiccion() != null) {
				dto.setLocalizacion(ideaProyectoBean.getProyectoJurisdiccion().getJurisdiccion().getCodigo());
			}
			if (ideaProyectoBean.getProyectoPresupuesto() != null) {
				if (ideaProyectoBean.getProyectoPresupuesto().getMontoSolicitado() != null) {
					dto.setMontoFontar(ideaProyectoBean.getProyectoPresupuesto().getMontoSolicitado());
				}
			}
			if (ideaProyectoBean.getProyectoPresupuesto() != null) {
				if (ideaProyectoBean.getProyectoPresupuesto().getMontoTotal() != null) {
					dto.setMontoTotal(ideaProyectoBean.getProyectoPresupuesto().getMontoTotal());
				}
			}
			dto.setNroIdeaProyecto(ideaProyectoBean.getCodigo());
			dto.setOficial(this.getEvaluador(ideaProyectoBean));
			dto.setTitulo(ideaProyectoBean.getProyectoDatos().getTitulo());
			return dto;
		}
		
		
		private ProyectoDAO getProyectoDao(){
			return (ProyectoDAO) ContextUtil.getBean("proyectoDao");
			
		}
		
		private PersonaDAO getPersonaDao(){
			return (PersonaDAO) ContextUtil.getBean("personaDao");
			
		}
		
		private EvaluacionEvaluadorDAO getEvaluacionEvaluadorDao(){
			return (EvaluacionEvaluadorDAO) ContextUtil.getBean("evaluacionEvaluadorDao");
			
		}
		
		private String getEvaluador(IdeaProyectoBean bean) {
			Set<EvaluacionBean> setEval = bean.getEvaluaciones();
			StringBuffer link = new StringBuffer();
			for (EvaluacionBean evaluacionBean : setEval) {
				List<EvaluacionEvaluadorBean> eeList = this.getEvaluacionEvaluadorDao().findByEvaluacion(evaluacionBean.getId());
				for (EvaluacionEvaluadorBean evaluadorBean : eeList) {
					if (evaluadorBean.getEvaluador()!=null) {
						Long idPersona = new Long(evaluadorBean.getEvaluador());
						PersonaBean persona = this.getPersonaDao().read(idPersona);
						if (persona != null) {
							link.append(persona.getNombre());
							link.append('\n');
//							link.append("<br>");
						}
					}
				}
			}
			return link.toString();
		}

	}

}

	
