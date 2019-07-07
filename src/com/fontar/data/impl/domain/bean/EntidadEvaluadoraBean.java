package com.fontar.data.impl.domain.bean;

import java.util.Set;

/**
 * Esta clase contiene las entidades Evaluadoras,
 * las cuales son aquellas que actuan que agrupan evaluadores 
 * que luego son asignados en las distintas evaluaciones de los proyectos.
 */
public class EntidadEvaluadoraBean extends Entidable {
	private Long id;
	private String nroCBU;
	private String nroCuenta;
	private String nombreBeneficiario;
	private Long idEntidadBancaria;
	private EntidadBancariaBean entidadBancaria;
	private Set<EvaluadorBean> evaluadores;

	/** **************************************************** */

	public String getCuit() {

		return (getEntidad() == null) ? "Entidad=NULL" : getEntidad().getCuit();
	}

	public String getDenominacion() {

		return (getEntidad() == null) ? "Entidad=NULL" : getEntidad().getDenominacion();
	}

	/** **************************************************** */

	public String getNroCBU() {
		return this.nroCBU;
	}

	public void setNroCBU(String nroCBU) {
		this.nroCBU = nroCBU;
	}

	public String getNroCuenta() {
		return this.nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public String getNombreBeneficiario() {
		return this.nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEntidadBancaria() {
		return this.idEntidadBancaria;
	}

	public void setIdEntidadBancaria(Long idEntidadBancaria) {
		this.idEntidadBancaria = idEntidadBancaria;
	}

	public EntidadBancariaBean getEntidadBancaria() {
		return entidadBancaria;
	}

	public void setEntidadBancaria(EntidadBancariaBean entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}

	public Set<EvaluadorBean> getEvaluadores() {
		return evaluadores;
	}

	public void setEvaluadores(Set<EvaluadorBean> evaluadores) {
		this.evaluadores = evaluadores;
	}

}