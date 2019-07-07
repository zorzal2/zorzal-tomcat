package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;

/**
 * Dto para la cabecera de paquete
 * @author ssanchez
 * @version 1.00, 12/01/07
 */

public class PaqueteCabeceraDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long idPaquete;
	private TipoPaquete tipoPaquete;
	private String instrumento;
	private String comision;
	private TratamientoPaquete tratamiento;
	private EstadoPaquete estado;
	
	public String getComision() {
		return comision;
	}
	public void setComision(String comision) {
		this.comision = comision;
	}
	public EstadoPaquete getEstado() {
		return estado;
	}
	public void setEstado(EstadoPaquete estado) {
		this.estado = estado;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	public TratamientoPaquete getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(TratamientoPaquete tratamiento) {
		this.tratamiento = tratamiento;
	}
	public Long getIdPaquete() {
		return idPaquete;
	}
	public void setIdPaquete(Long idPaquete) {
		this.idPaquete = idPaquete;
	}
	
	public Boolean getEsComision() {
		return (TipoPaquete.COMISION.equals(this.tipoPaquete));
	}
	
	public TipoPaquete getTipoPaquete() {
		return tipoPaquete;
	}
	public void setTipoPaquete(TipoPaquete tipoPaquete) {
		this.tipoPaquete = tipoPaquete;
	}

}
