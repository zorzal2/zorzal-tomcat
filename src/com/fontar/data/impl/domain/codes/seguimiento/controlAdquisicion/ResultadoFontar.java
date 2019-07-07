package com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion;

import com.fontar.util.ResourceManager;

/**
 * Enumeración de los resultados fontar
 * del circuito de autorización.<br>
 * @author ssanchez
 */
public enum ResultadoFontar implements ResultadoProcedimiento {

	APROB_PEND_UFFA("aprobadoPendienteUffa"), 
	APROB_PEND_BID("aprobadoPendienteBid"),
	NO_AUTORIZADO("noAutorizado"),
	DESIERTO("desierto");
	
	private String descripcion;
	
	private ResultadoFontar(String key) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.controlAdquisicion.resultadoFontar." + key);
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
	/* (non-Javadoc)
	 * @see com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoProcedimiento#esNegativo()
	 */
	public boolean esNegativo() {
		return this.equals(NO_AUTORIZADO) || this.equals(DESIERTO);
	}
	/**
	 * Determina si el resultado no es definitivo y depende de aprobacion
	 * de la Uffa o el Bid.
	 * @return
	 */
	public boolean enEsperaDeAprobacionExterna() {
		return this.equals(APROB_PEND_UFFA) || this.equals(APROB_PEND_BID);
	}
}
