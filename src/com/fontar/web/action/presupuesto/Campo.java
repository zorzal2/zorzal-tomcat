/**
 * 
 */
package com.fontar.web.action.presupuesto;

public class Campo {
	String label;
	String propiedad;
	String style;
	
	public Campo(String label, String propiedad) {
		this.label = label;
		this.propiedad = propiedad;
		style = "";
	}
	public Campo(String label, String propiedad, String style) {
		this.label = label;
		this.propiedad = propiedad;
		this.style = style;
	}
	public String getLabel() {
		return label;
	}
	public String getPropiedad() {
		return propiedad;
	}
	public String getStyle() {
		return style;
	}
}