package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan las jurisdicciones legales para la presentación de proyectos y
 * al mismo tiempo las provincias para las distintas locacizaciones (empleadas en entidades, proyectos, etc).
 * Las jurisficciones básicamente corresponden a las provincias del pais Argentina con la opción adicional de <code>Nación</code>.
 * @see com.fontar.data.impl.domain.bean.LocalizacionBean  
 */
public class JurisdiccionBean {

	private Long id;

	private String codigo;

	private String descripcion;

	private Boolean activo;
	
	private RegionBean region;
	
	private Long idRegion;
	
	private String emerix;


	public RegionBean getRegion() {
		return region;
	}

	public void setRegion(RegionBean region) {
		this.region = region;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public String getEmerix() {
		return emerix;
	}

	public void setEmerix(String emerix) {
		this.emerix = emerix;
	}
}
