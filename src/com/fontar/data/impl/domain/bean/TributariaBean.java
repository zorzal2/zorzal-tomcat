package com.fontar.data.impl.domain.bean;


/**
 * Estos objetos representan las distintas opciones de condiciones tributarias 
 * de una entidad Beneficiaria. 
 * @see com.fontar.data.impl.domain.bean.EntidadBancariaBean
 */
public class TributariaBean {

	private Long id;

	private String denominacion;

	private String codigo;

	private String emerix;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEmerix() {
		return emerix;
	}

	public void setEmerix(String emerix) {
		this.emerix = emerix;
	}

}
