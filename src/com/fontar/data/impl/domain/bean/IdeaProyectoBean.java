package com.fontar.data.impl.domain.bean;

import java.util.HashSet;
import java.util.Set;

import com.fontar.bus.api.ventanilla.IdeaProyectoService;
import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.pragma.util.ContextUtil;

/**
 * Las Ideas proyectos son requeridas previamente a presentar proyecto por instrumentos de Ventanilla Permanente. 
 * Anteriormente a que una entidad beneficiaria pueda presentar un proyecto bajo un instrumento 
 * de Beneficio de Ventanilla Permanente ésta debe presentar y tener aprobada su respectiva Idea Proyecto.<br>
 * Las Ideas Proyectos se modelan como una clase particular de proyectos (<code>proyectosRaiz</code>).
 * Los distintos estado de una idea proyecto se definen en <code>EstadoIdeaProyecto</code>.  
 * @see EstadoIdeaProyecto
 */
public class IdeaProyectoBean extends ProyectoRaizBean implements Adjuntable {

	private Long codigoIdeaProyecto;
	
	private String instrumentoRecomendado = null;

	private EstadoIdeaProyecto estado;

	private Set adjuntos = new HashSet();
	
	public IdeaProyectoBean() {
		super();
		//getInstrumentoRecomendado();
	}

	public Long getCodigoIdeaProyecto() {
		return codigoIdeaProyecto;
	}

	public void setCodigoIdeaProyecto(Long codigoIdeaProyecto) {
		this.codigoIdeaProyecto = codigoIdeaProyecto;
	}

	public boolean estaElegible() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.ELEGIBLE);
		}
		else {
			return false;
		}
	}	
	
	public boolean estaIniciada() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.INICIADO);
		}
		else {
			return false;
		}
	}

	public boolean estaFinalizada() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.FINALIZADO);
		}
		else {
			return false;
		}
	}

	public boolean estaAnulada() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.ANULADO);
		}
		else {
			return false;
		}
	}

	public boolean estaEnEvaluacion() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.EVALUACION);
		}
		else {
			return false;
		}
	}

	public boolean estaCerrada() {
		if (this.getEstado() != null) {
			return this.getEstado().equals(EstadoIdeaProyecto.CERRADO);
		}
		else {
			return false;
		}
	}

	public void setEstadoElegible() {
		this.setEstado(EstadoIdeaProyecto.ELEGIBLE);
	}

	public void setEstadoIniciada() {
		this.setEstado(EstadoIdeaProyecto.INICIADO);
	}

	public void setEstadoFinalizada() {
		this.setEstado(EstadoIdeaProyecto.FINALIZADO);
	}
	
	public void setEstadoAnulada() {
		this.setEstado(EstadoIdeaProyecto.ANULADO);
	}

	public void setEstadoPendiente() {
		this.setEstado(EstadoIdeaProyecto.PENDIENTE);
	}

	public void setEstadoEnEvaluacion() {
		this.setEstado(EstadoIdeaProyecto.EVALUACION);
	}

	public void setEstadoCerrada() {
		this.setEstado(EstadoIdeaProyecto.CERRADO);
	}

	public EstadoIdeaProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoIdeaProyecto estado) {
		this.estado = estado;
	}

	@Override
	protected String getCodigoEstado() {
		return estado.name();
	}

	@Override
	protected void setCodigoEstado(String codigoEstado) {
		estado = EstadoIdeaProyecto.valueOf(codigoEstado);
	}

	@Override
	public void cerrarProyecto() {
		this.estado = EstadoIdeaProyecto.CERRADO;
	}

	@Override
	public void anularProyecto() {
		this.estado = EstadoIdeaProyecto.ANULADO;
	}
	
	@Override
	public void reconsiderarProyecto() {
		this.estado = EstadoIdeaProyecto.EVALUACION;
	}
	
	@Override
	public void enProcesoEvaluacion() {
		// TODO: Chequear condiciones para evaluación
		this.estado = EstadoIdeaProyecto.EVALUACION;
	}
	
	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

	public String getInstrumentoRecomendado() {
		if(instrumentoRecomendado==null)
			instrumentoRecomendado = baseGetInstrumentoRecomendado();
		return instrumentoRecomendado;
	}
	private String baseGetInstrumentoRecomendado() {
		IdeaProyectoService evaluacionService = ContextUtil.getBean("ideaProyectoService");
		EvaluacionBean evaluacion = evaluacionService.getUltimaEvaluacionPorJunta(getId());
		if(evaluacion==null) return null;
		
		String recomendacion = evaluacion.getRecomendacion();
		if(recomendacion==null) return null;
		return recomendacion;
	}
}