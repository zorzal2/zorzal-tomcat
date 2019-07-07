package com.fontar.consultas;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.displaytag.tags.TableTagParameters;

import com.fontar.web.action.consultas.Result;

public class ConsultaServiceImpl implements ConsultaService {

	Map<String,Consulta> consultas;
	
	private int pageSize = 15;
	
	
	public Map<String, Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(Map<String, Consulta> consultas) {
		this.consultas = consultas;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<Consulta> list() {
		return this.consultas.values();
	}

	public void setInitialContext(HttpServletRequest request, String reportName) {
		Informe consulta = this.consultas.get(reportName);
		request.setAttribute("consulta", consulta);
		consulta.getResolver().setInitialContext(request);
	}

	public void processRequest(HttpServletRequest request,  String reportName) {
		Informe consulta = this.consultas.get(reportName);
		String requestedPageString = request.getParameter("page");
		int requestedPage = (requestedPageString == null)? 1 : Integer.parseInt(requestedPageString);
		Result result = new Result(requestedPage,this.pageSize);
		result.setPaging( this.isPaging(request) );
		consulta.getResolver().execute(request, result);
		request.setAttribute("consulta", consulta);
		request.setAttribute("result", result);
	}

	
	private boolean isPaging(HttpServletRequest request){
	   	return request.getParameter(TableTagParameters.PARAMETER_EXPORTING) == null;
	}

	public void validateRequest(HttpServletRequest request, ActionErrors errors, InformeFilterForm form) {
		String consultaName = request.getParameter("id");
		Informe consulta = this.consultas.get(consultaName);
		consulta.getResolver().validate(form,errors);
	}
	
}
