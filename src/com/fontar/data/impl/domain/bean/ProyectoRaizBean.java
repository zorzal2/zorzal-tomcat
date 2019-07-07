package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fontar.data.api.domain.Evaluable;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.api.domain.codes.Enumerable;
import com.pragma.bus.DeveloperException;

/**
 * Un Proyecto Raiz representa un proyecto genérico presentado por un beneficiario. 
 * Puede ser bien un proyecto presentado sobre un instrumento de beneficio 
 * específico o bien una Idea Proyecto (sin instrumento asociado).
 * Todo proyecto Raiz tiene un estado y esta asociado a un circuito de workflow, 
 * el cual puede ser diferente entre los distinso proyectos.
 * Todo proyecto Raiz puede tener asociado un conjunto de Evaluaciones y de seguimientos.
 * Además puede contener un presupesto original, 
 * es decir el presentado incialmente por el beneficario y un 
 * presupuesto, que corresponde al presupuesto vigente del proyecto.
 *    
 * @see com.fontar.data.impl.domain.bean.InstrumentoBean
 * @see com.fontar.data.impl.domain.bean.IdeaProyectoBean
 * @see com.fontar.data.impl.domain.bean.ProyectoBean
 */
public abstract class ProyectoRaizBean implements Evaluable, Workflowable {

	protected Long id;

	protected Long idDatos;

	protected Long idPresupuestoOriginal;

	protected Long idProyectoJurisdiccion;

	protected Long idEmpleoPermanente;

	protected Long idInstrumento;

	protected Long idPresupuesto;

	protected Long idWorkFlow;

	protected String codigo;

	protected Enumerable estado;

	protected String codigoEstado;

	protected Boolean estadoReconsideracion;

	protected ProyectoDatosBean proyectoDatos;

	protected EmpleoPermanenteBean empleoPermanente;

	protected ProyectoJurisdiccionBean proyectoJurisdiccion;

	protected ProyectoPresupuestoBean proyectoPresupuesto;

	protected ProyectoPresupuestoBean proyectoPresupuestoOriginal;

	protected InstrumentoBean instrumento;
	
	private String proyectoPitec;
	
	private Set<EvaluacionBean> evaluaciones;
	
	private Set<SeguimientoBean> seguimientos;
	
	protected Long emerix;

	public EmpleoPermanenteBean getEmpleoPermanente() {
		return empleoPermanente;
	}

	public void setEmpleoPermanente(EmpleoPermanenteBean empleoPermanente) {
		this.empleoPermanente = empleoPermanente;
	}

	public InstrumentoBean getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(InstrumentoBean instrumento) {
		this.instrumento = instrumento;
	}

	public ProyectoJurisdiccionBean getProyectoJurisdiccion() {
		return proyectoJurisdiccion;
	}

	public void setProyectoJurisdiccion(ProyectoJurisdiccionBean proyectoJurisdiccion) {
		this.proyectoJurisdiccion = proyectoJurisdiccion;
	}

	public ProyectoPresupuestoBean getProyectoPresupuesto() {
		return proyectoPresupuesto;
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoBean proyectoPresupuesto) {
		this.proyectoPresupuesto = proyectoPresupuesto;
	}

	public ProyectoPresupuestoBean getProyectoPresupuestoOriginal() {
		return proyectoPresupuestoOriginal;
	}

	public void setProyectoPresupuestoOriginal(ProyectoPresupuestoBean proyectoPresupuestoOriginal) {
		this.proyectoPresupuestoOriginal = proyectoPresupuestoOriginal;
	}

	public ProyectoDatosBean getProyectoDatos() {
		return proyectoDatos;
	}

