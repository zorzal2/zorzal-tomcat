package com.fontar.data.impl.domain.bean;

import com.fontar.data.impl.domain.codes.entidad.TipoEntidad;

/**
 * Los objetos de esta clase representan otras entidades que participan (además de la beneficiaria) 
 * en la ejecución de un proyecto. Para esta entidades que participan se establecer 
 * el tipo de entidad, la función y la relación contractual en relación al proyecto. 
 */
public class EntidadIntervinientesBean extends Auditable {

	private Long id;
	
	private Long idEntidad;
	
	private TipoEntidad tipoEntidad;

	private String funcion;

	private String relacion;
	
	private Boolean activo;

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getRelacion() {
		return relacion;
	}

	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}

	public TipoEntidad getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(TipoEntidad tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

}
