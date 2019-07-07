package com.fontar.data.impl.domain.bean;

import java.util.Date;

/**
 * Estos objetos representan las distintas Comisiones AD-Hoc conformadas para la evaluación de paquetes de proyectos (paquetes de comisión).
 * Una comisión se crea bajo por medio de una resolución y se utiliza en un instrumento 
 * de beneficio para establecer la comisión especifica por la cual deben ser tratados todos 
 * los paquetes de proyectos correspondientes a dicho instrumento.   
 */
public class ComisionBean {

	private Long id;

	private String denominacion;

	private String resolucion;

	private Date fechaBaja;

	private Date fechaAlta;

	private String observacion;

	private String descripcion;

	private Boolean activo;

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

}
