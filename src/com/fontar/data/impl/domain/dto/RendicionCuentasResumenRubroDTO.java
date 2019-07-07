package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.pragma.util.MathUtils;

/**
 * DTO que representa el resumen de dinero aportado para un rubro (parte/contraparte/total).
 * 
 * Una instancia de este DTO representa una fila de uno de los cuadros 
 * de la vista de Análisis de Gastos
 * 
 * @author gboaglio
 *
 */
public class RendicionCuentasResumenRubroDTO {
	
	private Long nroOrden;
	private Long idRubro;	
	private String nombreRubro;
	private BigDecimal montoParte;
	private BigDecimal montoContraparte;
	private BigDecimal costoTotal;
	private Long idRubroPadre;
	
	private RendicionCuentasResumenRubroDTO() {}
	
	public RendicionCuentasResumenRubroDTO(RubroBean rubro, BigDecimal montoParte, BigDecimal montoContraparte) {		
		this.nroOrden = rubro.getNroOrden();
		this.idRubro = rubro.getId();		
		this.nombreRubro = rubro.getNombre();
		this.idRubroPadre = rubro.getIdRubroPadre();
		this.montoParte = montoParte;
		this.montoContraparte = montoContraparte;

		if(montoParte!=null && montoContraparte!=null) this.costoTotal = montoParte.add(montoContraparte);
		else if(montoParte!=null) this.costoTotal = montoParte;
		else this.costoTotal = montoContraparte;
	}
	
	public RendicionCuentasResumenRubroDTO(RubroBean rubro) {
		this.nroOrden = rubro.getNroOrden();
		this.idRubro = rubro.getId();		
		this.nombreRubro = rubro.getNombre();
		this.idRubroPadre = rubro.getIdRubroPadre();
		this.montoParte =  BigDecimal.ZERO;
		this.montoContraparte = BigDecimal.ZERO;
		this.costoTotal = BigDecimal.ZERO;
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

	public String getWrappedMontoParte() {				
		return MathUtils.formatTwoPlaces(montoParte);		
	}

	public String getWrappedMontoContraparte() {		
		return MathUtils.formatTwoPlaces(montoContraparte);
	}

	public String getWrappedCostoTotal() {		
		return MathUtils.formatTwoPlaces(costoTotal);
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}
	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}
	
	public String getNombreRubro() {
		return nombreRubro;
	}

	public void setNombreRubro(String nombreRubro) {
		this.nombreRubro = nombreRubro;
	}
	
	public Long getIdRubro() {
		return idRubro;
	}

	public void setIdRubro(Long idRubro) {
		this.idRubro = idRubro;
	}

	public Long getIdRubroPadre() {
		return idRubroPadre;
	}

	public void setIdRubroPadre(Long idRubroPadre) {
		this.idRubroPadre = idRubroPadre;
	}

	public Long getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}	 

	@Override	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(this.nroOrden).append(",");		
		sb.append(this.idRubro).append(",");;			
		sb.append(this.nombreRubro).append(",");;		
		sb.append(this.montoParte).append(",");;		
		sb.append(this.montoContraparte).append(",");;		
		sb.append(this.costoTotal).append(",");;
		sb.append(this.idRubroPadre);		
		sb.append(")");
		return sb.toString();		
	}

}
