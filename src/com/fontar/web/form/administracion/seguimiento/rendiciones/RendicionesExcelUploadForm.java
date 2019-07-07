package com.fontar.web.form.administracion.seguimiento.rendiciones;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.fontar.web.form.BaseActionUploadForm;

public class RendicionesExcelUploadForm extends BaseActionUploadForm {

	private static final long serialVersionUID = 1L;
	
	private Long seguimientoId;
	private Boolean borrarExistentes = false;

	public Long getSeguimientoId() {
		return seguimientoId;
	}
	public void setSeguimientoId(Long seguimientoId) {
		this.seguimientoId = seguimientoId;
	}
	public Boolean getBorrarExistentes() {
		return borrarExistentes;
	}
	public void setBorrarExistentes(Boolean borrarExistentes) {
		this.borrarExistentes = borrarExistentes;
	}
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.borrarExistentes = false;
	}
	@Override
	public void reset(ActionMapping arg0, ServletRequest arg1) {
		super.reset(arg0, arg1);
		this.borrarExistentes = false;
	}
	
}
