package com.fontar.data.impl.domain.dto;

public class ItemEvaluacionEvaluadorDTO {

	private String id;

	private String idEvaluador;

	private String idEntidadEvaluadora;

	private String evaluador;

	private String entidadEvaluadora;

	private String lugar;

	public String getEntidadEvaluadora() {
		return entidadEvaluadora;
	}

	public void setEntidadEvaluadora(String entidadEvaluadora) {
		this.entidadEvaluadora = entidadEvaluadora;
	}

	public String getEvaluador() {
		return evaluador;
	}

	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
