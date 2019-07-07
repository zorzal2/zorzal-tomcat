package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

public class ControlFacturasDTO {
 	
	private String numeroFactura;
	private String nombreProveedor;
	private String descripcion;
	private BigDecimal montoTotal;
	private String seguimiento;
	private String codigoProyecto;
	private String empresa;
	
	
	
	public ControlFacturasDTO(String numeroFactura, String nombreProveedor, String descripcion, 
								BigDecimal montoTotal, String seguimiento, String codigoProyecto, String empresa) {
		
		this.numeroFactura = numeroFactura;
		this.nombreProveedor = nombreProveedor;
		this.descripcion = descripcion;
		this.montoTotal = montoTotal;
		this.seguimiento = seguimiento;
		this.codigoProyecto = codigoProyecto;
		this.empresa = empresa;
	}
	
	public String getCodigoProyecto() {
		return codigoProyecto;
	}
	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}	
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}	
	public String getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(String seguimiento) {
		this.seguimiento = seguimiento;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(");
		sb.append(this.numeroFactura).append(",");
		sb.append(this.nombreProveedor).append(",");
		sb.append(this.descripcion).append(",");
		sb.append(this.montoTotal).append(",");
		sb.append(this.seguimiento).append(",");
		sb.append(this.codigoProyecto).append(",");
		sb.append(this.empresa).append(",");
		sb.append(")");
		
		return sb.toString();
	}
	
}
