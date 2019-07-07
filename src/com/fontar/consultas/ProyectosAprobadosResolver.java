package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

public class ProyectosAprobadosResolver extends ConsultaProyectoResolver implements Resolver {

	
	@Override
	public void setInitialContextImpl(HttpServletRequest request) {
		
	}
	
	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		proyecto.add(Property.forName("fechaResolucion").isNotNull());
		proyecto.addOrder(Order.asc("codigo"));		
	}
	
}
