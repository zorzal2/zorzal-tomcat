package com.fontar.data.impl.domain.dto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;

/**
 * Dto para la cabecera de proyecto
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoCabeceraDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String instrumento;
	private String txtEntidadBeneficiaria;
	private Enumerable estado;
	private Boolean estaEnReconsideracion;
	private Boolean estaEnPaquete;
	private Boolean aplicaCargaAlicuotaCF;
		
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Enumerable getEstado() {
		return estado;
	}
	public void setEstado(Enumerable estado) {
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
	public Boolean getIsCerrado() {
		if(this.estado != null)
			return (this.estado.equals(EstadoProyecto.CERRADO));
		else
			return false;
	}
	public Boolean getAplicaCargaAlicuotaCF() {
		return aplicaCargaAlicuotaCF;
	}
	public void setAplicaCargaAlicuotaCF(Boolean aplicaCargaAlicuotaCF) {
		this.aplicaCargaAlicuotaCF = aplicaCargaAlicuotaCF;
	}
}