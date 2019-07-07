package com.fontar.data.impl.domain.dto;



public class VisualizarProcedimientoDTO {

	private Long id;
	
	private String codigo;
	
	private String fechaRecepcion;
	
	private String evaluador;
	
	private String remision;
	
	private String montoAdjudicacion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public String getMontoAdjudicacion() {
		return montoAdjudicacion;
	}

	public void setMontoAdjudicacion(String montoAdjudicacion) {
		this.montoAdjudicacion = montoAdjudicacion;
	}

	public String getRemision() {
		return remision;
	}

	public void setRemision(String remision) {
		this.remision = remision;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
