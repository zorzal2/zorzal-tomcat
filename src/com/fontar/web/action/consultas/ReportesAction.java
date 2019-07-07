package com.fontar.web.action.consultas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;

import com.fontar.consultas.reportes.Executor;
import com.fontar.consultas.reportes.ReporteService;
import com.fontar.seguridad.cripto.FontarCryptographicService;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ReportesAction extends BaseMappingDispatchAction {
	
	private ReporteService reporteService;
	

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("success");
	}
	
	public ActionForward inventario(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("reportes", reporteService.list());
		return mapping.findForward("success");
	}
	
	public ActionForward initReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String reportName = request.getParameter("id");
		reporteService.setInitialContext(request,reportName);
		request.setAttribute("PARAMETER_EXPORTING",TableTagParameters.PARAMETER_EXPORTING);
		return mapping.findForward(reportName);
	}
	
	
	public ActionForward executeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
						
		FontarCryptographicService service = (FontarCryptographicService) ContextUtil.getBean("cryptographicService");
		String reportName = request.getParameter("id");
		reporteService.setInitialContext(request,reportName);
		
		if(service.encyptionAvailable()){			
			Executor executor = reporteService.getReporteExecutor(reportName);
			executor.init(request);
			if (!executor.result().isEmpty()){
				//Esto es para que sobre ssl no de error al querer ver el adjunto
				response.setHeader("PRAGMA","");
				response.setHeader("EXPIRES","");
				JasperExportUtils.exportToPDF(executor,response);
				return null;
			}else{
				ActionMessages errors = getErrors(request);
				this.addError(errors,"app.error.vacio");
				saveErrors(request,errors);
//				request.setAttribute("emptyPagina","El Reporte no contiene datos con los filtros elegidos");
				return mapping.findForward("invalid");
			}

		}else{
			ActionMessages errors = getErrors(request);
			this.addError(errors,"app.error.encrypt");
			saveErrors(request,errors);
			return mapping.findForward("invalid");			
		}

	}
	
	public ActionForward resetReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		form.reset(mapping,request);
		return mapping.findForward("success");
	}
	
	public ReporteService getReporteService() {
		return reporteService;
	}

	public void setReporteService(ReporteService reporteService) {
		this.reporteService = reporteService;
	}
}
