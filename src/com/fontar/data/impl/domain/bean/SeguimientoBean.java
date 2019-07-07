package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.util.ResourceManager;

/**
 * Estos objetos representan los distintos informes de avance del proyecto.
 * Para un proyecto aproabado, durante la ejecución del mismo, el beneficiario 
 * presenta una serie de informes del seguimiento. En estos informes
 * se presentan los informes de avance técnicos y de los gastos asociados a la etapa 
 * del proyecto ejecutada.
 * 
 * Cada seguimento tiene asociado un estado y un circuito de workflow. En este circuito  
 * se definirán evaluaciones para la evaluación de la aprobación del seguimiento. 
 * 
 * @see com.fontar.data.impl.domain.bean.RendicionCuentasBean
 * @see com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento
 * @see com.fontar.data.impl.domain.bean.EvaluacionGeneralBean
 */
public class SeguimientoBean extends Auditable implements Workflowable, Adjuntable {

	private Long id;		
	
	private Boolean esTecnico;
	private Boolean esFinanciero;
	
	private Date fecha;
	private String descripcion;	
		
	private Set<RendicionCuentasBean> rendiciones = new HashSet<RendicionCuentasBean>();
	
	private Set<ProyectoDesembolsoBean> desembolsosVinculados = new HashSet<ProyectoDesembolsoBean>();

	private Long idWorkFlow;
	
	private Set adjuntos = new HashSet();

	private Long idProyecto;
	private ProyectoBean proyecto;
	
	private EstadoSeguimiento estado;
	
	private String observacion;

	//Montos persistidos
	private BigDecimal montoPresupuestoSegunAvance = null;
	private BigDecimal montoPendienteDeRendicion = null;

	public EstadoSeguimiento getEstado() {
		return estado;
	}
	public void setEstado(EstadoSeguimiento estado) {
		this.estado = estado;
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
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
		
	/**
	 * Devuelve true si el seguimiento incluye rendición de cuentas.
	 * Se usa para la columna "Incluye Rendicion de Cuentas" del inventario
	 */
	public boolean getIncluyeRendicionDeCuentas() {
		return this.rendiciones.size() > 0;
	}
	
	/**
	 * Devuelve true si el seguimiento contiene al menos un adjunto.
	 * Se usa para la columna "Incluye Informe Avance" del inventario
	 */
	public boolean getIncluyeInformeAvance() {
		return this.adjuntos.size() > 0;
	}
	
	public Long getIdWorkFlow() {
		return idWorkFlow;
	}
	
	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;		
	}
	
