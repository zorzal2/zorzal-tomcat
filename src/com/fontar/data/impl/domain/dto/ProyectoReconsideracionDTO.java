package com.fontar.data.impl.domain.dto;

import java.util.Date;



public class ProyectoReconsideracionDTO {

	private Long id;
	private Date fecha;
	private String fundamentacion;
	private String dictamen;
	private String resolucion;
	private String resultado;
	private String observacion;

	public String getDictamen() {
		return dictamen;
	}
	public void setDictamen(String dictamen) {
		this.dictamen = dictamen;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getFundamentacion() {
		return fundamentacion;
	}
	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
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
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
