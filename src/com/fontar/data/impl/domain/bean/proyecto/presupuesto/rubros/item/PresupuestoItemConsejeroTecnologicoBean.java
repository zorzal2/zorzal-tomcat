package com.fontar.data.impl.domain.bean.proyecto.presupuesto.rubros.item;

public class PresupuestoItemConsejeroTecnologicoBean extends
		PresupuestoItemRecursoHumanoBean {

	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private String categoria;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
