package com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion;

import com.fontar.data.api.domain.codes.Enumerable;

public interface ResultadoProcedimiento extends Enumerable {

	/**
	 * Determina si el estado implica retroceso. Esto es, si es un estado
	 * que no permite avance hacia la aprobación y el pago. Se cumple si
	 * el estado es NO_AUTORIZADO o DESIERTO
	 * @return
	 */
	public abstract boolean esNegativo();
	public abstract String getName();

}