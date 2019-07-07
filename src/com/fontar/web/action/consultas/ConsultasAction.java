package com.fontar.web.action.consultas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.consultas.ConsultaService;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ConsultasAction  extends BaseMappingDispatchAction {

	private FontarCryptographicService criptoService;
	private ConsultaService consultaService;

	public FontarCryptographicService getCriptoService() {
		return criptoService;
	}

	public void setCriptoService(FontarCryptographicService criptoService) {
		this.criptoService = criptoService;
	}
	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("success");
	}

	
	public ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("consultas", consultaService.list());
		
		return mapping.findForward("success");
	}
	
	public ActionForward initConsulta(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String consultaName = request.getParameter("id");
		consultaService.setInitialContext(request,consultaName);
		return mapping.findForward(consultaName);
	}
	
	public ActionForward ejecutarConsulta(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		String reportName = request.getParameter("id");
		consultaService.setInitialContext(request,reportName);
		
		if(service.encyptionAvailable()){
			consultaService.processRequest(request, reportName);
		}else{
			ActionMessages errors = getErrors(request);
			this.addError(errors,"app.error.encrypt");
			saveErrors(request,errors);	
		}
		return mapping.findForward(reportName);
	}
	
	public ActionForward resetConsulta(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		form.reset(mapping,request);
		return mapping.findForward("success");
	}

	public ConsultaService getConsultaService() {
		return consultaService;
	}

	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
	
}
