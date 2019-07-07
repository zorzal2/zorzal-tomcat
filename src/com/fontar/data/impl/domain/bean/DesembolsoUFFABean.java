package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Los desembolsos se registran para cada elemento del PAC (Plan de Adquisiciones Consolidado).
 * Se registra la entrega de un importe (puede ser parcial con respecto al monto original) para el item de PAC.    
 * @see com.fontar.data.impl.domain.bean.PacBean 
 */
public class DesembolsoUFFABean {

	private Long id;
	
	private Long idPac;
	
	private PacBean pac;
	
	private String ordenPago;
	
	private Integer cuota;
	
	private Date fechaPedido;
	
	private BigDecimal montoDesembolsado;
	
	private Date fechaPago;
	
	private BigDecimal montoPago;
	
	private Boolean esAnticipo;
	
	private Long idMoneda;
	
	private MonedaBean moneda;

	public Integer getCuota() {
		return cuota;
	}

	public void setCuota(Integer cuota) {
		this.cuota = cuota;
	}

	public Boolean getEsAnticipo() {
		return esAnticipo;
	}

	public void setEsAnticipo(Boolean esAnticipo) {
		this.esAnticipo = esAnticipo;
	}

	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
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

	public Long getIdPac() {
		return idPac;
	}

	public void setIdPac(Long idPac) {
		this.idPac = idPac;
	}

	public MonedaBean getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaBean moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public String getOrdenPago() {
		return ordenPago;
	}

	public void setOrdenPago(String ordenPago) {
		this.ordenPago = ordenPago;
	}

	public PacBean getPac() {
		return pac;
	}

	public void setPac(PacBean pac) {
		this.pac = pac;
	}
	
}
