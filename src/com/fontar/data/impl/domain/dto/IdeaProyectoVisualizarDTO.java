package com.fontar.data.impl.domain.dto;

import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;
import com.pragma.util.StringUtil;

public class IdeaProyectoVisualizarDTO extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String codigoIdeaProyecto;

	private String fechaIngreso;

	private String tipoProyecto;
	
	private String titulo;
	
	private String resumen;
	
	private String instrumentoSolicitado;
	
	private EncryptedObject montoTotal;
	
	private EncryptedObject montoSolicitado;
	
	private String jurisdiccion;
	
	private String entidadBeneficiaria;
	
	private String observaciones;

	private String instrumentoRecomendado;
	
	private Integer duracion;
	
	private Long idPersonaLegal;

	private PersonaDTO personaLegal;

	private Long idPersonaDirector;

	private PersonaDTO personaDirector;

	private Long idPersonaRepresentante;

	private PersonaDTO personaRepresentante;

	public String getCodigoIdeaProyecto() {
		return codigoIdeaProyecto;
	}

	public void setCodigoIdeaProyecto(String codigoIdeaProyecto) {
		this.codigoIdeaProyecto = codigoIdeaProyecto;
	}

	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getInstrumentoSolicitado() {
		return instrumentoSolicitado;
	}

	public void setInstrumentoSolicitado(String instrumentoSolicitado) {
		this.instrumentoSolicitado = instrumentoSolicitado;
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}


	public EncryptedObject getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(EncryptedObject montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	public EncryptedObject getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(EncryptedObject montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMontoTotalAsString() {
		try {
			return StringUtil.formatMoneyForPresentation((Number)getMontoTotal().getObject());
		}
		catch (Exception e) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}
	public String getMontoSolicitadoAsString() {
		try {
			return StringUtil.formatMoneyForPresentation((Number)this.getMontoSolicitado().getObject());
		}
		catch (Exception e) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
		
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getInstrumentoRecomendado() {
		return instrumentoRecomendado;
	}

	public void setInstrumentoRecomendado(String recomendacion) {
		this.instrumentoRecomendado = recomendacion;
	}

	public Long getIdPersonaDirector() {
		return idPersonaDirector;
	}

	public void setIdPersonaDirector(Long idPersonaDirector) {
		this.idPersonaDirector = idPersonaDirector;
	}

	public Long getIdPersonaLegal() {
		return idPersonaLegal;
	}

	public void setIdPersonaLegal(Long idPersonaLegal) {
		this.idPersonaLegal = idPersonaLegal;
	}

	public Long getIdPersonaRepresentante() {
		return idPersonaRepresentante;
	}

	public void setIdPersonaRepresentante(Long idPersonaRepresentante) {
		this.idPersonaRepresentante = idPersonaRepresentante;
	}

	public PersonaDTO getPersonaDirector() {
		return personaDirector;
	}

	public void setPersonaDirector(PersonaDTO personaDirector) {
		this.personaDirector = personaDirector;
	}

	public PersonaDTO getPersonaLegal() {
		return personaLegal;
	}

	public void setPersonaLegal(PersonaDTO personaLegal) {
		this.personaLegal = personaLegal;
	}

	public PersonaDTO getPersonaRepresentante() {
		return personaRepresentante;
	}

	public void setPersonaRepresentante(PersonaDTO personaRepresentante) {
		this.personaRepresentante = personaRepresentante;
	}
}
