package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item;


public class PresupuestoItemConsultorBean extends PresupuestoItemBean {

	private static final long serialVersionUID = 1L;
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
