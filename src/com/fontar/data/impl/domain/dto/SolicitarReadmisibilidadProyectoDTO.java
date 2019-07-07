package com.fontar.data.impl.domain.dto;


/**
 * Dto de readmisibilidad de un proyecto
 * @author ttoth
 * @version 1.01, 01/012/07
 */
public class SolicitarReadmisibilidadProyectoDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String fechaPresentacion;
	
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

}
