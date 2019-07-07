package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.impl.domain.codes.instrumentoDef.TipoInstrumentoDef;

/**
 * Estos objetos representan templates de instrumentos. 
 * Por ejemplo, puede existir una única definición del instrumento ARAI, para 
 * el cual anualmente se crea un instrumento de Convocatoria (<code>LlamadoConvocatoriaBean</code>).
 */
public class InstrumentoDefBean implements Adjuntable {

	private Long id;

	private String identificador;

	private String denominacion;

	private BigDecimal monto;

	private String modalidad;

	private BigDecimal proporcionApoyo;

	private String periodoGracia;

	private String plazoEjecucion;

	private String plazoAmortizacion;

	private String tasaInteres;

	private String garantia;

	private Boolean permiteMultipleJurisdiccion;

	private Boolean permiteFinanciamientoBancario;

	private Boolean permiteComision;

	private Boolean permiteSecretaria;

	private Boolean permiteAdjudicacion;

	private Boolean permitePropiciado;

	private Boolean permiteAdquisicion;

	private String observacion;

	private Long idFuenteFinanciamiento;

	private FuenteFinanciamientoBean fuenteFinanciamiento;

	private Date fecha;

	private TipoInstrumentoDef tipo;

	private Boolean activo;

	private Set adjuntos = new HashSet();

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getProporcionApoyo() {
		return proporcionApoyo;
	}

	public void setProporcionApoyo(BigDecimal proporcionApoyo) {
		this.proporcionApoyo = proporcionApoyo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public FuenteFinanciamientoBean getFuenteFinanciamiento() {
		return fuenteFinanciamiento;
	}

	public void setFuenteFinanciamiento(FuenteFinanciamientoBean fuenteFinanciamiento) {
		this.fuenteFinanciamiento = fuenteFinanciamiento;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getPeriodoGracia() {
		return periodoGracia;
	}

	public void setPeriodoGracia(String periodoGracia) {
		this.periodoGracia = periodoGracia;
	}

	public Boolean getPermiteAdjudicacion() {
		return permiteAdjudicacion;
	}

	public void setPermiteAdjudicacion(Boolean permiteAdjudicacion) {
		this.permiteAdjudicacion = permiteAdjudicacion;
	}

	public Boolean getPermiteComision() {
		return permiteComision;
	}

	public void setPermiteComision(Boolean permiteComision) {
		this.permiteComision = permiteComision;
	}

	public Boolean getPermiteFinanciamientoBancario() {
		return permiteFinanciamientoBancario;
	}

	public void setPermiteFinanciamientoBancario(Boolean permiteFinanciamientoBancario) {
		this.permiteFinanciamientoBancario = permiteFinanciamientoBancario;
	}

	public Boolean getPermiteMultipleJurisdiccion() {
		return permiteMultipleJurisdiccion;
	}

	public void setPermiteMultipleJurisdiccion(Boolean permiteMultipleJurisdiccion) {
		this.permiteMultipleJurisdiccion = permiteMultipleJurisdiccion;
	}

	public Boolean getPermitePropiciado() {
		return permitePropiciado;
	}

	public void setPermitePropiciado(Boolean permitePropiciado) {
		this.permitePropiciado = permitePropiciado;
	}

	public Boolean getPermiteSecretaria() {
		return permiteSecretaria;
	}

	public void setPermiteSecretaria(Boolean permiteSecretaria) {
		this.permiteSecretaria = permiteSecretaria;
	}

	public String getPlazoAmortizacion() {
		return plazoAmortizacion;
	}

	public void setPlazoAmortizacion(String plazoAmortizacion) {
		this.plazoAmortizacion = plazoAmortizacion;
	}

	public String getPlazoEjecucion() {
		return plazoEjecucion;
	}

	public void setPlazoEjecucion(String plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	public String getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(String tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public Long getIdFuenteFinanciamiento() {
		return idFuenteFinanciamiento;
	}

	public void setIdFuenteFinanciamiento(Long idFuenteFinanciamiento) {
		this.idFuenteFinanciamiento = idFuenteFinanciamiento;
	}

	public TipoInstrumentoDef getTipo() {
		return tipo;
	}

	public void setTipo(TipoInstrumentoDef tipo) {
		this.tipo = tipo;
	}

	@SuppressWarnings("unused")
	public String getCodigoTipo() {
		return tipo.name();
	}

	@SuppressWarnings("unused")
	public void setCodigoTipo(String codigoTipo) {
		tipo = TipoInstrumentoDef.valueOf(codigoTipo);
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

	public Boolean getPermiteAdquisicion() {
		return permiteAdquisicion;
	}

	public void setPermiteAdquisicion(Boolean permiteAdquisicion) {
		this.permiteAdquisicion = permiteAdquisicion;
	}
}
