package com.fontar.data.impl.domain.dto;

import com.fontar.data.api.domain.codes.Enumerable;


/**
 * 
 * @author ssanchez
 *
 */
public class ProyectoRaizDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String id;
	private String idEntidadBeneficiaria;
	private String entidadBeneficiaria;
	private String codigo;
	private String clase;
	private Long idPresupuestoOriginal;
	private Long idPresupuesto;
	private Enumerable estado;
	private String instrumento;
	private String tipoInstrumentoDef;
	private Boolean permiteAdquisicion;
	private Integer duracion;
	private String tipoMatrizPresupuesto;
	private Long idPersonaLegal;
	private PersonaDTO personaLegal;
	private Long idPersonaDirector;
	private PersonaDTO personaDirector;
	private Long idPersonaRepresentante;
	private PersonaDTO personaRepresentante;

	public String getTipoMatrizPresupuesto() {
		return tipoMatrizPresupuesto;
	}

	public void setTipoMatrizPresupuesto(String tipoMatrizPresupuesto) {
		this.tipoMatrizPresupuesto = tipoMatrizPresupuesto;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Enumerable getEstado() {
		return estado;
	}

	public void setEstado(Enumerable estado) {
		this.estado = estado;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getId() {
		return id;
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

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(String idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public Long getIdPresupuestoOriginal() {
		return idPresupuestoOriginal;
	}

	public void setIdPresupuestoOriginal(Long idPresupuestoOriginal) {
		this.idPresupuestoOriginal = idPresupuestoOriginal;
	}

	public Boolean getPermiteAdquisicion() {
		return permiteAdquisicion;
	}

	public void setPermiteAdquisicion(Boolean permiteAdquisicion) {
		this.permiteAdquisicion = permiteAdquisicion;
	}

	public String getTipoInstrumentoDef() {
		return tipoInstrumentoDef;
	}

	public void setTipoInstrumentoDef(String tipoInstrumentoDef) {
		this.tipoInstrumentoDef = tipoInstrumentoDef;
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
