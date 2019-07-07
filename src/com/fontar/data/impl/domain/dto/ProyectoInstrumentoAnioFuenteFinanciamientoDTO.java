package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.fontar.data.impl.domain.bean.FuenteFinanciamientoBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.pragma.util.DateTimeUtil;

/**
 * DTO que contiene datos de un <code>Proyecto</code>
 * necesarios para el reporte de "Proyectos por instrumento, por Año y por Fuente de financiamiento"
 * 
 * @author gboaglio
 */
public class ProyectoInstrumentoAnioFuenteFinanciamientoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String fuenteFinanciamiento;
	private int anioPresentacion;
	private String instrumento;
	private String fechaCierre;
	private int cantidadProyectosPresentados;
	private int cantidadProyectosAprobados;
	private BigDecimal montoTotal;
	private BigDecimal montoParte;
	private BigDecimal montoContraparte;	

	/**
	 * Crea el Dto con los datos de <code>Proyecto</code>.
	 * 
	 * @param proyecto
	 */
	public ProyectoInstrumentoAnioFuenteFinanciamientoDTO(ProyectoRaizBean proyecto) {
		
		Date fechaIngreso = proyecto.getProyectoDatos().getFechaIngreso();

		if (fechaIngreso != null) {
			this.anioPresentacion = DateTimeUtil.getCalendar(fechaIngreso).get(Calendar.YEAR);			
		}
		
		InstrumentoBean instrumento = proyecto.getInstrumento();
		
		if (instrumento != null) {
			this.instrumento = instrumento.getDenominacion();
			
			FuenteFinanciamientoBean fuenteFinanciamiento = instrumento.getInstrumentoDef().getFuenteFinanciamiento();
			
			if (fuenteFinanciamiento != null) {
				this.fuenteFinanciamiento = fuenteFinanciamiento.getIdentificador();				
			}
		
			if (!instrumento.esVentanilla()) {
				Date cierre = ((LlamadoConvocatoriaBean) instrumento).getFechaCierre();
				if (cierre != null) {
					this.fechaCierre = DateTimeUtil.formatDate(cierre);
				}
			}
		}

	
	}
	
	
	public int getAnioPresentacion() {
		return anioPresentacion;
	}

	public void setAnioPresentacion(int anioPresentacion) {
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

	public String getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

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
	
	public ProyectoInstrumentoAnioFuenteFinanciamientoDTO() {
		this.setFuenteFinanciamiento("");
	}
		
}
