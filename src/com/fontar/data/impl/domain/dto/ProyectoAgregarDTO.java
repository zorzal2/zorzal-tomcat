package com.fontar.data.impl.domain.dto;

/**
 * 
 * @author gboaglio
 * 
 */
public class ProyectoAgregarDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String entidadBeneficiariaOrigen;

	private Long idPresentacion;
	
	private Long idInstrumento;
	
	private String codigoPresentacion;

	private Boolean permiteFinanciamientoBancario;
	
	public void readFrom(ProyectoAgregarDTO other) {
		entidadBeneficiariaOrigen = other.entidadBeneficiariaOrigen;
		idPresentacion = other.idPresentacion;
		idInstrumento = other.idInstrumento;
		codigoPresentacion = other.codigoPresentacion;
		permiteFinanciamientoBancario = other.permiteFinanciamientoBancario;		
	}

	public String getEntidadBeneficiariaOrigen() {
		return entidadBeneficiariaOrigen;
	}

	public void setEntidadBeneficiariaOrigen(String entidadBeneficiariaOrigen) {
		this.entidadBeneficiariaOrigen = entidadBeneficiariaOrigen;
	}

	public String getCodigoPresentacion() {
		return codigoPresentacion;
	}

	public void setCodigoPresentacion(String codigoPresentacion) {
		this.codigoPresentacion = codigoPresentacion;
	}

	public Boolean getPermiteFinanciamientoBancario() {
		return permiteFinanciamientoBancario;
	}

	public void setPermiteFinanciamientoBancario(Boolean permiteFinanciamientoBancario) {
		this.permiteFinanciamientoBancario = permiteFinanciamientoBancario;
	}

	public Long getIdPresentacion() {
		return idPresentacion;
	}

	public void setIdPresentacion(Long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public void updateWith(ProyectoAgregarDTO other) {
		if(this.entidadBeneficiariaOrigen==null) this.entidadBeneficiariaOrigen = other.entidadBeneficiariaOrigen;
		if(this.idPresentacion==null) this.idPresentacion = other.idPresentacion;
		if(this.idInstrumento==null) this.idInstrumento = other.idInstrumento;
		if(this.codigoPresentacion==null) this.codigoPresentacion = other.codigoPresentacion;
		if(this.permiteFinanciamientoBancario==null) this.permiteFinanciamientoBancario = other.permiteFinanciamientoBancario;	
	}

}
