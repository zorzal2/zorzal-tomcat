package com.fontar.data.impl.domain.dto;


/**
 * Dto para la cabecera de proyecto
 * @author ssanchez
 * @version 1.00, 08/01/07
 */

public class ProyectoRaizCabeceraDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String instrumento;
	private String tipoInstrumentoDef;
	private Boolean permiteAdquisicion;
	private String entidadBeneficiaria;
	private String estado;
	private String estadoExtraInfo;
	private Integer duracion;
	
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstadoExtraInfo() {
		return estadoExtraInfo;
	}
	public void setEstadoExtraInfo(String estadoExtraInfo) {
		this.estadoExtraInfo = estadoExtraInfo;
	}
	boolean hasExtraInfo() {
		return estadoExtraInfo!=null;
	} 
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
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
}