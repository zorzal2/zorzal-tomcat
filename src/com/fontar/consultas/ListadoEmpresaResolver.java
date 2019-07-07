package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class ListadoEmpresaResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
	}

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		Criteria beneficiariaCriteria = proyectoDatos.createCriteria("entidadBeneficiaria");
		Criteria entidadCriteria = beneficiariaCriteria.createCriteria("entidad");
		entidadCriteria.addOrder(Order.asc("denominacion"));
		Criteria pjuriCriteria = proyecto.createCriteria("proyectoJurisdiccion");
		Criteria jurisdiccionCriteria = pjuriCriteria.createCriteria("jurisdiccion");
		jurisdiccionCriteria.addOrder(Order.asc("descripcion"));
		proyecto.addOrder(Order.asc("codigo"));
	}
	
	
}
