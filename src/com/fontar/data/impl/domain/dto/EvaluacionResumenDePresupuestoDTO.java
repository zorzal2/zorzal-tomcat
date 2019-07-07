package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

/**
 * Contiene los montos Fontar/Contraparte/Total solicitados y aprobados para una 
 * evaluación. 
 * @author llobeto
 *
 */
public class EvaluacionResumenDePresupuestoDTO {
	private BigDecimal montoFontarSolicitado;
	private BigDecimal montoContraparteSolicitado;
	private BigDecimal montoTotalSolicitado;

	private BigDecimal montoFontarAprobado;
	private BigDecimal montoContraparteAprobado;
	
	private boolean desplegarEnParteYContraparte;
	private boolean requiereContextoDeEncriptacion = false;

	private BigDecimal montoTotalAprobado;
	public BigDecimal getMontoContraparteAprobado() {
		return montoContraparteAprobado;
	}
	public void setMontoContraparteAprobado(BigDecimal montoContraparteAprobado) {
		this.montoContraparteAprobado = montoContraparteAprobado;
	}
	public BigDecimal getMontoContraparteSolicitado() {
		return montoContraparteSolicitado;
	}
	public void setMontoContraparteSolicitado(BigDecimal montoContraparteSolicitado) {
		this.montoContraparteSolicitado = montoContraparteSolicitado;
	}
	public BigDecimal getMontoFontarAprobado() {
		return montoFontarAprobado;
	}
	public void setMontoFontarAprobado(BigDecimal montoFontarAprobado) {
		this.montoFontarAprobado = montoFontarAprobado;
	}
	public BigDecimal getMontoFontarSolicitado() {
		return montoFontarSolicitado;
	}
	public void setMontoFontarSolicitado(BigDecimal montoFontarSolicitado) {
		this.montoFontarSolicitado = montoFontarSolicitado;
	}
	public BigDecimal getMontoTotalAprobado() {
		return montoTotalAprobado;
	}
	public void setMontoTotalAprobado(BigDecimal montoTotalAprobado) {
		this.montoTotalAprobado = montoTotalAprobado;
	}
	public BigDecimal getMontoTotalSolicitado() {
		return montoTotalSolicitado;
	}
	public void setMontoTotalSolicitado(BigDecimal montoTotalSolicitado) {
		this.montoTotalSolicitado = montoTotalSolicitado;
	}
	public boolean isRequiereContextoDeEncriptacion() {
		return requiereContextoDeEncriptacion;
	}
	public void setRequiereContextoDeEncriptacion(boolean requiereContextoDeEncriptacion) {
		this.requiereContextoDeEncriptacion = requiereContextoDeEncriptacion;
	}
	public boolean isDesplegarEnParteYContraparte() {
		return desplegarEnParteYContraparte;
	}
	public void setDesplegarEnParteYContraparte(boolean desplegarEnParteYContraparte) {
		this.desplegarEnParteYContraparte = desplegarEnParteYContraparte;
	}
}