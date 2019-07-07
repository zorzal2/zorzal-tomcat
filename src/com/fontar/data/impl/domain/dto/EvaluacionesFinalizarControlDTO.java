package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;

public class EvaluacionesFinalizarControlDTO implements Evaluacion {

	private Long idEvaluacion;
	private String tipo;
	private String evaluadores;
	private String resultado;
	private EstadoEvaluacion estado;
	private String recomendacion;
	
	
	public EstadoEvaluacion getEstado() {
		return estado;
	}
	public void setEstado(EstadoEvaluacion estado) {
		this.estado = estado;
	}
	public String getRecomendacion() {
		return recomendacion;
	}
	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Long getIdEvaluacion() {
		return idEvaluacion;
	}
	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public String getEvaluadores() {
		return evaluadores;
	}
	public void setEvaluadores(String evaluadores) {
		this.evaluadores = evaluadores;
	}
}