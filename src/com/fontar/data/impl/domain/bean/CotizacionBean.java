package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.dto.CotizacionDTO;


/**
 * Estos objetos son registros de cotización en pesos para fechas diarias 
 * de las distintas monedas utilizadas en el sistema.
 * @see CotizacionDTO.hbm.xml
 */
public class CotizacionBean {

	private Long id;
	private Date fecha;
	private BigDecimal monto;
	private Long idMoneda;
	private MonedaBean moneda;
	
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
	public MonedaBean getMoneda() {
		return moneda;
	}
	public void setMoneda(MonedaBean moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
}