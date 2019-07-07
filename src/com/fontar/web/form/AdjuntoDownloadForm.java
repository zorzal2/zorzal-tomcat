package com.fontar.web.form;

import org.apache.struts.action.ActionForm;

public class AdjuntoDownloadForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String filename;
	private String contentType;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
