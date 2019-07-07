package com.fontar.data.impl.domain.codes.proyecto.pac;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

/**
 * @author ssanchez
 */
public enum EstadoPacItem implements Enumerable {
	
	PENDIENTE_DE_COMPRA("pendienteDeCompra"),
	EN_PROCESO_DE_COMPRA("enProcesoDeCompra"),
	ADJUDICADO("adjudicado"),
	DESEMBOLSADO("desembolsado"),
	ANULADO("anulado");
		
	private String descripcion;

	private EstadoPacItem(String key) {
		this.descripcion = ResourceManager.getCodesResource("app.codes.proyecto.pac.estado." + key);
		
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
