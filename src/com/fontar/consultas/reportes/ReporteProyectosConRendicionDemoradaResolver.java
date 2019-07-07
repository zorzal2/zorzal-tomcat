package com.fontar.consultas.reportes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.consultas.reportes.wrapper.ReporteDeProyectoConFechaWrapper;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringPattern;
import com.pragma.util.StringUtil;

public class ReporteProyectosConRendicionDemoradaResolver implements Resolver {
	
	private static int ATRASO_MAXIMO_EN_MESES = 4;

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

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, Result result) {
		
		String idInstrumento = String.valueOf(((ReporteFilterForm)request.getAttribute("reporteFiltersForm")).getFilter("llamadoAConvocatoria"));
		
		String filtroPorInstrumento;
		if(StringUtil.isEmpty(idInstrumento)) {
			filtroPorInstrumento = "";
		} else {
			filtroPorInstrumento = "and s.proyecto.idInstrumento="+idInstrumento;
		}
		
		StringPattern queryProyectosEnSeguimientoConAlMenosUnaRendicion = new StringPattern(
				"select " +
				"	s.proyecto " +
				"from " +
				"	SeguimientoBean s " +
				"where " +
				"	s.estado <> 'ANULADO' " +
				"	and s.proyecto.codigoEstado='SEGUIMIENTO' " +
				"	and size(s.rendiciones)>0 " +
				"	${filtroPorInstrumento} " +
				"order by s.proyecto.codigo");
		queryProyectosEnSeguimientoConAlMenosUnaRendicion.set("filtroPorInstrumento", filtroPorInstrumento);
		

		Session session = this.getSessionFactory().openSession();
		List<ProyectoBean> proyectosEnSeguimientoConAlMenosUnaRendicion = session.createQuery(queryProyectosEnSeguimientoConAlMenosUnaRendicion.toString()).list();
		
		List<ReporteDeProyectoConFechaWrapper> resultados = new ArrayList<ReporteDeProyectoConFechaWrapper>(proyectosEnSeguimientoConAlMenosUnaRendicion.size());
		Set<Long> idsDeProyectosEnResultado = new HashSet<Long>();
		
		for(ProyectoBean candidato : proyectosEnSeguimientoConAlMenosUnaRendicion) {
			if(!idsDeProyectosEnResultado.contains(candidato.getId())){
				Date fechaDeUltimaRendicion = fechaDeUltimaRendicion(candidato);
				if(esAntigua(fechaDeUltimaRendicion)) {
					resultados.add(new ReporteDeProyectoConFechaWrapper(candidato, fechaDeUltimaRendicion));
					idsDeProyectosEnResultado.add(candidato.getId());
				}
			}
		}		
		result.init(resultados, resultados.size());
	}

	private boolean esAntigua(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -ATRASO_MAXIMO_EN_MESES); 
		return date.before(cal.getTime());
	}

	private Date fechaDeUltimaRendicion(ProyectoBean candidato) {
		Date fechaDeUltimaRendicion = null;
		for(SeguimientoBean seguimiento : candidato.getSeguimientos()) {
			for(RendicionCuentasBean rendicion : seguimiento.getRendiciones()) {
				if(fechaDeUltimaRendicion == null) fechaDeUltimaRendicion = rendicion.getFecha();
				else {
					if(fechaDeUltimaRendicion.before(rendicion.getFecha())) {
						fechaDeUltimaRendicion = rendicion.getFecha();
					}
				}
			}
		}
		return fechaDeUltimaRendicion;
	}

	public void validate(InformeFilterForm form, ActionErrors errors) {
	}

}