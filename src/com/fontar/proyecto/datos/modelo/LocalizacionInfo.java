package com.fontar.proyecto.datos.modelo;

import com.fontar.data.impl.domain.dto.LocalizacionDTO;

public class LocalizacionInfo {

	private String direccion;
	private String localidad;
	private String jurisdiccion;
	private Long   idJurisdiccion;
	private String pais;
	private String telefono;
	private String email;
	private String web;
	private String cp;
	private String fax;

	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}
	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}
	public LocalizacionDTO toDto() {
		LocalizacionDTO dto = new LocalizacionDTO();
		dto.setCodigoPostal(this.getCp());
		dto.setDireccion(this.getDireccion());
		dto.setEmail(this.getEmail());
		dto.setFax(this.getFax());
		dto.setIdJurisdiccion(this.getIdJurisdiccion());
		dto.setLocalidad(this.getLocalidad());
		dto.setPais(this.getPais());
		dto.setTelefono(this.getTelefono());
		dto.setPaginaWeb(this.getWeb());
		return dto;
	}
}
