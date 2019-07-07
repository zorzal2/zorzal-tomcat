package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;

/**
 * Dto para la cabecera de <code>Evaluacion de Seguimiento</code>.<br> 
 * @author ssanchez
 */
public class EvaluacionSeguimientoCabeceraDTO {

	private static final long serialVersionUID = 1L;
	
	private String idProyecto;
	private String idInstrumento;
	private String idSeguimiento;
	private String idEvaluacion;
	private EstadoEvaluacion estado;
	private String tipo;
	private String fecha;
	private String entidadBeneficiaria;
	private String descripcionDeSeguimiento;
	

	public EstadoEvaluacion getEstado() {
		return estado;
	}
	public void setEstado(EstadoEvaluacion estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdEvaluacion() {
		return idEvaluacion;
	}
	public void setIdEvaluacion(String idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public String getIdInstrumento() {
		return idInstrumento;
	}
	public void setIdInstrumento(String idInstrumento) {
		this.idInstrumento = idInstrumento;
	}
	public String getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}
	public String getIdSeguimiento() {
		return idSeguimiento;
	}
	public void setIdSeguimiento(String idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}
	public void setEntidadBeneficiaria(String idEntidadBeneficiaria) {
		this.entidadBeneficiaria = idEntidadBeneficiaria;
	}
	public String getDescripcionDeSeguimiento() {
		return descripcionDeSeguimiento;
	}
	public void setDescripcionDeSeguimiento(String descripcionDeSeguimiento) {
		this.descripcionDeSeguimiento = descripcionDeSeguimiento;
	}
}