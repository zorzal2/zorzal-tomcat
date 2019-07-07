package com.fontar.consultas.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import com.fontar.consultas.reportes.wrapper.ReporteDeProyectoConMontoWrapper;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringUtil;

public class ReporteProyectosConRendicionPendienteResolver implements Resolver {

	private SessionFactory sessionFactory;
	
	private static final BigDecimal TOLERANCIA = new BigDecimal(0.05); //5 centavos
	
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

	public void execute(HttpServletRequest request, Result result) {
		
		String idInstrumento = String.valueOf(((ReporteFilterForm)request.getAttribute("reporteFiltersForm")).getFilter("llamadoAConvocatoria"));
		
		Session session = this.getSessionFactory().openSession();
		//Obtengo la parte del total rendido aprobado/gestionado por proyecto
		Map<Long, BigDecimal> montoParteRendidoTotal = montoParteRendidoTotal(idInstrumento, session);
		//Proyectos sin desembolsos pendientes con el monto pagado
		Map<Long, BigDecimal> montoDesembolsadoTotal = montoDesembolsadoTotal(idInstrumento, session);
		//Proyectos
		List<ProyectoBean> proyectos = proyectosOrdenados(idInstrumento, session);
		
		List<ReporteDeProyectoConMontoWrapper> resultados = new ArrayList<ReporteDeProyectoConMontoWrapper>(montoDesembolsadoTotal.size());
		//Filtro los proyectos que tienen un monto desembolsado mayor al monto rendido
		for(ProyectoBean proyecto : proyectos) {
			BigDecimal montoDesembolsado = montoDesembolsadoTotal.get(proyecto.getId());
			if(montoDesembolsado!=null) {
				BigDecimal montoRendido = montoParteRendidoTotal.get(proyecto.getId());
				//Si no tiene rendiciones el monto rendido es 0
				BigDecimal montoPendienteDeRendicion;
				if(montoRendido==null) {
					montoPendienteDeRendicion = montoDesembolsado;
				} else {
					montoPendienteDeRendicion = montoDesembolsado.subtract(montoRendido);
				}
				//Comparo con una tolerancia por temas de redondeo
				if(montoPendienteDeRendicion.compareTo(TOLERANCIA) > 0) {
					resultados.add(new ReporteDeProyectoConMontoWrapper(proyecto, montoPendienteDeRendicion));
				}
			}
		}		
		result.init(resultados, resultados.size());
	}

	@SuppressWarnings("unchecked")
	private Map<Long, BigDecimal> montoDesembolsadoTotal(String idInstrumento, Session session) {
		Map<Long, BigDecimal> montoDesembolsadoTotal = new HashMap<Long, BigDecimal>();
		String sQuery =
			"select " +
			"	p.id," +
			"	sum(d2.montoDesembolsado) " +
			"from " +
			"	ProyectoBean p, " +
			"	ProyectoDesembolsoBean d2 " +
			"where	" +
			"	p.id not in (select d.idProyecto from ProyectoDesembolsoBean d where d.montoDesembolsado is null) and " +
			"	p.codigoEstado not in ('ANULADO', 'CERRADO', 'FINALIZADO') and " +
			(StringUtil.isEmpty(idInstrumento)? "" : ("p.idInstrumento = "+idInstrumento+" and ")) +
			"	d2.idProyecto = p.id " +
			"group by p.id ";
		
		List<Object[]> desembolsoPorProyecto = session.createQuery(sQuery).list();
		
		for(Object[] tupla : desembolsoPorProyecto) {
			Long proyecto = (Long) tupla[0];
			BigDecimal totalDesembolsado = (BigDecimal) tupla[1];
			montoDesembolsadoTotal.put(proyecto, totalDesembolsado);
		}
		return montoDesembolsadoTotal;
	}

	@SuppressWarnings("unchecked")
	private List<ProyectoBean> proyectosOrdenados(String idInstrumento, Session session) {
		String sQuery =
			"select " +
			"	p " +
			"from " +
			"	ProyectoBean p " +
			"where	" +
			"	p.id not in (select d.idProyecto from ProyectoDesembolsoBean d where d.montoDesembolsado is null) and " +
			(StringUtil.isEmpty(idInstrumento)? "" : ("p.idInstrumento = "+idInstrumento+" and "))+
			"	p.codigoEstado not in ('ANULADO', 'CERRADO', 'FINALIZADO') " +
			"order by p.codigo";
		
		return session.createQuery(sQuery).list();
	}

	@SuppressWarnings("unchecked")
	private Map<Long, BigDecimal> montoParteRendidoTotal(String idInstrumento, Session session) {
		Map<Long, BigDecimal> montoParteRendidoTotal = new HashMap<Long, BigDecimal>();
		
		
		/*
		 * Id de proyectos sin desembolsos pendientes de pago y con alguna rendicion, monto parte total rendido autorizado
		 * Los proyectos que no aparecen tienen una rndicion aprobada de 0
		 */
		String sQuery =  
			"select "+ 
			"	p.id, "+
			"	sum(case when r.montoParteGestion is not null then r.montoParteGestion else r.montoParteEvaluacion end) "+
			"from "+
			"	ProyectoBean p, "+
			"	RendicionCuentasBean r "+
			"where " +
			"	r.seguimiento.proyecto.id = p.id and " +
			"	r.seguimiento.estado not in ('ANULADO', 'RECHAZADO', 'CERRADO', 'NO_GESTIONADO') and "+			
			"	(r.montoParteEvaluacion is not null or r.montoParteGestion is not null) and " +
			"	p.codigoEstado not in ('ANULADO', 'CERRADO', 'FINALIZADO') and " +
			(StringUtil.isEmpty(idInstrumento)? "" : ("p.idInstrumento = "+idInstrumento+" and ")) +
			"	p.id not in (select d.idProyecto from ProyectoDesembolsoBean d where d.fechaPago is null) " +
			"group by p.id ";
		
		List<Object[]> rendicionPorProyecto = session.createQuery(sQuery).list();
		
		for(Object[] tupla : rendicionPorProyecto) {
			Long idProyecto = (Long) tupla[0];
			BigDecimal totalRendido = (BigDecimal) tupla[1];
			montoParteRendidoTotal.put(idProyecto, totalRendido);
		}
		return montoParteRendidoTotal;
	}
	
	public void validate(InformeFilterForm form, ActionErrors errors) {
	}
}