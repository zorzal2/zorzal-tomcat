package com.fontar.data.impl.domain.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fontar.bus.impl.SinProyCargadoException;
import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.seguridad.EncryptedObject;

/**
 * Estos objetos representan evaluaciones del proyecto, que pueden serpara la aprobación del proyecto o idea proyecto, 
 * o bien para la aprobación de un seguimiento del proyecto (para proyectos ya aprobados).
 * Hay distintos tipos de evaluaciones (ver <code>TipoEvaluacion</code>), las evaluaciones genreales son aquellas que 
 * tienen un circuito de workflow especifico y son realizadas por evaluadores.
 * Una evaluación tiene un estado y, eventualmente un resultado respecto a su aprobación.
 * La información de resultado es considerada confidencial 
 * y por este motivo esta información es registrada de manera encriptada.
 * @see  com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion
 * @see com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion
 * @see com.fontar.data.impl.domain.codes.seguimiento.evaluacion.EstadoEvaluacionSeguimiento
 * @see com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion
 */
public class EvaluacionBean extends Auditable implements Workflowable,Adjuntable {
	// ~ Instance fields
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private Long id;
	
	private EncryptedObject resultado;

	private String recomendacion;

	private String fundamentacion;

	private Date fecha;

	private Date fechaInicial;

	private Long idCronograma;

	private Long idPlanTrabajo;

	private Long idPresupuesto;

	private Long idPresupuestoInicial;

	private String observacion;

	private Long idWorkFlow;

	private Long idProyecto;

	private ProyectoRaizBean proyecto;

	private ProyectoPresupuestoBean proyectoPresupuesto;

	private ProyectoPresupuestoBean proyectoPresupuestoInicial;

	private TipoEvaluacion tipo;

	private EstadoEvaluacion estado;
	
	private Long idPaqRechElegibilidad;
	
	private Long idPaquete;
	
	private boolean esDictamen;

	private Set adjuntos = new HashSet();

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

	public EvaluacionBean() {
		this.setEsDictamen(false);
	}
	
	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * 
	 */
	public Long getIdWorkFlow() {
		return idWorkFlow;
	}

