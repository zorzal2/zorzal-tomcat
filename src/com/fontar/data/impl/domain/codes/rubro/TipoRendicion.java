package com.fontar.data.impl.domain.codes.rubro;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum TipoRendicion implements Enumerable {
	GENERAL("app.codes.rendicion.tipo.general"),
	RECURSO_HUMANO_ADICIONAL("app.codes.rendicion.tipo.rrhhAdicional"),
	RECURSO_HUMANO_PROPIO("app.codes.rendicion.tipo.rrhhPropio"),
	CANON_INSTITUCIONAL("app.codes.rendicion.tipo.canonInstitucional"),
	DIRECTOR_EXPERTO("app.codes.rendicion.tipo.directorExperto"),
	CONSEJERO_TECNOLOGICO("app.codes.rendicion.tipo.consejeroTecnologico"),
	CONSULTOR("app.codes.rendicion.tipo.consultor");
		
	private String descripcion;

	private TipoRendicion(String key) {
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
	
	public static TipoRendicion getByDescription(String description) {
		for(TipoRendicion tr : values()) {
			if(tr.getDescripcion().equals(description)) return tr;
		}
		return null;
	}
}
