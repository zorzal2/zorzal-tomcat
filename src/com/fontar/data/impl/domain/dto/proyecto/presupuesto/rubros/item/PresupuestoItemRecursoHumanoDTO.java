package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.item;


public abstract class PresupuestoItemRecursoHumanoDTO extends PresupuestoItemDTO {
	private String profesion;
	private String funcion;
	private String identificacionTributaria;
	private Double costoMensual;
	private Double dedicacion;
	private Double participacion;

	public Double getDedicacion() {
		return dedicacion;
	}
	public void setDedicacion(Double dedicacion) {
		this.dedicacion = dedicacion;
	}
	public String getFuncion() {
		return funcion;
	}
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	public String getIdentificacionTributaria() {
		return identificacionTributaria;
	}
	public void setIdentificacionTributaria(String identificacionTributaria) {
		this.identificacionTributaria = identificacionTributaria;
	}
	public Double getParticipacion() {
		return participacion;
	}
	public void setParticipacion(Double mesesDeParticipacion) {
		this.participacion = mesesDeParticipacion;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public Double getCostoMensual() {
		return costoMensual;
	}
	public void setCostoMensual(Double costoMensual) {
		this.costoMensual = costoMensual;
	}
}
