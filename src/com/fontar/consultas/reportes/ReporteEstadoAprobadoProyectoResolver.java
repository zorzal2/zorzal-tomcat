package com.fontar.consultas.reportes;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;

public class ReporteEstadoAprobadoProyectoResolver extends ReporteProyectoResolver implements Resolver {

	public void setInitialContextImpl(HttpServletRequest request) {
	}


	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		proyecto.add(Property.forName("fechaResolucion").isNotNull());
		proyecto.addOrder(Order.asc("fechaResolucion"));	
		proyecto.addOrder(Order.asc("codigo"));	
	}

}

	
