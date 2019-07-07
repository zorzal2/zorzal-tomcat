package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;

/**
 * 
 * @author gboaglio
 * 
 */

public class IdeaProyectoDTO extends DTO implements EvaluableDTO {

	private static final long serialVersionUID = 1L;

	private Long codigoIdeaProyecto;

	private Date fechaIngreso;

	private BigDecimal montoTotal;

	private BigDecimal montoSolicitado;

	private Long idJurisdiccion;

	private Long idEntidadBeneficiaria;

	private Long idTipoProyecto;

	private String titulo;

	private String resumen;

	private String instrumentoSolicitado;

	private String observaciones;

	private String id;

	private EstadoIdeaProyecto estado;

	private String entidadBeneficiaria;
	
	private String jurisdiccion;
	
	private Integer duracion;
	
	private Long idPersonaLegal;

	private PersonaDTO personaLegal;

	private Long idPersonaDirector;

	private PersonaDTO personaDirector;

	private Long idPersonaRepresentante;

	private PersonaDTO personaRepresentante;


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

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public Long getIdTipoProyecto() {
		return idTipoProyecto;
	}

	public void setIdTipoProyecto(Long idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public String getInstrumentoSolicitado() {
		return instrumentoSolicitado;
	}

	public void setInstrumentoSolicitado(String instrumentoSolicitado) {
		this.instrumentoSolicitado = instrumentoSolicitado;
	}

	public BigDecimal getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getCodigoIdeaProyecto() {
		return codigoIdeaProyecto;
	}

	public void setCodigoIdeaProyecto(Long codigoIdeaProyecto) {
		this.codigoIdeaProyecto = codigoIdeaProyecto;
	}

	public String getId() {
		return this.id;
	}

	public String getDescripcionEstado() {
		return this.getEstado().getDescripcion();
	}

	public EstadoIdeaProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoIdeaProyecto estado) {
		this.estado = estado;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	
	public String getTxtPersonaDirector() {
		return personaDirector==null ? "" : personaDirector.getNombre();
	}
	public String getTxtPersonaLegal() {
		return personaLegal==null ? "" : personaLegal.getNombre();
	}
	public String getTxtPersonaRepresentante() {
		return personaRepresentante==null ? "" : personaRepresentante.getNombre();
	}
}
