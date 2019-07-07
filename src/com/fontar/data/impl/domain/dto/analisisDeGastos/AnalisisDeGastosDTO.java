package com.fontar.data.impl.domain.dto.analisisDeGastos;

import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;



/**
 * DTO con todos los datos necesarios para mostrar una pantalla de Análisis de Gastos.
 * Para mostrar en:
 * <ul>
 * 	<li>Visualización de evaluación contable de seguimiento</li>
 * 	<li>Carga de resultado de evaluación contable de seguimiento</li>
 * 	<li>Visualización de seguimiento</li>
 * 	<li>Evaluación de gestión de pago de seguimiento</li>
 * </ul>
 * <br>Incluye:
 * <ul>
 * 	<li>Cuadro de análisis de pertinencia de gastos</li>
 * 	<li>Fundamentación (FONTAR/UFA/Ambas/Ninguna según corresponda)</li>
 * 	<li>Cronograma de desembolso</li>
 * 	<li>Cálculos auxiliares, si corresponden</li>
 * </ul>
 * @author llobeto
 *
 */
public class AnalisisDeGastosDTO {
	//Contenido general
	private CuadroDeAnalisisDeGastosDTO cuadroDeAnalisisDeGastos = null;
	private String fundamentacionFontar = null;
	private String fundamentacionUFFA = null;
	private CronogramaDeDesembolsosDTO cronogramaDeDesembolsos = null;
	private CalculosDeAnalisisDeGastosDTO calculos = null;
	private Long idSeguimiento;
	
	public AnalisisDeGastosDTO(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}

	public CalculosDeAnalisisDeGastosDTO getCalculos() {
		return calculos;
	}
	public void setCalculos(CalculosDeAnalisisDeGastosDTO calculos) {
		this.calculos = calculos;
	}
	public CronogramaDeDesembolsosDTO getCronogramaDeDesembolsos() {
		return cronogramaDeDesembolsos;
	}
	public void setCronogramaDeDesembolsos(CronogramaDeDesembolsosDTO cronogramaDeDesembolsos) {
		this.cronogramaDeDesembolsos = cronogramaDeDesembolsos;
	}
	public CuadroDeAnalisisDeGastosDTO getCuadroDeAnalisisDeGastos() {
		return cuadroDeAnalisisDeGastos;
	}
	public void setCuadroDeAnalisisDeGastos(CuadroDeAnalisisDeGastosDTO cuadroDeAnalisisDeGastos) {
		this.cuadroDeAnalisisDeGastos = cuadroDeAnalisisDeGastos;
	}
	public String getFundamentacionFontar() {
		return fundamentacionFontar;
	}
	public void setFundamentacionFontar(String fundamentacionFontar) {
		this.fundamentacionFontar = fundamentacionFontar;
	}
	public String getFundamentacionUFFA() {
		return fundamentacionUFFA;
	}
	public void setFundamentacionUFFA(String fundamentacionUFFA) {
		this.fundamentacionUFFA = fundamentacionUFFA;
	}

	public Long getIdSeguimiento() {
		return idSeguimiento;
	}
}





