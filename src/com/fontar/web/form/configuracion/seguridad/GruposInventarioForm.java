package com.fontar.web.form.configuracion.seguridad;

import org.apache.struts.action.ActionForm;

public class GruposInventarioForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
