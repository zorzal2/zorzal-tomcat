package com.fontar.data.impl.domain.codes.rubro;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum TipoRubro implements Enumerable {
	BIEN("app.codes.rubro.tipo.bien"),
	INSUMO("app.codes.rubro.tipo.insumo"),
	RECURSO_HUMANO_PROPIO("app.codes.rubro.tipo.recursoHumanoPropio"),
	RECURSO_HUMANO_ADICIONAL("app.codes.rubro.tipo.recursoHumanoAdicional"),
	CONSULTOR("app.codes.rubro.tipo.consultor"),
	CONSEJERO_TECNOLOGICO("app.codes.rubro.tipo.consejeroTecnologico"),
	CANON_INSTITUCIONAL("app.codes.rubro.tipo.canonInsitucional"),
	DIRECTOR_EXPERTO("app.codes.rubro.tipo.directorExperto"),
	RUBRO_PADRE("app.codes.rubro.tipo.rubroPadre");
		
	private String descripcion;

	private TipoRubro(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
	
}
