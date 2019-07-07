package com.fontar.data.impl.domain.bean;

/**
 * Este objeto registra datos de la situación de empleo respecto a cada Entidad Benficiaria.
 * Tanto en la Entidad Beneficiaria como en el Proyecto se puede registrar esta información.  
 */
public class EmpleoPermanenteBean {

	private Long id;

	private Integer cantidadTecnicos;

	private Integer cantidadOperariosCalificados;

	private Integer cantidadOperariosNoCalificados;

	private Integer cantidadProfesionales;

	public Integer getCantidadOperariosCalificados() {
		return cantidadOperariosCalificados;
	}

	public void setCantidadOperariosCalificados(Integer cantidadOperariosCalificados) {
		this.cantidadOperariosCalificados = cantidadOperariosCalificados;
	}

	public Integer getCantidadOperariosNoCalificados() {
		return cantidadOperariosNoCalificados;
	}

	public void setCantidadOperariosNoCalificados(Integer cantidadOperariosNoCalificados) {
		this.cantidadOperariosNoCalificados = cantidadOperariosNoCalificados;
	}

	public Integer getCantidadProfesionales() {
		return cantidadProfesionales;
	}

	public void setCantidadProfesionales(Integer cantidadProfesionales) {
		this.cantidadProfesionales = cantidadProfesionales;
	}

	public Integer getCantidadTecnicos() {
		return cantidadTecnicos;
	}

	public void setCantidadTecnicos(Integer cantidadTecnicos) {
		this.cantidadTecnicos = cantidadTecnicos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
