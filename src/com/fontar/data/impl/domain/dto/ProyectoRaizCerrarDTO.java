package com.fontar.data.impl.domain.dto;

public class ProyectoRaizCerrarDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String estadoEnPaquete;

	private String clase;

	public String getEstadoEnPaquete() {
		return estadoEnPaquete;
	}

	public void setEstadoEnPaquete(String estadoEnPaquete) {
		this.estadoEnPaquete = estadoEnPaquete;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

}
