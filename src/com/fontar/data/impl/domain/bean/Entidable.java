package com.fontar.data.impl.domain.bean;

/**
 * Esta clase tiene el objetivo de contener la
 * entidad y se usa para generar una entidad de las clases hijas.
 */
public class Entidable {

	private EntidadBean entidad;

	public EntidadBean getEntidad() {
		if (entidad == null) {
			entidad = new EntidadBean();
		}
		return entidad;
	}

	public void setEntidad(EntidadBean entidad) {
		this.entidad = entidad;
	}
}
