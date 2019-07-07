package com.fontar.data.impl.domain.dto.analisisDeGastos;

import com.fontar.data.impl.domain.dto.CronogramaDeDesembolsosDTO;



/**
 * DTO con todos los datos necesarios para mostrar una pantalla de An�lisis de Gastos.
 * Para mostrar en:
 * <ul>
 * 	<li>Visualizaci�n de evaluaci�n contable de seguimiento</li>
 * 	<li>Carga de resultado de evaluaci�n contable de seguimiento</li>
 * 	<li>Visualizaci�n de seguimiento</li>
 * 	<li>Evaluaci�n de gesti�n de pago de seguimiento</li>
 * </ul>
 * <br>Incluye:
 * <ul>
 * 	<li>Cuadro de an�lisis de pertinencia de gastos</li>
 * 	<li>Fundamentaci�n (FONTAR/UFA/Ambas/Ninguna seg�n corresponda)</li>
 * 	<li>Cronograma de desembolso</li>
 * 	<li>C�lculos auxiliares, si corresponden</li>
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





