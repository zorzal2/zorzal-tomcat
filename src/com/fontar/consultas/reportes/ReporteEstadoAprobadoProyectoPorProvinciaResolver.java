package com.fontar.consultas.reportes;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.util.Util;

public class ReporteEstadoAprobadoProyectoPorProvinciaResolver extends ReporteProyectoResolver implements Resolver {

	public void setInitialContextImpl(HttpServletRequest request) {
		request.setAttribute("jurisdicciones", this.getCollectionHandler().getJurisdicciones());
	}


	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
		String jurisdiccion = (String) form.getFilter("jurisdiccion");
		proyecto.add(Property.forName("fechaResolucion").isNotNull());
		Criteria pjuriCriteria = proyecto.createCriteria("proyectoJurisdiccion");
		if (!Util.isBlank(jurisdiccion)) {
			pjuriCriteria.add(Expression.eq( "idJurisdiccion", new Long(jurisdiccion)));
		}
		Criteria jurisdiccionCriteria = pjuriCriteria.createCriteria("jurisdiccion");
		jurisdiccionCriteria.addOrder(Order.asc("descripcion"));
		proyecto.addOrder(Order.asc("codigo"));
		
	}

}

	
