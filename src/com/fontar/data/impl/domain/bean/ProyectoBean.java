package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.seguridad.EncryptedObject;

/**
 * Estos objetos representa aquellos proyectos que fueron presentados bajo un instrumento de Beneficio especifico.
 * Si este instrumento es de Convocatoria entonces el proyecto referencia a la Presentacion de Convocatoria (ver PresentacionConvocatoriaBean).
 * En cambio, si este instrumento corresponde a una Vetanilla Permanente, entonces se relaciona con la Idea proyecto mediante el atributo idProyectoOrigen.
 * Todo proyecto además de tener una estado contiene una recomendación. La recomendación establece la situación actual en cuanto a la aceptación
 * del proyecto. La principal razon por la cual la recomendación no forma parte del estado del proyecto es 
 * que por un requerimiento específico se solicito que sólo el dato de recomendación sea un dato confidencia que debai ser manejado mediante un mencanismo de encriptación. 
 *    
 * @see com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean
 * @see com.fontar.data.impl.domain.codes.proyecto.Recomendacion
 * @see com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto 
 */ 
public class ProyectoBean extends ProyectoRaizBean implements Adjuntable {

	private EncryptedObject recomendacion;
	
	private Long idPlanTrabajo;

	private Long idPresentacion;

	private Long idProyectoOrigen;

	private Long idProyectoPitec;

	private BigDecimal porcentajeAlicuotaAdjudicada;

	private BigDecimal porcentajeAlicuotaSolicitada;

	private Boolean estadoEnPaquete;

	private EstadoProyecto estado;

	private String motivoCierre;

	private Long idProyectoPaquete;

	private BigDecimal montoAdjudicado;

	private BigDecimal montoAprobado;
	
	private String codigoResolucion;
	
	private Date fechaResolucion; 

	private PresentacionConvocatoriaBean presentacionConvocatoria;

	private Set adjuntos = new HashSet();
	
	private Recomendacion recomendacionFinal;
	
	private Date fechaCierreFinal;
	
	private Date fechaFirmaDeContrato;
	
	private ProyectoPaqueteBean proyectoPaquete;

	public Recomendacion getRecomendacionFinal() {
		return recomendacionFinal;
	}

	public void setRecomendacionFinal(Recomendacion recomendacionFinal) {
		this.recomendacionFinal = recomendacionFinal;
	}

	public Long getIdProyectoPitec() {
		return idProyectoPitec;
	}

	public void setIdProyectoPitec(Long idProyectoPitec) {
		this.idProyectoPitec = idProyectoPitec;
	}

	/*
	 * Datos del bean
	 */
	public Long getIdPlanTrabajo() {
		return idPlanTrabajo;
	}

	public void setIdPlanTrabajo(Long idPlanTrabajo) {
		this.idPlanTrabajo = idPlanTrabajo;
	}

	public Long getIdPresentacion() {
		return idPresentacion;
	}

