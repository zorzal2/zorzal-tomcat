package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.seguridad.EncryptedObject;

public class EvaluacionResumenDTO {

	private TipoEvaluacion tipoEvaluacion;

	private EncryptedObject resultadoEvaluacion;

	public ResultadoEvaluacion getResultadoEvaluacion() throws SecurityException {
		return (ResultadoEvaluacion) resultadoEvaluacion.getObject();
	}

	@Deprecated
	public void setResultadoEvaluacion(ResultadoEvaluacion resultadoEvaluacion) {
		throw new IllegalArgumentException("No puede usarse este setter");
		//FIXME remover en cuanto se haya asgurado que no se utiliza el setter
	}
	
	public void setResultadoEvaluacion(EncryptedObject resultadoEvaluacion) {
		this.resultadoEvaluacion = resultadoEvaluacion;
	}
	
	public TipoEvaluacion getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}



}
