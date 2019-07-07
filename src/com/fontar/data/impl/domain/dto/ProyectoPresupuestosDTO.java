package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;

/** 
 * @author gboaglio
 */
public class ProyectoPresupuestosDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private ProyectoPresupuestoBean proyectoPresupuesto;	
	private ProyectoPresupuestoBean proyectoPresupuestoOriginal;
	
	public ProyectoPresupuestosDTO(ProyectoPresupuestoBean presupuesto, ProyectoPresupuestoBean original) {
		this.proyectoPresupuesto = presupuesto;
		this.proyectoPresupuestoOriginal = original;		
	}

	public ProyectoPresupuestoBean getProyectoPresupuesto() {
		return proyectoPresupuesto;
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoBean proyectoPresupuesto) {
		this.proyectoPresupuesto = proyectoPresupuesto;
	}

	public ProyectoPresupuestoBean getProyectoPresupuestoOriginal() {
		return proyectoPresupuestoOriginal;
	}

	public void setProyectoPresupuestoOriginal(ProyectoPresupuestoBean proyectoPresupuestoOriginal) {
		this.proyectoPresupuestoOriginal = proyectoPresupuestoOriginal;
	}

	


}