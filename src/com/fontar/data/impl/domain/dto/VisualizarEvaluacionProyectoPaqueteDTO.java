package com.fontar.data.impl.domain.dto;

public class VisualizarEvaluacionProyectoPaqueteDTO {
	private String idProyecto;

	private String resultado;

	private String fundamentacion;

	private String idPresupuesto;

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public String getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(String idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
