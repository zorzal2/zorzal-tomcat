package com.fontar.consultas.reportes;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;

public class ResumenProyectosDTO {
	
	private String fuenteFinanciamiento;
	
	private String instrumento;
	
	private int cantidadProyectosPresentados;
	private int cantidadProyectosAprobados;
	
	private Integer anioPresentacion;
	
	private BigDecimal montoTotal = BigDecimal.ZERO;
	
	private BigDecimal montoContraparte = BigDecimal.ZERO;
	
	private BigDecimal montoParte = BigDecimal.ZERO;
	
	
	
	public String getFuenteFinanciamiento() {
		return fuenteFinanciamiento;
	}
	
	public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
		this.fuenteFinanciamiento = fuenteFinanciamiento;
	}
	
	
	
	public String getInstrumento() {
		return instrumento;
	}
	
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	
	public void update(ProyectoPresupuestoBean presupuesto , Date resolucion){
		
		if(presupuesto!=null){
			
			if( presupuesto.getMontoTotal() != null)
				this.montoTotal = this.montoTotal.add( presupuesto.getMontoTotal() );
			
			if(presupuesto.getMontoSolicitado() != null)
				this.montoParte = this.montoParte.add( presupuesto.getMontoSolicitado());
			
			if(presupuesto.getMontoEmpresa() != null)
				this.montoContraparte =  this.montoContraparte.add( presupuesto.getMontoEmpresa());
		}
		
		this.cantidadProyectosPresentados++;
		
		if(resolucion!=null)
			this.cantidadProyectosAprobados++;
	}

	public Integer getAnioPresentacion() {
		return anioPresentacion;
	}

	public void setAnioPresentacion(Integer anioPresentacion) {
		this.anioPresentacion = anioPresentacion;
	}

	public int getCantidadProyectosAprobados() {
		return cantidadProyectosAprobados;
	}

	public void setCantidadProyectosAprobados(int cantidadProyectosAprobados) {
		this.cantidadProyectosAprobados = cantidadProyectosAprobados;
	}

	public int getCantidadProyectosPresentados() {
		return cantidadProyectosPresentados;
	}

	public void setCantidadProyectosPresentados(int cantidadProyectosPresentados) {
		this.cantidadProyectosPresentados = cantidadProyectosPresentados;
	}

	public BigDecimal getMontoContraparte() {
		return montoContraparte;
	}

	public void setMontoContraparte(BigDecimal montoContraparte) {
		this.montoContraparte = montoContraparte;
	}

	public BigDecimal getMontoParte() {
		return montoParte;
	}

	public void setMontoParte(BigDecimal montoParte) {
		this.montoParte = montoParte;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	



}