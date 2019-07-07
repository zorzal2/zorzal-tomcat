package com.fontar.data.impl.domain.dto;

/**
 * 
 * @author ttoth
 * 
 */
public class FacturacionDTO {

	private Long id;

	private Long idEntidadBeneficiaria;

	private String[] numeroPeriodico;

	private String[] numeroFacturacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String[] getNumeroFacturacion() {
		return numeroFacturacion;
	}

	public void setNumeroFacturacion(String[] numeroFacturacion) {
		this.numeroFacturacion = numeroFacturacion;
	}

	public String[] getNumeroPeriodico() {
		return numeroPeriodico;
	}

	public void setNumeroPeriodico(String[] numeroPeriodico) {
		this.numeroPeriodico = numeroPeriodico;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}
}
