package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoUffaBid;

/**
 * Estos objetos representa los item del PAC tratados bajo un cierto Procedimiento de Control de Adquisiciones.
 * 
 * @see com.fontar.data.impl.domain.bean.ProcedimientoBean
 * @see com.fontar.data.impl.domain.bean.PacBean
 */
public class ProcedimientoItemBean {

	private Long id;
	private ResultadoFontar resultadoFontar;
	private BigDecimal montoFontar;
	private ResultadoUffaBid resultadoUffa;
	private BigDecimal montoUffa;
	private ResultadoUffaBid resultadoBid;
	private BigDecimal montoBid;

	private Long idProcedimiento;
	private ProcedimientoBean procedimiento;

	private Long idPacItem;
	private PacBean pacItem;

	private Long idMoneda;
	private MonedaBean moneda;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Moneda en la que se exprasa el monto de adjudicación.
	 * @return
	 */
	public Long getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}
	
	/**
	 * Identificador del item del PAC (PacBean) asociado.
	 * @return
	 */
	public Long getIdPacItem() {
		return idPacItem;
	}
	public void setIdPacItem(Long idPacItem) {
		this.idPacItem = idPacItem;
	}
	public Long getIdProcedimiento() {
		return idProcedimiento;
	}
	public void setIdProcedimiento(Long idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
	
	/**
	 * Moneda en la que se exprasa el monto de adjudicación.
	 * @return
	 */
	public MonedaBean getMoneda() {
		return moneda;
	}
	public void setMoneda(MonedaBean moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getMontoBid() {
		return montoBid;
	}
	
	
	public void setMontoBid(BigDecimal montoBid) {
		this.montoBid = montoBid;
	}
	public BigDecimal getMontoFontar() {
		return montoFontar;
	}
	public void setMontoFontar(BigDecimal montoFontar) {
		this.montoFontar = montoFontar;
	}
	public BigDecimal getMontoUffa() {
		return montoUffa;
	}
	public void setMontoUffa(BigDecimal montoUffa) {
		this.montoUffa = montoUffa;
	}
	public PacBean getPacItem() {
		return pacItem;
	}
	public void setPacItem(PacBean pacItem) {
		this.pacItem = pacItem;
	}
	public ProcedimientoBean getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(ProcedimientoBean procedimiento) {
		this.procedimiento = procedimiento;
	}
	public ResultadoUffaBid getResultadoBid() {
		return resultadoBid;
	}
	public void setResultadoBid(ResultadoUffaBid resultadoBid) {
		this.resultadoBid = resultadoBid;
	}
	public ResultadoFontar getResultadoFontar() {
		return resultadoFontar;
	}
	public void setResultadoFontar(ResultadoFontar resultadoFontar) {
		this.resultadoFontar = resultadoFontar;
	}
	public ResultadoUffaBid getResultadoUffa() {
		return resultadoUffa;
	}
	public void setResultadoUffa(ResultadoUffaBid resultadoUffa) {
		this.resultadoUffa = resultadoUffa;
	}
	public boolean equals(Object obj) {
		return pacItem.equals(obj);
	}
	public EstadoPacItem getCodigoEstado() {
		return pacItem.getCodigoEstado();
	}
	public String getDescripcion() {
		return pacItem.getDescripcion();
	}
	public Boolean getEsPatrimonio() {
		return pacItem.getEsPatrimonio();
	}
	public String getEtapa() {
		return pacItem.getEtapa();
	}
	public Date getFecha() {
		return pacItem.getFecha();
	}
	public Date getFechaAdjudicacion() {
		return pacItem.getFechaAdjudicacion();
	}
	public Date getFechaDesembolso() {
		return pacItem.getFechaDesembolso();
	}
	public Date getFechaEstado() {
		return pacItem.getFechaEstado();
	}
	public Long getIdProyecto() {
		return pacItem.getIdProyecto();
	}
	public Long getIdRubro() {
		return pacItem.getIdRubro();
	}
	public Long getIdTipoAdquisicion() {
		return pacItem.getIdTipoAdquisicion();
	}
	public Long getItem() {
		return pacItem.getItem();
	}
	public BigDecimal getMontoAdjudicacion() {
		return pacItem.getMontoAdjudicacion();
	}
	public BigDecimal getMontoDesembolsado() {
		return pacItem.getMontoDesembolsado();
	}
	public String getObservaciones() {
		return pacItem.getObservaciones();
	}
	public String getProveedor() {
		return pacItem.getProveedor();
	}
	public ProyectoBean getProyecto() {
		return pacItem.getProyecto();
	}
	public RubroBean getRubro() {
		return pacItem.getRubro();
	}
	public TipoAdquisicionBean getTipoAdquisicion() {
		return pacItem.getTipoAdquisicion();
	}
	public int hashCode() {
		return pacItem.hashCode();
	}
	public void setCodigoEstado(EstadoPacItem codigoEstado) {
		pacItem.setCodigoEstado(codigoEstado);
	}
	public void setDescripcion(String descripcion) {
		pacItem.setDescripcion(descripcion);
	}
	public void setEsPatrimonio(Boolean esPatrimonio) {
		pacItem.setEsPatrimonio(esPatrimonio);
	}
	public void setEtapa(String etapa) {
		pacItem.setEtapa(etapa);
	}
	public void setFecha(Date fecha) {
		pacItem.setFecha(fecha);
	}
	public void setFechaAdjudicacion(Date fechaAdjudicacion) {
		pacItem.setFechaAdjudicacion(fechaAdjudicacion);
	}
	public void setFechaDesembolso(Date fechaDesembolso) {
		pacItem.setFechaDesembolso(fechaDesembolso);
	}
	public void setFechaEstado(Date fechaEstado) {
		pacItem.setFechaEstado(fechaEstado);
	}
	public void setIdProyecto(Long idProyecto) {
		pacItem.setIdProyecto(idProyecto);
	}
	public void setIdRubro(Long idRubro) {
		pacItem.setIdRubro(idRubro);
	}
	public void setIdTipoAdquisicion(Long idTipoAdquisicion) {
		pacItem.setIdTipoAdquisicion(idTipoAdquisicion);
	}
	public void setItem(Long item) {
		pacItem.setItem(item);
	}
	public void setMontoAdjudicacion(BigDecimal montoAdjudicacion) {
		pacItem.setMontoAdjudicacion(montoAdjudicacion);
	}
	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		pacItem.setMontoDesembolsado(montoDesembolsado);
	}
	public void setObservaciones(String observaciones) {
		pacItem.setObservaciones(observaciones);
	}
	public void setProveedor(String proveedor) {
		pacItem.setProveedor(proveedor);
	}
	public void setProyecto(ProyectoBean proyecto) {
		pacItem.setProyecto(proyecto);
	}
	public void setRubro(RubroBean rubro) {
		pacItem.setRubro(rubro);
	}
	public void setTipoAdquisicion(TipoAdquisicionBean tipoAdquisicion) {
		pacItem.setTipoAdquisicion(tipoAdquisicion);
	}
	public String toString() {
		return pacItem.toString();
	}
	
	public Boolean tieneDatosResultadoFontar() {
		return this.resultadoFontar!=null;
	}
	
	public Boolean tieneDatosResultadoUffa() {
		return this.resultadoUffa!=null;
	}	
	
	public Boolean tieneDatosResultadoBid() {
		return this.resultadoBid!=null;
	}	
	/**
	 * Determina si el item tiene una instancia de evaluacion aprobada
	 * por la UFFA pero no tiene rechazo ni aprobacion definitiva. O sea
	 * si preclasifica.
	 * @return
	 */
	public boolean tieneAprobacionParcialDeUffa() {
		
		return ResultadoUffaBid.APROB_PLIEGO.equals(this.resultadoUffa) || 
				ResultadoUffaBid.APROB_PRE_CLASIF.equals(this.resultadoUffa);
	}
	/**
	 * Determina si el item tiene una instancia de evaluacion aprobada
	 * por la UFFA pero no tiene rechazo ni aprobacion definitiva. O sea
	 * si preclasifica.
	 * @return
	 */
	public Boolean tieneAprobacionParcialDelBid() {

		return	ResultadoUffaBid.APROB_PLIEGO.equals(this.resultadoBid) || 
				ResultadoUffaBid.APROB_PRE_CLASIF.equals(this.resultadoBid);
	}
	/**
	 * Decide si este item esta en espera de aprobacion por parte de la Uffa o el Bid.
	 * @return
	 */
	public boolean estaEnEsperaDeAprobacionExterna() {
		return 	getCodigoEstado().equals(EstadoPacItem.EN_PROCESO_DE_COMPRA)
			&& getResultadoFontar()!=null
			&&	getResultadoFontar().enEsperaDeAprobacionExterna()
			&&	getProcedimiento().getEstado().equals(EstadoProcedimiento.EN_AUTORIZACION);
	}
}