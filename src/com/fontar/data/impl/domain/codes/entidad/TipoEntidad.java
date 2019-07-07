package com.fontar.data.impl.domain.codes.entidad;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum TipoEntidad implements Enumerable{


	EMPRESA("app.codes.entidadIntervinientes.tipo.empresas"),
	UVT("app.codes.entidadIntervinientes.tipo.unidades"), 
	ENTIDAD_IT("app.codes.entidadIntervinientes.tipo.entidades"), 
	OTROS("app.codes.entidadIntervinientes.tipo.otros");
	

	private String descripcion;

	private TipoEntidad(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
