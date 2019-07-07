package com.fontar.web.form.proyecto.presupuesto;

import org.apache.struts.validator.ValidatorForm;

public class EditarPresupuestoForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long idPresupuesto;
	Long idProyecto;
	String fundamentacion;

	public String getFundamentacion() {
		return fundamentacion;
	}
	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
}
