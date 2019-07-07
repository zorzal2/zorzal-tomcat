/**
 * 
 */
package com.fontar.data.impl.domain.dto.proyecto.presupuesto;

import com.fontar.bus.api.proyecto.presupuesto.PresupuestoPosProcessingTask;
import com.fontar.data.impl.domain.dto.ArchivoDTO;

public class PresupuestoAdjuntoDTO {
	private ArchivoDTO archivo;
	private ProyectoPresupuestoDTO presupuesto;
	private Long idProyecto;
	private Long idPresupuestoAnterior;
	private PresupuestoPosProcessingTask task = null;

	public PresupuestoAdjuntoDTO(
			ArchivoDTO archivoDTO,
			ProyectoPresupuestoDTO presupuesto,
			Long idProyecto,
			Long idPresupuestoAnterior
	) {
		this.archivo = archivoDTO;
		this.idProyecto = idProyecto;
		this.presupuesto = presupuesto;
		this.idPresupuestoAnterior = idPresupuestoAnterior;
	}

	public ArchivoDTO getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoDTO archivo) {
		this.archivo = archivo;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public ProyectoPresupuestoDTO getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(ProyectoPresupuestoDTO presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Long getIdPresupuestoAnterior() {
		return idPresupuestoAnterior;
	}

	public void setIdPresupuestoAnterior(Long idPresupuestoAnterior) {
		this.idPresupuestoAnterior = idPresupuestoAnterior;
	}

	public PresupuestoPosProcessingTask getTask() {
		return task;
	}

	public void setTask(PresupuestoPosProcessingTask task) {
		this.task = task;
	}
}