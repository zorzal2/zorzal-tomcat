package com.fontar.seguridad;

public interface EvaluacionEvaluadorSecurityConfigInterceptor {
	/** 
	 * Maneja la asignacion ante un cambio en el vinculo entre evaluacion
	 * y evaluador.
	 **/
	public void update(EvaluacionEvaluadorUpdateEvent updateEvent);
}