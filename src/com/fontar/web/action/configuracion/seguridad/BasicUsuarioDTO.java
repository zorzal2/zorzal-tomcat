package com.fontar.web.action.configuracion.seguridad;


public class BasicUsuarioDTO   {
	private String userId;
	private String username;
	private String nombre;
	private String apellido;
	private String nombrePersona;

	public boolean equals(Object obj) {
		if(obj instanceof BasicUsuarioDTO)
			return userId.equals(((BasicUsuarioDTO)obj).getUserId());
		else return false;
	}
	public int hashCode() {
		return userId.hashCode();
	}
	
	public String getApellido() {
		return apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreCompleto() {
		return this.apellido + ", " + this.nombre;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
}
