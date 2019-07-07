package com.fontar.data.impl.domain.dto;

import java.util.Collection;

public class MatrizCriterioDTO {

	private String idMatrizCriterio;
	private Boolean activo;
	
	private String denominacion;
	private Collection<MatrizCriterioItemDTO> criterioList;

	public Collection<MatrizCriterioItemDTO> getCriterioList() {
		return criterioList;
	}
	public void setCriterioList(Collection<MatrizCriterioItemDTO> criterioList) {
		this.criterioList = criterioList;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getIdMatrizCriterio() {
		return idMatrizCriterio;
	}
	public void setIdMatrizCriterio(String idMatrizCriterio) {
		this.idMatrizCriterio = idMatrizCriterio;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}