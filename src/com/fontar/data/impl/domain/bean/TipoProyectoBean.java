package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan una clasificación de proyectos.
 * La clasificación para cada proyecto se define en los objetos ProyectoDatosBean.
 * En los instrumentos de beneficio tambien se emplea esta clasificación 
 * para definir determinados aspectos de acuedo al tipo de proyecto (ver DistribucionTipoProyectoBean).  
 * 
 * @see com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean
 * @see com.fontar.data.impl.domain.bean.ProyectoDatosBean
 */
public class TipoProyectoBean {

	private Long id;

	private String nombre;

	private String nombreCorto;

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
}
