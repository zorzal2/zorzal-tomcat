package com.fontar.proyecto.datos.modelo;

import java.util.Date;

import com.pragma.util.StringUtil;

public class PersonaInfo {
	private String nombre;
	private String sexo;
	private Date fechaNacimiento;
	private String cuit;
	private LocalizacionInfo localizacion = new LocalizacionInfo();
	
	public LocalizacionInfo getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(LocalizacionInfo localizacion) {
		this.localizacion = localizacion;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public boolean isEmpty() {
		return !StringUtil.anyNotEmpty(
			nombre,
			sexo,
			cuit,
			
			localizacion.getDireccion(),
			localizacion.getLocalidad(),
			localizacion.getJurisdiccion(),
			localizacion.getPais(),
			localizacion.getTelefono(),
			localizacion.getEmail(),
			localizacion.getWeb(),
			localizacion.getCp(),
			localizacion.getFax()
		);
	}
	public long getIdentification() {
		return 	(((long)System.identityHashCode(getCuit()))<<30)
			+	((long)System.identityHashCode(getNombre()));
	}
}
