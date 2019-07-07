package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
/**
 * En un instrumento de beneficio, se puede registrar mediante estos objetos la asignación  
 * del monto de financiamiento total en función de las distintas jurisdicciones o regiones.
 * @see com.fontar.data.impl.domain.bean.InstrumentoBean 
 */
public class DistribucionFinanciamientoBean {

	private Long id;

	private Long idRegion;

	private Long idJurisdiccion;

	private Long idInstrumento;

	private BigDecimal monto;

	private BigDecimal porcentaje;

	private JurisdiccionBean jurisdiccion;

	private RegionBean region;

	public JurisdiccionBean getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(JurisdiccionBean jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public RegionBean getRegion() {
		return region;
	}

	public void setRegion(RegionBean region) {
		this.region = region;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
}