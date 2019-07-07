package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.pragma.util.StringUtil;

/**
 * Estos objetos representan los procedimientos del Control de Adquisiciones 
 * para items del PAC. La asociación entre procedimientos e items del PAC esta representada mediante los objetos ProcedimientoItemBean. 
 * 
 * Cada procedimiento tiene asociado un circuito de workflow en el cual se llevaran a cabo las
 * distintas tareas como son Designa el evaluador técnico, Cargar Fundamentación Evaluador, Ingresar Remisión, Cargar Resultado Autorización, etc.   
 * 
 * @see com.fontar.data.impl.domain.bean.ProcedimientoItemBean
 * @see com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio
 * @see com.fontar.data.impl.domain.bean.PacBean
 * @see com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento
 * @see com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento 
 */
public class ProcedimientoBean implements Workflowable {

	private Long id;
	private EstadoProcedimiento estado;
	private Date fechaRecepcion;
	private String codigo;
	private String descripcion;
	private Date fechaAsignacionEvaluador;
	private String descripcionAsignacionEvaluador;
	private Date fechaFundamentacion;
	private String descripcionFundamentacion;
	private Date fechaResultadoFontar;
	private String observacionResultadoFontar;
	private Date fechaRemisionUffa;
	private String observacionRemisionUffa;
	private Date fechaRemisionBid;
	private String observacionRemisionBid;
	private Date fechaResultadoUffa;
	private String observacionResultadoUffa;
	private Date fechaResultadoBid;
	private String observacionResultadoBid;
	private Long idWorkFlow;

	private Long idProyecto;
    private ProyectoRaizBean proyecto;

	private Long idPersonaEvaluador;
	private EvaluadorBean evaluador;

    private Long idTipoAdquisicion;
	private TipoAdquisicionBean tipoAdquisicion;
	
	public ProcedimientoBean() {
	}

