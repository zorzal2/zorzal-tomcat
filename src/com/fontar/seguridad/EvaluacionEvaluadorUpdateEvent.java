package com.fontar.seguridad;

import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;


/**
 * Representa un evento de cambio de asignacion de evaluadores a evaluacion.
 * @author llobeto
 *
 */
public class EvaluacionEvaluadorUpdateEvent  {

	private EvaluacionGeneralBean evaluacion;

	public EvaluacionEvaluadorUpdateEvent(EvaluacionGeneralBean evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public EvaluacionGeneralBean getEvaluacion() {
		return evaluacion;
	}
}
