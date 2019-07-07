package com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion;

import com.fontar.util.ResourceManager;

/**
 * Enumeración de los resultados uffa
 * del circuito de autorización.<br>
 * @author ssanchez
 */
public enum ResultadoUffaBid implements ResultadoProcedimiento {

	APROB_PLIEGO("aprobacionPliego"), 
	APROB_PRE_CLASIF("aprobacionPPreClasificacion"),
	APROB_FINAL("aprobacionFinal"),
	NO_AUTORIZADO("noAutorizado"),
	DESIERTO("desierto");
	
	private String descripcion;
	
	private ResultadoUffaBid(String key) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.controlAdquisicion.resultadoUffa." + key);
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
	 * Determina si el estado implica retroceso. Esto es, si es un estado
	 * que no permite avance hacia la aprobación y el pago. Se cumple si
	 * el estado es NO_AUTORIZADO o DESIERTO
	 * @return
	 */
	public boolean esNegativo() {
		return this.equals(NO_AUTORIZADO) || this.equals(DESIERTO);
	}
}
