package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.pragma.util.interfaces.Bean;


public abstract class PresupuestoRubroBean implements Bean {

	private Long id;
	private RubroBean rubro;
	private Double montoParte; 
	private Double montoContraparte; 

	public RubroBean getRubro() {
		return rubro;
	}

	public void setRubro(RubroBean rubro) {
		this.rubro = rubro;
	}

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

	public Double getMontoTotal() {
		Double auxParte      = (Double) this.montoParte;
		Double auxContraparte = (Double) this.montoContraparte; 
		return (auxParte + auxContraparte);
	}
}
