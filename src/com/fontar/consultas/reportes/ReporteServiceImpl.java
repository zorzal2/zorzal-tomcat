package com.fontar.consultas.reportes;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.struts.action.ActionErrors;

import com.fontar.consultas.Informe;
import com.fontar.consultas.InformeFilterForm;



public class ReporteServiceImpl implements ReporteService  {

	private Map<String, Reporte> reportes;
	
	private String reportLocation; 
	
	private String reportProcessURL;

	private String logo;
		
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getReportLocation() {
		return reportLocation;
	}

	public void setReportLocation(String reportLocation) {
		this.reportLocation = reportLocation;
	}
	
	public String getReporteProcessURL() {
		return reportProcessURL;
	}

	public void setReportProcessURL(String reportProcessURL) {
		this.reportProcessURL = reportProcessURL;
	}

	public Map<String, Reporte> getReportes() {
		return reportes;
	}

	public void setReportes(Map<String, Reporte> reportes) {
		this.reportes = reportes;
	}

	public Collection<Reporte> list(){
		return  reportes.values();
	}

	public Reporte getReporte(String reportName) {
		return this.reportes.get(reportName);
	}

	public Map<String, Object> getReportContext(String reportName) {
		return new HashMap<String, Object>();
	}

	public void setInitialContext(HttpServletRequest request, String reportName) {
		Informe reporte = this.getReporte(reportName);
		request.setAttribute("reporte",reporte);
		
		reporte.getResolver().setInitialContext(request);
	}

	public Executor getReporteExecutor(String reportName ) {
		Reporte reporte = this.getReporte(reportName);
		Executor executor = new Executor( reporte);
		executor.setLogo(this.logo);
		InputStream reportStream = this.getClass().getResourceAsStream( this.reportLocation + reporte.getTemplate() );
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
			executor.setJasperReport( jasperReport );
			return executor;
		}catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void validateRequest(HttpServletRequest request, ActionErrors errors, InformeFilterForm form) {
		String informeName = request.getParameter("id");
		Informe informe = this.reportes.get(informeName);
		informe.getResolver().validate(form,errors);
	}

	
}
