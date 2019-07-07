package com.fontar.data.impl.domain.bean;

/**
 * Estos objetos representan las sucesivas jurisdicciones que fueron asignadas a un proyecto. 
 * @see com.fontar.data.impl.domain.bean.ProyectoRaizBean
 */
public class ProyectoJurisdiccionBean extends Auditable {

	private Long id;

	private Long idJurisdiccion;

	private String codigo;

	private JurisdiccionBean jurisdiccion;

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

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public JurisdiccionBean getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(JurisdiccionBean jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

}
