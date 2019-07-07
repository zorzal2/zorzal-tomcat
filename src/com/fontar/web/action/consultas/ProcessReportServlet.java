package com.fontar.web.action.consultas;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.fontar.consultas.reportes.Executor;
import com.fontar.consultas.reportes.ReporteService;
import com.pragma.util.ContextUtil;

public class ProcessReportServlet extends HttpServlet {

	
	private static final long serialVersionUID = -7307678328552607848L;

	
	
	protected ReporteService getService(){
		return (ReporteService) ContextUtil.getBean("reportService");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.process(request, response);
	}
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        try {
        	String reportName = request.getParameter("id");
        	Executor executor =  this.getService().getReporteExecutor(reportName);
        	executor.init(request);
			JasperReportsUtils.renderAsPdf(executor.getJasperReport(), executor.getParameters(), executor.result(),response.getOutputStream());
		}
		catch (JRException e) {
			throw new RuntimeException(e);
		}

	}
	

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	private String resolveReportName(HttpServletRequest request){
		return request.getRequestURI().replace(this.getService().getReporteProcessURL(),"");
	}
	
}
