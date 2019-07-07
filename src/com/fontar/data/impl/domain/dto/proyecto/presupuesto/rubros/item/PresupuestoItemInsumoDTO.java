package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item;


public class PresupuestoItemInsumoDTO extends PresupuestoItemDTO {
	private String unidadMedida;
	private Double cantidad;
	private Double costoUnitario;
	
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getCostoUnitario() {
		return costoUnitario;
	}
	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
}