	public String getBusinessDescription() {	
		StringBuffer sb = new StringBuffer();

		sb.append(ResourceManager.getHeaderResource("app.header.nroProyecto"));
		sb.append(":");
		sb.append(this.getProyecto().getCodigo());
		sb.append(" ");
		
		sb.append(ResourceManager.getHeaderResource("app.header.idSeguimiento"));
		sb.append(":");
		sb.append(this.getId());
		sb.append(" ");
		
		if (this.getProyecto().getInstrumento()!= null){
			sb.append(ResourceManager.getHeaderResource("app.header.instrumento"));
			sb.append(":");
			sb.append(this.getProyecto().getInstrumento().getDenominacion());	
			sb.append(" ");
		}		
		
		if (this.getProyecto().getProyectoDatos()!=null && this.getProyecto().getProyectoDatos().getEntidadBeneficiaria() != null){
			sb.append("Beneficiaria");
			sb.append(":");
			sb.append(this.getProyecto().getProyectoDatos().getEntidadBeneficiaria().getDenominacion());	
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	public Set getAdjuntos() {
		return adjuntos;
	}
	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;		
	}
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
	public ProyectoBean getProyecto() {
		return proyecto;
	}
	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}
	public Boolean getEsFinanciero() {
		return esFinanciero;
	}
	public void setEsFinanciero(Boolean esFinanciero) {
		this.esFinanciero = esFinanciero;
	}
	public Boolean getEsTecnico() {
		return esTecnico;
	}
	public void setEsTecnico(Boolean esTecnico) {
		this.esTecnico = esTecnico;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observaciones) {
		this.observacion = observaciones;
	}
	public Set<RendicionCuentasBean> getRendiciones() {
		return rendiciones;
	}
	public void setRendiciones(Set<RendicionCuentasBean> rendiciones) {
		this.rendiciones = rendiciones;
	}
	public void enProcesoEvaluacion() {
		// TODO: Chequear condiciones para evaluación
		this.estado = EstadoSeguimiento.EVALUACION;
	}
	public boolean estaRecienIniciado() {
		return this.estado.equals(EstadoSeguimiento.INICIADO);
	}
	/**
	 * Un seguimiento se considera positivo si no esta ni anulado, ni
	 * rechazado ni no gestionado ni no autorizado.
	 * @return
	 */
	public boolean esPositivo() {
		return !esNegativo();
	}
	/**
	 * Un seguimiento se considera negativo si se anuló, o se rechazaron sus rendiciones.
	 * Pudo haberse rechazado en una evaluacion de finalizar control o cerrandose
	 * @return
	 */
	public boolean esNegativo() {
		return
		estado.equals(EstadoSeguimiento.ANULADO) ||
		estado.equals(EstadoSeguimiento.RECHAZADO) ||
		estado.equals(EstadoSeguimiento.CERRADO);
	}
	public boolean estaActivo() {
		return !estaTerminado();
	}
	public boolean estaTerminado() {
		EstadoSeguimiento estadoSeguimiento = getEstado();
		return	estadoSeguimiento.equals(EstadoSeguimiento.CERRADO) ||
				estadoSeguimiento.equals(EstadoSeguimiento.ANULADO) ||
				estadoSeguimiento.equals(EstadoSeguimiento.GESTIONADO) ||
				estadoSeguimiento.equals(EstadoSeguimiento.NO_GESTIONADO) ||
				estadoSeguimiento.equals(EstadoSeguimiento.RECHAZADO) ||
				estadoSeguimiento.equals(EstadoSeguimiento.FINALIZADO);
	}
	public Set<ProyectoDesembolsoBean> getDesembolsosVinculados() {
		return desembolsosVinculados;
	}
	public void setDesembolsosVinculados(Set<ProyectoDesembolsoBean> desembolsosAutorizados) {
		this.desembolsosVinculados = desembolsosAutorizados;
	} 
	public boolean tieneDesembolsosAutorizadosPendientesDePago() {
		Set<ProyectoDesembolsoBean> desembolsosAutorizados2 = getDesembolsosVinculados();
		for (ProyectoDesembolsoBean desembolso : desembolsosAutorizados2) {
			if(!desembolso.yaFuePagado()) return true;
		}
		return false;
	}
	/**
	 * Si es nulo, debe calcularse como:
	 *   Evaluación Fontar: monto desembolsado - total rendido aprobado
	 *   Evaluación UFFA: monto desembolsado - total rendido gestionado
	 * @param montoPendienteDeRendicion
	 */
	public BigDecimal getMontoPendienteDeRendicion() {
		return montoPendienteDeRendicion;
	}
	/**
	 * Si es nulo, debe calcularse como:
	 *   Evaluación Fontar: monto desembolsado - total rendido aprobado
	 *   Evaluación UFFA: monto desembolsado - total rendido gestionado
	 * @param montoPendienteDeRendicion
	 */
	public void setMontoPendienteDeRendicion(BigDecimal montoPendienteDeRendicion) {
		this.montoPendienteDeRendicion = montoPendienteDeRendicion;
	}
	/**
	 * Presupuesto según informe de avance. En seguimientos abiertos podrá ser nulo y deberá calcularse
	 * como la suma de los montos desembolsados o autorizados según el cronograma de desembolsos.
	 * En seguimientos terminados contiene la suma de los montos desembolsados o autorizados al momento
	 * del cierre, o un valor ingresado manualmente que representa los montos previstos según avance del
	 * proyecto.
	 * @return
	 */
	public BigDecimal getMontoPresupuestoSegunAvance() {
		return montoPresupuestoSegunAvance;
	}
	/**
	 * Presupuesto según informe de avance. En seguimientos abiertos será nulo y deberá calcularse
	 * como la suma de los montos previstos según el cronograma de desembolsos para desembolsos aprobados.
	 * En seguimientos terminados contiene la suma de los montos previstos al momento
	 * del cierre para desembolsos aprobados, o un valor ingresado manualmente que representa los montos 
	 * previstos según avance del proyecto.
	 * @param montoPresupuestoSegunAvance
	 */
	public void setMontoPresupuestoSegunAvance(BigDecimal montoPresupuestoSegunAvance) {
		this.montoPresupuestoSegunAvance = montoPresupuestoSegunAvance;
	}
}
