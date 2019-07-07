package com.fontar.bus.impl.seguimientos.seguimientos;

import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;

public class ResumenRendicionesPorProyecto  {

	
	private ProyectoPresupuestoBean presupuesto;
	
	private BigDecimal totalRendicionesEnEvaluacion;
	
	private BigDecimal totalRendicionesEnGestion;

	private BigDecimal totalRendidoSeguimientoActual;
	
	private BigDecimal totalAprobadoSeguimientoActual;
	
	public ResumenRendicionesPorProyecto(ProyectoPresupuestoBean presupuesto, BigDecimal totalRendicionesEnEvaluacion, BigDecimal totalRendicionesEnGestion) {
		super();
		this.presupuesto = presupuesto;
		this.presupuesto.getMontoSolicitado(); //evita lazy initialization
		this.totalRendicionesEnEvaluacion = totalRendicionesEnEvaluacion;
		this.totalRendicionesEnGestion = totalRendicionesEnGestion;
	}

	public ProyectoPresupuestoBean getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(ProyectoPresupuestoBean presupuesto) {
		this.presupuesto = presupuesto;
	}

	public BigDecimal getTotalRendicionesEnEvaluacion() {
		return totalRendicionesEnEvaluacion;
	}

	public void setTotalRendicionesEnEvaluacion(BigDecimal totalRendicionesEnEvaluacion) {
		this.totalRendicionesEnEvaluacion = totalRendicionesEnEvaluacion;
	}

	public BigDecimal getTotalRendicionesEnGestion() {
		return totalRendicionesEnGestion;
	}

	public void setTotalRendicionesEnGestion(BigDecimal totalRendicionesEnGestion) {
		this.totalRendicionesEnGestion = totalRendicionesEnGestion;
	}
	
	
	
	public BigDecimal getTotalRendidoSeguimientoActual() {
		return totalRendidoSeguimientoActual;
	}

	public void setTotalRendidoSeguimientoActual(BigDecimal totalRendidoSeguimientoActual) {
		this.totalRendidoSeguimientoActual = totalRendidoSeguimientoActual;
	}

	
	public BigDecimal getTotalAprobadoSeguimientoActual() {
		return totalAprobadoSeguimientoActual;
	}

	public void setTotalAprobadoSeguimientoActual(BigDecimal totalAprobadoSeguimientoActual) {
		this.totalAprobadoSeguimientoActual = totalAprobadoSeguimientoActual;
	}

	public boolean validarTotalRendiciones(){
		BigDecimal total = BigDecimal.ZERO;
		
		if(this.getTotalRendicionesEnEvaluacion()!=null)
			total = total.add(this.getTotalRendicionesEnEvaluacion());

		if(this.getTotalRendicionesEnGestion()!=null)
			total = total.add(this.getTotalRendicionesEnGestion());

		//es menor o igual
		return total.compareTo( presupuesto.getMontoSolicitado()) < 1;
	}
	
	
	public boolean validarTotalRendicionesSeguimientoActual(){
		BigDecimal totalRendido = BigDecimal.ZERO;
		if(this.getTotalRendidoSeguimientoActual()!=null)
			totalRendido = totalRendido.add(this.getTotalRendidoSeguimientoActual());
		
		BigDecimal totalAprobado = BigDecimal.ZERO;
		if(this.getTotalAprobadoSeguimientoActual()!=null)
			totalAprobado = totalAprobado.add(this.getTotalAprobadoSeguimientoActual());

		//es menor o igual
		return totalAprobado.compareTo( totalRendido ) < 1;

	}

}
