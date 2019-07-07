package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.web.action.consultas.Result;
import com.pragma.util.StringPattern;
import com.pragma.util.StringUtil;
/**
 * Modelo de resolver que puede usarse como referencia para crear nuevos reportes.
 * @author llobeto
 *
 */
public class ReporteFacturacionPorEmpresaResolver implements Resolver {
	
	private static final String COLECCION_DE_LLAMADOS_A_CONVOCATORIA = "llamadosAConvocatoria";
	private static final String COLECCION_DE_ANIOS_DE_FACTURACION = "aniosDeFacturacion";

	private static final String FILTRO_LLAMADO_A_CONVOCATORIA = "llamadoAConvocatoria";
	private static final String FILTRO_ANIO_DE_FACTURACION = "anioDeFacturacion";
	private static final String FILTRO_DE_ENTIDAD_BENEFICIARIA = "entidadBeneficiaria";

	private SessionFactory sessionFactory;
	
	
	public void setInitialContext(HttpServletRequest request) {
		request.setAttribute(COLECCION_DE_LLAMADOS_A_CONVOCATORIA, CollectionHandler.defaultInstance().getLlamadosAConvocatoria());
		request.setAttribute(COLECCION_DE_ANIOS_DE_FACTURACION, CollectionHandler.defaultInstance().getAñoDeFacturacion());
	}

	public Collection result(HttpServletRequest request) {
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		return result.getList();
	}

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, Result result) {
		
		String idLlamadoAConvocatoria = filterValue(request, FILTRO_LLAMADO_A_CONVOCATORIA);
		String nombreDeEntidadBeneficiaria =  filterValue(request, FILTRO_DE_ENTIDAD_BENEFICIARIA);
		String añoDeFacturacion =  filterValue(request, FILTRO_ANIO_DE_FACTURACION);
		
		String query = crearQuery(nombreDeEntidadBeneficiaria, añoDeFacturacion, idLlamadoAConvocatoria);
		
		//Creo la sesión de hibernate
		Session session = getSessionFactory().openSession();
		
		List<FacturacionPorEmpresaDTO> resultados = session.createQuery(query).list();
		
		result.init(resultados, resultados.size());
	}

	private String crearQuery(String nombreDeEntidadBeneficiaria, String añoDeFacturacion, String idLlamadoAConvocatoria) {
		StringPattern queryPattern =
			new StringPattern(
				"select 																	" +
				"	new com.fontar.consultas.reportes.FacturacionPorEmpresaDTO(				" +
				"			e.entidad.denominacion,											" +
				"			f.numeroPeriodico,												" +
				"			f.numeroFacturacion 											" +
				"		)																	" +
				"from 																		" +
				"	FacturacionBean f,				 										" +
				"	EntidadBeneficiariaBean e												" +
				"where 																		" +
				"	e.entidad.borrado = false and 											" +
				"	f.idEntidadBeneficiaria = e.id and										" +
				"	${filtroPorConvocatoria} and											" +
				"	${filtroPorAño} and 													" +
				"	${filtroPorEntidad} 													" +
				"order by e.entidad.denominacion, f.numeroPeriodico							"
				); 

		String filtroPorConvocatoria;
		if(StringUtil.isEmpty(idLlamadoAConvocatoria)) {
			filtroPorConvocatoria = "1=1";
		} else {
			filtroPorConvocatoria = 
				"	e.id in (																" +
				"				select 														" +
				"					p.proyectoDatos.idEntidadBeneficiaria 					" +
				"				from														" +
				"					 ProyectoBean p 										" +
				"				where 														" +
				"					p.idInstrumento = ${idLlamadoAConvocatoria} and 		" +
				"					p.codigoEstado<>'ANULADO'								" +
				"			) 																" ;
		}
		
		String filtroPorAño;
		if(StringUtil.isEmpty(añoDeFacturacion)) {
			filtroPorAño = "1=1";
		} else {
			filtroPorAño = "f.numeroPeriodico="+añoDeFacturacion;
		}
		
		String filtroPorEntidad;
		if(StringUtil.isEmpty(nombreDeEntidadBeneficiaria)) {
			filtroPorEntidad = "1=1";
		} else {
			filtroPorEntidad = "upper(e.entidad.denominacion) like '%"+nombreDeEntidadBeneficiaria.toUpperCase()+"%'";
		}
		
		queryPattern.set("filtroPorAño", filtroPorAño);
		queryPattern.set("filtroPorEntidad", filtroPorEntidad);
		queryPattern.set("filtroPorConvocatoria", filtroPorConvocatoria);
		queryPattern.set("idLlamadoAConvocatoria", idLlamadoAConvocatoria);
		
		return queryPattern.toString();
	}

	public void validate(InformeFilterForm form, ActionErrors errors) {
	}

	/**
	 * A partir del request, obtiene el valor elegido por el usuario para el
	 * filtro con el nombre <code>nombreDelFiltro</code>.
	 * @param request
	 * @param nombreDelFiltro
	 * @return
	 */
	protected String filterValue(HttpServletRequest request, String nombreDelFiltro) {
		/*
		 * Este método es auxiliar y no deberia cambiar.
		 */
		ReporteFilterForm form = (ReporteFilterForm)request.getAttribute("reporteFiltersForm");
		return String.valueOf(form.getFilter(nombreDelFiltro));
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Map getParameters(HttpServletRequest request) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		String añoDeFacturacion =  filterValue(request, FILTRO_ANIO_DE_FACTURACION);
		
		if(StringUtil.isEmpty(añoDeFacturacion)) {
			parametros.put("anioDeFacturacion", "Todos");
		} else {
			parametros.put("anioDeFacturacion", añoDeFacturacion);
		}
		
		String idLlamadoAConvocatoria = filterValue(request, FILTRO_LLAMADO_A_CONVOCATORIA);
		if(StringUtil.isEmpty(idLlamadoAConvocatoria)) {
			parametros.put("convocatoria", "Todas");
		} else {
			String convocatoria = null;
			for(LabelValueBean labelValue : CollectionHandler.defaultInstance().getLlamadosAConvocatoria()) {
				if(idLlamadoAConvocatoria.equals(labelValue.getValue())) {
					convocatoria = labelValue.getLabel(); 
				}
			}			
			parametros.put("convocatoria", convocatoria);
		}
		return parametros;
	}
}