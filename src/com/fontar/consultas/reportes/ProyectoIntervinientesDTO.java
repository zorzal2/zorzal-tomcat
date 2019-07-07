package com.fontar.consultas.reportes;

import java.math.BigDecimal;

public class ProyectoIntervinientesDTO {

	private String denominacion;
	
	private String codigo;
	
	private String identificador;
	
	private String entidadBeneficiaria;
	
	private String titulo;
	
	private String jurisdiccion;
	
	private BigDecimal montoTotal;
	
	private BigDecimal montoFontar;
	
	private String codigoCiiu;
	
	private String estado;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCiiu() {
		return codigoCiiu;
	}

	public void setCodigoCiiu(String codigoCiiu) {
		this.codigoCiiu = codigoCiiu;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public BigDecimal getMontoFontar() {
		return montoFontar;
	}

	public void setMontoFontar(BigDecimal montoFontar) {
		this.montoFontar = montoFontar;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ProyectoIntervinientesDTO() {
		this.setMontoFontar(BigDecimal.ZERO);
		this.setMontoTotal(BigDecimal.ZERO);
		this.setCodigoCiiu("");
	}
}
