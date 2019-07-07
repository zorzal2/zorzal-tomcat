package com.fontar.consultas.reportes;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.CambioEstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;

public class ReporteProyectosAdmitidosResolver extends ReporteProyectoResolver implements Resolver {
	
	
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
				
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CambioEstadoProyecto.class);
		detachedCriteria.add(Property.forName("estadoFinal").eq(EstadoProyecto.ADMITIDO));
		detachedCriteria.setProjection(Property.forName("idProyecto"));
		proyecto.add(Property.forName("id").in( detachedCriteria ) );

		proyecto.addOrder(Order.asc("codigo"));


	}

	
		
}






