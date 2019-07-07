package com.fontar.data.impl.domain.dto;

import com.fontar.data.api.domain.codes.Enumerable;

public class ProyectoRaizEvaluarDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String id;

	private String codigo;

	private Enumerable estado;

	private String clase;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Enumerable getEstado() {
		return estado;
	}

	public void setEstado(Enumerable estado) {
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

}
