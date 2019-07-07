package com.fontar.consultas.reportes;

import java.util.Collection;


public interface ReporteService extends InformesService {
	
	public Collection<Reporte> list();	
	public Reporte getReporte(String reportName);
	public Executor getReporteExecutor(String reportName);	
	public String getReporteProcessURL();
	
}

