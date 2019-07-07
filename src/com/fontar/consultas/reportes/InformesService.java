package com.fontar.consultas.reportes;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.fontar.consultas.InformeFilterForm;

public interface InformesService {
	
	public void setInitialContext(HttpServletRequest request , String reportName);	
	
	public void validateRequest(HttpServletRequest request, ActionErrors errors, InformeFilterForm form);
		
}

