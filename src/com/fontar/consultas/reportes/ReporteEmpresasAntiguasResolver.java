package com.fontar.consultas.reportes;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericTypeValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.EntidadBeneficiariaBean;
import com.fontar.util.Util;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringUtil;

public class ReporteEmpresasAntiguasResolver implements Resolver {

	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public void setInitialContext(HttpServletRequest request) {
		
	}

	public Map getParameters(HttpServletRequest request) {
		return new HashMap();
	}

	public Collection result(HttpServletRequest request) {
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		return result.getList();
	}

	public void execute(HttpServletRequest request, Result result) {
		
		InformeFilterForm filtersForm = (InformeFilterForm) request.getAttribute("reporteFiltersForm");
		Session session = this.getSessionFactory().openSession();
		
		Criteria beneficiariaCriteria = session.createCriteria(EntidadBeneficiariaBean.class);
		
		// Filtro de antigüedad minima de la entidad beneficiaria (en años)
		String strAntiguedadDesde = (String) filtersForm.getFilter("antiguedadDesde");
				
		int antiguedad = 0;
		
		if (!Util.isBlank(strAntiguedadDesde)) {
			antiguedad = Integer.parseInt(strAntiguedadDesde);		
		}
		
		// Resto a la fecha actual tantos años como haya ingresado el usuario
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -antiguedad);
		
		// Me quedo con aquellas entidades que fueron creadas en el rango pedido
		// beneficiariaCriteria.add(Property.forName("fechaInicioActividad").le(c.getTime()));
		beneficiariaCriteria.add(Expression.le("numeroConstitucion", c.get(Calendar.YEAR)));
		
		Criteria entidadCriteria = beneficiariaCriteria.createCriteria("entidad");
		entidadCriteria.addOrder(Order.asc("denominacion"));
				
		result.init(entidadCriteria);
	}

	public void validate(InformeFilterForm form, ActionErrors errors) {
		 String anio = form.getFilterAsString("antiguedadDesde");
		 if (StringUtil.isNotEmpty(anio)) {
			 if (GenericTypeValidator.formatLong(anio) == null)
				 errors.add("antiguedadDesde", new ActionMessage("El campo Año no es válido", false));
			 else if ((new Long(anio)) < 0)
				 errors.add("antiguedadDesde", new ActionMessage("El campo Año no es válido", false));
		 }
		 else 
			 errors.add("antiguedadDesde", new ActionMessage("Falta ingresar un dato al campo Año", false));
	}
	
	

		
}






