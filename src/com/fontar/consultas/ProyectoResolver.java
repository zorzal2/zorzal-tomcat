package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class ProyectoResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		proyecto.addOrder(Order.asc("codigo"));
	}
	
	
}
