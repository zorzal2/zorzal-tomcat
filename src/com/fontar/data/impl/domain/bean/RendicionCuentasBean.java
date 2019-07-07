package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Estos objetos representas items de rendiciones de cuentas.
 * Para un proyecto, durante la ejecución del mismo, se presentan 
 * informes del seguimiento (ver SeguimientoBean). En estos informes
 * se presentan informes de avance y los gastos asociados a la etapa 
 * del proyecto ejecutada.
 * 
 *  A la presentación de estos gastos se la denomina Rendicion de Cuentas.
 *  Una rendicion de cuentas esta siempre expresada en la moneda pesos y 
 *  corresponde a un rubro especifico. En una rendición hay cuatro tipos de montos:
 *  montos de la rendición, que son los incialmente rendidos, los montos de evaluación, 
 *  que son los mismo montos corregidos durante la etapa de evaluación del seguimiento, 
 *  los montos de gestion que son los montos aprobados para gestionar, y 
 *  finalmente los montos ajustados que son los montos que finalmente se contablizaron.<br> 
 *  En una rendición, de acuedo al Tipo de Rendición aplican o no algunos de los atributos del objeto.
 *  
 *   
 *  
 * @see com.fontar.data.impl.domain.bean.SeguimientoBean
 * @see com.fontar.data.impl.domain.codes.rubro.TipoRendicion
 */
public class RendicionCuentasBean {	
	
	private Long id;
	
	private Long idSeguimiento;
	private SeguimientoBean seguimiento;

	private String numeroFactura;
		
	private Long idRubro;
	private RubroBean rubro;
	
	private String numeroRecibo;
	
	private Long idPersona;
	private PersonaBean persona;
	
	private Date fecha;
	
	private String nombreProveedor;
	
	private BigDecimal montoTotal;
	
	private boolean tieneCertificadoProveedor;		// sólo para Rubro General
	
	private BigDecimal montoParteRendicion;
	private BigDecimal montoContraparteRendicion;
	
	private BigDecimal montoParteEvaluacion;
	private BigDecimal montoContraparteEvaluacion;
	private BigDecimal montoTotalEvaluacion;
	
	private BigDecimal montoParteGestion;
	private BigDecimal montoContraparteGestion;
	private BigDecimal montoTotalGestion;
	
	private BigDecimal montoParteAjustado;
	private BigDecimal montoContraparteAjustado;
	private BigDecimal montoTotalAjustado;
	
	private String paisProveedor;					// sólo para Rubro General	
	
	private String profesionPersona;		
	private String descripcion;						// Rubro Persona => función del proyecto | Rubro General => descripción
	
	private BigDecimal montoSueldoMensual;			// sólo para Rubro Persona	
	private BigDecimal porcentajeDedicacion;		// sólo para Rubro Persona
	private Long mesesParticipacion;				// sólo para Rubro Persona
	
	private String observaciones;
	
	private Date version;	
	
	public Date getVersion() {
		return version;
	}
	public void setVersion(Date version) {
		this.version = version;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fechaRendicion) {
		this.fecha = fechaRendicion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Long getIdRubro() {
		return idRubro;
	}
	public void setIdRubro(Long idRubro) {
		this.idRubro = idRubro;
	}
	public Long getMesesParticipacion() {
		return mesesParticipacion;
	}
	public void setMesesParticipacion(Long mesesParticipacion) {
		this.mesesParticipacion = mesesParticipacion;
	}
	public BigDecimal getMontoContraparteEvaluacion() {
		return montoContraparteEvaluacion;
	}
	public void setMontoContraparteEvaluacion(BigDecimal montoContraparteEvaluacion) {
		this.montoContraparteEvaluacion = montoContraparteEvaluacion;
	}
	public BigDecimal getMontoContraparteGestion() {
		return montoContraparteGestion;
	}
	public void setMontoContraparteGestion(BigDecimal montoContraparteGestion) {
		this.montoContraparteGestion = montoContraparteGestion;
	}
	public BigDecimal getMontoContraparteRendicion() {
		return montoContraparteRendicion;
	}
	public void setMontoContraparteRendicion(BigDecimal montoContraparteRendicion) {
		this.montoContraparteRendicion = montoContraparteRendicion;
	}
	public BigDecimal getMontoParteEvaluacion() {
		return montoParteEvaluacion;
	}
	public void setMontoParteEvaluacion(BigDecimal montoParteEvaluacion) {
		this.montoParteEvaluacion = montoParteEvaluacion;
	}
	public BigDecimal getMontoParteGestion() {
		return montoParteGestion;
	}
	public void setMontoParteGestion(BigDecimal montoParteGestion) {
		this.montoParteGestion = montoParteGestion;
	}
	public BigDecimal getMontoParteRendicion() {
		return montoParteRendicion;
	}
	public void setMontoParteRendicion(BigDecimal montoParteRendicion) {
		this.montoParteRendicion = montoParteRendicion;
	}
	public BigDecimal getMontoSueldoMensual() {
		return montoSueldoMensual;
	}
	public void setMontoSueldoMensual(BigDecimal montoSueldoMensual) {
		this.montoSueldoMensual = montoSueldoMensual;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public String getNumeroRecibo() {
		return numeroRecibo;
	}
	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}
	public String getPaisProveedor() {
		return paisProveedor;
	}
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
	}
	public PersonaBean getPersona() {
		return persona;
	}
	public void setPersona(PersonaBean personaBean) {
		this.persona = personaBean;
	}
	public BigDecimal getPorcentajeDedicacion() {
		return porcentajeDedicacion;
	}
	public void setPorcentajeDedicacion(BigDecimal porcentajeDedicacion) {
		this.porcentajeDedicacion = porcentajeDedicacion;
	}
	public String getProfesionPersona() {
		return profesionPersona;
	}
	public void setProfesionPersona(String profesionPersona) {
		this.profesionPersona = profesionPersona;
	}
	
