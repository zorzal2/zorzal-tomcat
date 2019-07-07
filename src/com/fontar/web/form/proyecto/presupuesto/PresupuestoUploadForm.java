package com.fontar.web.form.proyecto.presupuesto;

import com.fontar.web.form.BaseActionUploadForm;

public class PresupuestoUploadForm extends BaseActionUploadForm {

	private static final long serialVersionUID = 1L;
	
	private Long idProyecto;
	private Long idPresupuesto;

	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long id) {
		this.idProyecto = id;
	}
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
}
