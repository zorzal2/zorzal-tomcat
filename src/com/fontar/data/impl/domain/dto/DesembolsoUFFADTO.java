package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;



public class DesembolsoUFFADTO {

	private Long id;
	
	private Long idPac;
	
	private PacDTO pac;
	
	private String ordenPago;
	
	private Integer cuota;
	
	private Date fechaPedido;
	
	private BigDecimal montoDesembolsado;
	
	private Date fechaPago;
	
	private BigDecimal montoPago;
	
	private Boolean esAnticipo;
	
	private Long idMoneda;
	
	private MonedaDTO moneda;

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

	public MonedaDTO getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaDTO moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
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

	public PacDTO getPac() {
		return pac;
	}

	public void setPac(PacDTO pac) {
		this.pac = pac;
	}

}
