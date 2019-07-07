package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.consultas.reportes.wrapper.ReporteDeProyectoConFechaYDesembolsoWrapper;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringPattern;
import com.pragma.util.StringUtil;

public class ReporteProyectosConDemoraEnDesembolsosResolver implements Resolver {

	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public void setInitialContext(HttpServletRequest request) {
		request.setAttribute("llamadosAConvocatoria", CollectionHandler.defaultInstance().getLlamadosAConvocatoriaDeCredito());
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

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, Result result) {
		
		String idInstrumento = String.valueOf(((ReporteFilterForm)request.getAttribute("reporteFiltersForm")).getFilter("llamadoAConvocatoria"));
		
		String filtroPorInstrumento;
		if(StringUtil.isEmpty(idInstrumento)) {
			filtroPorInstrumento = "";
		} else {
			filtroPorInstrumento = "d.proyecto.idInstrumento="+idInstrumento+" and";
		}
		
		Session session = this.getSessionFactory().openSession();
		
		StringPattern query = new StringPattern(
				"select new com.fontar.consultas.reportes.wrapper.ReporteDeProyectoConFechaYDesembolsoWrapper(d.proyecto, a.fechaPago, d)" +
				"from ProyectoDesembolsoBean d, ProyectoDesembolsoBean a " +
				"where " +
				"	a.esAnticipo = true and " +				//a es Anticipo
				"	a.fechaPago is not null and " +			//a esta Pagado
				"	a.proyecto.codigoEstado not in ('ANULADO', 'CERRADO', 'FINALIZADO') and "+ //El proyecto esta activo
				"	d.esAnticipo = false and " +			//d no es un anticipo
				"	d.fechaPago is null and " +				//d no esta pagado todavia
				"	a.idProyecto = d.idProyecto and " +		//a y d pertenecen al mismo proyecto
				"	${filtroPorInstrumento} " +				//filtro opcional sobre el proyecto
				"	(a.fechaPago + d.plazo) < :now");		//El plazo de pago de d con respecto a la fecha de pago de a vencio
		
		query.set("filtroPorInstrumento", filtroPorInstrumento);
		
		List<ReporteDeProyectoConFechaYDesembolsoWrapper> resultados = session.createQuery(query.toString()).setDate("now", new Date()).list();
		
		result.init(resultados, resultados.size());
	}


	public void validate(InformeFilterForm form, ActionErrors errors) {
	}

}