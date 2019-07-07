package com.fontar.data.impl.domain.dto;

public class CabeceraDTO {

	
	private String denominacionInstrumento;
	private String entidadBeneficiaria;
	private String estadoProyecto;	
	private Boolean estaEnReconsideracion;
	
	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}
	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}
	
	public String getEstadoProyecto() {
		return estadoProyecto;
	}
	public void setEstadoProyecto(String estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}
	public String getDenominacionInstrumento() {
		return denominacionInstrumento;
	}
	public void setDenominacionInstrumento(String denominacionInstrumento) {
		this.denominacionInstrumento = denominacionInstrumento;
	}
	public Boolean getEstaEnReconsideracion() {
		return estaEnReconsideracion;
	}
	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		this.estaEnReconsideracion = estaEnReconsideracion;
	}
	
	

}
