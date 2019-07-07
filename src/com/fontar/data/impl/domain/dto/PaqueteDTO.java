package com.fontar.data.impl.domain.dto;

import java.util.Collection;

import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;

public class PaqueteDTO {

	private Long id;

	private String tipo;

	private TratamientoPaquete tratamiento;

	private String estado;

	private Long idInstrumento;

	private String codigoActa;

	private String observacion;

	private String comision;

	private Collection<ProyectoFilaDTO> filasProyectos;

	private String denominacionInstrumento;

	private String descInstrumento;
	
	private TipoPaquete tipoPaquete;
	
	private Boolean esCreditoFiscal;
	
	private Boolean instrumentoPermiteAdjudicacion;

	public Boolean getInstrumentoPermiteAdjudicacion() {
		return instrumentoPermiteAdjudicacion;
	}

	public void setInstrumentoPermiteAdjudicacion(Boolean instrumentoPermiteAdjudicacion) {
		this.instrumentoPermiteAdjudicacion = instrumentoPermiteAdjudicacion;
	}

	public Boolean getEsCreditoFiscal() {
		return esCreditoFiscal;
	}

	public void setEsCreditoFiscal(Boolean esCreditoFiscal) {
		this.esCreditoFiscal = esCreditoFiscal;
	}

	public String getCodigoActa() {
		return codigoActa;
	}

	public void setCodigoActa(String codigoActa) {
		this.codigoActa = codigoActa;
	}

	public Collection<ProyectoFilaDTO> getFilasProyectos() {
		return filasProyectos;
	}

	public void setFilasProyectos(Collection<ProyectoFilaDTO> filasProyectos) {
		this.filasProyectos = filasProyectos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public TratamientoPaquete getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(TratamientoPaquete tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public String getDescInstrumento() {
		return descInstrumento;
	}

	public void setDescInstrumento(String descInstrumento) {
		this.descInstrumento = descInstrumento;
	}

	public void setDenominacionInstrumento(String denominacionInstrumento) {
		this.denominacionInstrumento = denominacionInstrumento;
	}

	public String getDenominacionInstrumento() {
		return denominacionInstrumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoPaquete getTipoPaquete() {
		return tipoPaquete;
	}

	public void setTipoPaquete(TipoPaquete tipoPaquete) {
		this.tipoPaquete = tipoPaquete;
	}
}
