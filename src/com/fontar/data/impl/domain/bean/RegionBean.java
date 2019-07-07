package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan un grupo de zonas del pais Argentina. 
 * Estas zonas agrupan jurisdicciones, donde una jurisdicci�n puede corresponder a una �nica regi�n.
 * La clasificaci�n en regiones tambi�n se emplea en relaci�n a los instrumentos 
 * para registrar cierta informaci�n del instrumento en funci�n de las regiones. 
 *    
 * @see com.fontar.data.impl.domain.bean.JurisdiccionBean
 * @see com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean
 */
public class RegionBean {

	private Long id;

	private String nombre;

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


}
