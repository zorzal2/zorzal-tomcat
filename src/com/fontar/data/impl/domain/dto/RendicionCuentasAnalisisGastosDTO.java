package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.pragma.util.MathUtils;
import com.pragma.util.StringUtil;



/**
 * DTO para el Análisis de Pertinencia de Gastos de un Seguimiento.
 *  
 * Está formado por todos los cuadros que son parte del resumen, más  
 * una lista que representa la columna de porcentajes de avance que se 
 * autocalcula a partir de los valores de los cuadros.
 * 
 * @author gboaglio
 *
 */
public class RendicionCuentasAnalisisGastosDTO {
	
	private RendicionCuentasAnalisisGastosCuadroDTO cuadroCostosTotalesProyecto;
	private RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionSolicitada;
	private RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionSolicitadaAnterior;	
	
	private RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionAprobada;	
	private RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionAprobadaAnterior;
	
	private List<String> prcsAvance = new ArrayList<String>();
	private String prcTotalAvance;
	
	private int maxIndex;
		
	private BigDecimal porcentajeConsolidadoTotal;
	
	private BigDecimal porcentajeConsolidadoContraparte;
	
	//Monto parte
	private BigDecimal porcentajeConsolidadoFontar;
	
	/**
	 *  
	 */
	public RendicionCuentasAnalisisGastosDTO(RendicionCuentasAnalisisGastosCuadroDTO cuadroCostosTotalesProyecto, 
			                                 RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionSolicitada, 
			                                 RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionSolicitadaAnterior, 
			                                 RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionAprobada, 
			                                 RendicionCuentasAnalisisGastosCuadroDTO cuadroRendicionAprobadaAnterior) {
	
		this.cuadroCostosTotalesProyecto = cuadroCostosTotalesProyecto;
		this.cuadroRendicionSolicitada = cuadroRendicionSolicitada;
		this.cuadroRendicionSolicitadaAnterior = cuadroRendicionSolicitadaAnterior;
		this.cuadroRendicionAprobada = cuadroRendicionAprobada;
		this.cuadroRendicionAprobadaAnterior = cuadroRendicionAprobadaAnterior;
				
		this.maxIndex = cuadroRendicionSolicitada.cantidadDeRubros() - 1;
		
		calcularPorcentajesAvance();
		
		calcularTotalesConsolidados();
	}

	private void calcularTotalesConsolidados() {
		//Antiguo calculo de total
		/*if(!this.cuadroCostosTotalesProyecto.getMontoTotal().equals(BigDecimal.ZERO))
			this.porcentajeConsolidadoTotal = MathUtils.getPorcentaje( this.cuadroRendicionAprobadaAnterior.getMontoTotal().add(this.cuadroRendicionAprobada.getMontoTotal()) , 
					this.getCuadroCostosTotalesProyecto().getMontoTotal());*/
		
		if(!this.cuadroCostosTotalesProyecto.getMontoTotalContraparte().equals(BigDecimal.ZERO))
			//this.porcentajeConsolidadoContraparte = MathUtils.getPorcentaje( this.cuadroRendicionAprobadaAnterior.getMontoTotalContraparte().add(this.cuadroRendicionAprobada.getMontoTotalContraparte() ), this.getCuadroCostosTotalesProyecto().getMontoTotalContraparte() , false);
			this.porcentajeConsolidadoContraparte = MathUtils.getPorcentaje( this.cuadroRendicionAprobadaAnterior.getMontoTotalContraparte().add(this.cuadroRendicionAprobada.getMontoTotalContraparte() ), 
					this.cuadroRendicionAprobadaAnterior.getMontoTotal().add(this.cuadroRendicionAprobada.getMontoTotal() ));

		
		if(!this.cuadroCostosTotalesProyecto.getMontoTotalParte().equals(BigDecimal.ZERO))
			//this.porcentajeConsolidadoFontar = MathUtils.getPorcentaje(  this.cuadroRendicionAprobadaAnterior.getMontoTotalParte().add(this.cuadroRendicionAprobada.getMontoTotalParte()) , this.getCuadroCostosTotalesProyecto().getMontoTotalParte(), false);
			this.porcentajeConsolidadoFontar = MathUtils.getPorcentaje(  this.cuadroRendicionAprobadaAnterior.getMontoTotalParte().add(this.cuadroRendicionAprobada.getMontoTotalParte()) ,
					this.cuadroRendicionAprobadaAnterior.getMontoTotal().add(this.cuadroRendicionAprobada.getMontoTotal()));
		//Total
		if(this.porcentajeConsolidadoContraparte != null && this.porcentajeConsolidadoFontar != null) 
			this.porcentajeConsolidadoTotal = this.porcentajeConsolidadoContraparte.add(this.porcentajeConsolidadoFontar);
	}

