package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.seguridad.EncryptedObject;

/**
 * DTO para los datos de los Proyectos que se muestran en la vista de evaluación
 * de un Paquete.
 * 
 * @author gboaglio
 * 
 */

public class ProyectoFilaDTO {

	private Long idProyecto;

	private EncryptedObject resultado;

	private String proyecto;

	private String nombreEntidad;

	private String titulo;
	
	private Recomendacion recomendacion;
	
	private TipoPaquete tipoPaquete;
	
	private TratamientoPaquete tratamientoPaquete;
	
	private Boolean permiteAdjudicacionInstrumento;
	
	private List<EvaluacionResumenDTO> evaluaciones;
	
	private String tipoPresupuesto;
	
	private ProyectoBean proyectoBean;
	
	private PaqueteDTO paqueteDTO;
	
	private Long idEvaluacion;
	
	private EvaluacionDTO evaluacion;

	//Presupuesto
	private BigDecimal beneficioFONTARSolicitado;
	private BigDecimal TotalSolicitado;
	private BigDecimal totalAprobado;
	private BigDecimal beneficioFONTARAprobado;

	private BigDecimal porcentajeAlicuotaSolicitada;
	private BigDecimal porcentajeAlicuotaAdjudicada;


	public EvaluacionDTO getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionDTO evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getTipoPresupuesto() {
		return tipoPresupuesto;
	}

	public void setTipoPresupuesto(String tipoPresupuesto) {
		this.tipoPresupuesto = tipoPresupuesto;
	}

	public TipoPaquete getTipoPaquete() {
		return tipoPaquete;
	}

	public void setTipoPaquete(TipoPaquete tipoPaquete) {
		this.tipoPaquete = tipoPaquete;
	}

	public TratamientoPaquete getTratamientoPaquete() {
		return tratamientoPaquete;
	}

	public void setTratamientoPaquete(TratamientoPaquete tratamientoPaquete) {
		this.tratamientoPaquete = tratamientoPaquete;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String codigo) {
		this.proyecto = codigo;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public boolean getPermiteAdjudicacionInstrumento() {
		return permiteAdjudicacionInstrumento;
	}

	public void setPermiteAdjudicacionInstrumento(Boolean permiteAdjudicacionInstrumento) {
		this.permiteAdjudicacionInstrumento = permiteAdjudicacionInstrumento;
	}

	public List<EvaluacionResumenDTO> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionResumenDTO> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public String getResultado() {
		if(this.resultado == null)
			return null;
		else
			return com.fontar.seguridad.ObjectUtils.encriptedEnumSafeGet( this.resultado );
	}
	/**
	 * Devuelve el resultado de la evaluacion o null si no tiene. Si no hay contexto
	 * de encriptacion dispara una excepcion SecurityException.
	 * @return
	 */
	public ResultadoEvaluacion getResultadoEvaluacion() {
		if(resultado == null)
			return null;
		
		return (ResultadoEvaluacion) resultado.getObject();
	}
	
	public void setResultado(ResultadoEvaluacion resultado) {
		if(this.resultado == null)
			this.resultado = new EncryptedObject(resultado,null,true);
		else
			this.resultado = this.resultado.update(resultado);
	}
	public void setResultado(EncryptedObject resultado) {
		this.resultado = resultado;
	}

	public PaqueteDTO getPaqueteDTO() {
		return paqueteDTO;
	}

	public void setPaqueteDTO(PaqueteDTO paqueteDTO) {
		this.paqueteDTO = paqueteDTO;
	}

	public ProyectoBean getProyectoBean() {
		return proyectoBean;
	}

	public void setProyectoBean(ProyectoBean proyectoBean) {
		this.proyectoBean = proyectoBean;
	}

	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	/**
	 * Para proyectos de CF. Es el total aprobado x alicuota 
	 * @return
	 */
	public BigDecimal getBeneficioFONTARAdjudicado() {
		BigDecimal totalAprobado2 = this.getTotalAprobado();
		BigDecimal porcentajeAlicuotaAdjudicada2 = this.getPorcentajeAlicuotaAdjudicada();
		if(totalAprobado2==null || porcentajeAlicuotaAdjudicada2==null)
			return null;
		return totalAprobado2.multiply(porcentajeAlicuotaAdjudicada2).divide(new BigDecimal(100.0));
	}
	/**
	 * Aporte de FONTAR del último presupuesto.
	 * @return
	 */
	public BigDecimal getBeneficioFONTARAprobado() {
		return beneficioFONTARAprobado;
	}
	/**
	 * Aporte de FONTAR del último presupuesto.
	 * @return
	 */
	public void setBeneficioFONTARAprobado(BigDecimal beneficioFONTARAprobado) {
		this.beneficioFONTARAprobado = beneficioFONTARAprobado;
	}
	/**
	 * Aporte de fontar que consta en el presupuesto original.
	 * @return
	 */
	public BigDecimal getBeneficioFONTARSolicitado() {
		return beneficioFONTARSolicitado;
	}
	/**
	 * Aporte de fontar que consta en el presupuesto original. Solo
	 * valido para proyectos No-CF
	 * @return
	 */
	public void setBeneficioFONTARSolicitado(BigDecimal beneficioFONTARSolicitado) {
		this.beneficioFONTARSolicitado = beneficioFONTARSolicitado;
	}
	/**
	 * PR_ALICUOTA_ADJUDICADA. Alicuota a adjudicar. Por default es la solicitada.
	 * @return
	 */
	public BigDecimal getPorcentajeAlicuotaAdjudicada() {
		return porcentajeAlicuotaAdjudicada;
	}
	/**
	 * PR_ALICUOTA_ADJUDICADA. Alicuota a adjudicar. Por default es la solicitada.
	 * @return
	 */
	public void setPorcentajeAlicuotaAdjudicada(BigDecimal porcentajeAlicuotaAdjudicada) {
		this.porcentajeAlicuotaAdjudicada = porcentajeAlicuotaAdjudicada;
	}
	/**
	 * PR_ALICUOTA_SOLICITADA. Alicuota solicitada originalmente.
	 * @return
	 */
	public BigDecimal getPorcentajeAlicuotaSolicitada() {
		return porcentajeAlicuotaSolicitada;
	}
	/**
	 * PR_ALICUOTA_SOLICITADA. Alicuota solicitada originalmente.
	 * @return
	 */
	public void setPorcentajeAlicuotaSolicitada(BigDecimal porcentajeAlicuotaSolicitada) {
		this.porcentajeAlicuotaSolicitada = porcentajeAlicuotaSolicitada;
	}
	/**
	 * Total del presupuesto vigente de la evaluacion. Si no hay un presupuesto
	 * vigente en la evaluacion, total del presupuesto inicial de la evaluacion.
	 * @return
	 */
	public BigDecimal getTotalAprobado() {
		return totalAprobado;
	}
	/**
	 * Total del presupuesto vigente de la evaluacion. Si no hay un presupuesto
	 * vigente en la evaluacion, total del presupuesto inicial de la evaluacion.
	 * @param totalAprobado
	 */
	public void setTotalAprobado(BigDecimal totalAprobado) {
		this.totalAprobado = totalAprobado;
	}
	/**
	 * Total del presupuesto original del proyecto.
	 * @return
	 */
	public BigDecimal getTotalSolicitado() {
		return TotalSolicitado;
	}
	/**
	 * Total del presupuesto original del proyecto.
	 * @param totalSolicitado
	 */
	public void setTotalSolicitado(BigDecimal totalSolicitado) {
		TotalSolicitado = totalSolicitado;
	}
	
}