package com.fontar.data.impl.domain.dto.analisisDeGastos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.fontar.bus.impl.seguimientos.seguimientos.ConceptoDeAnalisisDeGasto;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;
import com.pragma.util.CollectionUtils;
import com.pragma.util.MathUtils;



/**
 * DTO para el Cuadro de Análisis de Pertinencia de Gastos de un Seguimiento.
 * Contiene todos los datos necesarios para mostrar el cuadro completo. Incluye:
 * <ul>
 * 	<li>Listado ordenado de rubros</li>
 * 	<li>Listado ordenado de bloques (Costos totales, Monto de Inversión Rendido Anteriormente, ...)</li>
 * 	<li>Listado ordenado de porcentaje de avance por rubro</li>
 * 	<li>Porcentaje total de avance</li>
 * 	<li>Consolidado (Fontar/Contraparte/Total)</li>
 * </ul>
 * 
 * @author llobeto
 *
 */
public class CuadroDeAnalisisDeGastosDTO {
	
	private List<RubroDTO> rubros;
	private LinkedHashMap<ConceptoDeAnalisisDeGasto, AnalisisDeGastosPorConceptoDTO> analisisPorConcepto;
	private List<BigDecimal> porcentajeDeAvance;
	private BigDecimal porcentajeTotalDeAvance;
	
	private BigDecimal porGestionarFontar;
	private BigDecimal porGestionarContraparte;
	private BigDecimal porGestionarTotal;
	//Datos contextuales
	boolean desplegarEnParteYContraparte = true;
	
	public CuadroDeAnalisisDeGastosDTO(List<AnalisisDeGastosPorConceptoDTO> gastosPorConcepto) {
		
		this.analisisPorConcepto = new LinkedHashMap<ConceptoDeAnalisisDeGasto, AnalisisDeGastosPorConceptoDTO>();
		for(AnalisisDeGastosPorConceptoDTO bloque : gastosPorConcepto) {
			this.analisisPorConcepto.put(bloque.getConcepto(), bloque);
		}
		llenarRubros();
		calcularPorcentajesAvance();
		calcularTotalesConsolidados();
		calcularPorGestionar();
	}

	private void calcularPorGestionar() {
		AnalisisDeGastosPorConceptoDTO rendicionActualGestionada = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualGestionada);
		
		BigDecimal aprobadoFontar;
		BigDecimal gestionadoFontar;
		BigDecimal aprobadoContraparte;
		BigDecimal gestionadoContraparte;
		BigDecimal aprobadoTotal;
		BigDecimal gestionadoTotal;
		
		if(rendicionActualGestionada==null) {
			porGestionarFontar = null; 
			porGestionarContraparte = null; 
			porGestionarTotal = null;
		} else {
			aprobadoFontar = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada).getMontoFontarTotal();
			gestionadoFontar = rendicionActualGestionada.getMontoFontarTotal();
			
