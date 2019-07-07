package com.fontar.data.impl.domain.dto;

import java.util.Date;

public class AdjuntoDTO {
	private Long id;

	private String nombre;

	private String descripcion;

	private Date fecha;

	private Long cantidadLongitud;

	private String tipoContenido;

	private Long idAdjuntoContenido;

	public Long getCantidadLongitud() {
		return cantidadLongitud;
	}

	public void setCantidadLongitud(Long cantidadLongitud) {
		this.cantidadLongitud = cantidadLongitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAdjuntoContenido() {
		return idAdjuntoContenido;
	}

	public void setIdAdjuntoContenido(Long idAdjuntoContenido) {
		this.idAdjuntoContenido = idAdjuntoContenido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

}
