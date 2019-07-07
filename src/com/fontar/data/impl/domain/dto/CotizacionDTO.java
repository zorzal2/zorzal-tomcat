package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author llobeto
 */
public class CotizacionDTO {

	private Long id;
	private Date fecha;
	private BigDecimal monto;
	private Long idMoneda;
	private MonedaDTO moneda;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}
	public MonedaDTO getMoneda() {
		return moneda;
	}
	public void setMoneda(MonedaDTO moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
}