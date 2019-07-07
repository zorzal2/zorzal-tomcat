package com.fontar.web.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class AdjuntoUploadForm extends BaseValidationUploadForm {

	
	static final int MAX_FILE_SIZE = 2 * 1024 * 1024;//in bytes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String description;
	private String nombre;
	private Date fecha;
	private Long idAdjunto;
	
	public Long getIdAdjunto() {
		return idAdjunto;
	}
	public void setIdAdjunto(Long idAdjunto) {
		this.idAdjunto = idAdjunto;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors actionErrors = super.validate(arg0, arg1);
		if(this.getContent().getFileSize() > MAX_FILE_SIZE)
			 actionErrors.add("size", new ActionMessage("El tamaño del archivo es superior a 2MB", false));
		return actionErrors;
	}
	
	
}
