package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.consultas.InformeFilterForm;
import com.fontar.consultas.ReporteFilterForm;
import com.fontar.consultas.Resolver;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.util.FontarValidation;
import com.fontar.web.action.consultas.Result;
/**
 * Modelo de resolver que puede usarse como referencia para crear nuevos reportes.
 * @author llobeto
 *
 */
public class ResolverModelo implements Resolver {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public void setInitialContext(HttpServletRequest request) {
		/*
		 * En este método se encarga de inicializar la página en donde se muestran los
		 * filtros del reporte.
		 * Ejemplo:
		 */
		request.setAttribute("llamadosAConvocatoria", CollectionHandler.defaultInstance().getLlamadosAConvocatoria());
		/*
		 * El nombre llamadosAConvocatoria es el que se usará en el JSP de filtros en la
		 * propiedad collection para generar el listado:
		 * 
		 * <html:options collection="llamadosAConvocatoria" property="value" labelProperty="label"/> 
		 * 
		 */
	}

	public Map getParameters(HttpServletRequest request) {
		/*
		 * En este método se pueden mandar parámetros al formulario generado
		 * con IReports. Se pueden referenciar con la expresión $P{NOMBRE_DE_PARAMETRO}
		 * Por ejemplo:
		 
		 HashMap<String, Object> parametros = new HashMap<String, Object>();
		 parametros.put("FECHA_ACTUAL", new Date());
		 parametros.put("PROYECTO", this.proyecto);
		 return parametros;
		 
		 * Si no es necesario enviar parámetros se puede usar:

		 return new HashMap();
		 
		 */
		
		return new HashMap();
		 
	}

	public Collection result(HttpServletRequest request) {
		/*
		 * Este método devuelve la colección de elementos a mostrar.
		 * En principio, no es necesario moficiarlo.
		 */
		Result result = new Result();
		result.setPaging(false);
		this.execute(request,result);
		return result.getList();
	}

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, Result result) {
		/*
		 * En este método se obtienen del request los parámetros del formulario
		 * y en función de ellos se obtienen los objetos a listar en el reporte.
		 */
		
		
		/*
		 * OBTENCIÓN DE PARÁMETROS PARA EL FORMULARIO
		 * 
		 * Por ejemplo, para obtener el id del proyecto elegido por el usuario en un 
		 * filtro de proyectos se puede hacer:
		 */
		
		Long idProyecto = new Long( filterValue(request, "proyectoFilter") );
		
		/*
		 * En este caso "proyectoFilter" corresponde al nombre que se usará
		 * en el JSP de la página de filtros como nombre de filtro:
		 * 
		 * <html:select property="filter(proyectoFilter)">
		 * 
		 * filterValue devuelve el valor elegido en forma de String.
		 */
		
		/*
		 * CONSULTA
		 * 
		 * A continuación hay que obtener la lista de elementos a mostrar. 
		 * Por ejemplo, para obtener el listado de seguimientos del proyecto
		 * elegido por el usuario se podría escribir:		  
		 */
		
		//Creo la sesión de hibernate
		org.hibernate.classic.Session session = getSessionFactory().openSession();
		
		List resultado;
		
		//En forma de query en HQL:		
		String strQuery = "select seg from SeguimientoBean seg where seg.proyecto.id = :idProyecto order by seg.proyecto.codigo";
		Query query = session.createQuery(strQuery);
		query.setLong("idProyecto", idProyecto);
		resultado = query.list();
		
		//O usando Criteria:
		Criteria criteria = session.createCriteria(SeguimientoBean.class);
		criteria.add(Expression.eq("proyecto.id", idProyecto));
		criteria.addOrder(Order.asc("proyecto.codigo"));
		resultado = criteria.list();
		
		/*
		 * Finalmente, con la lista obtenida inicializo el objeto result
		 */
		result.init(resultado, resultado.size());
	}


	public void validate(InformeFilterForm form, ActionErrors errors) {
		/*
		 * En este metodo se validan los filtros que completó el usuario.
		 * Por ejemplo:
		 * 
		 
		 String before = form.getFilterAsString("before");   /Obtengo el valor ingresado
		 if(!FontarValidation.isDate(before,true)) {
		 	//Agrego un mensaje de error asociado al campo que no se ingreso correctamente
			 errors.add("before", new ActionMessage("El campo desde no es válido", false));
		 }
		 
		 * 
		 */
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

}