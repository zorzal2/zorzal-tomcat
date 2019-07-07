package com.fontar.consultas;

public abstract class Informe {

	private Resolver resolver;

	private String nombre;
	
	private String descripcion;

	private String titulo;
	
	private String permissionRequired;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Resolver getResolver() {
		return resolver;
	}

	public void setResolver(Resolver resolver) {
		this.resolver = resolver;
	}

	public String getPermissionRequired() {
		return permissionRequired;
	}

	public void setPermissionRequired(String permissionRequired) {
		this.permissionRequired = permissionRequired;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
}
