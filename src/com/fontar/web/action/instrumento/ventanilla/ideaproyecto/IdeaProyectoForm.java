package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pragma.util.StringUtil;

public class IdeaProyectoForm extends com.pragma.web.PragmaDynaValidatorForm {

	
	private static final long serialVersionUID = 6979190489828110740L;

	
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {

		ActionErrors actionErrors = super.validate(arg0, arg1);
		if(!actionErrors.get("montoSolicitado").hasNext()){
			String montoTotal = this.getString("montoTotal");
			String montoSolicitado = this.getString("montoSolicitado");
			if(!StringUtil.isEmpty(montoTotal) && (!StringUtil.isEmpty(montoSolicitado))) {
				if ((new BigDecimal(montoTotal) ).compareTo( new BigDecimal(montoSolicitado) ) < 0  ) {
					actionErrors.add("montoSolicitado", new ActionMessage("El Monto Solicitado ($) debe ser menor al Monto Total ($)", false));
				}
			}
		}
		return actionErrors;
	}

	

}

