package com.fontar.data.impl.domain.dto.analisisDeGastos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fontar.bus.impl.seguimientos.seguimientos.ConceptoDeAnalisisDeGasto;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;
import com.pragma.util.MathUtils;

/**
 * DTO para un Cuadro del Análisis de Pertinencia de Gastos de un Seguimiento. 
 * Representa un bloque (como Monto de Inversión Rendido Anteriormente) y contiene
 * los valores en Fontar/contraparte/total para cada rubro raíz, totales y porcentuales. 
 * 
 * @author llobeto
 */
public class AnalisisDeGastosPorConceptoDTO {
	
	private ConceptoDeAnalisisDeGasto concepto;
	private List<AnalisisDeGastoPorConceptoYRubroDTO> items;	

	private BigDecimal montoFontarTotal;
	private BigDecimal montoContraparteTotal;	
	private BigDecimal montoTotal;
	
	private BigDecimal porcentajeFontar;
	private BigDecimal porcentajeContraparte;
	private BigDecimal porcentajeTotal;
	
	private BigDecimal consolidadoFontar = null;
	private BigDecimal consolidadoContraparte = null;
	private BigDecimal consolidadoTotal = null;
		
	/**
	 *  Crea un cuadro para el Análisis de Gastos. 
	 *  Agrupa por los ids presentes en <code>idPadres</code>.
	 *    
	 *  @cuadro valores fontar/contraparte/total de TODOS los rubros.
	 *  	    Si el rubro es padre, sus valores en @cuadro son 0.
	 *  
	 *  @idPadres ids de los rubros padres en los que hay hijos que agrupar.
	 */
	public AnalisisDeGastosPorConceptoDTO(List<AnalisisDeGastoPorConceptoYRubroDTO> items, ConceptoDeAnalisisDeGasto concepto) {		
		
		this.concepto = concepto;
		this.items = items;
		
		propagarValores(items);
		eliminarNoRaices(items);
		setTotales();
	}

	/**
	 * Devuelve la cantidad de rubros que forman parte del cuadro  
	 */
	public int cantidadDeRubros() {
		return items.size();
	}
	
	/**
	 * Calcula los "totales" y la "composición porcentual" del cuadro y los guarda en las variables privadas.
	 */
	private void setTotales() {
		
		BigDecimal parte = BigDecimal.ZERO;
		BigDecimal contraparte = BigDecimal.ZERO;
		BigDecimal suma = BigDecimal.ZERO;		
		
		for(AnalisisDeGastoPorConceptoYRubroDTO rendicionRubro : items) {
			parte = parte.add(rendicionRubro.getMontoFontar());
			contraparte = contraparte.add(rendicionRubro.getMontoContraparte());
			suma = suma.add(rendicionRubro.getCostoTotal());
		}
		
		// Cálculo de Montos Totales Parte/Contraparte/Total
		this.montoFontarTotal = parte;
		this.montoContraparteTotal = contraparte;
		this.montoTotal = suma;
		
		// Cálculo de la composición porcentual		
		porcentajeFontar = BigDecimal.ZERO;
		porcentajeContraparte = BigDecimal.ZERO;
				
		if (suma.signum() != 0) {
			porcentajeFontar = MathUtils.getPorcentaje(parte, suma);
			porcentajeContraparte = MathUtils.getPorcentaje(contraparte,suma);
		}

		this.porcentajeTotal = new BigDecimal(100); 
	}

	/** Getters / Setters **/

	public BigDecimal getPrcContraparte() {
		return porcentajeContraparte;
	}

	public void setPorcentajeContraparte(BigDecimal porcentajeContraparte) {
		this.porcentajeContraparte = porcentajeContraparte;
	}

	public BigDecimal getPorcentajeFontar() {
		return porcentajeFontar;
	}

	public void setPorcentajeFontar(BigDecimal porcentajeFontar) {
		this.porcentajeFontar = porcentajeFontar;
	}

	public BigDecimal getPorcentajeTotal() {
		return porcentajeTotal;
	}

	public void setPorcentajeTotal(BigDecimal porcentajeTotal) {
		this.porcentajeTotal = porcentajeTotal;
	}

	private class DTOInfo {
		public BigDecimal parteAcumulada;
		public BigDecimal contraparteAcumulada;
		public BigDecimal totalAcumulado;
		
		public BigDecimal parteNoPropagada;
		public BigDecimal contraparteNoPropagada;
		public BigDecimal totalNoPropagado;
		
		public AnalisisDeGastoPorConceptoYRubroDTO dto;
		
