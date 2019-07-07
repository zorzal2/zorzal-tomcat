package com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * Enumeraci�n de los estados de <code>Control Adquisici�n</code>.<br>
 */
public enum EstadoProcedimiento implements Enumerable {

	INGRESADO("ingresado", true), 
	EN_EVALUACION("enEvaluacion", true),
	CON_EVALUACION("conEvaluacion", true),
	EN_AUTORIZACION("enAutorizacion", true),
	CON_RESULTADO("conResultado", true),
	FINALIZADO("finalizado", false);
	
	private String descripcion;
	private boolean activo;
	
	private EstadoProcedimiento(String key, boolean activo) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.controlAdquisicion.estado." + key);
		this.activo = activo;
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
	/**
	 * Devuelve si el procedimiento no est� en un estado que represente
	 * cancelacion, finalizacion o anulacion.
	 * @return
	 */
	public boolean estaActivo() {
		return activo;
	}
}
