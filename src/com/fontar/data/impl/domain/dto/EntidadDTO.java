package com.fontar.data.impl.domain.dto;

public class EntidadDTO {

	private Long id;

	private String denominacion;

	private String cuit;

	private String contacto;

	private String descripcion;

	private Boolean activo;

	private Boolean evaluadora;

	private Boolean bancaria;

	private Boolean beneficiaria;

	private LocalizacionDTO localizacion;

	private Long idLocalizacion;

	private EntidadBeneficiariaDTO entidadBeneficiaria;

	private EntidadEvaluadoraDTO entidadEvaluadora;

	public EntidadBeneficiariaDTO getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(EntidadBeneficiariaDTO entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public EntidadEvaluadoraDTO getEntidadEvaluadora() {
		return entidadEvaluadora;
	}

	public void setEntidadEvaluadora(EntidadEvaluadoraDTO entidadEvaluadora) {
		this.entidadEvaluadora = entidadEvaluadora;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getEvaluadora() {
		return evaluadora;
	}

	public void setEvaluadora(Boolean evaluadora) {
		this.evaluadora = evaluadora;
	}

	public Boolean getBancaria() {
		return bancaria;
	}

	public void setBancaria(Boolean bancaria) {
		this.bancaria = bancaria;
	}

	public Boolean getBeneficiaria() {
		return beneficiaria;
	}

	public void setBeneficiaria(Boolean beneficiaria) {
		this.beneficiaria = beneficiaria;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLocalizacion() {
		return idLocalizacion;
	}

	public void setIdLocalizacion(Long idLocalizacion) {
		this.idLocalizacion = idLocalizacion;
	}

	public LocalizacionDTO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionDTO localizacion) {
		this.localizacion = localizacion;
	}
}
