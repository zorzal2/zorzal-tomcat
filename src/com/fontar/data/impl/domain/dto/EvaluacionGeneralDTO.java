package com.fontar.data.impl.domain.dto;

import java.util.Collection;

import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacionFinanciera;

public class EvaluacionGeneralDTO extends EvaluacionDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String esAuditoriaContable;

	private String esContable;

	private String esEconomica;

	private String esFinanciera;

	private String esTecnica;

	private String esVisitaTecnica;

	private String fechaEntregaComprometida;

	private TipoEvaluacionFinanciera tipoEvaluacionFinanciera;

	private Collection<EvaluacionEvaluadorDTO> evaluadores;

	private Boolean aceptada;
	
	private String clasificacion;

	public Boolean getAceptada() {
		return aceptada;
	}

	public void setAceptada(Boolean aceptada) {
		this.aceptada = aceptada;
	}

	public String getEsAuditoriaContable() {
		return esAuditoriaContable;
	}

	public void setEsAuditoriaContable(String esAuditoriaContable) {
		this.esAuditoriaContable = esAuditoriaContable;
	}

	public String getEsContable() {
		return esContable;
	}

	public void setEsContable(String esContable) {
		this.esContable = esContable;
	}

	public String getEsEconomica() {
		return esEconomica;
	}

	public void setEsEconomica(String esEconomica) {
		this.esEconomica = esEconomica;
	}

	public String getEsFinanciera() {
		return esFinanciera;
	}

	public void setEsFinanciera(String esFinanciera) {
		this.esFinanciera = esFinanciera;
	}

	public String getEsTecnica() {
		return esTecnica;
	}

	public void setEsTecnica(String esTecnica) {
		this.esTecnica = esTecnica;
	}

	public String getEsVisitaTecnica() {
		return esVisitaTecnica;
	}

	public void setEsVisitaTecnica(String esVisitaTecnica) {
		this.esVisitaTecnica = esVisitaTecnica;
	}

	public String getFechaEntregaComprometida() {
		return fechaEntregaComprometida;
	}

	public void setFechaEntregaComprometida(String fechaEntregaComprometida) {
		this.fechaEntregaComprometida = fechaEntregaComprometida;
	}

	public TipoEvaluacionFinanciera getTipoEvaluacionFinanciera() {
		return tipoEvaluacionFinanciera;
	}

	public void setTipoEvaluacionFinanciera(TipoEvaluacionFinanciera tipoEvaluacionFinanciera) {
		this.tipoEvaluacionFinanciera = tipoEvaluacionFinanciera;
	}

	public Collection<EvaluacionEvaluadorDTO> getEvaluadores() {
		return evaluadores;
	}

	public void setEvaluadores(Collection<EvaluacionEvaluadorDTO> evaluadores) {
		this.evaluadores = evaluadores;
	}
	
	public Long getIdSeguimiento() {
		return this.getBitacora().getIdSeguimiento();
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
}