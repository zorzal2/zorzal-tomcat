package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;

/**
 * DTO para los datos de los Proyectos que se muestran en la vista de
 * modificación de un Paquete
 * 
 * @author ssanchez
 * 
 */

public class ProyectoFilaModificacionPaqueteDTO {

	private Long idProyecto;
	
	private Long idInstrumento;

	private String entidadBeneficiaria;

	private String titulo;

	private EncryptedObject recomendacion;

	private Boolean esActivo;

	private String codigo;

	private BigDecimal montoSolicitado;

	private BigDecimal montoAprobado;

	private BigDecimal montoAdjudicado;

	private List<EvaluacionResumenDTO> evaluaciones;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(String entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	

	public Recomendacion getRecomendacion() {
		if(recomendacion==null) return null;
		else return (Recomendacion) recomendacion.getObject();
	}

	public void setRecomendacion(EncryptedObject recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<EvaluacionResumenDTO> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionResumenDTO> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public BigDecimal getMontoAdjudicado() {
		return montoAdjudicado;
	}

	public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
		this.montoAdjudicado = montoAdjudicado;
	}

	public BigDecimal getMontoAprobado() {
		return montoAprobado;
	}

	public void setMontoAprobado(BigDecimal montoAprobado) {
		this.montoAprobado = montoAprobado;
	}

	public BigDecimal getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}
	
	public String getDescripcionRecomendacion(){
		return ObjectUtils.encriptedEnumSafeGet(this.recomendacion);
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}
}
