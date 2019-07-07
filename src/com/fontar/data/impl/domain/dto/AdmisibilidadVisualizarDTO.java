package com.fontar.data.impl.domain.dto;


public class AdmisibilidadVisualizarDTO {

	private String fechaAdmision;

	private String nroDisposicion;
	
	private String fundamentacion;
	
	private String observaciones;

	public String getFechaAdmision() {
		return fechaAdmision;
	}

	public void setFechaAdmision(String fechaAdmision) {
		this.fechaAdmision = fechaAdmision;
	}

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public String getNroDisposicion() {
		return nroDisposicion;
	}

	public void setNroDisposicion(String nroDisposicion) {
		this.nroDisposicion = nroDisposicion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