	public void setProyectoDatos(ProyectoDatosBean proyectoDatos) {
		this.proyectoDatos = proyectoDatos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdEmpleoPermanente() {
		return idEmpleoPermanente;
	}

	public void setIdEmpleoPermanente(Long idEmpleoPermanente) {
		this.idEmpleoPermanente = idEmpleoPermanente;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public Long getIdWorkFlow() {
		return idWorkFlow;
	}

	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDatos() {
		return idDatos;
	}

	public void setIdDatos(Long idDatos) {
		this.idDatos = idDatos;
	}

	public Long getIdPresupuestoOriginal() {
		return idPresupuestoOriginal;
	}

	public void setIdPresupuestoOriginal(Long idPresupuestoOriginal) {
		this.idPresupuestoOriginal = idPresupuestoOriginal;
	}

	public Long getIdProyectoJurisdiccion() {
		return idProyectoJurisdiccion;
	}

	public void setIdProyectoJurisdiccion(Long idProyectoJurisdiccion) {
		this.idProyectoJurisdiccion = idProyectoJurisdiccion;
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

	public abstract void cerrarProyecto();
	
	public abstract void reconsiderarProyecto();

	public abstract void anularProyecto();
	
	public abstract void enProcesoEvaluacion();

	public String getDescripcionEstado() {
		return this.getEstado().getDescripcion();
	}
	
	public Boolean estaEnReconsideracion() {
		return getEstadoReconsideracion();
	}

	public String getBusinessDescription() {
		StringBuffer sb = new StringBuffer();

		sb.append("Código:");
		sb.append(this.codigo);
		sb.append(" ");
		
		if (this.getInstrumento() != null){
			sb.append("Instrumento:");
			sb.append(this.getInstrumento().getDenominacion());	
		}		
		
		sb.append(" ");
		sb.append("Beneficiaria:");
		sb.append(this.proyectoDatos.getEntidadBeneficiaria().getDenominacion());
		
		return sb.toString();
	
	}

	/* NEGOCIO */

	public List<String> tieneEvaluacionesAbiertas() {
		List<String> pendientes = new ArrayList<String>();
		for (EvaluacionBean evaluacion : evaluaciones)
			if (evaluacion.getAbierta())
				pendientes.add(evaluacion.getId().toString());

		return pendientes;
	}

	public List<String> seguimientosAbiertos() {
		List<String> pendientes = new ArrayList<String>();
		for (SeguimientoBean seguimiento: getSeguimientos()) {
			if (seguimiento.estaActivo()) {
				pendientes.add(seguimiento.getId().toString());
			}
		}

		return pendientes;
	}
	
	public Set<EvaluacionBean> getEvaluaciones() {
		return evaluaciones;
	}

	@SuppressWarnings("unchecked")
	public void setEvaluaciones(Set evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	@SuppressWarnings("unchecked")
	public void setSeguimientos(Set seguimientos) {
		this.seguimientos = seguimientos;
	}
	
	public void setEstadoReconsideracion(Boolean estadoReconsideracion) {
		this.estadoReconsideracion = estadoReconsideracion;
	}

	public Boolean getEstadoReconsideracion() {
		return (estadoReconsideracion == null) ? false : estadoReconsideracion;
	}

	public Set<SeguimientoBean> getSeguimientos() {
		return seguimientos;
	}

	public ProyectoPresupuestoBean getUltimoPresupuesto() {
		ProyectoPresupuestoBean presupuesto = null;
		if(getIdPresupuestoOriginal()!=null) {
			if(getIdPresupuesto()!=null) presupuesto = getProyectoPresupuesto();
			else presupuesto = getProyectoPresupuestoOriginal();
			//chequeo de consistencia
			if(presupuesto==null) throw new DeveloperException("Estado inconsistente del bean.");
		}
		return presupuesto;
	}
	
	public BigDecimal getMontoSolicitado() {
		if(proyectoPresupuesto==null) return null;
		return proyectoPresupuesto.getMontoSolicitado();
	}

	public Long getEmerix() {
		return emerix;
	}

	public void setEmerix(Long emerix) {
		this.emerix = emerix;
	}

	public String getProyectoPitec() {
		return proyectoPitec;
	}

	public void setProyectoPitec(String proyectoPitec) {
		this.proyectoPitec = proyectoPitec;
	}
}