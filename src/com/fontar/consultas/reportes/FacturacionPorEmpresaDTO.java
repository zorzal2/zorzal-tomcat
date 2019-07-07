package com.fontar.consultas.reportes;

import java.math.BigDecimal;

public class FacturacionPorEmpresaDTO {
	private String denominacionDeEntidad;
	private Integer anioDeFacturacion;
	private BigDecimal monto;
	
	
	public FacturacionPorEmpresaDTO(String denominacionDeEntidad, Integer anioDeFacturacion, BigDecimal monto) {
		super();
		this.denominacionDeEntidad = denominacionDeEntidad;
		this.anioDeFacturacion = anioDeFacturacion;
		this.monto = monto;
	}
	public Integer getAnioDeFacturacion() {
		return anioDeFacturacion;
	}
	public String getDenominacionDeEntidad() {
		return denominacionDeEntidad;
	}
	public BigDecimal getMonto() {
		return monto;
	}
}
