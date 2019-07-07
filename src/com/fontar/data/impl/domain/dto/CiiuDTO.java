package com.fontar.data.impl.domain.dto;

public class CiiuDTO {

	private Long id;

	private String nombre;

	private String codigo;

	private CiiuDTO padre;

	private Long idPadre;

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public CiiuDTO getPadre() {
		return padre;
	}

	public void setPadre(CiiuDTO padre) {
		this.padre = padre;
	}
}
