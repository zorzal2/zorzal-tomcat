package com.fontar.data.impl.domain.dto;

/**
 * Dto para la cabecera de presentacion convocatoria
 * @author ssanchez
 * @version 1.00, 10/01/07
 */
public class PresentacionCabeceraDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String convocatoria;
	private String jurisdiccion;
	private String solicitante;

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}
	public String getJurisdiccion() {
		return jurisdiccion;
	}
	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
}
