package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;

public class BitacoraDTO extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idProyecto;

	private Long idSeguimiento;

	private Long idEvaluacion;

	private String idUsuario;

	private String descripcion;

	private String fechaAsunto;

	private TipoBitacora tipo;

	private String fechaRegistro;

	private String tema;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaAsunto() {
		return fechaAsunto;
	}

	public void setFechaAsunto(String fechaAsunto) {
		this.fechaAsunto = fechaAsunto;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Long getIdSeguimiento() {
		return idSeguimiento;
	}

	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public TipoBitacora getTipo() {
		return tipo;
	}

	public void setTipo(TipoBitacora tipo) {
		this.tipo = tipo;
	}
}