	public ProcedimientoBean(Long idPersonaEvaluador, Date fechaAsignacionEvaluador, String descripcionAsignacionEvaluador) {
		this.fechaAsignacionEvaluador = fechaAsignacionEvaluador;
		this.descripcionAsignacionEvaluador = descripcionAsignacionEvaluador;
		this.idPersonaEvaluador = idPersonaEvaluador;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionAsignacionEvaluador() {
		return descripcionAsignacionEvaluador;
	}
	public void setDescripcionAsignacionEvaluador(String descripcionAsignacionEvaluador) {
		this.descripcionAsignacionEvaluador = descripcionAsignacionEvaluador;
	}
	public String getDescripcionFundamentacion() {
		return descripcionFundamentacion;
	}
	public void setDescripcionFundamentacion(String descripcionFundamentacion) {
		this.descripcionFundamentacion = descripcionFundamentacion;
	}
	
	/**
	 * Informa el estado del procedimiento
	 * @return
	 */
	public EstadoProcedimiento getEstado() {
		return estado;
	}
	public void setEstado(EstadoProcedimiento estado) {
		this.estado = estado;
	}
	public EvaluadorBean getEvaluador() {
		return evaluador;
	}
	public void setEvaluador(EvaluadorBean evaluador) {
		this.evaluador = evaluador;
	}
	public Date getFechaAsignacionEvaluador() {
		return fechaAsignacionEvaluador;
	}
	public void setFechaAsignacionEvaluador(Date fechaAsignacionEvaluador) {
		this.fechaAsignacionEvaluador = fechaAsignacionEvaluador;
	}
	public Date getFechaFundamentacion() {
		return fechaFundamentacion;
	}
	public void setFechaFundamentacion(Date fechaFundamentacion) {
		this.fechaFundamentacion = fechaFundamentacion;
	}
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public Date getFechaRemisionBid() {
		return fechaRemisionBid;
	}
	public void setFechaRemisionBid(Date fechaRemisionBid) {
		this.fechaRemisionBid = fechaRemisionBid;
	}
	public Date getFechaRemisionUffa() {
		return fechaRemisionUffa;
	}
	public void setFechaRemisionUffa(Date fechaRemisionUffa) {
		this.fechaRemisionUffa = fechaRemisionUffa;
	}
	public Date getFechaResultadoBid() {
		return fechaResultadoBid;
	}
	public void setFechaResultadoBid(Date fechaResultadoBid) {
		this.fechaResultadoBid = fechaResultadoBid;
	}
	public Date getFechaResultadoUffa() {
		return fechaResultadoUffa;
	}
	public void setFechaResultadoUffa(Date fechaResultadoUffa) {
		this.fechaResultadoUffa = fechaResultadoUffa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPersonaEvaluador() {
		return idPersonaEvaluador;
	}
	public void setIdPersonaEvaluador(Long idPersonaEvaluador) {
		this.idPersonaEvaluador = idPersonaEvaluador;
	}
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
	public Long getIdTipoAdquisicion() {
		return idTipoAdquisicion;
	}
	public void setIdTipoAdquisicion(Long idTipoAdquisicion) {
		this.idTipoAdquisicion = idTipoAdquisicion;
	}
	public ProyectoRaizBean getProyecto() {
		return proyecto;
	}
	public void setProyecto(ProyectoRaizBean proyecto) {
		this.proyecto = proyecto;
	}
	public TipoAdquisicionBean getTipoAdquisicion() {
		return tipoAdquisicion;
	}
	public void setTipoAdquisicion(TipoAdquisicionBean tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}
	public Date getFechaResultadoFontar() {
		return fechaResultadoFontar;
	}

	public void setFechaResultadoFontar(Date fechaResultadoFontar) {
		this.fechaResultadoFontar = fechaResultadoFontar;
	}

	public String getObservacionResultadoBid() {
		return observacionResultadoBid;
	}

	public void setObservacionResultadoBid(String observacionResultadoBid) {
		this.observacionResultadoBid = observacionResultadoBid;
	}

	public String getObservacionResultadoFontar() {
		return observacionResultadoFontar;
	}

	public void setObservacionResultadoFontar(String observacionResultadoFontar) {
		this.observacionResultadoFontar = observacionResultadoFontar;
	}

	public String getObservacionResultadoUffa() {
		return observacionResultadoUffa;
	}

	public void setObservacionResultadoUffa(String observacionResultadoUffa) {
		this.observacionResultadoUffa = observacionResultadoUffa;
	}
	
	public String getObservacionRemisionBid() {
		return observacionRemisionBid;
	}

	public void setObservacionRemisionBid(String observacionRemisionBid) {
		this.observacionRemisionBid = observacionRemisionBid;
	}

	public String getObservacionRemisionUffa() {
		return observacionRemisionUffa;
	}

	public void setObservacionRemisionUffa(String observacionRemisionUffa) {
		this.observacionRemisionUffa = observacionRemisionUffa;
	}

	public Long getIdWorkFlow() {
		return idWorkFlow;
	}
	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}
	public String getBusinessDescription() {
		StringBuffer sb = new StringBuffer();

		sb.append("Nro:");
		sb.append(this.id);
		sb.append(" ");
		
		sb.append("Fecha:");
		sb.append(this.fechaRecepcion);
		sb.append(" ");
		
		sb.append("Estado:");
		sb.append(this.estado.getDescripcion());
		sb.append(" ");

		return sb.toString();
	}

	public Boolean tieneDatosEvaluadorTecnico() {
		
		Boolean tiene = false;
		
		tiene = this.idPersonaEvaluador!=null && 
				this.fechaAsignacionEvaluador!=null && 
				!StringUtil.isEmpty(this.descripcionAsignacionEvaluador);
		
		return tiene;	
	}
	
	public Boolean tieneDatosFundamentacionEvaluador() {
		
		Boolean tiene = false;
		
		tiene = this.fechaFundamentacion!=null && 
				!StringUtil.isEmpty(this.descripcionFundamentacion);
		
		return tiene;	
	}	
	
	public Boolean tieneDatosRemisionUffa() {
		
		Boolean tiene = false;
		
		tiene = this.fechaRemisionUffa!=null &&
		!StringUtil.isEmpty(this.observacionRemisionUffa); 
		
		
		return tiene;	
	}	
	
	public Boolean tieneDatosRemisionBid() {
		
		Boolean tiene = false;
		
		tiene = this.fechaRemisionBid!=null &&
		!StringUtil.isEmpty(this.observacionRemisionBid); 
		
		
		return tiene;	
	}
	
	public Boolean tieneDatosResultadoUffa() {
		return this.fechaResultadoUffa!=null;
	}	
	
	public Boolean tieneDatosResultadoBid() {
		
		Boolean tiene = false;
		
		tiene = this.fechaResultadoBid!=null &&
		!StringUtil.isEmpty(this.observacionResultadoBid); 
		
		
		return tiene;	
	}
	
	public boolean estaActivo() {
		return estado.estaActivo();
	}
}