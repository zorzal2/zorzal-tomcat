package com.fontar.consultas.reportes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Property;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteProyectoResolver;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.web.action.consultas.Result;

public class ReporteConsejeriasDatosResolver extends ReporteProyectoResolver implements Resolver {
	
	@Override
	public void setCriteria(InformeFilterForm form, Session session, Criteria proyecto, Criteria proyectoDatos) {
				
		proyecto.add(Property.forName("proyectoPresupuesto").isNotNull());		
		proyecto.add(Property.forName("fechaResolucion").isNotNull());		
		proyecto.add(Expression.sqlRestriction("TP_PROYECTO = 'P'"));
		proyecto.add(Property.forName("instrumento").isNotNull());		
		Criteria instrumento = proyecto.createCriteria("instrumento");
		Criteria matrizPresupuesto = instrumento.createCriteria("matrizPresupuesto");
		matrizPresupuesto.add(Expression.sqlRestriction("TP_MATRIZ in ('CF_CONSEJERIAS','CONSEJERIAS')"));
	}

	@Override
	public Collection result(HttpServletRequest request) {
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		Collection proyectos = new ArrayList();
		ConsejeriasDatos datos = new ConsejeriasDatos();
		for (Object object : result.getList()) {
			proyectos = datos.llenar(proyectos,(ProyectoBean)object);
		}
		Comparator comparator2 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				ConsejeriasDatosDTO dto1 = (ConsejeriasDatosDTO) arg0;
				ConsejeriasDatosDTO dto2 = (ConsejeriasDatosDTO) arg1;
				return dto1.getIdentifier().compareToIgnoreCase(dto2.getIdentifier());
			}};

		Comparator comparator3 = new Comparator(){
			public int compare(Object arg0, Object arg1) {
				ConsejeriasDatosDTO dto1 = (ConsejeriasDatosDTO) arg0;
				ConsejeriasDatosDTO dto2 = (ConsejeriasDatosDTO) arg1;
				return dto1.getNombre().compareToIgnoreCase(dto2.getNombre());
			}};

		Collections.sort((List<ConsejeriasDatosDTO>)proyectos,comparator3);
		Collections.sort((List<ConsejeriasDatosDTO>)proyectos,comparator2);
		return proyectos;
	}


	@Override
	public void setInitialContextImpl(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
	
}






