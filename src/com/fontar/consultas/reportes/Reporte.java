package com.fontar.consultas.reportes;

import com.fontar.consultas.Informe;

public class Reporte  extends Informe {

	/**
	 * Template utilizado para generar el reporte
	 */
	private String template;

	public String getTemplate() {
		return template == null ? this.getNombre() + ".jrxml" : template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
		
}
