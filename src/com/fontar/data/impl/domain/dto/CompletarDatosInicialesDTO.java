package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;

public class CompletarDatosInicialesDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Recomendacion recomendacionFinal;
	private Date fechaResolucion;
	private String codigoResolucion;
	private BigDecimal porcentajeAlicuotaAdjudicada;
	private BigDecimal porcentajeAlicuotaSolicitada;
	private BigDecimal montoFontarRrhh;
	private BigDecimal montoContraparteRrhh;
	private BigDecimal montoFontarBienCapital;
	private BigDecimal montoContraparteBienCapital;
	private BigDecimal montoFontarConsultoriaServicio;
	private BigDecimal montoContraparteConsultoriaServicio;
	private BigDecimal montoFontarMaterialInsumo;
	private BigDecimal montoContraparteMaterialInsumo;
	private BigDecimal montoFontarOtro;
	private BigDecimal montoContraparteOtro;
	private Date fechaFirmaDeContrato;
	private String observacion;
	
	public String getCodigoResolucion() {
		return codigoResolucion;
	}
	public void setCodigoResolucion(String codigoResolucion) {
		this.codigoResolucion = codigoResolucion;
	}
	public Date getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	public BigDecimal getMontoContraparteBienCapital() {
		return montoContraparteBienCapital;
	}
	public void setMontoContraparteBienCapital(BigDecimal montoContraparteBienCapital) {
		this.montoContraparteBienCapital = montoContraparteBienCapital;
	}
	public BigDecimal getMontoContraparteConsultoriaServicio() {
		return montoContraparteConsultoriaServicio;
	}
	public void setMontoContraparteConsultoriaServicio(BigDecimal montoContraparteConsultoriaServicio) {
		this.montoContraparteConsultoriaServicio = montoContraparteConsultoriaServicio;
	}
	public BigDecimal getMontoContraparteMaterialInsumo() {
		return montoContraparteMaterialInsumo;
	}
	public void setMontoContraparteMaterialInsumo(BigDecimal montoContraparteMaterialInsumo) {
		this.montoContraparteMaterialInsumo = montoContraparteMaterialInsumo;
	}
	public BigDecimal getMontoContraparteOtro() {
		return montoContraparteOtro;
	}
	public void setMontoContraparteOtro(BigDecimal montoContraparteOtro) {
		this.montoContraparteOtro = montoContraparteOtro;
	}
	public BigDecimal getMontoContraparteRrhh() {
		return montoContraparteRrhh;
	}
	public void setMontoContraparteRrhh(BigDecimal montoContraparteRrhh) {
		this.montoContraparteRrhh = montoContraparteRrhh;
	}
	public BigDecimal getMontoFontarBienCapital() {
		return montoFontarBienCapital;
	}
	public void setMontoFontarBienCapital(BigDecimal montoFontarBienCapital) {
		this.montoFontarBienCapital = montoFontarBienCapital;
	}
	public BigDecimal getMontoFontarConsultoriaServicio() {
		return montoFontarConsultoriaServicio;
	}
	public void setMontoFontarConsultoriaServicio(BigDecimal montoFontarConsultoriaServicio) {
		this.montoFontarConsultoriaServicio = montoFontarConsultoriaServicio;
	}
	public BigDecimal getMontoFontarMaterialInsumo() {
		return montoFontarMaterialInsumo;
	}
	public void setMontoFontarMaterialInsumo(BigDecimal montoFontarMaterialInsumo) {
		this.montoFontarMaterialInsumo = montoFontarMaterialInsumo;
	}
	public BigDecimal getMontoFontarOtro() {
		return montoFontarOtro;
	}
	public void setMontoFontarOtro(BigDecimal montoFontarOtro) {
		this.montoFontarOtro = montoFontarOtro;
	}
	public BigDecimal getMontoFontarRrhh() {
		return montoFontarRrhh;
	}
	public void setMontoFontarRrhh(BigDecimal montoFontarRrhh) {
		this.montoFontarRrhh = montoFontarRrhh;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public BigDecimal getPorcentajeAlicuotaAdjudicada() {
		return porcentajeAlicuotaAdjudicada;
	}
	public BigDecimal getPorcentajeAlicuotaSolicitada() {
		return porcentajeAlicuotaSolicitada;
	}
	public void setPorcentajeAlicuotaAdjudicada(BigDecimal porcentajeAlicuotaAdjudicada) {
		this.porcentajeAlicuotaAdjudicada = porcentajeAlicuotaAdjudicada;
	}
	public void setPorcentajeAlicuotaSolicitada(BigDecimal porcentajeAlicuotaSolicitada) {
		this.porcentajeAlicuotaSolicitada = porcentajeAlicuotaSolicitada;
	}
	public Recomendacion getRecomendacionFinal() {
		return recomendacionFinal;
	}
	public void setRecomendacionFinal(Recomendacion recomendacionFinal) {
		this.recomendacionFinal = recomendacionFinal;
	}
	public Date getFechaFirmaDeContrato() {
		return fechaFirmaDeContrato;
	}
	public void setFechaFirmaDeContrato(Date fechaFirmaDeContrato) {
		this.fechaFirmaDeContrato = fechaFirmaDeContrato;
	}
	

}
