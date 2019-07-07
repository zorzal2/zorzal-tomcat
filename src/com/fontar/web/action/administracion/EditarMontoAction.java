package com.fontar.web.action.administracion;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.configuracion.ConfiguracionServicio;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.PragmaDynaValidatorForm;

public class EditarMontoAction extends MappingDispatchAction {

	public static interface MontoSaver {
		public void saveEdicionMonto(String entidad, Long id, String propiedad, BigDecimal valorNuevo);
	}

	private ConfiguracionServicio configService;

	public void setConfigService(ConfiguracionServicio service) {
		this.configService = service;
	}

	public ActionForward cargar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {
		
		PragmaDynaValidatorForm formulario = (PragmaDynaValidatorForm) form;
	 
		if (StringUtil.isEmpty(formulario.getString("montoNuevo"))){
			Object monto = formulario.get("montoActual");
			formulario.set("montoNuevo", monto);
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		String strHandler= (String) dynaForm.get("handler");
		if(StringUtil.isEmpty(strHandler))
			this.configService.saveEdicionMonto(dynaForm.getString("entidad") + "Bean", (Long)dynaForm.get("id"), dynaForm.getString("propiedad"), new BigDecimal(dynaForm.get("montoNuevo").toString()));
		else{
		    MontoSaver bean = (MontoSaver) ContextUtil.getBean(strHandler);
		    try {
		    	bean.saveEdicionMonto(dynaForm.getString("entidad") + "Bean", (Long)dynaForm.get("id"), dynaForm.getString("propiedad"), new BigDecimal(dynaForm.get("montoNuevo").toString()));
		    } catch(Exception e) {
		    	ActionMessages errors = getErrors(request);
		    	ActionUtil.addError(errors, e.getMessage());
		    	saveErrors(request, errors);
		    	return mapping.getInputForward();
		    }
		}		
		return mapping.findForward("success");
	}
/*
	public ActionForward validateMonto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String monto = BeanUtils.getProperty(form, "montoNuevo");

		try {
			if (Util.isBlank( monto))
				throw new Exception("Se requiere el campo Monto");

			//FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
			// validate
		}catch (Exception ex) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(request, messages);
			return mapping.findForward("invalid");
		}
		
		return mapping.findForward("success");
	}
*/
	
}