			aprobadoContraparte = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada).getMontoContraparteTotal();
			gestionadoContraparte = rendicionActualGestionada.getMontoContraparteTotal();
			
			aprobadoTotal = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada).getMontoTotal();
			gestionadoTotal = rendicionActualGestionada.getMontoTotal();

			porGestionarFontar = aprobadoFontar.subtract(gestionadoFontar); 
			porGestionarContraparte = aprobadoContraparte.subtract(gestionadoContraparte); 
			porGestionarTotal = aprobadoTotal.subtract(gestionadoTotal); 
		}
		
	}

	private void llenarRubros() {
		rubros = new ArrayList<RubroDTO>(5);
		if(analisisPorConcepto.isEmpty()) return;
		else {
			for(AnalisisDeGastoPorConceptoYRubroDTO fila : CollectionUtils.getAny(analisisPorConcepto.values()).getItems()) {
				rubros.add(fila.getRubro());
			}
		}
	}

	private void calcularTotalesConsolidados() {
		
		AnalisisDeGastosPorConceptoDTO aprobadoAnteriormente = analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente);
		AnalisisDeGastosPorConceptoDTO aprobadoActual = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada);
		AnalisisDeGastosPorConceptoDTO gestionadoActual = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualGestionada);
		
		if(aprobadoAnteriormente==null) {
			//No se puede calcular.
			return;
		}
		else {
			//Consolidado Aprobado
			if(aprobadoActual!=null) {
				aprobadoActual.setConsolidadoContraparte(MathUtils.getPorcentaje(
						aprobadoAnteriormente.getMontoContraparteTotal().add(aprobadoActual.getMontoContraparteTotal())
						,	aprobadoAnteriormente.getMontoTotal().add(aprobadoActual.getMontoTotal())
				));
				aprobadoActual.setConsolidadoFontar(MathUtils.getPorcentaje(
						aprobadoAnteriormente.getMontoFontarTotal().add(aprobadoActual.getMontoFontarTotal()) 
						,	aprobadoAnteriormente.getMontoTotal().add(aprobadoActual.getMontoTotal())
				));
				if(aprobadoActual.getConsolidadoContraparte() != null && aprobadoActual.getConsolidadoFontar() != null) { 
					aprobadoActual.setConsolidadoTotal(aprobadoActual.getConsolidadoContraparte().add(aprobadoActual.getConsolidadoFontar()));
				}
			}			
			//Consolidado Gestionado
			if(gestionadoActual!=null) {
				gestionadoActual.setConsolidadoContraparte(MathUtils.getPorcentaje(
						aprobadoAnteriormente.getMontoContraparteTotal().add(gestionadoActual.getMontoContraparteTotal())
					,	aprobadoAnteriormente.getMontoTotal().add(gestionadoActual.getMontoTotal())
				));
				gestionadoActual.setConsolidadoContraparte(MathUtils.getPorcentaje(
						aprobadoAnteriormente.getMontoFontarTotal().add(gestionadoActual.getMontoFontarTotal()) 
					,	aprobadoAnteriormente.getMontoTotal().add(gestionadoActual.getMontoTotal())
				));
				if(gestionadoActual.getConsolidadoFontar() != null && gestionadoActual.getConsolidadoFontar() != null) { 
					gestionadoActual.setConsolidadoTotal(gestionadoActual.getConsolidadoContraparte().add(gestionadoActual.getConsolidadoFontar()));
				}
			}			
		}
	}

	/**
	 *	Calcula el Porcentajes de Avance por rubro.
	 */
	private void calcularPorcentajesAvance() {
	
		List<AnalisisDeGastoPorConceptoYRubroDTO> aprobadoAnteriormente = analisisPorConcepto(ConceptoDeAnalisisDeGasto.MontoDeInversionAprobadoAnteriormente).getItems();
		List<AnalisisDeGastoPorConceptoYRubroDTO> aprobadoActual = analisisPorConcepto(ConceptoDeAnalisisDeGasto.RendicionActualAprobada).getItems();
		List<AnalisisDeGastoPorConceptoYRubroDTO> costosTotales = analisisPorConcepto(ConceptoDeAnalisisDeGasto.CostosTotalesDelProyecto).getItems();
		
		int i = 0;
		
		BigDecimal totalAprobado = BigDecimal.ZERO;
		BigDecimal totalCostos   = BigDecimal.ZERO;
		
		porcentajeDeAvance = new ArrayList<BigDecimal>();
		
		while ( i < costosTotales.size()) {
			
			BigDecimal totalAprobadoAnteriorRubroActual = aprobadoAnteriormente.get(i).getCostoTotal();
			BigDecimal totalAprobadoRubroActual = aprobadoActual.get(i).getCostoTotal();
			BigDecimal totalCostosRubroActual = costosTotales.get(i).getCostoTotal();
			BigDecimal sumaAprobadosRubroActual = totalAprobadoAnteriorRubroActual.add(totalAprobadoRubroActual);
			
			BigDecimal avance = BigDecimal.ZERO;
			
			if (totalCostosRubroActual.signum() == 1) {
				avance = MathUtils.getPorcentaje(sumaAprobadosRubroActual,totalCostosRubroActual);				
			}
			
			porcentajeDeAvance.add(avance);
			
			totalAprobado = totalAprobado.add(sumaAprobadosRubroActual);
			totalCostos   = totalCostos.add(totalCostosRubroActual);
			
			i++;
		}
		
		// Calculo el porcentaje total de Avances				
		porcentajeTotalDeAvance = BigDecimal.ZERO;
		
		if(totalCostos.signum() == 1) {
			porcentajeTotalDeAvance = MathUtils.getPorcentaje(totalAprobado,totalCostos);
		}
	}
	
	/** Getters / Setters **/


	public AnalisisDeGastosPorConceptoDTO analisisPorConcepto(ConceptoDeAnalisisDeGasto concepto) {
		return analisisPorConcepto.get(concepto);
	}

	public Collection<AnalisisDeGastosPorConceptoDTO> getAnalisisPorConcepto() {
		return analisisPorConcepto.values();
	}

	public boolean getDesplegarEnParteYContraparte() {
		return desplegarEnParteYContraparte;
	}

	public void setDesplegarEnParteYContraparte(boolean desplegarEnParteYContraparte) {
		this.desplegarEnParteYContraparte = desplegarEnParteYContraparte;
	}

	public List<BigDecimal> getPorcentajeDeAvance() {
		return porcentajeDeAvance;
	}

	public void setPorcentajeDeAvance(List<BigDecimal> porcentajeDeAvance) {
		this.porcentajeDeAvance = porcentajeDeAvance;
	}

	public BigDecimal getPorcentajeTotalDeAvance() {
		return porcentajeTotalDeAvance;
	}

	public void setPorcentajeTotalDeAvance(BigDecimal porcentajeTotalDeAvance) {
		this.porcentajeTotalDeAvance = porcentajeTotalDeAvance;
	}

	public BigDecimal getPorGestionarContraparte() {
		return porGestionarContraparte;
	}

	public void setPorGestionarContraparte(BigDecimal porGestionarContraparte) {
		this.porGestionarContraparte = porGestionarContraparte;
	}

	public BigDecimal getPorGestionarFontar() {
		return porGestionarFontar;
	}

	public void setPorGestionarFontar(BigDecimal porGestionarFontar) {
		this.porGestionarFontar = porGestionarFontar;
	}

	public BigDecimal getPorGestionarTotal() {
		return porGestionarTotal;
	}

	public void setPorGestionarTotal(BigDecimal porGestionarTotal) {
		this.porGestionarTotal = porGestionarTotal;
	}

	public List<RubroDTO> getRubros() {
		return rubros;
	}

	public void setRubros(List<RubroDTO> rubros) {
		this.rubros = rubros;
	}
}





