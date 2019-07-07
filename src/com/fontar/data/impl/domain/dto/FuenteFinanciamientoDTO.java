package com.fontar.data.impl.domain.dto;

public class FuenteFinanciamientoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String identificador;

	private String denominacion;

	private String activo;

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
}
