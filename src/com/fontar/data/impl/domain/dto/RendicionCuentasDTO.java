package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.assembler.RendicionCuentasAssembler;
import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;

/**
 * 
 * @author gboaglio
 * 
 */
public class RendicionCuentasDTO {
	
	private Long id;
	
	private TipoRendicion tipo;
	private Long idSeguimiento;
	private String descripcionSeguimiento;

	private String numeroFactura;
		
	private Long idRubro;
	private String nombreRubro;
	
	private String numeroRecibo;
	
	private Long idPersona;
	private String nombrePersona;
	
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
	
	private String paisProveedor;					// sólo para Rubro General	
	
	private String profesionPersona;		
	private String descripcion;						// Rubro Persona => función del proyecto | Rubro General => descripción
	
	private BigDecimal montoSueldoMensual;			// sólo para Rubro Persona	
	private BigDecimal porcentajeDedicacion;		// sólo para Rubro Persona
	private Long mesesParticipacion;				// sólo para Rubro Persona
	
	private String observaciones;
	
	private Date version;	
	
	public RendicionCuentasDTO() {}

	public RendicionCuentasDTO(RendicionCuentasBean rendicion) {
		RendicionCuentasAssembler.getInstance().buildDto(rendicion, this);
	}
	
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public BigDecimal getMontoTotalEvaluacion() {
		if(montoTotalEvaluacion!=null) return montoTotalEvaluacion;
		else {
			if(montoParteEvaluacion==null || montoContraparteEvaluacion==null) return null;
			return montoParteEvaluacion.add(montoContraparteEvaluacion);
		}
	}
	public void setMontoTotalEvaluacion(BigDecimal montoTotalEvaluacion) {
		this.montoTotalEvaluacion = montoTotalEvaluacion;
	}
	public BigDecimal getMontoTotalGestion() {
		if(montoTotalGestion!=null) return montoTotalGestion;
		else {
			if(montoParteGestion==null || montoContraparteGestion==null) return null;
			return montoParteGestion.add(montoContraparteGestion);
		}
	}
	public void setMontoTotalGestion(BigDecimal montoTotalGestion) {
		this.montoTotalGestion = montoTotalGestion;
	}
	public String getDescripcionSeguimiento() {
		return descripcionSeguimiento;
	}
	public void setDescripcionSeguimiento(String descripcionSeguimiento) {
		this.descripcionSeguimiento = descripcionSeguimiento;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	public String getNombreRubro() {
		return nombreRubro;
	}
	public void setNombreRubro(String nombreRubro) {
		this.nombreRubro = nombreRubro;
	}

	public void setTipo(TipoRendicion tipoRendicion) {
		this.tipo = tipoRendicion;		
	}

	public TipoRendicion getTipo() {
		return tipo;
	}
}






