package com.fontar.data.impl.domain.dto;

public class EntidadEvaluadoraDTO {

	private Long id;

	private String nroCBU;

	private String nroCuenta;

	private String nombreBeneficiario;

	private Long idEntidadBancaria;

	private EntidadBancariaDTO entidadBancaria;

	public EntidadBancariaDTO getEntidadBancaria() {
		return entidadBancaria;
	}

	public void setEntidadBancaria(EntidadBancariaDTO entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEntidadBancaria() {
		return idEntidadBancaria;
	}

	public void setIdEntidadBancaria(Long idEntidadBancaria) {
		this.idEntidadBancaria = idEntidadBancaria;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getNroCBU() {
		return nroCBU;
	}

	public void setNroCBU(String nroCBU) {
		this.nroCBU = nroCBU;
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
}
