package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.seguridad.EncryptedObject;

/**
 * DTO para los datos de los Proyectos que se muestran en la vista de
 * visualización de un Paquete.
 * 
 * @author ssanchez
 * @version 1.00, 29/11/06
 */

public class VisualizarProyectoFilaDTO {

	private Long idProyecto;
	
	private Long idPaquete;

	private String resultado;

	private String proyecto;

	private String nombreEntidad;

	private String titulo;

	private BigDecimal montoSolicitado;

	private BigDecimal porcentajeSolicitado;

	private BigDecimal montoAprobado;

	private BigDecimal montoAdjudicado;

	private EncryptedObject recomendacion;

	private String tipoPaquete;

	private String tratamientoPaquete;

	private Boolean permiteAdjudicacionInstrumento;

	private List<EvaluacionResumenDTO> evaluaciones;

	public String getTipoPaquete() {
		return tipoPaquete;
	}

	public void setTipoPaquete(String tipoPaquete) {
		this.tipoPaquete = tipoPaquete;
	}

	public String getTratamientoPaquete() {
		return tratamientoPaquete;
	}

	public void setTratamientoPaquete(String tratamientoPaquete) {
		this.tratamientoPaquete = tratamientoPaquete;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String codigo) {
		this.proyecto = codigo;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
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

	public BigDecimal getPorcentajeSolicitado() {
		return porcentajeSolicitado;
	}

	public void setPorcentajeSolicitado(BigDecimal porcentajeSolicitado) {
		this.porcentajeSolicitado = porcentajeSolicitado;
	}

	

	public Recomendacion getRecomendacion() throws SecurityException {
		return (Recomendacion) recomendacion.getObject();
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

	public Long getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public boolean getPermiteAdjudicacionInstrumento() {
		return permiteAdjudicacionInstrumento;
	}

	public void setPermiteAdjudicacionInstrumento(Boolean permiteAdjudicacionInstrumento) {
		this.permiteAdjudicacionInstrumento = permiteAdjudicacionInstrumento;
	}

	public List<EvaluacionResumenDTO> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionResumenDTO> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Long getIdPaquete() {
		return idPaquete;
	}

	public void setIdPaquete(Long idPaquete) {
		this.idPaquete = idPaquete;
	}
}