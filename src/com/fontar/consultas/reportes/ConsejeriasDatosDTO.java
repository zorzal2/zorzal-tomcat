package com.fontar.consultas.reportes;



public class ConsejeriasDatosDTO {
	
	private String identifier;
	
	private String nombre;

	private String profesion;
	
	private String cuil;
	
	private String funcion;
	
	private Double sueldo;
	
	private Double dedicacion;
	
	private Double participacion;
	
	private Double costo;
	
	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

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

	public Double getParticipacion() {
		return participacion;
	}

	public void setParticipacion(Double participacion) {
		this.participacion = participacion;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
