package com.fontar.data.impl.domain.codes.proyecto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum ProximoPaso implements Enumerable {

	
	EVALUAR("app.codes.proyecto.paso.evaluar"),
	COMISION("app.codes.proyecto.paso.comision"),
	SECRETARIA("app.codes.proyecto.paso.secretaria"),
	DIRECTORIO_EVAL("app.codes.proyecto.paso.directorioe");
		
	private String descripcion;

	private ProximoPaso(String key) {
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
