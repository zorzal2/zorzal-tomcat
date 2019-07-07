package com.fontar.data.impl.domain.dto;

import java.util.Collection;




public class CuadroResumenPacDTO {

	private String montoPendiente;
	
	private String montoProceso;
	
	private String montoAdjudicacionDesembolso;
	
	private String montoDesembolsado;
	
	private String montoAnticipio;
	
	private String montoFontarTotal;
	
	private String montoCPTotal;
	
	private String montoFontarFinanciamiento;
	
	private String montoCPFinanciamiento;
	
	private String montoFontarDif;
	
	private String montoCPDif;
	
	private Boolean esPermitidoDesembolsar;

	private Boolean esDistintoZero;
	
	private Collection<MonedaDTO> monedasSinCotizacion;

	public Collection<MonedaDTO> getMonedasSinCotizacion() {
		return monedasSinCotizacion;
	}

	public void setMonedasSinCotizacion(Collection<MonedaDTO> monedasSinCotizacion) {
		this.monedasSinCotizacion = monedasSinCotizacion;
	}

	public Boolean getEsPermitidoDesembolsar() {
		return esPermitidoDesembolsar;
	}

	public void setEsPermitidoDesembolsar(Boolean esPermitidoDesembolsar) {
		this.esPermitidoDesembolsar = esPermitidoDesembolsar;
	}

	public String getMontoAdjudicacionDesembolso() {
		return montoAdjudicacionDesembolso;
	}

	public void setMontoAdjudicacionDesembolso(String montoAdjudicacionDesembolso) {
		this.montoAdjudicacionDesembolso = montoAdjudicacionDesembolso;
	}

	public String getMontoAnticipio() {
		return montoAnticipio;
	}

	public void setMontoAnticipio(String montoAnticipio) {
		this.montoAnticipio = montoAnticipio;
	}

	public String getMontoCPDif() {
		return montoCPDif;
	}

	public void setMontoCPDif(String montoCPDif) {
		this.montoCPDif = montoCPDif;
	}

	public String getMontoCPFinanciamiento() {
		return montoCPFinanciamiento;
	}

	public void setMontoCPFinanciamiento(String montoCPFinanciamiento) {
		this.montoCPFinanciamiento = montoCPFinanciamiento;
	}

	public String getMontoCPTotal() {
		return montoCPTotal;
	}

	public void setMontoCPTotal(String montoCPTotal) {
		this.montoCPTotal = montoCPTotal;
	}

	public String getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(String montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	public String getMontoFontarDif() {
		return montoFontarDif;
	}

	public void setMontoFontarDif(String montoFontarDif) {
		this.montoFontarDif = montoFontarDif;
	}

	public String getMontoFontarFinanciamiento() {
		return montoFontarFinanciamiento;
	}

	public void setMontoFontarFinanciamiento(String montoFontarFinanciamiento) {
		this.montoFontarFinanciamiento = montoFontarFinanciamiento;
	}

	public String getMontoFontarTotal() {
		return montoFontarTotal;
	}

	public void setMontoFontarTotal(String montoFontarTotal) {
		this.montoFontarTotal = montoFontarTotal;
	}

	public String getMontoPendiente() {
		return montoPendiente;
	}

	public void setMontoPendiente(String montoPendiente) {
		this.montoPendiente = montoPendiente;
	}

	public String getMontoProceso() {
		return montoProceso;
	}

	public void setMontoProceso(String montoProceso) {
		this.montoProceso = montoProceso;
	}

	public Boolean getEsDistintoZero() {
		return esDistintoZero;
	}

	public void setEsDistintoZero(Boolean esDistintoZero) {
		this.esDistintoZero = esDistintoZero;
	}

}
