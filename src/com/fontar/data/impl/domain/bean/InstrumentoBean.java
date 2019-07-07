package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.data.impl.assembler.DistribucionFinanciamientoDTOAssembler;
import com.fontar.data.impl.domain.codes.instrumento.TipoFinanciamientoAsignacionInstrumento;
import com.fontar.data.impl.domain.codes.instrumento.TipoFinanciamientoInstrumento;

/**
 * Esta clase representa los objetos que conforman los instrumentos de beneficio para los cuales se presentan proyectos.
 * Estos instrumentos pueden ser de Ventanilla Permanente (<code>VentanillaPermanenteBean</code>) y de Llamado a Convocatoria (<code>LlamadoConvocatoriaBean</code>).
 * Además estos instrumentos se definen en función de un template o definición de instrumento(<code>InstrumentoDefBean</code>).
 * @see com.fontar.data.impl.domain.bean.InstrumentoDefBean
 * @see com.fontar.data.impl.domain.bean.VentanillaPermanenteBean 
 * @see com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean
 */
public class InstrumentoBean implements Adjuntable {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private Long id;

	private Long emerix;

	private Long idCartera;

	private String varianteEmerix;

	private String identificador;

	private String denominacion;

	private String modalidad;

	private Date fechaReconocimientoGastos;

	private BigDecimal montoFinanciamiento;

	private BigDecimal montoFinanciamientoTotal;

	private BigDecimal proporcionApoyo;

	private String periodoGracia;

	private String plazoEjecucion;

	private String plazoAmortizacion;

	private String tasaInteres;

	private String garantia;

	private Boolean permiteFinanciamientoBancario;

	private Boolean permiteComision;

	private Boolean permiteSecretaria;
	
	private Boolean permiteAdjudicacion;

	private Boolean permiteMultipleJurisdiccion;

	private Boolean permitePropiciado;
	
	private Boolean paraProyectoHistorico;

	private Integer PlazoReconsideracion;

	private Integer firmaContrato;

	private String observaciones;

	private String tipoDistribucionFinanciamiento;

	private Long idMatrizPresupuesto;

	private Long idComision;

	private Long idInstrumentoDef;

	private MatrizPresupuestoBean matrizPresupuesto;

	private CarteraBean cartera;

	private Long idInstrumentoVersion;

	public Enumerable estado;

	protected String codigoEstado;

	protected TipoFinanciamientoAsignacionInstrumento tipoFinanciamientoAsignacion;

	protected String codigoTipoFinanciamientoAsignacion;

	protected TipoFinanciamientoInstrumento tipoFinanciamiento;

	protected String codigoTipoFinanciamiento;

	private ComisionBean comision;

	private InstrumentoDefBean instrumentoDef;

	private InstrumentoVersionBean instrumentoVersionActual;

	private Set distribucionFinanciamientos;

	private Set instrumentoVersiones;

