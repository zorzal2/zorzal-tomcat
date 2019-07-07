package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;

/**
 * Estos objetos representan una clase particular de instrumentos de beneficios.
 * En este tipo de instrumentos el interesado (una entidad Benficiaria) 
 * puede presentar el proyecto dentro de un perido específico de presentación.
 * Con la presentación se conforma primeramente una <code>PresentacionConvocatoriaBean</code>.     
 * @see com.fontar.data.impl.domain.bean.InstrumentoBean
 * @see com.fontar.bean.PresentacionConvocatoriaBean
 */
public class LlamadoConvocatoriaBean extends InstrumentoBean {

	private Integer plazoReadmision;

	private Boolean esIdeaProyectoPitec;

	private Date fechaApertura;

	private Date fechaCierre;

	private EstadoLlamadoConvocatoria estado;

	public Boolean getEsVigente() {
		return fechaCierre != null && !fechaCierre.before(new Date())
				&& (fechaApertura == null || !fechaApertura.after(new Date()));
	}

	public Boolean getEsIdeaProyectoPitec() {
		return esIdeaProyectoPitec;
	}

	public void setEsIdeaProyectoPitec(Boolean esIdeaProyectoPitec) {
		this.esIdeaProyectoPitec = esIdeaProyectoPitec;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Integer getPlazoReadmision() {
		return plazoReadmision;
	}

	public void setPlazoReadmision(Integer plazoReadmision) {
		this.plazoReadmision = plazoReadmision;
	}

	public EstadoLlamadoConvocatoria getEstado() {
		return estado;
	}

	public void setEstado(EstadoLlamadoConvocatoria estado) {
		this.estado = estado;
	}

	@Override
	protected String getCodigoEstado() {
		return estado.name();
	}

	@Override
	protected void setCodigoEstado(String codigoEstado) {
		estado = EstadoLlamadoConvocatoria.valueOf(codigoEstado);
	}
}
