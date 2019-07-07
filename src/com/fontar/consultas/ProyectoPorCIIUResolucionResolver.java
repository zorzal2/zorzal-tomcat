package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

public class ProyectoPorCIIUResolucionResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
//		proyecto.add(Expression.not(Expression.eq( "codigoEstado", EstadoProyecto.CERRADO.getName() ) ));
		proyecto.add(Property.forName("fechaResolucion").isNotNull());
		
		Criteria ciiuCriteria = proyectoDatos.createCriteria("ciiu");
		ciiuCriteria.addOrder(Order.asc("codigo"));
		Criteria instrumentoCriteria = proyecto.createCriteria("instrumento");
		instrumentoCriteria.addOrder(Order.asc("denominacion"));
		proyecto.addOrder(Order.asc("codigo"));
	}
	
	
}
