package com.fontar.web.form.proyecto.pac;

import com.fontar.web.form.BaseActionUploadForm;

public class PacExcelUploadForm extends BaseActionUploadForm {

	private static final long serialVersionUID = 1L;
	
	private Long proyectoId;

	public Long getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}
}
