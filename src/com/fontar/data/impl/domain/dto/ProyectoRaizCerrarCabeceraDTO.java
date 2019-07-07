package com.fontar.data.impl.domain.dto;

import com.fontar.data.api.domain.codes.Enumerable;

public class ProyectoRaizCerrarCabeceraDTO extends ProyectoRaizCerrarDTO {

	private static final long serialVersionUID = 1L;

	private ProyectoRaizCerrarDTO proyectoRaizCerrarDTO = new ProyectoRaizCerrarDTO();

	private String codigo;

	private String instrumento;

	private String txtEntidadBeneficiaria;

	private Enumerable estado;

	private Boolean estaEnReconsideracion;

	private Boolean estaEnPaquete;

	public Enumerable getEstado() {
		return estado;
	}

	public void setEstado(Enumerable estado) {
		this.estado = estado;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getEstaEnPaquete() {
		return estaEnPaquete;
	}

	public void setEstaEnPaquete(Boolean estaEnPaquete) {
		this.estaEnPaquete = estaEnPaquete;
	}

	public Boolean getEstaEnReconsideracion() {
		return estaEnReconsideracion;
	}

	public void setEstaEnReconsideracion(Boolean estaEnReconsideracion) {
		this.estaEnReconsideracion = estaEnReconsideracion;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public ProyectoRaizCerrarDTO getProyectoRaizCerrarDTO() {
		return proyectoRaizCerrarDTO;
	}

	public void setProyectoRaizCerrarDTO(ProyectoRaizCerrarDTO proyectoRaizCerrarDTO) {
		this.proyectoRaizCerrarDTO = proyectoRaizCerrarDTO;
	}

	public String getTxtEntidadBeneficiaria() {
		return txtEntidadBeneficiaria;
	}

	public void setTxtEntidadBeneficiaria(String txtEntidadBeneficiaria) {
		this.txtEntidadBeneficiaria = txtEntidadBeneficiaria;
	}

	public String getEstadoEnPaquete() {
		return proyectoRaizCerrarDTO.getEstadoEnPaquete();
	}

	public void setEstadoEnPaquete(String estadoEnPaquete) {
		proyectoRaizCerrarDTO.setEstadoEnPaquete(estadoEnPaquete);
	}

	public String getClase() {
		return proyectoRaizCerrarDTO.getClase();
	}

	public void setClase(String clase) {
		proyectoRaizCerrarDTO.setClase(clase);
	}

}
