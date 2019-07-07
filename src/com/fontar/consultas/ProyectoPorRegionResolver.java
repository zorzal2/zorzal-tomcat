package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import com.fontar.data.api.dao.RegionDAO;
import com.fontar.data.impl.domain.bean.RegionBean;
import com.fontar.util.Util;
import com.pragma.util.ContextUtil;

public class ProyectoPorRegionResolver extends ConsultaProyectoResolver implements Resolver {

	
	public void setInitialContextImpl(HttpServletRequest request) {
		request.setAttribute("regiones", this.getCollectionHandler().getRegiones());
	}

	private RegionBean getRegion(InformeFilterForm filtersForm){
		String region = (String) filtersForm.getFilter("region");
		return !Util.isBlank(region)  ?  ( (RegionDAO)ContextUtil.getBean("regionDao") ).read(Long.valueOf(region)) : null;
	}


	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {

		//filtro por region
		RegionBean region = this.getRegion(form);
		if(region!=null){
			Criteria jurisdiccion = proyecto.createCriteria("proyectoJurisdiccion").createCriteria("jurisdiccion");
			jurisdiccion.add(Property.forName("region").eq(region));
			Criteria orderRegion = jurisdiccion.createCriteria("region");
			orderRegion.addOrder( Order.desc("nombre") );
		}
		
		proyecto.addOrder(Order.desc("codigo"));
	}

	
	
	
}
