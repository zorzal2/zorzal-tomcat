package com.fontar.data.impl.domain.dto;

import java.util.Date;

public class ArchivoDTO {
	private String nombre;
	private Date fecha;
	private Long longitud;
	private String tipoContenido;
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getLongitud() {
		return longitud;
	}
	public void setLongitud(Long longitud) {
		this.longitud = longitud;
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
