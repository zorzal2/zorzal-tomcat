package com.fontar.data.impl.domain.dto;


/**
 * DTO para la cabecera de visualización de un seguimiento
 * @author gboaglio
 */

public class SeguimientoVisualizacionCabeceraDTO extends ProyectoCabeceraDTO {

	private static final long serialVersionUID = 1L;
	
	private ProyectoCabeceraDTO cabeceraProyecto;
	
	private String numeroSeguimiento;

	private String descripcion;
	
	private String fecha;

	private String descripcionEstado;
	
		
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getNumeroSeguimiento() {
		return numeroSeguimiento;
	}
	public void setNumeroSeguimiento(String numeroSeguimiento) {
		this.numeroSeguimiento = numeroSeguimiento;
	}
	public ProyectoCabeceraDTO getCabeceraProyecto() {
		return cabeceraProyecto;
	}
	public void setCabeceraProyecto(ProyectoCabeceraDTO cabeceraProyecto) {
		this.cabeceraProyecto = cabeceraProyecto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcionSeguimiento) {
		this.descripcion = descripcionSeguimiento;
	}
	
	
}