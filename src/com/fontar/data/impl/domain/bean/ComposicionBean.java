package com.fontar.data.impl.domain.bean;

/**
 * En estos objetos se representan las relacionas que establecer la composicion de entidadades beneficarias.
 */
public class ComposicionBean {

	private Long id;

	private Long idEntidadBeneficiaria;

	private Long idEntidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

}
