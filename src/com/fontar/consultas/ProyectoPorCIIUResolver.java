package com.fontar.consultas;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.util.Util;

public class ProyectoPorCIIUResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {

		//Filtro por CIIU
		String text = (String)form.getFilter("txtCiiu");
		
		if (!Util.isBlank(text)) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CiiuBean.class);
			detachedCriteria.setProjection( Property.forName("id") );
			detachedCriteria.add(Property.forName("codigo").like(text + "%"));
			
			Collection idList = detachedCriteria.getExecutableCriteria(session).list();
			if (idList.isEmpty())
				idList.add(new Long(-1));
			
			proyectoDatos.add(Property.forName("idCiiu").in(idList ));
			Criteria ciiuCriteria = proyectoDatos.createCriteria("ciiu");
			ciiuCriteria.addOrder(Order.asc("codigo"));
			Criteria beneficiariaCriteria = proyectoDatos.createCriteria("entidadBeneficiaria");
			Criteria entidadCriteria = beneficiariaCriteria.createCriteria("entidad");
			entidadCriteria.addOrder(Order.asc("denominacion"));
			proyecto.addOrder(Order.asc("codigo"));
		}
	}
	
	
}
