package com.fontar.data.impl.domain.codes.notificacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de estados de una <code>Notificacion</code>.<br>
 * @author ssanchez
 */
public enum EstadoNotificacion implements Enumerable {

	PENDIENTE_ENVIO("app.codes.notificacio.estado.pendienteEnvio"), 
	PENDIENTE_ACUSE("app.codes.notificacio.estado.pendienteAcuse"),
	CERRADA("app.codes.notificacio.estado.cerrada"),
	ANULADA("app.codes.notificacio.estado.anulada"),
	FINALIZADA("app.codes.notificacio.estado.finalizada"); 
	
	private String descripcion;

	private EstadoNotificacion(String key) {
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
		return this.descripcion;
	}
}
