package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.fontar.consultas.reportes.ReporteService;
import com.pragma.util.ContextUtil;

public class ReporteFilterForm extends InformeFilterForm {
	    
	private static final long serialVersionUID = 1L;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);

		ReporteService service = (ReporteService) ContextUtil.getBean("reporteService");
		service.validateRequest(request,errors,this);
				
		return errors;
	}
	    
    
}



