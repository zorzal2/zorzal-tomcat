package com.fontar.consultas;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.fontar.web.action.consultas.Result;

public interface Resolver {

	/**
	 * Agregar los atributos necesario al request para la
	 * inicializacion del reporte 
	 */
	public void setInitialContext(HttpServletRequest request);

	/**
	 * Genera los parametros del reporte 	 
	 */
	public Map getParameters(HttpServletRequest request);

	/**
	 * Obtiene los datos necesarios para generar el reporte 
	 */
	public Collection result(HttpServletRequest request);

	public void execute(HttpServletRequest request, Result result);

	public void validate(InformeFilterForm form, ActionErrors errors);

}
