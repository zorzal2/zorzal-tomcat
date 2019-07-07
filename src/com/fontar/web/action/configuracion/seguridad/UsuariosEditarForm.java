package com.fontar.web.action.configuracion.seguridad;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;
import com.pragma.web.PragmaDynaValidatorForm;

public class UsuariosEditarForm extends PragmaDynaValidatorForm {

	private static final long serialVersionUID = 4594699220600883849L;
	
	
	

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {

		ActionErrors actionErrors = super.validate(arg0, arg1);
		String appPassword = this.getString("appPassword");
		if(actionErrors.isEmpty() && appPassword != null ){
			FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
			if(!service.validatetApplicationPrivateKey(appPassword.toCharArray()))
				actionErrors.add("appPassword", new ActionMessage("La contrasenia de aplicación es incorrecta", false));
		}
		return actionErrors;
	}
	

}
