package com.fontar.data.impl.domain.dto;


/**
 * Dto de visualizacion de un instrumento
 * @author ttoth
 * @version 1.01, 09/03/07
 */
public class InstrumentoVisualizacionDTO {
	private static final long serialVersionUID = 1L;

	private String identificador;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
}
