package com.fontar.data.impl.domain.dto.analisisDeGastos;

import java.math.BigDecimal;

import com.fontar.data.impl.assembler.proyecto.presupuesto.rubros.RubroDTOAssembler;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;

/**
 * DTO que representa el resumen de dinero aportado para un rubro (parte/contraparte/total).
 * 
 * Una instancia de este DTO representa una fila de uno de los cuadros 
 * de la vista de Análisis de Gastos
 * 
 * @author llobeto
 *
 */
public class AnalisisDeGastoPorConceptoYRubroDTO {
	
	private RubroDTO rubro;	
	private BigDecimal montoFontar;
	private BigDecimal montoContraparte;
	private BigDecimal costoTotal;
	
	private AnalisisDeGastoPorConceptoYRubroDTO() {}
	
	public AnalisisDeGastoPorConceptoYRubroDTO(RubroBean rubro, BigDecimal montoFontar, BigDecimal montoContraparte) {		
		this.rubro = RubroDTOAssembler.instance.buildDto(rubro);
		this.montoFontar = montoFontar;
		this.montoContraparte = montoContraparte;

		if(montoFontar!=null && montoContraparte!=null) this.costoTotal = montoFontar.add(montoContraparte);
		else if(montoFontar!=null) {
			this.costoTotal = montoFontar;
			this.montoContraparte = BigDecimal.ZERO;
		} else {
			this.costoTotal = montoContraparte;
			this.montoFontar = BigDecimal.ZERO;
		}
	}
	
	public BigDecimal getMontoContraparte() {
		return montoContraparte;
	}
	public void setMontoContraparte(BigDecimal montoContraparte) {
		this.montoContraparte = montoContraparte;
	}
	
	public BigDecimal getMontoFontar() {		
		return montoFontar;
	}

	public void setMontoFontar(BigDecimal montoFontar) {
		this.montoFontar = montoFontar;
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}
	
	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	public RubroDTO getRubro() {
		return rubro;
	}
}