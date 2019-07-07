package com.fontar.data.impl.domain.dto;

public class EvaluarResultadoProyectoDTO implements Evaluacion {

	private Long idEvaluacion;
	private String evaluaciones;
	private String resultado;
	private String elegibleString;
	private boolean esElegible;
	
	/* (non-Javadoc)
	 * @see com.fontar.data.impl.domain.dto.Evaluacion#getResultado()
	 */
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	/* (non-Javadoc)
	 * @see com.fontar.data.impl.domain.dto.Evaluacion#getIdEvaluacion()
	 */
	public Long getIdEvaluacion() {
		return idEvaluacion;
	}
	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public String getEvaluaciones() {
		return evaluaciones;
	}
	public void setEvaluaciones(String evaluaciones) {
		this.evaluaciones = evaluaciones;
	}
	public String getElegibleString() {
		return elegibleString;
	}
	public void setElegibleString(String elegibleString) {
		this.elegibleString = elegibleString;
	}
	public boolean isEsElegible() {
		return esElegible;
	}
	public void setEsElegible(boolean esElegible) {
		this.esElegible = esElegible;
	}
}