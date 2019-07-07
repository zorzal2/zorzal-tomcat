package com.fontar.data.impl.domain.dto;

public class VisualizarEvaluacionIdeaProyectoDTO extends VisualizarEvaluacionProyectoDTO {

	private String jurisdiccion;

	public VisualizarEvaluacionIdeaProyectoDTO(EvaluacionDTO dto) {
		super(dto);
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

}
