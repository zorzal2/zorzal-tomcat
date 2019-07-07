package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;

/**
 * Dto para la cabecera de proyecto
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class IdeaProyectoCabeceraDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long codigoIdeaProyecto;
	private String jurisdiccion;
	private String entidadBeneficiaria;
	private Boolean estaEnReconsideracion;
	private EstadoIdeaProyecto estado;
	

	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}
	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}
	public EstadoIdeaProyecto getEstado() {
		return estado;
	}
	public void setEstado(EstadoIdeaProyecto estado) {
		this.estado = estado;
	}
	public Long getCodigoIdeaProyecto() {
		return codigoIdeaProyecto;
	}
	public void setCodigoIdeaProyecto(Long codigoIdeaProyecto) {
		this.codigoIdeaProyecto = codigoIdeaProyecto;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public Boolean getEstaEnReconsideracion() {
		return estaEnReconsideracion;
	}
	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		this.estaEnReconsideracion = estaEnReconsideracion;
	}
}
