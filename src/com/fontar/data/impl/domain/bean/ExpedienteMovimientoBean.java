package com.fontar.data.impl.domain.bean;

import java.util.Date;


/**
 * Esta clase permite definir objetos que representan los movimientos reales de 
 * expediente fisico de cada proyecto. 
 * Con esta información se puede llevar el control de donde se encuentra el expediente. 
 * @see com.fontar.data.impl.domain.bean.ProyectoBean   
 */
public class ExpedienteMovimientoBean extends Auditable{

	private Long id;
	
	private Date fecha;
	
	private String ubicacion;
	
	private Date fechaDevolucion;
	
	private String observacion;
	
	private Boolean estado;
	
	private Long idPersona;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
}