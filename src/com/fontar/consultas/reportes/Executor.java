package com.fontar.consultas.reportes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperReport;

public class Executor  {

	private Reporte reporte;
	
	/** Reporte compilado**/
	private JasperReport jasperReport;
	
	private Collection result;
	
	/** Parametros del reporte **/
	Map parameters = new HashMap();
	
	/** Request**/
	HttpServletRequest request;
	

	private String logo;
	
	public Executor(Reporte reporte) {
		super();
		this.reporte = reporte;
	}

	
	public JasperReport getJasperReport() {
		return jasperReport;
	}

	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
	}


	public Reporte getReporte() {
		return reporte;
	}


	public void setReporte(Reporte reporte) {
		this.reporte = reporte;
	}


	public void setInitialContext(HttpServletRequest request) {
		reporte.getResolver().setInitialContext(request);
	}

	@SuppressWarnings("unchecked")
	public void init(HttpServletRequest request) {
		this.request = request;
		this.parameters = this.reporte.getResolver().getParameters(request);
		this.parameters.put("logo",this.logo);
	}

	@SuppressWarnings("unchecked")
	public void addParameter(Object key, Object value){
		this.parameters.put(key,value);
	}
	
	@SuppressWarnings("unchecked")
	public Map getParameters() {
		Map requestedParameters = this.getReporte().getResolver().getParameters(request); 
		for (Object key: requestedParameters.keySet())
			this.parameters.put(key, requestedParameters.get(key));
		return parameters;
	}


	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
	public Collection result(){
		if (result == null) {
			result = this.getReporte().getResolver().result(this.request);
		}
		return result;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public Collection getResult() {
		return result;
	}


	public void setResult(Collection result) {
		this.result = result;
	}
	
	
	
}
