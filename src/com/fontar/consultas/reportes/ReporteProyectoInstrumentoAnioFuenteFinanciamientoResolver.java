package com.fontar.consultas.reportes;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.web.action.consultas.Result;

public class ReporteProyectoInstrumentoAnioFuenteFinanciamientoResolver extends ReporteProyectoResolver implements Resolver {
	
	
	@Override
	/**
	 * Agrega información adicional en el request a la agregada por "setInitialContext" de ReporteProyectoResolver
	 */
	public void setInitialContextImpl(HttpServletRequest request) {
		
	}

	/**
	 * Agrega criterios adicionales a los creados en "execute" de ReporteProyectoResolver
	 */
	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		proyecto.add(Expression.sqlRestriction("TP_PROYECTO = 'P'"));
		proyecto.add(Expression.ne("codigoEstado",EstadoProyecto.ANULADO.getName()));
	}

	
	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, Result result) {
		
		SortedMap<String,ResumenProyectosDTO> indice = new TreeMap<String,ResumenProyectosDTO>();
		
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Session session = this.getSessionFactory().openSession();
		
		Criteria criteria = session.createCriteria(ProyectoBean.class);
		
		//Filtro por fecha
		String before = (String) filtersForm.getFilter("before");
		String after = (String) filtersForm.getFilter("after");
		String date = (String) filtersForm.getFilter("presentacionResolucion");
		
		Criteria proyectoDatosCriteria = criteria.createCriteria("proyectoDatos");
		
		if(date.equals("presentacion"))
			this.setPresentacionFilter(proyectoDatosCriteria,after,before);
		else
			this.setResolucionFilter(criteria,after,before);
		
		
		this.setCriteria(filtersForm,session,criteria,proyectoDatosCriteria);
		
		ProjectionList projections =  Projections.projectionList();
		projections.add(Projections.property("proyectoPresupuesto"));
		projections.add(Projections.property("instrumento")); 
		projections.add(Projections.property("proyectoDatos"));
		projections.add(Projections.property("fechaResolucion"));
		
		criteria.setProjection( projections );
		
		ScrollableResults results = criteria.scroll();
		Calendar calendar = Calendar.getInstance();
		
		while(results.next()){
			
			ProyectoPresupuestoBean presupuesto = (ProyectoPresupuestoBean) results.get(0);
			InstrumentoBean instrumento = (InstrumentoBean) results.get(1);
			ProyectoDatosBean datos = (ProyectoDatosBean) results.get(2);
			FuenteFinanciamientoBean fuente = instrumento.getInstrumentoDef().getFuenteFinanciamiento();
			Date fechaResolucion = (Date) results.get(3);
			calendar.setTime( datos.getFechaIngreso() );
			int year = calendar.get(Calendar.YEAR);
			
			String key = fuente.getIdentificador() + year + instrumento.getIdentificador();
			ResumenProyectosDTO resumen = (ResumenProyectosDTO) indice.get( key );
			if(resumen == null){
				resumen = new ResumenProyectosDTO();
				resumen.setFuenteFinanciamiento( fuente.getIdentificador() );
				resumen.setInstrumento( instrumento.getIdentificador() );
				resumen.setAnioPresentacion(year);
				indice.put(key,resumen);
			}
			
			resumen.update( presupuesto , fechaResolucion );
			
		}

		List list = new LinkedList(indice.values());
		result.init( list , list.size());
	}
	
	
		
}






