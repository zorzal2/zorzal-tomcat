package com.fontar.consultas.reportes;

public class EstadoSituacionConvocatoriaDTO {
	
	private Integer enEvaluacion;

	private Integer presentados;
	
	private Integer cerrados;
	
	private Integer admitidos;
	
	private Integer reAdmitidos;
	
	private Integer aprobadosEnPrimeraInstancia;
	
	private Integer rechazadosEnPrimeraInstancia;
	
	private Integer aprobadosReconsiderados;
	
	private Integer rechazadosReconsiderados;
	
	
	public EstadoSituacionConvocatoriaDTO(){
		this.presentados = 0;
		this.enEvaluacion = 0;
	}
	
	public Integer getEnEvaluacion() {
		return enEvaluacion;
	}

	public void setEnEvaluacion(Integer enEvaluacion) {
		this.enEvaluacion = enEvaluacion;
	}

	public Integer getPresentados() {
		return presentados;
	}

	public void setPresentados(Integer presentados) {
		this.presentados = presentados;
	}

	public Integer getCerrados() {
		return cerrados;
	}

	public void setCerrados(Integer cerrados) {
		this.cerrados = cerrados;
	}

	public Integer getAdmitidos() {
		return admitidos;
	}

	public void setAdmitidos(Integer admitidos) {
		this.admitidos = admitidos;
	}

	public Integer getAprobadosEnPrimeraInstancia() {
		return aprobadosEnPrimeraInstancia;
	}

	public void setAprobadosEnPrimeraInstancia(Integer aprobadosEnPrimeraInstancia) {
		this.aprobadosEnPrimeraInstancia = aprobadosEnPrimeraInstancia;
	}

	public Integer getAprobadosReconsiderados() {
		return aprobadosReconsiderados;
	}

	public void setAprobadosReconsiderados(Integer aprobadosReconsiderados) {
		this.aprobadosReconsiderados = aprobadosReconsiderados;
	}

	public Integer getReAdmitidos() {
		return reAdmitidos;
	}

	public void setReAdmitidos(Integer reAdmitidos) {
		this.reAdmitidos = reAdmitidos;
	}

	public Integer getRechazadosEnPrimeraInstancia() {
		return rechazadosEnPrimeraInstancia;
	}

	public void setRechazadosEnPrimeraInstancia(Integer rechazadosEnPrimeraInstancia) {
		this.rechazadosEnPrimeraInstancia = rechazadosEnPrimeraInstancia;
	}

	public Integer getRechazadosReconsiderados() {
		return rechazadosReconsiderados;
	}

	public void setRechazadosReconsiderados(Integer rechazadosReconsiderados) {
		this.rechazadosReconsiderados = rechazadosReconsiderados;
	}


}
