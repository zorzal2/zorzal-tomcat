package com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.pragma.util.StringUtil;
/**
 *Las Etapas corresponden a las distintos items dentro de un plan de proyecto.
 *Donde el plan de proyecto forma parte de un presupesto de un proyecto dado.
 * 
 *Cada etapa contiene un conjunto de actividadades a ser realizadas.
 *
 * @see com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean
 * @see com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.ActividadBean
 */
public class EtapaBean {
	public static final int MAX_DESCRIPCION_SIZE = 1000; 
	public static final int MAX_OBSERVACION_SIZE = 1000; 
	public static final int MAX_AVANCE_SIZE = 500; 
	public static final int MAX_NOMBRE_SIZE = 60; 
	
	private Long id;
	private Long idPresupuesto;
	private ProyectoPresupuestoBean presupuesto;
	private Set<ActividadBean> actividades;
	private String nombre;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private String avance;
	private String observaciones;
	
	public Set<ActividadBean> getActividades() {
		return actividades;
	}
	public void setActividades(Set<ActividadBean> actividades) {
		this.actividades = actividades;
	}
	public void addActividad(ActividadBean actividad) {
		if(getActividades()==null) setActividades(new LinkedHashSet<ActividadBean>());
		getActividades().add(actividad);
	}
	public String getAvance() {
		return avance;
	}
	public void setAvance(String avance) {
		this.avance = StringUtil.trimToSize(avance, MAX_AVANCE_SIZE);
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = StringUtil.trimToSize(descripcion, MAX_DESCRIPCION_SIZE);
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
		this.nombre = StringUtil.trimToSize(nombre, MAX_NOMBRE_SIZE);
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = StringUtil.trimToSize(observaciones, MAX_OBSERVACION_SIZE);
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("["+getNombre()+": "+getDescripcion()+" ("+getInicio()+", "+getFin()+")]\n");
		if(actividades==null) buffer.append("\t<Sin actividades>\n");
		else {
			for (ActividadBean actividad : actividades) {
				buffer.append("\t");
				buffer.append(actividad);
				buffer.append('\n');				
			}
		}
		return buffer.toString();
	}
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public ProyectoPresupuestoBean getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(ProyectoPresupuestoBean presupuesto) {
		this.presupuesto = presupuesto;
	}
	
}
