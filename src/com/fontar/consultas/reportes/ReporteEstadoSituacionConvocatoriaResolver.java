package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.data.api.dao.LlamadoConvocatoriaDAO;
import com.fontar.data.impl.domain.bean.CambioEstadoProyecto;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.ContextUtil;

public class ReporteEstadoSituacionConvocatoriaResolver implements Resolver {

	private CollectionHandler collectionHandler = new CollectionHandler();

	
	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setInitialContext(HttpServletRequest request) {
		request.setAttribute("convocatorias", this.collectionHandler.getConvocatorias(false));
	}

	@SuppressWarnings("unchecked")
	public Map getParameters(HttpServletRequest request) {
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Long convocatoria = Long.valueOf( filtersForm.getFilterAsString("convocatoria"));
		LlamadoConvocatoriaDAO llamadoConvocatoriaDAO = (LlamadoConvocatoriaDAO) ContextUtil.getBean("llamadoConvocatoriaDao");
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = llamadoConvocatoriaDAO.read(convocatoria);
		Map parameters = new HashMap();
		parameters.put("convocatoria",llamadoConvocatoriaBean.getIdentificador());
		return parameters;
	}

	@SuppressWarnings("unchecked")
	public Collection result(HttpServletRequest request) {

		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Long convocatoria = Long.valueOf( filtersForm.getFilterAsString("convocatoria"));
		
		EstadoSituacionConvocatoriaDTO dto = new EstadoSituacionConvocatoriaDTO();
		
		
		Session session = this.sessionFactory.openSession();
		
				
		//Proyectos Cerrados
		Criteria criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("codigoEstado").eq(EstadoProyecto.CERRADO.getName()));
		dto.setCerrados( (Integer) criteria.uniqueResult() );

		
		//Proyectos Presentados
		//cantProyectosConv
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		dto.setPresentados( (Integer) criteria.uniqueResult() );

		
		//Proyectos en evaluacion
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		String[] estados = new String[]{	
					EstadoProyecto.EVALUACION.getName(),
					EstadoProyecto.CONT_COM.getName() ,
					EstadoProyecto.CONT_DIR_EVAL.getName(),
					EstadoProyecto.CONT_SEC.getName(),
				};
		criteria.add(Property.forName("codigoEstado").in(estados));
		dto.setEnEvaluacion( (Integer) criteria.uniqueResult() );
		
		//Admitidos
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("id").in( this.getNewCriteriaRegistrocambioEstadoProyecto(session,EstadoProyecto.ADMITIDO)));
		dto.setAdmitidos( (Integer) criteria.uniqueResult() );

		//Readmitidos
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("id").in( this.getNewCriteriaRegistrocambioEstadoProyecto(session,EstadoProyecto.NO_ADMITIDO)));
		criteria.add(Property.forName("id").in( this.getNewCriteriaRegistrocambioEstadoProyecto(session,EstadoProyecto.ADMITIDO)));
		dto.setReAdmitidos( (Integer) criteria.uniqueResult() );
		

		//Aprobados en primera instancia
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("fechaResolucion").isNotNull());
		criteria.add(Property.forName("estadoReconsideracion").eq(Boolean.FALSE));
		dto.setAprobadosEnPrimeraInstancia( (Integer) criteria.uniqueResult() );


		//Aprobados reconsiderados
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("fechaResolucion").isNotNull());
		criteria.add(Property.forName("estadoReconsideracion").eq(Boolean.TRUE));
		dto.setAprobadosReconsiderados( (Integer) criteria.uniqueResult() );

		//Rechazados en primera instancia 
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("recomendacionFinal").eq(Recomendacion.RECHAZADO));
		criteria.add(Property.forName("estadoReconsideracion").eq(Boolean.FALSE));
		dto.setRechazadosEnPrimeraInstancia( (Integer) criteria.uniqueResult() );
		

		//Rechazados reconsiderados 
		criteria = this.getNewCriteriaProyectos(session,convocatoria);
		criteria.add(Property.forName("recomendacionFinal").eq(Recomendacion.RECHAZADO));
		criteria.add(Property.forName("estadoReconsideracion").eq(Boolean.TRUE));
		dto.setRechazadosReconsiderados( (Integer) criteria.uniqueResult() );
		
		Collection result = new LinkedList();
		result.add( dto );
		return result;
	}
	
		
	public void execute(HttpServletRequest request, Result result) {
		// TODO Auto-generated method stub
		
	}
	
	public void validate(InformeFilterForm form, ActionErrors errors) {
		// TODO Auto-generated method stub
		
	}
	
	private Criteria getNewCriteriaProyectos(Session session, Long idConvocatoria){
		//Proyectos
		Criteria criteria = session.createCriteria(ProyectoBean.class);
		criteria.add(Expression.ne("codigoEstado",EstadoProyecto.ANULADO.getName()));
		//Filtro por convocatoria
		criteria.add(Property.forName("idInstrumento").eq(idConvocatoria));
		
		
		//Proyeccion de cantidad
		criteria.setProjection(Projections.rowCount());
		
		return criteria;
	}
	
	private DetachedCriteria getNewCriteriaRegistrocambioEstadoProyecto(Session session, EstadoProyecto estadoProyecto){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CambioEstadoProyecto.class);
		detachedCriteria.add(Property.forName("estadoFinal").eq(estadoProyecto));
		detachedCriteria.setProjection(Projections.property("idProyecto"));
		return detachedCriteria; 
	}

}

	
