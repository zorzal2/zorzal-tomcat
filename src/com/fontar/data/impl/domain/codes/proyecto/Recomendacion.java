package com.fontar.data.impl.domain.codes.proyecto;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum Recomendacion implements Enumerable {

	
	APROBADO("aprobado", true),
	APROBADO_CON_MODIF_MONTO("aprobadoModificacionMonto", true),
	RECHAZADO("rechazado", false),
	A_DEFINIR("aDefinir", false),
	ADJUDICADO("adjudicado", true),
	NO_ADJUDICADO("noAdjudicado", true),
    APROBADO_ADJUDICADO("aprobadoAdjudicado", true),
    APROBADO_MM_ADJUDICADO("aprobadoMmAdjudicado", true);
	
	private String descripcion;
	private boolean implicaAprobacion;

	private Recomendacion(String key, boolean implicaAprobacion) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.proyecto.recomendacion." + key );
		this.implicaAprobacion = implicaAprobacion;
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
	
	public boolean implicaAprobacion() {
		return implicaAprobacion;
	}
}