	/**
	 *	Arma la columna de "Porcentajes de Avance".
	 */
	private void calcularPorcentajesAvance() {
	
		List<RendicionCuentasResumenRubroDTO> rendicionAprobadaAnterior = cuadroRendicionAprobadaAnterior.getCuadro();
		List<RendicionCuentasResumenRubroDTO> rendicionAprobada = cuadroRendicionAprobada.getCuadro();
		List<RendicionCuentasResumenRubroDTO> costosTotales = cuadroCostosTotalesProyecto.getCuadro();
		
		int i = 0;
				
		
		BigDecimal totalAprobado = BigDecimal.ZERO;
		BigDecimal totalCostos   = BigDecimal.ZERO;
		
		while ( i < this.cuadroRendicionSolicitada.cantidadDeRubros()) {
			
			BigDecimal totalAprobadoAnteriorRubroActual = rendicionAprobadaAnterior.get(i).getCostoTotal();
			BigDecimal totalAprobadoRubroActual = rendicionAprobada.get(i).getCostoTotal();
			BigDecimal totalCostosRubroActual = costosTotales.get(i).getCostoTotal();
			BigDecimal sumaAprobadosRubroActual = totalAprobadoAnteriorRubroActual.add(totalAprobadoRubroActual);
			
			BigDecimal avance = BigDecimal.ZERO;
			
			if (totalCostosRubroActual.signum() == 1) {
				avance = MathUtils.getPorcentaje(sumaAprobadosRubroActual,totalCostosRubroActual);				
			}
			
			this.prcsAvance.add(StringUtil.formatTwoDecimalForPresentation(avance));
			
			totalAprobado = totalAprobado.add(sumaAprobadosRubroActual);
			totalCostos   = totalCostos.add(totalCostosRubroActual);
			
			i++;
		}
		
		
		// Calculo el porcentaje total de Avances				
		BigDecimal prcTotalAvances = BigDecimal.ZERO;
		
		if(totalCostos.signum() == 1) {
			prcTotalAvances = MathUtils.getPorcentaje(totalAprobado,totalCostos);
		}
		
		this.prcTotalAvance = StringUtil.formatTwoDecimalForPresentation(prcTotalAvances); 
		
	}
	
	/** Getters / Setters **/

	public RendicionCuentasAnalisisGastosCuadroDTO getCuadroCostosTotalesProyecto() {
		return cuadroCostosTotalesProyecto;
	}

	public void setCuadroCostosTotalesProyecto(RendicionCuentasAnalisisGastosCuadroDTO cuadroCostosTotalesProyecto) {
		this.cuadroCostosTotalesProyecto = cuadroCostosTotalesProyecto;
	}
	
	public RendicionCuentasAnalisisGastosCuadroDTO getCuadroRendicionAprobada() {
		return cuadroRendicionAprobada;
	}

	public void setCuadroRendicionAprobada(RendicionCuentasAnalisisGastosCuadroDTO rendicionAprobada) {
		this.cuadroRendicionAprobada = rendicionAprobada;
	}

	public RendicionCuentasAnalisisGastosCuadroDTO getCuadroRendicionAprobadaAnterior() {
		return cuadroRendicionAprobadaAnterior;
	}

	public void setCuadroRendicionAprobadaAnterior(RendicionCuentasAnalisisGastosCuadroDTO rendicionAprobadaAnterior) {
		this.cuadroRendicionAprobadaAnterior = rendicionAprobadaAnterior;
	}

	public RendicionCuentasAnalisisGastosCuadroDTO getCuadroRendicionSolicitada() {
		return cuadroRendicionSolicitada;
	}

	public void setCuadroRendicionSolicitada(RendicionCuentasAnalisisGastosCuadroDTO rendicionSolicitada) {
		this.cuadroRendicionSolicitada = rendicionSolicitada;
	}

	public RendicionCuentasAnalisisGastosCuadroDTO getCuadroRendicionSolicitadaAnterior() {
		return cuadroRendicionSolicitadaAnterior;
	}

	public void setCuadroRendicionSolicitadaAnterior(RendicionCuentasAnalisisGastosCuadroDTO rendicionSolicitadaAnterior) {
		this.cuadroRendicionSolicitadaAnterior = rendicionSolicitadaAnterior;
	}

	public int getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(int tamaño) {
		this.maxIndex = tamaño;
	}


	public List<String> getPrcsAvance() {
		return prcsAvance;
	}


	public void setPrcsAvance(List<String> prcsAvance) {
		this.prcsAvance = prcsAvance;
	}

	public String getPrcTotalAvance() {
		return prcTotalAvance;
	}

	public void setPrcTotalAvance(String totalAvance) {
		this.prcTotalAvance = totalAvance;
	}

	public String getPorcentajeConsolidadoContraparte() {
		return StringUtil.formatPercentageForPresentation(this.porcentajeConsolidadoContraparte);
	}

	public String getPorcentajeConsolidadoParte() {
		return StringUtil.formatPercentageForPresentation(this.porcentajeConsolidadoFontar);
	}

	public String getPorcentajeConsolidadoTotal() {
		return StringUtil.formatPercentageForPresentation( this.porcentajeConsolidadoTotal);
	}
	
}





