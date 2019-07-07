package com.fontar.consultas;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.fontar.consultas.reportes.InformesService;

public interface ConsultaService extends InformesService {
	
	public Collection<Consulta> list();	
	public void processRequest(HttpServletRequest request, String reportName);

}