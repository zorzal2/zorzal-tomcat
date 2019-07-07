package com.fontar.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.initialization.InitializeApplicationService;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.fontar.util.Util;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ApplicationManagerAction  extends BaseMappingDispatchAction {

	public FontarCryptographicService cryptoService;
	
	
	
	public InitializeApplicationService service;

	public InitializeApplicationService getService() {
		return service;
	}

	public void setService(InitializeApplicationService service) {
		this.service = service;
	}
	
	
	public FontarCryptographicService getCryptoService() {
		return cryptoService;
	}

	public void setCryptoService(FontarCryptographicService cryptoService) {
		this.cryptoService = cryptoService;
	}

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("status",this.cryptoService.getStatus());
		return mapping.findForward("success");
	}
	
	
	public ActionForward initializeEncryptionContext(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String appPassword = request.getParameter("password");
		if(!Util.isBlank(appPassword))
			this.cryptoService.startApplicationEncryptionContext(appPassword);
		
		request.setAttribute("status",this.cryptoService.getStatus());
		return mapping.findForward("success");
	}
	
}
