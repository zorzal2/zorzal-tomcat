package com.fontar.data.impl.domain.codes.evaluacion;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.util.ResourceManager;

/**
 * Enumeración de Resultados de Evaluacion
 */
public enum ResultadoEvaluacion implements Enumerable {

	APROBADO("aprobado", Recomendacion.APROBADO, true) ,
	APRO_MODIF_MONTO("aprobadoModificacionMonto", Recomendacion.APROBADO_CON_MODIF_MONTO, true),
	RECHAZADO("rechazado",Recomendacion.RECHAZADO, false),
	A_DEFINIR("sinDecision",Recomendacion.A_DEFINIR, false),
	A_EVALUAR("aEvaluar",Recomendacion.A_DEFINIR, false),
	ADJUDICADO("adjudicado",Recomendacion.ADJUDICADO, true),
	NO_ADJUDICADO("noAdjudicado",Recomendacion.NO_ADJUDICADO, true),
	APROB_ADJ("aprobadoAdjudicado",Recomendacion.APROBADO_ADJUDICADO, true), //TODO: a Confirmar si la recom. no deberia quedar en aprobado
	APROB_MM_ADJ("aprobadoModificacionMontoAdjudicado",Recomendacion.APROBADO_MM_ADJUDICADO, true); //TODO: a Confirmar si la recom. no deberia quedar en aprobado mm

	
	
	private String descripcion;
	
	private Recomendacion recomendacionAsociada;
	private boolean implicaAprobacion;

	public Recomendacion getRecomendacionProyecto() {
		return this.recomendacionAsociada;
	}
	
	private ResultadoEvaluacion(String key, Recomendacion recomendacion, boolean implicaAprobacion) {
		this.descripcion = ResourceManager.getCodesResource( "app.codes.evaluacion.resultado." + key);
		this.recomendacionAsociada = recomendacion;
		this.implicaAprobacion = implicaAprobacion;
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
	
	public static ResultadoEvaluacion paraRecomendacion(Recomendacion recomendacion, Boolean permiteAdjudicacion) {
		Recomendacion rActual = recomendacion;
		
		//si el instrumento permite adjudicación, en caso de aprobación, toma la recomendación para "aprob y adj" 
		if (permiteAdjudicacion) {
			if (Recomendacion.APROBADO.equals(recomendacion))
				rActual = Recomendacion.APROBADO_ADJUDICADO;
			else if (Recomendacion.APROBADO_CON_MODIF_MONTO.equals(recomendacion))
				rActual = Recomendacion.APROBADO_MM_ADJUDICADO; 
		}
		
		//obtiene el resultado asociado a la recomendación
		for(ResultadoEvaluacion resultado : ResultadoEvaluacion.values()) {
			if(resultado.getRecomendacionProyecto().equals(rActual)) return resultado;
		}
		return null;
	}
	
	public boolean implicaAprobacion() {
		return implicaAprobacion;
	}

	public boolean potencialAprobacion() {
		return implicaAprobacion || this==A_DEFINIR || this==A_EVALUAR;
	}
}