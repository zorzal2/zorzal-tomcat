package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan las distintas fuentes de financiamiento para los instrumentos de beneficio.
 * La fuentes de financiamiento es una propiedad de los las definiciones de instrumentos de Beneficio.  
 * @see com.fontar.data.impl.domain.bean.InstrumentoDefBean
 */
public class FuenteFinanciamientoBean {

	private Long id;

	private String identificador;

	private String denominacion;

	private Boolean activo;

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
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