	public RubroBean getRubro() {
		return rubro;
	}
	public void setRubro(RubroBean rubroBean) {
		this.rubro = rubroBean;
	}
	public boolean getTieneCertificadoProveedor() {
		return tieneCertificadoProveedor;
	}
	public void setTieneCertificadoProveedor(boolean tieneCertificadoProveedor) {
		this.tieneCertificadoProveedor = tieneCertificadoProveedor;
	}
	public Long getIdSeguimiento() {
		return idSeguimiento;
	}
	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}
	public SeguimientoBean getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(SeguimientoBean seguimiento) {
		this.seguimiento = seguimiento;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public BigDecimal getMontoTotalEvaluacion() {
		if(montoTotalEvaluacion!=null) return montoTotalEvaluacion;
		else{
			if(montoParteEvaluacion!=null && montoContraparteEvaluacion!=null) return montoParteEvaluacion.add(montoContraparteEvaluacion);
			else return null;
		}
	}
	public void setMontoTotalEvaluacion(BigDecimal montoTotalEvaluacion) {
		this.montoTotalEvaluacion = montoTotalEvaluacion;
	}
	public BigDecimal getMontoTotalGestion() {
		if(montoTotalGestion!=null) return montoTotalGestion;
		else {
			if(montoParteGestion==null || montoContraparteGestion==null) return null;
			else return montoParteGestion.add(montoContraparteGestion);
		}
	}
	public void setMontoTotalGestion(BigDecimal montoTotalGestion) {
		this.montoTotalGestion = montoTotalGestion;
	}

	public BigDecimal getMontoContraparteAjustado() {

		if(montoContraparteAjustado!=null) return montoContraparteAjustado;
		else if(montoContraparteGestion!=null) montoContraparteAjustado=montoContraparteGestion;
		else if(montoContraparteEvaluacion!=null) montoContraparteAjustado=montoContraparteEvaluacion; 
		else montoContraparteAjustado=montoContraparteRendicion;

		return montoContraparteAjustado;
	}
	public void setMontoContraparteAjustado(BigDecimal montoContraparteAjustado) {
		this.montoContraparteAjustado = montoContraparteAjustado;
	}
	
	public BigDecimal getMontoParteAjustado() {
		
		if(montoParteAjustado!=null) return montoParteAjustado;
		else if(montoParteGestion!=null) montoParteAjustado=montoParteGestion;
		else if(montoParteEvaluacion!=null) montoParteAjustado=montoParteEvaluacion; 
		else montoParteAjustado=montoParteRendicion;

		return montoParteAjustado;
	}
	public void setMontoParteAjustado(BigDecimal montoParteAjustado) {
		this.montoParteAjustado = montoParteAjustado;
	}
	/**
	 * Devuelve el último monto total disponible.
	 * @return
	 */
	public BigDecimal getMontoTotalAjustado() {

		BigDecimal montoParte = this.getMontoParteAjustado();
		BigDecimal montoContraparte = this.getMontoContraparteAjustado();
		
		if(montoParte!=null && montoContraparte!=null) montoTotalAjustado=montoParte.add(montoContraparte);
		else if(montoParte!=null) montoTotalAjustado=montoParte;
		else montoTotalAjustado=montoContraparte;
		
		return montoTotalAjustado;
	}
	public void setMontoTotalAjustado(BigDecimal montoTotalAjustado) {
		this.montoTotalAjustado = montoTotalAjustado;
	}
	/**
	 * Devuelve el monto de gestión si lo hay. Si no, devuelve el monto
	 * de evaluación. Si no hay evaluación, devuelve null.
	 * @return
	 */
	public BigDecimal getMontoTotalAprobadoOGestionado() {
		if(montoParteGestion!=null && montoContraparteGestion!=null) 
			return montoParteGestion.add(montoContraparteGestion);
		else {
			if(montoParteEvaluacion!=null && montoContraparteEvaluacion!=null) 
				return montoParteEvaluacion.add(montoContraparteEvaluacion);
			else {
				return null;
			}
		}
	}
	public BigDecimal getMontoParteAprobadoOGestionado() {
		if(montoParteGestion!=null) 
			return montoParteGestion;
		else {
			if(montoParteEvaluacion!=null) 
				return montoParteEvaluacion;
			else {
				return null;
			}
		}
	}
	public BigDecimal getMontoContraparteAprobadoOGestionado() {
		if(montoContraparteGestion!=null) 
			return montoContraparteGestion;
		else {
			if(montoContraparteEvaluacion!=null) 
				return montoContraparteEvaluacion;
			else {
				return null;
			}
		}
	}
	
	public void ActualizarMontoTotal(){
		this.setMontoTotal(this.getMontoParteRendicion().add(this.getMontoContraparteRendicion()));
	}
	
}