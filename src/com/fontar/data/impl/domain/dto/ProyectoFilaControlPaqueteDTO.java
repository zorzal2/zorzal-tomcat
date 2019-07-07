package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;

/**
 * DTO para los datos de los Proyectos que se muestran en la vista de control de
 * un Paquete
 * 
 * @author ssanchez
 * 
 */

public class ProyectoFilaControlPaqueteDTO {

	private Long idProyecto;

	private String entidadBeneficiaria;

	private String titulo;

	private Recomendacion recomendacion;

	private Boolean esActivo;

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
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
