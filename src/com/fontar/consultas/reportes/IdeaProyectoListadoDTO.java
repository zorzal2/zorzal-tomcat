package com.fontar.consultas.reportes;

import java.math.BigDecimal;

public class IdeaProyectoListadoDTO {

	private String nroIdeaProyecto;
	
	private String entidadBeneficiaria;
	
	private String titulo;
	
	private String fecha;
	
	private String instrumentoSolicitado;
	
	private String oficial;
	
	private BigDecimal montoTotal;
	
	private BigDecimal montoFontar;
	
	private String localizacion;
	
	private String estado;
	
	private String instrumentoAs;
	
	private String resolucion;
	
	private String fechaResolucion;

	public IdeaProyectoListadoDTO() {
		this.setMontoFontar(BigDecimal.ZERO);
		this.setMontoTotal(BigDecimal.ZERO);
		this.setInstrumentoAs("");
		this.setFechaResolucion("");
		this.setResolucion("");
		this.setLocalizacion("");
		this.setInstrumentoSolicitado("");
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(String fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getInstrumentoAs() {
		return instrumentoAs;
	}

	public void setInstrumentoAs(String instrumentoAs) {
		this.instrumentoAs = instrumentoAs;
	}

	public String getInstrumentoSolicitado() {
		return instrumentoSolicitado;
	}

	public void setInstrumentoSolicitado(String instrumentoSolicitado) {
		this.instrumentoSolicitado = instrumentoSolicitado;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
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

	public String getNroIdeaProyecto() {
		return nroIdeaProyecto;
	}

	public void setNroIdeaProyecto(String nroIdeaProyecto) {
		this.nroIdeaProyecto = nroIdeaProyecto;
	}

	public String getOficial() {
		return oficial;
	}

	public void setOficial(String oficial) {
		this.oficial = oficial;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
