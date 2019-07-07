package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pragma.util.MathUtils;
import com.pragma.util.StringUtil;

/**
 * DTO para un Cuadro del Análisis de Pertinencia de Gastos de un Seguimiento. 
 * 
 * Está conformado por la lista de <parte/contraparte/total> de cada uno de los rubros,  
 * y por variables que guardan los "Totales" de ese cuadro y su "Composición porcentual" 
 * 
 * @author gboaglio 
 */
public class RendicionCuentasAnalisisGastosCuadroDTO {
	
	private List<RendicionCuentasResumenRubroDTO> cuadro;	

	private BigDecimal totalParte;
	private BigDecimal totalContraparte;	
	private BigDecimal total;
	
	private String prcParte;
	private String prcContraparte;
	private String prcTotal;
		
	public RendicionCuentasAnalisisGastosCuadroDTO() {		
	}

	/**
	 *  Crea un cuadro para el Análisis de Gastos. 
	 *  Agrupa por los ids presentes en @idPadres.
	 *    
	 *  @cuadro = valores parte/contraparte/total de TODOS los rubros.
	 *  	      Si el rubro es padre, sus valores en @cuadro son 0.
	 *  
	 *  @idPadres = ids de los rubros padres en los que hay hijos que agrupar.
	 */
	public RendicionCuentasAnalisisGastosCuadroDTO(List<RendicionCuentasResumenRubroDTO> cuadro) {		
		
		this.cuadro = cuadro;
		
		propagarValores(cuadro);
		eliminarNoRaices(cuadro);
		setTotales();
	}

	/**
	 * Devuelve la cantidad de rubros que forman parte del cuadro  
	 */
	public int cantidadDeRubros() {
		return cuadro.size();
	}
	
	/**
	 * Calcula los "totales" y la "composición porcentual" del cuadro y los guarda en las variables privadas.
	 */
	private void setTotales() {
		
		BigDecimal parte = BigDecimal.ZERO;
		BigDecimal contraparte = BigDecimal.ZERO;
		BigDecimal suma = BigDecimal.ZERO;		
		
		for(RendicionCuentasResumenRubroDTO rendicionRubro : this.cuadro) {
			parte = parte.add(rendicionRubro.getMontoParte());
			contraparte = contraparte.add(rendicionRubro.getMontoContraparte());
			suma = suma.add(rendicionRubro.getCostoTotal());
		}
		
		// Cálculo de Montos Totales Parte/Contraparte/Total
		this.totalParte = parte;
		this.totalContraparte = contraparte;
		this.total = suma;
		
		// Cálculo de la composición porcentual		
		BigDecimal prcParte = BigDecimal.ZERO;
		BigDecimal prcContraparte = BigDecimal.ZERO;
				
		if (suma.signum() != 0) {
			prcParte = MathUtils.getPorcentaje(parte, suma);
			prcContraparte = MathUtils.getPorcentaje(contraparte,suma);
		}

		this.prcParte =  StringUtil.formatPercentageForPresentation(prcParte);
		this.prcContraparte = StringUtil.formatPercentageForPresentation(prcContraparte);
		this.prcTotal = StringUtil.formatPercentageForPresentation(100); 
	}

	/** Getters / Setters **/
	
	public List<RendicionCuentasResumenRubroDTO> getCuadro() {
		return cuadro;
	}

	public void setCuadro(List<RendicionCuentasResumenRubroDTO> cuadro) {
		this.cuadro = cuadro;
	}

	public String getTotal() {
		return MathUtils.formatTwoPlaces(this.total);
	}


	public String getTotalContraparte() {
		return  MathUtils.formatTwoPlaces(this.totalContraparte);
	}


	public String getTotalParte() {
		return MathUtils.formatTwoPlaces(this.totalParte);
	}

	
	public BigDecimal getMontoTotal() {
		return this.total;
	}


	public BigDecimal getMontoTotalContraparte() {
		return this.totalContraparte;
	}


	public BigDecimal getMontoTotalParte() {
		return this.totalParte;
	}


	public String getPrcContraparte() {
		return prcContraparte;
	}

	public void setPrcContraparte(String porcentajeContraparte) {
		this.prcContraparte = porcentajeContraparte;
	}

	public String getPrcParte() {
		return prcParte;
	}

	public void setPrcParte(String porcentajeParte) {
		this.prcParte = porcentajeParte;
	}

	public String getPrcTotal() {
		return prcTotal;
	}

	public void setPrcTotal(String prcTotal) {
		this.prcTotal = prcTotal;
	}

	private class DTOInfo {
		public BigDecimal parteAcumulada;
		public BigDecimal contraparteAcumulada;
		public BigDecimal totalAcumulado;
		
		public BigDecimal parteNoPropagada;
		public BigDecimal contraparteNoPropagada;
		public BigDecimal totalNoPropagado;
		
		public RendicionCuentasResumenRubroDTO dto;
		
		public DTOInfo(RendicionCuentasResumenRubroDTO dto) {
			this.dto = dto;
			
			parteNoPropagada = dto.getMontoParte();
			contraparteNoPropagada = dto.getMontoContraparte();
			totalNoPropagado = dto.getCostoTotal();
		}
		public boolean propagarA(DTOInfo dtoInfoPadre) {
			RendicionCuentasResumenRubroDTO dtoPadre = dtoInfoPadre.dto;
			boolean huboPropagacion = false;
			if(parteNoPropagada.signum()!=0) {
				dtoPadre.setMontoParte(dtoPadre.getMontoParte().add(parteNoPropagada));
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
	private void propagarValores(List<RendicionCuentasResumenRubroDTO> cuadro) {
		Map<Long, DTOInfo> dtoInfos = new HashMap<Long, DTOInfo>();
		for (RendicionCuentasResumenRubroDTO dto : cuadro) {
			dtoInfos.put(dto.getIdRubro(), new DTOInfo(dto));
		}
		//Mientras haya montos que propagar propago
		boolean seguirPropagando = true;
		while(seguirPropagando) {
			seguirPropagando = false;
			//recorro cada dto y si tiene padre propago los montos no propagados al padre
			for (DTOInfo dtoInfo : dtoInfos.values()) {
				DTOInfo dtoInfoPadre = dtoInfos.get(dtoInfo.dto.getIdRubroPadre());
				if(dtoInfoPadre!=null) {
					boolean huboPropagacion = dtoInfo.propagarA(dtoInfoPadre);
					seguirPropagando = seguirPropagando || huboPropagacion;
				}
			}
		}
	}
	private void eliminarNoRaices(List<RendicionCuentasResumenRubroDTO> cuadro) {
		for (Iterator iter = cuadro.iterator(); iter.hasNext();) {
			RendicionCuentasResumenRubroDTO dto = (RendicionCuentasResumenRubroDTO) iter.next();
			if(dto.getIdRubroPadre()!=null) iter.remove();
		}
	}
}






