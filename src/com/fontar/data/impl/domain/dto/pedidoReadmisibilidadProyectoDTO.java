package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;

/**
 * Dto de admisibilidad de un proyecto
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */
public class pedidoReadmisibilidadProyectoDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String instrumento;

	private String txtEntidadBeneficiaria;

	private EstadoProyecto estado;

	private Boolean estaEnReconsideracion;

	private Boolean estaEnPaquete;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
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

	public String getTxtEntidadBeneficiaria() {
		return txtEntidadBeneficiaria;
	}

	public void setTxtEntidadBeneficiaria(String txtEntidadBeneficiaria) {
		this.txtEntidadBeneficiaria = txtEntidadBeneficiaria;
	}
}
