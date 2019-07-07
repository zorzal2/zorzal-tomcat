package com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan;

import java.util.Date;

public class ActividadDTO {
	private Long id;
	private Long etapaId;
	private String nombre;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private String avance;
	private String observacion;
	
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = avance;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getEtapaId() {
		return etapaId;
	}
	public void setEtapaId(Long etapaId) {
		this.etapaId = etapaId;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public String toString() {
		return "["+getNombre()+": "+getDescripcion()+" ("+getInicio()+", "+getFin()+")]";
	}
}
