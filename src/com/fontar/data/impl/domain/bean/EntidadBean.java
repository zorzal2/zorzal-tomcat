package com.fontar.data.impl.domain.bean;

import com.fontar.util.ResourceManager;
import com.pragma.util.StringUtil;

/**
 * Esta clase representa todas la entidades del sistema. 
 * Hay entidades Benficiarias, Entidades Bancarias y Entidades Evaluadoras, 
 * todas ellas comparten esta información. 
 */
public class EntidadBean {

	private Long id;

	private String denominacion;

	private String cuit;

	private String contacto;

	private String descripcion;

	private Boolean activo;

	private Boolean borrado = false;

	private Boolean evaluadora;

	private Boolean bancaria;

	private Boolean beneficiaria;

	private LocalizacionBean localizacion;

	private Long idLocalizacion;

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

	public LocalizacionBean getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionBean localizacion) {
		this.localizacion = localizacion;
	}

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}
	public void destroy() {
		setDenominacion(ResourceManager.getInformationalResource("app.msj.deleted", getDenominacion()));
		if(StringUtil.isNotEmpty(getCuit())) {
			//Evito que la entidad tenga el mismo cuit que una futura.
			setCuit("["+getCuit()+"]");
		}
	}
}
