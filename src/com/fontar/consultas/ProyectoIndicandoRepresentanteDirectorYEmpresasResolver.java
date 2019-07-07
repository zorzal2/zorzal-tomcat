package com.fontar.consultas;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

public class ProyectoIndicandoRepresentanteDirectorYEmpresasResolver extends ConsultaProyectoResolver implements Resolver {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setInitialContextImpl(HttpServletRequest request) {
				
	}

	public Map getParameters(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		proyecto.addOrder(Order.asc("codigo"));
	}
	
}
