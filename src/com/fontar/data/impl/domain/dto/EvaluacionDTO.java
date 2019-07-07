package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.EncryptedObject;

public class EvaluacionDTO {

	private Long id;

	private TipoEvaluacion tipo;

	private EstadoEvaluacion estado;

	private EncryptedObject resultadoEvaluacion;

	private String recomendacion;

	private String fundamentacion;

	private String fecha;

	private String fechaInicial;

	private Long idCronograma;

	private Long idPlanTrabajo;

	private Long idPresupuesto;
	
	private Long idPresupuestoInicial;

	private String observacion;

	private Long idWorkFlow;

	private Long idProyecto;
	
	private Boolean resumido = Boolean.FALSE;

	private EvaluableDTO evaluable;

	private BitacoraDTO bitacora;

	private ProyectoPresupuestoDTO proyectoPresupuesto;

	public ProyectoPresupuestoDTO getProyectoPresupuesto() {
		return proyectoPresupuesto;
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoDTO proyectoPresupuesto) {
		this.proyectoPresupuesto = proyectoPresupuesto;
	}

	public BitacoraDTO getBitacora() {
		return bitacora;
	}

	public void setBitacora(BitacoraDTO bitacora) {
		this.bitacora = bitacora;
	}

	public EstadoEvaluacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoEvaluacion estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFundamentacion() {
		return fundamentacion;
	}

	public void setFundamentacion(String fundamentacion) {
		this.fundamentacion = fundamentacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCronograma() {
		return idCronograma;
	}

	public void setIdCronograma(Long idCronograma) {
		this.idCronograma = idCronograma;
	}

	public Long getIdPlanTrabajo() {
		return idPlanTrabajo;
	}

	public void setIdPlanTrabajo(Long idPlanTrabajo) {
		this.idPlanTrabajo = idPlanTrabajo;
	}

	public Long getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Long getIdWorkFlow() {
		return idWorkFlow;
	}

	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public EvaluableDTO getEvaluable() {
		return this.evaluable;
	}

	public void setEvaluable(EvaluableDTO dto) {
		this.evaluable = dto;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public EncryptedObject getResultadoEvaluacion() {
		return resultadoEvaluacion;
	}

	public void setResultadoEvaluacion(EncryptedObject resultadoEvaluacion) {
		this.resultadoEvaluacion = resultadoEvaluacion;
	}
	
	public ResultadoEvaluacion getResultado() {
		if (resultadoEvaluacion == null)
			return ResultadoEvaluacion.A_DEFINIR;
		else
			return (ResultadoEvaluacion) resultadoEvaluacion.getObject();
	}

	public TipoEvaluacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvaluacion tipo) {
		this.tipo = tipo;
	}

	public String getTipoDescripcion() {
		return this.getTipo().getDescripcion();
	}
	
	public EvaluableDTO getProyecto() {
		return this.getEvaluable();
	}
	public Boolean getResumido(){
		return this.resumido;
	}
	public void setResumido(Boolean resumido){
		this.resumido=  resumido ;
	}
	public void setResumido(){
		this.setResumido( Boolean.TRUE );
	}

	public Long getIdPresupuestoInicial() {
		return idPresupuestoInicial;
	}

	public void setIdPresupuestoInicial(Long idPresupuestoInicial) {
		this.idPresupuestoInicial = idPresupuestoInicial;
	}

}