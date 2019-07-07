package com.fontar.bus.impl.seguimientos.seguimientos;

public enum ConceptoDeAnalisisDeGasto {
	CostosTotalesDelProyecto("app.codes.conceptoDeAnalisisDeGasto.CostosTotalesDelProyecto"),
	MontoDeInversionRendidoAnteriormente("app.codes.conceptoDeAnalisisDeGasto.MontoDeInversionRendidoAnteriormente"),
	MontoDeInversionAprobadoAnteriormente("app.codes.conceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente"),
	RendicionActualSolicitada("app.codes.conceptoDeAnalisisDeGasto.RendicionActualSolicitada"),
	RendicionActualAprobada("app.codes.conceptoDeAnalisisDeGasto.RendicionActualAprobada"),
	RendicionActualGestionada("app.codes.conceptoDeAnalisisDeGasto.RendicionActualGestionada"),
	RendicionActualAGestionar("app.codes.conceptoDeAnalisisDeGasto.RendicionActualAGestionar");

	private String key;

	private ConceptoDeAnalisisDeGasto(String key) {
		this.key = key;
	}

	/**
	 * Devuelve la clave que identifica al concepto. Puede ser usada para localizar
	 * la descripción en Codes.properties.
	 * @return
	 */
	public String getKey() {
		return key;
	}
}
