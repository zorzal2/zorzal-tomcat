package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item;


public class PresupuestoItemConsultorDTO extends PresupuestoItemDTO {
	private Double costoMensual;
	private Double participacion;

	public Double getParticipacion() {
		return participacion;
	}
	public void setParticipacion(Double mesesDeParticipacion) {
		this.participacion = mesesDeParticipacion;
	}
	public Double getCostoMensual() {
		return costoMensual;
	}
	public void setCostoMensual(Double costoMensual) {
		this.costoMensual = costoMensual;
	}
}
