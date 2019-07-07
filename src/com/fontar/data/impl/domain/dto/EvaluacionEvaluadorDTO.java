package com.fontar.data.impl.domain.dto;

public class EvaluacionEvaluadorDTO {

	private String idEvaluador;

	private String idEntidadEvaluadora;

	private String lugar;

	public String getIdEntidadEvaluadora() {
		return idEntidadEvaluadora;
	}

	public void setIdEntidadEvaluadora(String idEntidadEvaluadora) {
		this.idEntidadEvaluadora = idEntidadEvaluadora;
	}

	public String getIdEvaluador() {
		return idEvaluador;
	}

	public void setIdEvaluador(String idEvaluador) {
		this.idEvaluador = idEvaluador;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
}
