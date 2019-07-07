package com.fontar.data.impl.domain.bean;

import java.util.Collection;

/**
 * Estos objetos representan el archivos fisico de un adjunto.
 * @see com.fontar.data.impl.domain.bean.AdjuntoBean
 */
public class BandejaDeEntradaBean {

	long id;

	String tipo;

	String nombre;

	String estado;

	String fechaInicio;

	String responsable;

	Collection acciones;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection getAcciones() {
		return acciones;
	}

	public void setAcciones(Collection acciones) {
		this.acciones = acciones;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
