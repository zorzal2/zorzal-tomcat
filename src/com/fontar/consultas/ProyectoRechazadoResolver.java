package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;

public class ProyectoRechazadoResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		proyecto.add(Property.forName("recomendacionFinal").eq(Recomendacion.RECHAZADO));
		Criteria beneficiariaCriteria = proyectoDatos.createCriteria("entidadBeneficiaria");
		Criteria entidadCriteria = beneficiariaCriteria.createCriteria("entidad");
		entidadCriteria.addOrder(Order.asc("denominacion"));
		Criteria instrumentoCriteria = proyecto.createCriteria("instrumento");
		instrumentoCriteria.addOrder(Order.asc("denominacion"));
		proyecto.addOrder(Order.asc("codigo"));
	}
	
	
}
