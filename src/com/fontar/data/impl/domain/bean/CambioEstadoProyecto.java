package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;

public class CambioEstadoProyecto  {
	
	private static long nextid = 0;
	private synchronized static long getNextId() {
		nextid++;
		return nextid;
	}
	
	Long id;
	
	Long  idProyecto;
	ProyectoBean proyectoBean;
	
	Date fechaRegistro;
	
	EstadoProyecto estadoInicial;
	
	EstadoProyecto estadoFinal;
	
	public CambioEstadoProyecto() {
		this.id = getNextId();
	}

	public EstadoProyecto getEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(EstadoProyecto estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public EstadoProyecto getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(EstadoProyecto estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public ProyectoBean getProyecto() {
		return proyectoBean;
	}

	public void setProyecto(ProyectoBean proyectoBean) {
		this.proyectoBean = proyectoBean;
	}

		
	
}
