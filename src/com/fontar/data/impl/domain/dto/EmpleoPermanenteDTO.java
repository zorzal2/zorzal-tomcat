package com.fontar.data.impl.domain.dto;

public class EmpleoPermanenteDTO {

	private Long id;

	private Integer cantidadTecnicos;
	private String	cantidadTecnicosStr;

	private Integer cantidadOperariosCalificados;
	private String	cantidadOperariosCalificadosStr;
	
	private Integer cantidadOperariosNoCalificados;
	private String	cantidadOperariosNoCalificadosStr;

	private Integer cantidadProfesionales;
	private String	cantidadProfesionalesStr;

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

	public String getCantidadOperariosCalificadosStr() {
		return cantidadOperariosCalificadosStr;
	}

	public void setCantidadOperariosCalificadosStr(String cantidadOperariosCalificadosStr) {
		this.cantidadOperariosCalificadosStr = cantidadOperariosCalificadosStr==null? "" : cantidadOperariosCalificadosStr;
		try {
			this.cantidadOperariosCalificados = Integer.valueOf(cantidadOperariosCalificadosStr);
		} catch (Exception e) {
			this.cantidadOperariosCalificados = null;
		}
	}

	public String getCantidadOperariosNoCalificadosStr() {
		return cantidadOperariosNoCalificadosStr;
	}

	public void setCantidadOperariosNoCalificadosStr(String cantidadOperariosNoCalificadosStr) {
		this.cantidadOperariosNoCalificadosStr = cantidadOperariosNoCalificadosStr==null? "" : cantidadOperariosNoCalificadosStr;
		try {
			this.cantidadOperariosNoCalificados = Integer.valueOf(cantidadOperariosNoCalificadosStr);
		} catch (Exception e) {
			this.cantidadOperariosNoCalificados = null;
		}
	}

	public String getCantidadProfesionalesStr() {
		return cantidadProfesionalesStr;
	}

	public void setCantidadProfesionalesStr(String cantidadProfesionalesStr) {
		this.cantidadProfesionalesStr = cantidadProfesionalesStr==null? "" : cantidadProfesionalesStr;
		try {
			this.cantidadProfesionales = Integer.valueOf(cantidadProfesionalesStr);
		} catch (Exception e) {
			this.cantidadProfesionales = null;
		}
	}

	public String getCantidadTecnicosStr() {
		return cantidadTecnicosStr;
	}

	public void setCantidadTecnicosStr(String cantidadTecnicosStr) {
		this.cantidadTecnicosStr = cantidadTecnicosStr==null? "" : cantidadTecnicosStr;
		try {
			this.cantidadTecnicos = Integer.valueOf(cantidadTecnicosStr);
		} catch (Exception e) {
			this.cantidadTecnicos = null;
		}
	}
}
