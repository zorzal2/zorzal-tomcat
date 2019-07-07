package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;



public class VisualizarPacItemDTO {

	private String id;
	
	private String idMoneda;
	
	private String item;
	
	private String etapa;
	
	private String rubro;
	
	private String montoEstimado;
	
	private String montoAdjudicado;
	
	private BigDecimal montoAdjudicadoValor;
	
	private BigDecimal montoDesembolsado;
	
	private String montoTotalPedidoStr;
	
	private String montoADesembolsarStr;
	
	private String adquisicion;
	
	private String fechaEstimada;
	
	private String fechaAdjudicado;
	
	private String descripcion;

	public String getAdquisicion() {
		return adquisicion;
	}

	public void setAdquisicion(String adquisicion) {
		this.adquisicion = adquisicion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(String fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMontoAdjudicado() {
		return montoAdjudicado;
	}

	public void setMontoAdjudicado(String montoAdjudicado) {
		this.montoAdjudicado = montoAdjudicado;
	}

	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	public String getMontoTotalPedidoStr() {
		return montoTotalPedidoStr;
	}

	public void setMontoTotalPedidoStr(String montoTotalPedido) {
		this.montoTotalPedidoStr = montoTotalPedido;
	}
	
	public String getMontoEstimado() {
		return montoEstimado;
	}

	public void setMontoEstimado(String montoEstimado) {
		this.montoEstimado = montoEstimado;
	}

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public String getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(String idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getFechaAdjudicado() {
		return fechaAdjudicado;
	}

	public void setFechaAdjudicado(String fechaAdjudicado) {
		this.fechaAdjudicado = fechaAdjudicado;
	}

	public BigDecimal getMontoAdjudicadoValor() {
		return montoAdjudicadoValor;
	}

	public void setMontoAdjudicadoValor(BigDecimal montoAdjudicadoValor) {
		this.montoAdjudicadoValor = montoAdjudicadoValor;
	}

	public String getMontoADesembolsarStr() {
		return montoADesembolsarStr;
	}

	public void setMontoADesembolsarStr(String montoADesembolsarStr) {
		this.montoADesembolsarStr = montoADesembolsarStr;
	}


}
