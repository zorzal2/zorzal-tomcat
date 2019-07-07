package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

/** 
 * @author gboaglio
 */
public class InstrumentosMontosDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long idInstrumento;	
	private BigDecimal montoTotal;
	private BigDecimal montoParte;
	private BigDecimal montoContraparte;
	private int cantidadProyectosPresentados;
	private int cantidadProyectosAprobados;

	public InstrumentosMontosDTO(Long idInstrumento,BigDecimal montoParte, BigDecimal montoContraparte,BigDecimal montoTotal, int presentados, int aprobados) {
		this.idInstrumento = idInstrumento;
		this.montoParte = montoParte;
		this.montoContraparte = montoContraparte;
		this.montoTotal = montoTotal;
		this.cantidadProyectosPresentados = presentados;
		this.cantidadProyectosAprobados = aprobados;
	}
	
	public Long getIdInstrumento() {
		return idInstrumento;
	}
	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}
	public BigDecimal getMontoContraparte() {
		return montoContraparte;
	}
	public void setMontoContraparte(BigDecimal montoContraparte) {
		this.montoContraparte = montoContraparte;
	}
	public BigDecimal getMontoParte() {
		return montoParte;
	}
	public void setMontoParte(BigDecimal montoParte) {
		this.montoParte = montoParte;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public int getCantidadProyectosAprobados() {
		return cantidadProyectosAprobados;
	}

	public void setCantidadProyectosAprobados(int cantidadProyectosAprobados) {
		this.cantidadProyectosAprobados = cantidadProyectosAprobados;
	}

	public int getCantidadProyectosPresentados() {
		return cantidadProyectosPresentados;
	}

	public void setCantidadProyectosPresentados(int cantidadProyectosPresentados) {
		this.cantidadProyectosPresentados = cantidadProyectosPresentados;
	}	

		
}
