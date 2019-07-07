package com.fontar.data.impl.domain.bean;
/**
 * Estos objetos corresponden a la Clasificación Industrial Internacional Uniforme (CIIU).
 * Esta clasificacion se emplea para los proyectos y las Entidades Beneficiarias.
 */
public class CiiuBean {

	private Long id;

	private String nombre;

	private String codigo;

	private CiiuBean padre;

	private Long idPadre;

	public Long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
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

	public CiiuBean getPadre() {
		return padre;
	}

	public void setPadre(CiiuBean padre) {
		this.padre = padre;
	}
}
