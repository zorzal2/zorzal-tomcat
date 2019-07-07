package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros;

import com.pragma.util.interfaces.DTO;


public class PresupuestoRubroDTO implements DTO {

	private Long id;
	private RubroDTO rubro;
	private Double montoParte; 
	private Double montoContraparte;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMontoContraparte() {
		return montoContraparte;
	}
	public void setMontoContraparte(Double montoContraparte) {
		this.montoContraparte = montoContraparte;
	}
	public Double getMontoParte() {
		return montoParte;
	}
	public void setMontoParte(Double montoParte) {
		this.montoParte = montoParte;
	}
	public RubroDTO getRubro() {
		return rubro;
	}
	public void setRubro(RubroDTO rubro) {
		this.rubro = rubro;
	}
}