	private Set adjuntos = new HashSet();

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public ComisionBean getComision() {
		return comision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param comision Documentar el parametro!
	 */
	public void setComision(ComisionBean comision) {
		this.comision = comision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getDenominacion() {
		return denominacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param denominacion Documentar el parametro!
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Date getFechaReconocimientoGastos() {
		return fechaReconocimientoGastos;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param fechaReconocimientoGastos Documentar el parametro!
	 */
	public void setFechaReconocimientoGastos(Date fechaReconocimientoGastos) {
		this.fechaReconocimientoGastos = fechaReconocimientoGastos;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Integer getFirmaContrato() {
		return firmaContrato;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param firmaContrato Documentar el parametro!
	 */
	public void setFirmaContrato(Integer firmaContrato) {
		this.firmaContrato = firmaContrato;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getGarantia() {
		return garantia;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param garantia Documentar el parametro!
	 */
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param id Documentar el parametro!
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getIdComision() {
		return idComision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idComision Documentar el parametro!
	 */
	public void setIdComision(Long idComision) {
		this.idComision = idComision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param identificador Documentar el parametro!
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getIdInstrumentoDef() {
		return idInstrumentoDef;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idInstrumentoDef Documentar el parametro!
	 */
	public void setIdInstrumentoDef(Long idInstrumentoDef) {
		this.idInstrumentoDef = idInstrumentoDef;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getIdMatrizPresupuesto() {
		return idMatrizPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idMatrizPresupuesto Documentar el parametro!
	 */
	public void setIdMatrizPresupuesto(Long idMatrizPresupuesto) {
		this.idMatrizPresupuesto = idMatrizPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public InstrumentoDefBean getInstrumentoDef() {
		return instrumentoDef;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param instrumentoDef Documentar el parametro!
	 */
	public void setInstrumentoDef(InstrumentoDefBean instrumentoDef) {
		this.instrumentoDef = instrumentoDef;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public MatrizPresupuestoBean getMatrizPresupuesto() {
		return matrizPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param matrizPresupuesto Documentar el parametro!
	 */
	public void setMatrizPresupuesto(MatrizPresupuestoBean matrizPresupuesto) {
		this.matrizPresupuesto = matrizPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getModalidad() {
		return modalidad;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param modalidad Documentar el parametro!
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public BigDecimal getMontoFinanciamiento() {
		return montoFinanciamiento;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param montoFinanciamiento Documentar el parametro!
	 */
	public void setMontoFinanciamiento(BigDecimal montoFinanciamiento) {
		this.montoFinanciamiento = montoFinanciamiento;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param observaciones Documentar el parametro!
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getPeriodoGracia() {
		return periodoGracia;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param periodoGracia Documentar el parametro!
	 */
	public void setPeriodoGracia(String periodoGracia) {
		this.periodoGracia = periodoGracia;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Boolean getPermiteComision() {
		return permiteComision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param permiteComision Documentar el parametro!
	 */
	public void setPermiteComision(Boolean permiteComision) {
		this.permiteComision = permiteComision;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Boolean getPermiteFinanciamientoBancario() {
		return permiteFinanciamientoBancario;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param permiteFinanciamientoBancario Documentar el parametro!
	 */
	public void setPermiteFinanciamientoBancario(Boolean permiteFinanciamientoBancario) {
		this.permiteFinanciamientoBancario = permiteFinanciamientoBancario;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Boolean getPermiteMultipleJurisdiccion() {
		return permiteMultipleJurisdiccion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param permiteMultipleJurisdiccion Documentar el parametro!
	 */
	public void setPermiteMultipleJurisdiccion(Boolean permiteMultipleJurisdiccion) {
		this.permiteMultipleJurisdiccion = permiteMultipleJurisdiccion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Boolean getPermitePropiciado() {
		return permitePropiciado;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param permitePropiciado Documentar el parametro!
	 */
	public void setPermitePropiciado(Boolean permitePropiciado) {
		this.permitePropiciado = permitePropiciado;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Boolean getPermiteSecretaria() {
		return permiteSecretaria;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param permiteSecretaria Documentar el parametro!
	 */
	public void setPermiteSecretaria(Boolean permiteSecretaria) {
		this.permiteSecretaria = permiteSecretaria;
	}

	
	public Boolean getPermiteAdjudicacion() {
		return permiteAdjudicacion;
	}

	public void setPermiteAdjudicacion(Boolean permiteAdjudicacion) {
		this.permiteAdjudicacion = permiteAdjudicacion;
	}
	
	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getPlazoAmortizacion() {
		return plazoAmortizacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param plazoAmortizacion Documentar el parametro!
	 */
	public void setPlazoAmortizacion(String plazoAmortizacion) {
		this.plazoAmortizacion = plazoAmortizacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getPlazoEjecucion() {
		return plazoEjecucion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param plazoEjecucion Documentar el parametro!
	 */
	public void setPlazoEjecucion(String plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Integer getPlazoReconsideracion() {
		return PlazoReconsideracion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param plazoReconsideracion Documentar el parametro!
	 */
	public void setPlazoReconsideracion(Integer plazoReconsideracion) {
		PlazoReconsideracion = plazoReconsideracion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getTasaInteres() {
		return tasaInteres;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param tasaInteres Documentar el parametro!
	 */
	public void setTasaInteres(String tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public BigDecimal getProporcionApoyo() {
		return proporcionApoyo;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param proporcionApoyo Documentar el parametro!
	 */
	public void setProporcionApoyo(BigDecimal proporcionApoyo) {
		this.proporcionApoyo = proporcionApoyo;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public BigDecimal getMontoFinanciamientoTotal() {
		return montoFinanciamientoTotal;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param montoFinanciamientoTotal Documentar el parametro!
	 */
	public void setMontoFinanciamientoTotal(BigDecimal montoFinanciamientoTotal) {
		this.montoFinanciamientoTotal = montoFinanciamientoTotal;
	}

	public Set getDistribucionFinanciamientos() {
		return distribucionFinanciamientos;
	}

	public void setDistribucionFinanciamientos(Set distribucionFinanciamientos) {
		this.distribucionFinanciamientos = distribucionFinanciamientos;
	}

	@SuppressWarnings("unchecked")
	public void setListDistribucionFinanciamientosDTO(List distribucionFinanciamientosDTO) {
		List listBeans = DistribucionFinanciamientoDTOAssembler.buildListBeans(distribucionFinanciamientosDTO);

		this.distribucionFinanciamientos = new HashSet();
		this.distribucionFinanciamientos.addAll(listBeans);
	}

	public Set getInstrumentoVersiones() {
		return instrumentoVersiones;
	}

	public void setInstrumentoVersiones(Set instrumentoVersiones) {
		this.instrumentoVersiones = instrumentoVersiones;
	}

	public InstrumentoVersionBean getInstrumentoVersionActual() {
		return instrumentoVersionActual;
	}

	public void setInstrumentoVersionActual(InstrumentoVersionBean instrumentoVersionActual) {
		this.instrumentoVersionActual = instrumentoVersionActual;
	}

	public Long getIdInstrumentoVersion() {
		return idInstrumentoVersion;
	}

	public void setIdInstrumentoVersion(Long idInstrumentoVersion) {
		this.idInstrumentoVersion = idInstrumentoVersion;
	}

	public String getTipoDistribucionFinanciamiento() {
		return tipoDistribucionFinanciamiento;
	}

	public void setTipoDistribucionFinanciamiento(String tipoDistribucionFinanciamiento) {
		this.tipoDistribucionFinanciamiento = tipoDistribucionFinanciamiento;
	}

	public Enumerable getEstado() {
		return estado;
	}

	public void setEstado(Enumerable estado) {
		this.estado = estado;
	}

	@SuppressWarnings("unused")
	protected String getCodigoEstado() {
		return null;
	}

	@SuppressWarnings("unused")
	protected void setCodigoEstado(String codigoEstado) {
	}

	public TipoFinanciamientoAsignacionInstrumento getTipoFinanciamientoAsignacion() {
		return tipoFinanciamientoAsignacion;
	}

	public void setTipoFinanciamientoAsignacion(TipoFinanciamientoAsignacionInstrumento tipoFinanciamientoAsignacion) {
		this.tipoFinanciamientoAsignacion = tipoFinanciamientoAsignacion;
	}

	public String getCodigoTipoFinanciamientoAsignacion() {
		return tipoFinanciamientoAsignacion.name();
	}

	public void setCodigoTipoFinanciamientoAsignacion(String codigoTipoFinanciamientoAsignacion) {
		this.tipoFinanciamientoAsignacion = TipoFinanciamientoAsignacionInstrumento.valueOf(codigoTipoFinanciamientoAsignacion);
	}

	public String getCodigoTipoFinanciamiento() {
		return tipoFinanciamiento.name();
	}

	public void setCodigoTipoFinanciamiento(String codigoTipoFinanciamiento) {
		this.tipoFinanciamiento = TipoFinanciamientoInstrumento.valueOf(codigoTipoFinanciamiento);
	}

	public TipoFinanciamientoInstrumento getTipoFinanciamiento() {
		return tipoFinanciamiento;
	}

	public void setTipoFinanciamiento(TipoFinanciamientoInstrumento tipoFinanciamiento) {
		this.tipoFinanciamiento = tipoFinanciamiento;
	}
	
	public Boolean esVentanilla() {
		return this instanceof VentanillaPermanenteBean;
	}

	public Long getEmerix() {
		return emerix;
	}

	public void setEmerix(Long emerix) {
		this.emerix = emerix;
	}

	public String getVarianteEmerix() {
		return varianteEmerix;
	}

	public void setVarianteEmerix(String varianteEmerix) {
		this.varianteEmerix = varianteEmerix;
	}

	public Long getIdCartera() {
		return idCartera;
	}

	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

	public CarteraBean getCartera() {
		return cartera;
	}

	public void setCartera(CarteraBean cartera) {
		this.cartera = cartera;
	}

	public Boolean aplicaCargaAlicuotaCF() {
		return getMatrizPresupuesto().esTipoCFConsejerias() || getMatrizPresupuesto().esTipoCF();
	}

	public Boolean getParaProyectoHistorico() {
		return paraProyectoHistorico;
	}

	public void setParaProyectoHistorico(Boolean paraProyectoHistorico) {
		this.paraProyectoHistorico = paraProyectoHistorico;
	}
	
	public Boolean permiteAdquisicion() {
		return this.instrumentoDef.getPermiteAdquisicion();
	}
}