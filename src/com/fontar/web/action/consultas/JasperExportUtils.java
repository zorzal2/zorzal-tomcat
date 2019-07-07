package com.fontar.web.action.consultas;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.fontar.consultas.reportes.Executor;

public class JasperExportUtils extends ReportesAction {

	static final String PDF_CONTENT_TYPE = "application/pdf";
	static final String PDF_FILE_EXTENSION = ".pdf";
	
	static public void exportToPDF(JasperPrint jasperPrint,String fileName, HttpServletResponse response){

		try {
			byte[] content = JasperExportManager.exportReportToPdf(jasperPrint);
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		    response.setContentType( PDF_CONTENT_TYPE );
		    response.setContentLength(content.length);
		    OutputStream out = response.getOutputStream();
		    out.write(content);
		    out.flush();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static public void exportToPDF(Executor executor, HttpServletResponse response){
		try{
			JasperPrint jasperPrint  = JasperFillManager.fillReport(executor.getJasperReport(), executor.getParameters(), new JRBeanCollectionDataSource(executor.result()));
			String fileName = executor.getReporte().getNombre() + PDF_FILE_EXTENSION;
			exportToPDF(jasperPrint,fileName,response);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
