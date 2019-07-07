package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.ObjectUtils;

public  class VisualizarEvaluacionProyectoDTO extends EvaluacionDTO {

	private CabeceraDTO cabecera;
	
	private EvaluacionDTO dto;

	private String tipos;

	//informacion de presupuesto
	private String montoFontarSolicitado;
	
	private String montoTotalSolicitado;
	
	private String montoFontarAprobado;
	
	private String montoTotalAprobado;
	
	public VisualizarEvaluacionProyectoDTO(){
		super();
	}

	public VisualizarEvaluacionProyectoDTO(EvaluacionDTO dto) {
		super();
		this.dto = dto;
	}
	public Boolean getTieneResultado() {
		return this.dto.getResultado() != null;
	}
	public Boolean getMarcarAceptada() {
		if (this.getTieneResultado())
			return this.dto.getResultado().equals(ResultadoEvaluacion.APROBADO);
		return false;
	}
	public EvaluacionDTO getDto() {
		return dto;
	}
	public boolean equals(Object obj) {
		return dto.equals(obj);
	}
	public BitacoraDTO getBitacora() {
		return dto.getBitacora();
	}
	public EstadoEvaluacion getEstado() {
		return dto.getEstado();
	}
	public String getFecha() {
		return dto.getFecha();
	}
	public String getFechaInicial() {
		return dto.getFechaInicial();
	}
	public String getFundamentacion() {
		return dto.getFundamentacion();
	}
	public Long getId() {
		return dto.getId();
	}
	public Long getIdCronograma() {
		return dto.getIdCronograma();
	}
	public Long getIdPlanTrabajo() {
		return dto.getIdPlanTrabajo();
	}
	public Long getIdPresupuesto() {
		return dto.getIdPresupuesto();
	}
	public Long getIdProyecto() {
		return dto.getIdProyecto();
	}
	public Long getIdWorkFlow() {
		return dto.getIdWorkFlow();
	}
	public String getObservacion() {
		return dto.getObservacion();
	}
	public EvaluableDTO getProyecto() {
		return dto.getEvaluable();
	}
	public ProyectoPresupuestoDTO getProyectoPresupuesto() {
		return dto.getProyectoPresupuesto();
	}
	public String getRecomendacion() {
		return dto.getRecomendacion();
	}
	public ResultadoEvaluacion getResultado() {
		return dto.getResultado();
	}
	public TipoEvaluacion getTipo() {
		return dto.getTipo();
	}
	public String getTipos() {
		return tipos;
	}
	public void setTipos(String tipos) {
		this.tipos = tipos;
	}
	public String getResultadoDescripcion() {
		try{
			return (this.getResultado() != null) ? this.getResultado().getDescripcion() : "";
		}catch(SecurityException e){
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}
	public EvaluableDTO getEvaluable() {
		return dto.getEvaluable();
	}
	public void setResumido(){
		this.setResumido( Boolean.TRUE );
	}
	public CabeceraDTO getCabecera() {
		return cabecera;
	}
	public void setCabecera(CabeceraDTO cabecera) {
		this.cabecera = cabecera;
	}
	
	
	/** 
	 * Al cargar el resultado de una evaluacion no es posible editar el 
	 * presupuesto si el evaluable en cuestion refiere unIdeaProyecto
	 **/
	public boolean aceptaEdicionPresupuesto(){
		return ! this.getEvaluable().getClass().getSimpleName().contains("IdeaProyecto"); 
	}
	
	public boolean getAceptaEdicionPresupuesto(){
		return this.aceptaEdicionPresupuesto();
	}

	public String getMontoFontarAprobado() {
		return montoFontarAprobado;
	}

	public void setMontoFontarAprobado(String montoFontarAprobado) {
		this.montoFontarAprobado = montoFontarAprobado;
	}

	public String getMontoFontarSolicitado() {
		return montoFontarSolicitado;
	}

	public void setMontoFontarSolicitado(String montoFontarSolicitado) {
		this.montoFontarSolicitado = montoFontarSolicitado;
	}

	public String getMontoTotalAprobado() {
		return montoTotalAprobado;
	}

	public void setMontoTotalAprobado(String montoTotalAprobado) {
		this.montoTotalAprobado = montoTotalAprobado;
	}

	public String getMontoTotalSolicitado() {
		return montoTotalSolicitado;
	}

	public void setMontoTotalSolicitado(String montoTotalSolicitado) {
		this.montoTotalSolicitado = montoTotalSolicitado;
	}
}