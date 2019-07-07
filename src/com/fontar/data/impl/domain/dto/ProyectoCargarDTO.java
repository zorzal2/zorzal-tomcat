package com.fontar.data.impl.domain.dto;


/**
 * Dto usado en el ActionForm CargarProyectoDynaForm para devolver los 
 * datos ingresados en el formulario
 * 
 * @author ssanchez
 *
 */
public class ProyectoCargarDTO extends ProyectoEdicionDTO {

	private static final long serialVersionUID = 1L;
	
	private Long idProyectoPitec;
	private String tipoProyecto;

	public Long getIdProyectoPitec() {
		return idProyectoPitec;
	}
	public void setIdProyectoPitec(Long idProyectoPitec) {
		this.idProyectoPitec = idProyectoPitec;
	}
	public String getTipoProyecto() {
		return tipoProyecto;
	}
	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}
 }
