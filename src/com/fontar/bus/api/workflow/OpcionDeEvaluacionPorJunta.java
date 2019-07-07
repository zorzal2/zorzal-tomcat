package com.fontar.bus.api.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbpm.graph.def.Transition;

import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;

public enum OpcionDeEvaluacionPorJunta {
	ELEGIBLE(
			"app.codes.ideaProyecto.estado.elegible",	//Clave en Codes.properties 
			"SI", 										//Nombre de transicion de salida
			ResultadoEvaluacion.APROBADO,				//Resultado de la evaluacion
			EstadoIdeaProyecto.ELEGIBLE,				//Estado en el que queda la ideaProyecto al elegir esta opcion
			TipoNotificacion.RESULT_JNTA_APROB,			//Tipo de notificacion que debe generarse (opcional)
			true										//Requiere que se ingrese una recomendacion de instrumento?
	),
	
	NO_ELEGIBLE(
			"app.codes.ideaProyecto.estado.noElegible",	//Clave en Codes.properties                                   
			"NO", 										//Nombre de transicion de salida                              
			ResultadoEvaluacion.RECHAZADO,				//Resultado de la evaluacion                                  
			EstadoIdeaProyecto.NO_ELEGIBLE,				//Estado en el que queda la ideaProyecto al elegir esta opcion
			TipoNotificacion.RESULT_JNTA_NO_APROB,		//Tipo de notificacion que debe generarse (opcional)
			false										//Requiere que se ingrese una recomendacion de instrumento?
	),
	
	PENDIENTE(
			"app.codes.ideaProyecto.estado.pendiente",	//Clave en Codes.properties                                   
			"PENDIENTE", 								//Nombre de transicion de salida                              
			ResultadoEvaluacion.A_DEFINIR,				//Resultado de la evaluacion                                  
			EstadoIdeaProyecto.PENDIENTE				//Estado en el que queda la ideaProyecto al elegir esta opcion
	);
	
	private String key;
	private String transitionName;
	private TipoNotificacion tipoDeNotificacion;
	private ResultadoEvaluacion resultadoEvaluacion; 
	private EstadoIdeaProyecto estadoIdeaProyecto;
	private boolean requiereEspecificacionDeInstrumentoRecomendado;
	
	public boolean requiereEspecificacionDeInstrumentoRecomendado() {
		return requiereEspecificacionDeInstrumentoRecomendado;
	}

	public String getTransitionName() {
		return transitionName;
	}

	private OpcionDeEvaluacionPorJunta(String key, String transitionName, ResultadoEvaluacion resultadoEvaluacion, EstadoIdeaProyecto estadoIdeaProyecto) {
		this(key, transitionName, resultadoEvaluacion, estadoIdeaProyecto, null, false);
	}
	private OpcionDeEvaluacionPorJunta(String key, String transitionName, ResultadoEvaluacion resultadoEvaluacion, EstadoIdeaProyecto estadoIdeaProyecto, TipoNotificacion tipoDeNotificacion, boolean requiereEspecificacionDeInstrumentoRecomendado) {
		this.key = key;
		this.transitionName = transitionName;
		this.tipoDeNotificacion = tipoDeNotificacion;
		this.resultadoEvaluacion = resultadoEvaluacion;
		this.estadoIdeaProyecto = estadoIdeaProyecto;
		this.requiereEspecificacionDeInstrumentoRecomendado = requiereEspecificacionDeInstrumentoRecomendado;
	}

	public String getKey() {
		return key;
	}
	/**
	 * Devuelve la opción de evaluación por junta correspondiente al nombre de la 
	 * transición dado. 
	 * @return
	 */
	public static OpcionDeEvaluacionPorJunta porNombreDeTransicion(String nombre) {
		for (OpcionDeEvaluacionPorJunta opcion : values()) {
			if(opcion.getTransitionName().equalsIgnoreCase(nombre)) return opcion;
		}
		return null;
	}

	public static List<OpcionDeEvaluacionPorJunta> porTransiciones(Collection<Transition> transitions) {
		List<OpcionDeEvaluacionPorJunta> ret = new ArrayList<OpcionDeEvaluacionPorJunta>();
		for (OpcionDeEvaluacionPorJunta opcion : values()) {
			for(Transition transition : transitions) {
				if(opcion.getTransitionName().equalsIgnoreCase(transition.getName()))
					ret.add(opcion);
			}
		}
		return ret;
	}
	
	public String getName() {
		return name();
	}
	/**
	 * Determina si corresponde iniciar un circuito de notificación
	 * al elegir esta opción.
	 * @return
	 */
	public boolean requiereNotificacion() {
		return tipoDeNotificacion != null;
	}
	/**
	 * Tipo de notificacion que genera esta opción o null si
	 * no corresponde generar una notificación.
	 * @return
	 */
	public TipoNotificacion getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}
	/**
	 * Resultado de la evaluacion por junta asociado a la
	 * eleccion de esta opcion.
	 * @return
	 */
	public ResultadoEvaluacion getResultadoEvaluacion() {
		return resultadoEvaluacion;
	}
	/**
	 * Estado en el que debe quedar la idea proyecto al elegir esta opcion.
	 * @return
	 */
	public EstadoIdeaProyecto getEstadoIdeaProyecto() {
		return estadoIdeaProyecto;
	}
}
