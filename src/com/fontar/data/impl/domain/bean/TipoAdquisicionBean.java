package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan los diversos mecanismos para la adquisicion de bienes 
 * que aplican a los proyectos ARAI, es decir aqellos que tienen PAC. 
 * En un proyecto de este tipo, para cada item perteneciente al PAC se define el tipo de adquisicion a realizar.  
 * A su vez los procedimientos de adquisición corresponden a un unico tipo de adquisicion. 
 * @see com.fontar.data.impl.domain.bean.PacBean
 * @see com.fontar.data.impl.domain.bean.ProcedimientoBean
 */

public class TipoAdquisicionBean {

	private Long id;
	
	private String descripcion;
	
	private String codigo;

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
	
}
