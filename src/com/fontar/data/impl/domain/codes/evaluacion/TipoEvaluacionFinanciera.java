package com.fontar.data.impl.domain.codes.evaluacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.util.ResourceManager;

public enum TipoEvaluacionFinanciera implements Enumerable {

	BANCO("app.codes.evaluacion.tipoEvaluacionFinanciera.banco"),
	EVALUADOR("app.codes.evaluacion.tipoEvaluacionFinanciera.evaluador");

	private String descripcion;

	private TipoEvaluacionFinanciera(String key) {
		this.descripcion = ResourceManager.getCodesResource(key);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}
}
