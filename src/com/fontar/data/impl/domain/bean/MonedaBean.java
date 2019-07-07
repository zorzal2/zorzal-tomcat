package com.fontar.data.impl.domain.bean;


/**
 * Objetos que representan las distintas monedas del sistema.
 */
public class MonedaBean {

	private Long id;
	private String tipoMoneda;
	private String descripcion;
	private String codigoEmerix;

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoMoneda() {
		return tipoMoneda;
	}
	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	public String getCodigoEmerix() {
		return codigoEmerix;
	}
	public void setCodigoEmerix(String codigoEmerix) {
		this.codigoEmerix = codigoEmerix;
	}		
}