		public DTOInfo(AnalisisDeGastoPorConceptoYRubroDTO dto) {
			this.dto = dto;
			
			parteNoPropagada = dto.getMontoFontar();
			contraparteNoPropagada = dto.getMontoContraparte();
			totalNoPropagado = dto.getCostoTotal();
		}
		public boolean propagarA(DTOInfo dtoInfoPadre) {
			AnalisisDeGastoPorConceptoYRubroDTO dtoPadre = dtoInfoPadre.dto;
			boolean huboPropagacion = false;
			if(parteNoPropagada.signum()!=0) {
				dtoPadre.setMontoFontar(dtoPadre.getMontoFontar().add(parteNoPropagada));
				dtoInfoPadre.parteNoPropagada = dtoInfoPadre.parteNoPropagada.add(parteNoPropagada);
				parteNoPropagada = BigDecimal.ZERO;
				huboPropagacion = true;
			}
			if(contraparteNoPropagada.signum()!=0) {
				dtoPadre.setMontoContraparte(dtoPadre.getMontoContraparte().add(contraparteNoPropagada));
				dtoInfoPadre.contraparteNoPropagada = dtoInfoPadre.contraparteNoPropagada.add(contraparteNoPropagada);
				contraparteNoPropagada = BigDecimal.ZERO;
				huboPropagacion = true;
			}
			if(totalNoPropagado.signum()!=0) {
				dtoPadre.setCostoTotal(dtoPadre.getCostoTotal().add(totalNoPropagado));
				dtoInfoPadre.totalNoPropagado = dtoInfoPadre.totalNoPropagado.add(totalNoPropagado);
				totalNoPropagado = BigDecimal.ZERO;
				huboPropagacion = true;
			}
			return huboPropagacion;
		}
	}
	private void propagarValores(List<AnalisisDeGastoPorConceptoYRubroDTO> cuadro) {
		Map<Long, DTOInfo> dtoInfos = new HashMap<Long, DTOInfo>();
		for (AnalisisDeGastoPorConceptoYRubroDTO dto : cuadro) {
			dtoInfos.put(dto.getRubro().getId(), new DTOInfo(dto));
		}
		//Mientras haya montos que propagar propago
		boolean seguirPropagando = true;
		while(seguirPropagando) {
			seguirPropagando = false;
			//recorro cada dto y si tiene padre propago los montos no propagados al padre
			for (DTOInfo dtoInfo : dtoInfos.values()) {
				RubroDTO rubroPadre = dtoInfo.dto.getRubro().getRubroPadre();
				if(rubroPadre!=null) {
					DTOInfo dtoInfoPadre = dtoInfos.get(rubroPadre.getId());
					boolean huboPropagacion = dtoInfo.propagarA(dtoInfoPadre);
					seguirPropagando = seguirPropagando || huboPropagacion;
				}
			}
		}
	}
	private void eliminarNoRaices(List<AnalisisDeGastoPorConceptoYRubroDTO> cuadro) {
		for (Iterator iter = cuadro.iterator(); iter.hasNext();) {
			AnalisisDeGastoPorConceptoYRubroDTO dto = (AnalisisDeGastoPorConceptoYRubroDTO) iter.next();
			if(dto.getRubro().getRubroPadre()!=null) iter.remove();
		}
	}

	public ConceptoDeAnalisisDeGasto getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoDeAnalisisDeGasto conceptoDeAnalisisDeGasto) {
		this.concepto = conceptoDeAnalisisDeGasto;
	}

	public List<AnalisisDeGastoPorConceptoYRubroDTO> getItems() {
		return items;
	}

	public void setItems(List<AnalisisDeGastoPorConceptoYRubroDTO> items) {
		this.items = items;
	}

	public BigDecimal getMontoContraparteTotal() {
		return montoContraparteTotal;
	}

	public void setMontoContraparteTotal(BigDecimal montoContraparteTotal) {
		this.montoContraparteTotal = montoContraparteTotal;
	}

	public BigDecimal getMontoFontarTotal() {
		return montoFontarTotal;
	}

	public void setMontoFontarTotal(BigDecimal montoFontarTotal) {
		this.montoFontarTotal = montoFontarTotal;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public BigDecimal getPorcentajeContraparte() {
		return porcentajeContraparte;
	}

	public BigDecimal getConsolidadoContraparte() {
		return consolidadoContraparte;
	}

	public void setConsolidadoContraparte(BigDecimal consolidadoContraparte) {
		this.consolidadoContraparte = consolidadoContraparte;
	}

	public BigDecimal getConsolidadoFontar() {
		return consolidadoFontar;
	}

	public void setConsolidadoFontar(BigDecimal consolidadoFontar) {
		this.consolidadoFontar = consolidadoFontar;
	}

	public BigDecimal getConsolidadoTotal() {
		return consolidadoTotal;
	}

	public void setConsolidadoTotal(BigDecimal consolidadoTotal) {
		this.consolidadoTotal = consolidadoTotal;
	}
}