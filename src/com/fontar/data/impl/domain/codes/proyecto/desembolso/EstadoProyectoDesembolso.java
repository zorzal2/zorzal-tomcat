package com.fontar.data.impl.domain.codes.proyecto.desembolso;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * @author ssanchez
 */
public enum EstadoProyectoDesembolso implements Enumerable {
	
	PAGADO("pagado"),
	AUTORIZADO("autorizado"),
	NO_PAGADO("nopagado");
		
	private String descripcion;

	private EstadoProyectoDesembolso(String key) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.proyecto.desembolso.estado." + key);
		
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
