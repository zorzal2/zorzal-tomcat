package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;

import com.fontar.data.impl.domain.bean.RubroBean;

/**
 * DTO que contiene los datos totalizados para las rendiciones de un rubro raiz.
 * 
 * Una instancia de este DTO representa una fila del cuadro de Resumen de Rendiciones.
 * 
 * @author gboaglio
 *
 */
public class ResumenDeRendicionesDTO {
	
	private RendicionCuentasResumenRubroDTO aprobado;
	private RendicionCuentasResumenRubroDTO solicitado;
	
	private ResumenDeRendicionesDTO() {}
	
	public ResumenDeRendicionesDTO(
			RubroBean rubro, 
			BigDecimal montoSolicitadoParte, 
			BigDecimal montoSolicitadoContraparte,
			BigDecimal montoAprobadoParte, 
			BigDecimal montoAprobadoContraparte
			) {
		solicitado = new RendicionCuentasResumenRubroDTO(rubro, montoSolicitadoParte, montoSolicitadoContraparte);
		aprobado = new RendicionCuentasResumenRubroDTO(rubro, montoAprobadoParte, montoAprobadoContraparte);
	}

	public RendicionCuentasResumenRubroDTO getAprobado() {
		return aprobado;
	}

	public void setAprobado(RendicionCuentasResumenRubroDTO aprobado) {
		this.aprobado = aprobado;
	}

	public RendicionCuentasResumenRubroDTO getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(RendicionCuentasResumenRubroDTO solicitado) {
		this.solicitado = solicitado;
	}
	
	public String getNombreRubro() {
		return aprobado.getNombreRubro();
	}
	
	public Long getIdRubro() {
		return aprobado.getIdRubro();
	}

	public Long getIdRubroPadre() {
		return aprobado.getIdRubroPadre();
	}

	public Long getNroOrden() {
		return aprobado.getNroOrden();
	}
}