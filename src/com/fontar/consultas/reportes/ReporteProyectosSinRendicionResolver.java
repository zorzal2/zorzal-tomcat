package com.fontar.consultas.reportes;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringUtil;

public class ReporteProyectosSinRendicionResolver implements Resolver {
	
	private static int ATRASO_MAXIMO_EN_MESES = 6;

	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public void setInitialContext(HttpServletRequest request) {
		request.setAttribute("llamadosAConvocatoria", CollectionHandler.defaultInstance().getLlamadosAConvocatoria());
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
		
		String idInstrumento = String.valueOf(((ReporteFilterForm)request.getAttribute("reporteFiltersForm")).getFilter("llamadoAConvocatoria"));
		
		Session session = this.getSessionFactory().openSession();
		
		//Fecha de contrato minima para no considerar demorado al proyecto
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, -ATRASO_MAXIMO_EN_MESES); 
		Date hace6Meses = cal.getTime(); 
		
		//No me interesan los proyectos anulados, cerrados, finalizados o con rendiciones.
		String idsDeProyectosQueNoInteresan = 
			"select s.idProyecto " +
			"from SeguimientoBean s " +
			"where " +
			"	s.proyecto.codigoEstado in ('ANULADO', 'CERRADO', 'FINALIZADO') or " +
			"	(s.id in (select r.idSeguimiento from RendicionCuentasBean r) and " +
			"	s.estado <> 'ANULADO')";
		
		//proyectos con contrato firmado hace mas de 6 meses que no tienen seguimientos con
		//rendiciones asociados
		String proyectosConRendicionAtrasada = 
			"select o from ProyectoBean o " +
			"where o.fechaFirmaDeContrato is not null " +
			"  and o.fechaFirmaDeContrato < :hace6Meses " +
			(StringUtil.isEmpty(idInstrumento)? "" : "and o.idInstrumento = :idInstrumento") +
			"  and o.id not in ("+idsDeProyectosQueNoInteresan+") " +
			"order by o.codigo"; 
		
		Query query = session.createQuery(proyectosConRendicionAtrasada);
		query.setParameter("hace6Meses", hace6Meses);
		
		if(!StringUtil.isEmpty(idInstrumento)) {
			query.setParameter("idInstrumento", new Long(idInstrumento));
		}
		
		List list = query.list();

		result.init(list, list.size());
	}

	public void validate(InformeFilterForm form, ActionErrors errors) {
	}

}






