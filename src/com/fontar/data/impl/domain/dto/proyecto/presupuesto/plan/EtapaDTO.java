package com.fontar.data.impl.domain.dto.proyecto.presupuesto.plan;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class EtapaDTO {
	private Long id;
	private Long presupuestoId;
	private Set<ActividadDTO> actividades;
	private String nombre;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private String avance;
	private String observaciones;
	
	public Set<ActividadDTO> getActividades() {
		return actividades;
	}
	public void setActividades(Set<ActividadDTO> actividades) {
		this.actividades = actividades;
	}
	public void addActividad(ActividadDTO actividad) {
		if(getActividades()==null) setActividades(new LinkedHashSet<ActividadDTO>());
		getActividades().add(actividad);
	}
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = avance;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Long getProyectoPresupuestoId() {
		return presupuestoId;
	}
	public void setProyectoPresupuestoId(Long presupuestoId) {
		this.presupuestoId = presupuestoId;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("["+getNombre()+": "+getDescripcion()+" ("+getInicio()+", "+getFin()+")]\n");
		if(actividades==null) buffer.append("\t<Sin actividades>\n");
		else {
			for (ActividadDTO actividad : actividades) {
				buffer.append("\t");
				buffer.append(actividad);
				buffer.append('\n');				
			}
		}
		return buffer.toString();
	}
}
