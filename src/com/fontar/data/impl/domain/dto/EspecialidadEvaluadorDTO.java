package com.fontar.data.impl.domain.dto;

public class EspecialidadEvaluadorDTO extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String codigo;

	private String nombre;

	private String activo;

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
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
}
