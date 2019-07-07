package com.fontar.data.impl.domain.dto;

public class DistribucionFinanciamientoDTO {

	private String id;

	private String idRegion;

	private String idJurisdiccion;

	private String idInstrumento;

	private String monto;

	private String porcentaje;

	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(String idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public String getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(String idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public String getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

}
