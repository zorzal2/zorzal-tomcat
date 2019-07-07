package com.fontar.data.impl.domain.bean;

import com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente;

/**
 * Estos objetos representan una clase particular de instrumentos de beneficios.
 * En el caso que el interesado (una entidad Benficiaria) haya presentado una idea proyecto
 * la cual resultó aprobada/elegible el interesado puede presentar 
 * el proyecto concreto asociado al instrumento de ventanilla permanente recomentado.  
 * @see com.fontar.data.impl.domain.bean.InstrumentoBean
 * @see com.fontar.data.impl.domain.bean.IdeaProyectoBean
 * @see com.fontar.data.impl.domain.bean.ProyectoBean
 */
public class VentanillaPermanenteBean extends InstrumentoBean {

	EstadoVentanillaPermanente estado;

	/*** 
	 * Extension para los proyectos CAE
	 * **/
	private Boolean aceptaIdeaProyecto;
	
	public EstadoVentanillaPermanente getEstado() {
		return estado;
	}

	public void setEstado(EstadoVentanillaPermanente estado) {
		this.estado = estado;
	}

	@Override
	protected String getCodigoEstado() {
		return estado.name();
	}

	@Override
	protected void setCodigoEstado(String codigoEstado) {
		estado = EstadoVentanillaPermanente.valueOf(codigoEstado);
	}

	public Boolean getAceptaIdeaProyecto() {
		return aceptaIdeaProyecto;
	}

	public void setAceptaIdeaProyecto(Boolean aceptaIdeaProyecto) {
		this.aceptaIdeaProyecto = aceptaIdeaProyecto;
	}
	
	
	
}