	public void setIdPresentacion(Long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	public Long getIdProyectoOrigen() {
		return idProyectoOrigen;
	}

	public void setIdProyectoOrigen(Long idProyectoOrigen) {
		this.idProyectoOrigen = idProyectoOrigen;
	}

	public BigDecimal getPorcentajeAlicuotaAdjudicada() {
		return porcentajeAlicuotaAdjudicada;
	}

	public void setPorcentajeAlicuotaAdjudicada(BigDecimal porcentajeAlicuotaAdjudicada) {
		this.porcentajeAlicuotaAdjudicada = porcentajeAlicuotaAdjudicada;
	}

	public BigDecimal getPorcentajeAlicuotaSolicitada() {
		return porcentajeAlicuotaSolicitada;
	}

	public void setPorcentajeAlicuotaSolicitada(BigDecimal porcentajeAlicuotaSolicitada) {
		this.porcentajeAlicuotaSolicitada = porcentajeAlicuotaSolicitada;
	}

	public Recomendacion getRecomendacion() {
		return (Recomendacion) recomendacion.getObject();
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = this.recomendacion.update( recomendacion);
	}
	
	public EncryptedObject getRecomendacionProyecto() {
		return this.recomendacion;
	}
	
	public void setRecomendacionProyecto(EncryptedObject encryptedObject){
		this.recomendacion = encryptedObject;
	}

	public Long getIdProyectoPaquete() {
		return idProyectoPaquete;
	}

	public void setIdProyectoPaquete(Long idProyectoPaquete) {
		this.idProyectoPaquete = idProyectoPaquete;
	}

	public Boolean getEstadoEnPaquete() {
		return estadoEnPaquete;
	}

	public void setEstadoEnPaquete(Boolean estadoEnPaquete) {
		this.estadoEnPaquete = estadoEnPaquete;
	}

	public String getMotivoCierre() {
		return motivoCierre;
	}

	public void setMotivoCierre(String motivoCierre) {
		this.motivoCierre = motivoCierre;
	}


	/**
	 * 
	 * @return
	 */
	@Deprecated
	//DMONTEVERDE: esta propidad se calcula mal para los proyectos con alicuota (esta usando el monto FONTAR)
	//parece que no se esta utlizando
	public BigDecimal getMontoAdjudicado() {
		if (montoAdjudicado == null) {
			// Paquetes de tipo Directorio con tratamiento Adjudicación cuyo
			// instrumento tenga matriz de presupuesto
			// donde Tipo Presupuesto es CREDITO FISCAL
			if (
					this.getInstrumento().getMatrizPresupuesto().esTipoCF() ||
					this.getInstrumento().getMatrizPresupuesto().esTipoCFConsejerias()
				) {
				BigDecimal montoAprobado = this.getMontoAprobado();
				BigDecimal alicuota = this.getPorcentajeAlicuotaSolicitada();

				if (montoAprobado != null && alicuota != null) {
					montoAdjudicado = (alicuota.multiply(montoAprobado)).divide(new BigDecimal(100));
				}
			}
		}

		return montoAdjudicado;
	}

	public BigDecimal getMontoBeneficioFONTARSolicitado() {
		
		ProyectoPresupuestoBean presupuesto = this.getProyectoPresupuestoOriginal();
		if (presupuesto != null) {
			if(this.getInstrumento().aplicaCargaAlicuotaCF()) {
				BigDecimal alicuota = this.getPorcentajeAlicuotaSolicitada();
				BigDecimal monto = presupuesto.getMontoTotal();
				if (monto != null && alicuota != null) 
					return (monto.multiply(alicuota)).divide(new BigDecimal(100));
			}
			else
				return presupuesto.getMontoSolicitado();
		}
		return null;
	}
	
	/**
	 * Devuelve el monto de la Parte del presupuesto del
	 * proyecto cuando tiene una recomendacion de aprobacion.
	 * Si no hay una recomendacion aun o la recomendacion no
	 * implica aprobacion, este monto no esta definido y se
	 * devuelve null.
	 * @return
	 */
	public BigDecimal getMontoBeneficioFONTARAprobado() {
		
		ProyectoPresupuestoBean presupuesto = this.getProyectoPresupuesto();
	//	if (presupuesto !=null && tieneMontoAprobado()) {
		if (presupuesto !=null && estaAprobado()) {
			if(this.getInstrumento().aplicaCargaAlicuotaCF()) {
				BigDecimal alicuota = this.getPorcentajeAlicuotaAdjudicada();
				BigDecimal monto = presupuesto.getMontoTotal();
				if (monto != null && alicuota != null) 
					return (monto.multiply(alicuota)).divide(new BigDecimal(100));
			}
			else
				return presupuesto.getMontoSolicitado();
		}
		return null;
	}
	/**
	 * 
	 * @return
	 */
	public BigDecimal getMontoAprobado() {
		if (montoAprobado == null) {
			ProyectoPresupuestoBean presupuesto = null;

			// Si no tiene evaluaciones
			if (this.getIdPresupuesto() == null) {
				presupuesto = this.getProyectoPresupuestoOriginal();
			}
			// Si tiene evaluaciones
			else {
				presupuesto = this.getProyectoPresupuesto();
			}

			if (estaAprobado() && presupuesto != null) {
				montoAprobado = presupuesto.getMontoSolicitado();
			}
		}
		return montoAprobado;
	}
	/**
	 * Si el proyecto está aprobado devuelve el monto total (parte+contraparte) 
	 * de aprobación. Si no, devuelve null. Si el proyecto no tiene evaluaciones
	 * devuelve null.
	 * @return
	 */
	public BigDecimal getMontoTotalAprobado() {
		if (montoAprobado == null) {
			ProyectoPresupuestoBean presupuesto = null;
			
			// Si no tiene evaluaciones
			if (this.getIdPresupuesto() == null) {
				presupuesto = this.getProyectoPresupuestoOriginal();
			}
			// Si tiene evaluaciones
			else {
				presupuesto = this.getProyectoPresupuesto();
			}
			
			if (estaAprobado() && presupuesto != null) {
				montoAprobado = presupuesto.getMontoTotal();
			}
		}
		return montoAprobado;
	}

	/**
	 * Devuelve el monto total del presupuesto que inicialmente solicito
	 * la empresa. Si no se cargo ningun presupuesto, devuelve null.
	 * @return
	 */
	public BigDecimal getMontoTotalSolicitado() {
		ProyectoPresupuestoBean presupuesto = this.getProyectoPresupuestoOriginal();
		if(presupuesto==null) {
			return null;
		} else {
			return presupuesto.getMontoTotal();
		}
	}
	
	/**
	 * Marca el proyecto como controlado, utiliza información del Instrumento
	 */
	public void finalizarControlEvaluaciones(Recomendacion recomendacion) {
		// ¿Controlado por quien?
		if (instrumento.getPermiteComision()) {
			this.setEstado(EstadoProyecto.CONT_COM);
		}
		else if (instrumento.getPermiteSecretaria()) {
			this.setEstado(EstadoProyecto.CONT_SEC);
		}
		else {
			this.setEstado(EstadoProyecto.CONT_DIR_EVAL);
		}

		this.setRecomendacion(recomendacion);
	}
	/**
	 * 
	 * 
	 */
	public void enProcesoEvaluacion() {
		// TODO: Chequear condiciones para evaluación
		this.estado = EstadoProyecto.EVALUACION;
	}

	public EmpleoPermanenteBean getEmpleoPermanente() {
		return empleoPermanente;
	}

	public void setEmpleoPermanente(EmpleoPermanenteBean empleoPermanente) {
		this.empleoPermanente = empleoPermanente;
	}

	public PresentacionConvocatoriaBean getPresentacionConvocatoria() {
		return presentacionConvocatoria;
	}

	public void setPresentacionConvocatoria(PresentacionConvocatoriaBean presentacionConvocatoria) {
		this.presentacionConvocatoria = presentacionConvocatoria;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	@Override
	protected String getCodigoEstado() {
		return estado.name();
	}

	@Override
	protected void setCodigoEstado(String codigoEstado) {
		estado = EstadoProyecto.valueOf(codigoEstado);
	}

	public boolean esEditable() {
		return !(estado.equals(EstadoProyecto.CERRADO));
	}

	@Override
	public void cerrarProyecto() {
		this.estado = EstadoProyecto.CERRADO;
	}

	@Override
	public void anularProyecto() {
		this.estado = EstadoProyecto.ANULADO;
	}

	public void finalizarProyecto() {
		this.estado = EstadoProyecto.FINALIZADO;
	}
	
	@Override
	public void reconsiderarProyecto() {
		this.estado = EstadoProyecto.PED_RECON;
	}

	
	public Boolean estaEnPaquete() {
		return this.getEstadoEnPaquete();
	}

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

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


	//TODO ver si esta fecha no debe ser ingresada por el usuario
	public void updateResolucion(String codigoResolucion) {
		this.setCodigoResolucion(codigoResolucion);
		this.setFechaResolucion(new Date());
	}

	public Date getFechaCierreFinal() {
		return fechaCierreFinal;
	}

	public void setFechaCierreFinal(Date fechaCierreFinal) {
		this.fechaCierreFinal = fechaCierreFinal;
	}

	public Date getFechaFirmaDeContrato() {
		return fechaFirmaDeContrato;
	}

	public void setFechaFirmaDeContrato(Date fechaFirmaDeContrato) {
		this.fechaFirmaDeContrato = fechaFirmaDeContrato;
	}
	
	public boolean estaAbierto() {
		return !estaTerminado();
	}
	
	public boolean estaTerminado() {
		return estado.esTerminal();
	}

	/**
	 * Un proyecto se considera aprobado en cuanto tiene una
	 * recomendación final que implica aprobacion.
	 * @return
	 */
	public boolean estaAprobado() {
		return
				this.getRecomendacionFinal() != null
			&&	this.getRecomendacionFinal().implicaAprobacion();
	}
	/**
	 * Un proyecto tiene monto aprobado si se cumple alguna
	 * de las condiciones:
	 *  - Tiene recomendacion final positiva
	 *  - No tiene recomendación final pero tiene recomendacion
	 *    no final positiva
	 * @return
	 */
	private boolean tieneMontoAprobado() {
		return conAprobacionPrevista();
	}
	/**
	 * Determina si esta previsto que el proyecto se apruebe. Esto
	 * es: si ya está aprobado o tiene como recomendación alguna
	 * variante de la aprobacion.
	 * @return
	 */
	public boolean conAprobacionPrevista() {
		return estaAprobado() || (
					this.getRecomendacionFinal()==null
				&&	this.getRecomendacion() != null 
				&&	this.getRecomendacion().implicaAprobacion() );
	}

	public ProyectoPaqueteBean getProyectoPaquete() {
		return proyectoPaquete;
	}

	public void setProyectoPaquete(ProyectoPaqueteBean proyectoPaquete) {
		this.proyectoPaquete = proyectoPaquete;
	}
	
	public boolean estaAnulado() {
		return EstadoProyecto.ANULADO.equals(estado);
	}

}