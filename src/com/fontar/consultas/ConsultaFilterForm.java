package com.fontar.consultas;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.pragma.util.ContextUtil;

public class ConsultaFilterForm extends InformeFilterForm {
	    
	private static final long serialVersionUID = 1L;

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);

		ConsultaService service = (ConsultaService) ContextUtil.getBean("consultaService");
		service.validateRequest(request,errors,this);
				
		return errors;
	}
	    
    
}










