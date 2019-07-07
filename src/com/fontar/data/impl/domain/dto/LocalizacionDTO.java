package com.fontar.data.impl.domain.dto;

		
public class LocalizacionDTO {

	private Long id;

	private String direccion;

	private String localidad;

	private String departamento;

	private String codigoPostal;

	private String pais;

	private String telefono;

	private String fax;

	private String email;

	private String paginaWeb;

	private Long idJurisdiccion;

	private String nombreJurisdiccion;

	public Boolean isEmpty() {
		if (this.getDireccion().equals("") && this.getLocalidad().equals("")
				&& this.getDepartamento().equals("") && this.getCodigoPostal().equals("")
				&& this.getPaginaWeb().equals("") && this.getTelefono().equals("") && this.getFax().equals("")
				&& this.getEmail().equals("") && this.getIdJurisdiccion() == null) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreJurisdiccion() {
		return nombreJurisdiccion;
	}

	public void setNombreJurisdiccion(String nombreJurisdiccion) {
		this.nombreJurisdiccion = nombreJurisdiccion;
	}
}
