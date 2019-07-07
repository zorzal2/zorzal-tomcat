package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representa las distintas áreas de evaluación técnica.
 * Una persona evaluadora cotiene asignaciones dentro de estas categorias.
 * @see com.fontar.data.impl.domain.bean.EvaluadorBean   
 */
public class EspecialidadEvaluadorBean {

	private Long id;

	private String codigo;

	private String nombre;

	private Boolean activo;

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