	/**
	 * 
	 * @param idWorkFlow
	 */
	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param fecha Documentar el parametro!
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param fechaInicial Documentar el parametro!
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getFundamentacion() {
		return fundamentacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param fundamentacion Documentar el parametro!
	 */
	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
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
	public Long getIdCronograma() {
		return idCronograma;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idCronograma Documentar el parametro!
	 */
	public void setIdCronograma(Long idCronograma) {
		this.idCronograma = idCronograma;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getIdPlanTrabajo() {
		return idPlanTrabajo;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idPlanTrabajo Documentar el parametro!
	 */
	public void setIdPlanTrabajo(Long idPlanTrabajo) {
		this.idPlanTrabajo = idPlanTrabajo;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param idPresupuesto Documentar el parametro!
	 */
	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param observacion Documentar el parametro!
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public String getRecomendacion() {
		return recomendacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param recomendacion Documentar el parametro!
	 */
	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @return Documentar el valor de retorno!
	 */
	public ResultadoEvaluacion getResultado() {
		if (resultado ==null)  
			return null;
		return (ResultadoEvaluacion) resultado.getObject();
	}

	/**
	 * Documentar el objetivo del metodo!
	 * 
	 * @param resultado Documentar el parametro!
	 */
	public void setResultado(ResultadoEvaluacion resultado) {
		if(this.resultado == null)
			this.resultado = new EncryptedObject(resultado,null,true);
		else
			this.resultado = this.resultado.update(resultado);

		//this.resultadoData = null; // FIXME esto fuerza el durty del objeto
	}

	@SuppressWarnings("unused")
	private void  setResultadoEvaluacion(EncryptedObject resultado) {
		this.resultado = resultado;
	}
	
	public EncryptedObject getResultadoEvaluacion() {
		return this.resultado;
	}


	public Long getIdPresupuestoInicial() {
		return idPresupuestoInicial;
	}

	public void setIdPresupuestoInicial(Long idPresupuestoInicial) {
		this.idPresupuestoInicial = idPresupuestoInicial;
	}

	public ProyectoRaizBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoRaizBean proyecto) {
		this.proyecto = proyecto;
	}

	// ~ NEGOCIO
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Inicia la evaluación de un proyecto
	 */
	public void iniciar() {
		/*
		 * Si el instrumento asociado al Proyecto es con fondos de la LEY
		 * (aplica si el circuito de eval. Incluye Secretaria Permanente) y la
		 * Entidad Evaluadora es EXTERNA, la evaluación pasa a estado PENDIENTE
		 * DE AUTORIZACIÓN.
		 * 
		 * En caso contrario queda en estado PENDIENTE DE RESULTADO.
		 */

		if (proyecto == null) {
			throw new SinProyCargadoException();
		}

		boolean permiteSecretaria = false;

		if ((proyecto.getInstrumento() != null) && (proyecto.getInstrumento().getPermiteSecretaria()))
			permiteSecretaria = true;

		if (permiteSecretaria) {
			this.setEstado(EstadoEvaluacion.PEND_AUTORIZ);
		}
		else {
			this.setEstado(EstadoEvaluacion.PEND_RESULT);
		}
	}

	/**
	 * Verifica si la evaluación esta abierta
	 * @return
	 */
	public Boolean getAbierta() {
		return !(estado.equals(EstadoEvaluacion.ANULADA) || estado.equals(EstadoEvaluacion.CONFIRMADA) || estado.equals(EstadoEvaluacion.NO_AUTORIZADA));
	}

	/**
	 * Verifica si la evaluación esta aceptada
	 * @return
	 */
	public Boolean getAceptada() {
		Boolean aceptada = null;

		if (this.getResultado() != null) {
			if (this.getResultado().equals(ResultadoEvaluacion.RECHAZADO)) {
				aceptada = Boolean.FALSE;
			}

			if (this.getResultado().equals(ResultadoEvaluacion.APROBADO)) {
				aceptada = Boolean.TRUE;
			}
		}

		return aceptada;
	}

	public Boolean esAnulada() {
		return this.estado.equals(EstadoEvaluacion.ANULADA);
	}

	public ProyectoPresupuestoBean getProyectoPresupuesto() {
		return proyectoPresupuesto;
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoBean proyectoPresupuesto) {
		this.proyectoPresupuesto = proyectoPresupuesto;
	}

	public TipoEvaluacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvaluacion tipo) {
		this.tipo = tipo;
	}

	public EstadoEvaluacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoEvaluacion estado) {
		this.estado = estado;
	}

	public String getBusinessDescription() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Código:");
		sb.append(this.getProyecto().getCodigo());
		sb.append(" ");
		
		sb.append("Id:");
		sb.append(this.id);
		sb.append(" ");
		
		if (this.getProyecto().getInstrumento()!= null){
			sb.append("Instrumento:");
			sb.append(this.getProyecto().getInstrumento().getDenominacion());	
		}		
		
		sb.append(" ");
		sb.append("Beneficiaria:");
		sb.append(this.getProyecto().getProyectoDatos().getEntidadBeneficiaria().getDenominacion());
		
		return sb.toString();
	
	}

	public Long getIdPaqRechElegibilidad() {
		return idPaqRechElegibilidad;
	}
	public void setIdPaqRechElegibilidad(Long idPaqRechElegibilidad) {
		this.idPaqRechElegibilidad = idPaqRechElegibilidad;
	}

	public boolean esElegible() {
		if(idPaqRechElegibilidad == null)
			return true;
		else
			return false;
	}

	public Long getIdPaquete() {
		return idPaquete;
	}
	public void setIdPaquete(Long idPaquete) {
		this.idPaquete = idPaquete;
	}
	public boolean isEsDictamen() {
		return esDictamen;
	}
	public void setEsDictamen(boolean esDictamen) {
		this.esDictamen = esDictamen;
	}

	public ProyectoPresupuestoBean getProyectoPresupuestoInicial() {
		return proyectoPresupuestoInicial;
	}

	public void setProyectoPresupuestoInicial(ProyectoPresupuestoBean proyectoPresupuestoInicial) {
		this.proyectoPresupuestoInicial = proyectoPresupuestoInicial;
	}


}
