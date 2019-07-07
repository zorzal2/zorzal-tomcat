package com.fontar.web.form.proyecto.presupuesto;

import org.apache.struts.validator.ValidatorForm;

public class PresupuestoDetalleRubroForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String localizacionPresupuesto;
	Long idRubro;
	public Long getIdRubro() {
		return idRubro;
	}
	public void setIdRubro(Long idRubro) {
		this.idRubro = idRubro;
	}
	public String getLocalizacionPresupuesto() {
		return localizacionPresupuesto;
	}
	public void setLocalizacionPresupuesto(String localizacionPresupuesto) {
		this.localizacionPresupuesto = localizacionPresupuesto;
	}
}
