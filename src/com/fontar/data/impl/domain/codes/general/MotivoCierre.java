package com.fontar.data.impl.domain.codes.general;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Tipos de Paquete
 */
public enum MotivoCierre implements Enumerable {

	FINALIZO_POSIBILIDAD_RECONSIDERACION("app.codes.general.motivo.finalizoReconsideracion"),
	DISOLUCION("app.codes.general.motivo.disolucion"),
	RECHAZO_JURISDICCION("app.codes.general.motivo.jurisdiccion"),
	RECONSIDERACION_RECHAZADA("app.codes.general.motivo.rechazada"),
	RECHAZO_ADMISION("app.codes.general.motivo.admision"),
	OTRO("app.codes.general.motivo.otro");

	private String descripcion;

	private MotivoCierre(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
