package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fontar.util.Util;

public class ProyectoPorProvinciaResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
		request.setAttribute("jurisdicciones", this.getCollectionHandler().getJurisdicciones());
	}

//	private CiiuBean getCiius(InformeFilterForm filtersForm){
//		String ciiu = (String) filtersForm.getFilter("ciiu");
//		return !Util.isBlank(ciiu)  ?  ( (CiiuDAO)ContextUtil.getBean("ciiuDao") ).read(Long.valueOf(ciiu)) : null;
//	}
//

	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
		
//		proyecto.add(Expression.not(Expression.eq( "codigoEstado", EstadoProyecto.CERRADO.getName() ) ));
		String jurisdiccion = (String) form.getFilter("jurisdiccion");

		if (!Util.isBlank(jurisdiccion)) {
			Criteria pjuriCriteria = proyecto.createCriteria("proyectoJurisdiccion");
			pjuriCriteria.add(Expression.eq( "idJurisdiccion", new Long(jurisdiccion)));
			Criteria jurisdiccionCriteria = pjuriCriteria.createCriteria("jurisdiccion");
			jurisdiccionCriteria.addOrder(Order.asc("descripcion"));
//			Criteria codInstrumentoCriteria = proyecto.createCriteria("proyectoRaiz");
			Criteria instrumentoCriteria = proyecto.createCriteria("instrumento");
			instrumentoCriteria.addOrder(Order.asc("denominacion"));
			proyecto.addOrder(Order.asc("codigo"));
		}
	}
	
}
