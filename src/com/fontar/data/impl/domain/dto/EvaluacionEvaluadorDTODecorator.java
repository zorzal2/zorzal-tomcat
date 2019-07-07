package com.fontar.data.impl.domain.dto;

public class EvaluacionEvaluadorDTODecorator extends EvaluacionEvaluadorDTO {

	private EvaluacionEvaluadorDTO dto;

	private String entidadEvaluadora;

	private String evaluador;

	public EvaluacionEvaluadorDTODecorator(EvaluacionEvaluadorDTO dto) {
		super();
		this.dto = dto;
	}

	public boolean equals(Object obj) {
		return dto.equals(obj);
	}

	public String getIdEntidadEvaluadora() {
		return dto.getIdEntidadEvaluadora();
	}

	public String getIdEvaluador() {
		return dto.getIdEvaluador();
	}

	public String getLugar() {
		return dto.getLugar();
	}

	public int hashCode() {
		return dto.hashCode();
	}

	public void setIdEntidadEvaluadora(String idEntidadEvaluadora) {
		dto.setIdEntidadEvaluadora(idEntidadEvaluadora);
	}

	public void setIdEvaluador(String idEvaluador) {
		dto.setIdEvaluador(idEvaluador);
	}

	public void setLugar(String lugar) {
		dto.setLugar(lugar);
	}

	public String toString() {
		return dto.toString();
	}

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

}
