package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;

/**
 * El Plan de Adquisiciones Consolidado (PAC) define una planificación de 
 * adquisición de bienes y servicios informando los montos 
 * y las fechas estimadas y el tipo de procedimiento a utilizar.
 * El PAC aplica únicamente a los proyectos de tipo ARAI, es decir aquellos
 * que presentados bajo instrumentos de beneficio que tienen definido Matriz de Presupuesto de tipo ARAI.<br>
 * Cada PAC debe ser presentado por el beneficario luego de la firma del contrato el beneficiario debe presentar el PAC.<br>
 * 
 * El circuito de autorización de los items de PAC se realiza por medio de los Procedimientos (ver ProcedimientoBean).  
 * @see com.fontar.data.impl.domain.bean.ProcedimientoBean
 * @see com.fontar.data.impl.domain.bean.DesembolsoUFFABean
 * @see com.fontar.data.impl.domain.bean.TipoAdquisicionBean
 * @see com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem
 */
public class PacBean {

	private Long id;
	
	private Long idProyecto;
	
	private ProyectoBean proyecto;
	
	private Long item;
	
	
	private String etapa;
	
	private Long idRubro;
	
	private RubroBean rubro;
	
	private BigDecimal montoFontar;
	
	private Long idTipoAdquisicion;
	
	private TipoAdquisicionBean tipoAdquisicion;
	
	private Date fecha;
	
	private String descripcion;
	
	private EstadoPacItem codigoEstado;
	
	private Date fechaEstado;
	
    private Boolean esPatrimonio = Boolean.FALSE;
    
    private String proveedor;
    
    private Date fechaAdjudicacion;
    
    private BigDecimal montoAdjudicacion;
    
    private Date fechaDesembolso;
    
    private BigDecimal montoDesembolsado;

	private Long idMoneda;
	
	private MonedaBean moneda;

	private String observaciones;
	
	private Set<DesembolsoUFFABean> desembolsosUFFA;

	public Set<DesembolsoUFFABean> getDesembolsosUFFA() {
		return desembolsosUFFA;
	}

	public void setDesembolsosUFFA(Set<DesembolsoUFFABean> desembolsosUFFA) {
		this.desembolsosUFFA = desembolsosUFFA;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * Estado actual del item del PAC.
	 * @return
	 */
	public EstadoPacItem getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(EstadoPacItem codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	/**
	 * Es una descripción del bien o servicio que se adquiere.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Fecha estimada de la presentación del pedido de Compra
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Indica la etapa del plan de actividades del presupuesto relacionada con el ítem.
	 * @return
	 */
	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public Long getIdTipoAdquisicion() {
		return idTipoAdquisicion;
	}

	public void setIdTipoAdquisicion(Long idTipoAdquisicion) {
		this.idTipoAdquisicion = idTipoAdquisicion;
	}

	public Long getIdRubro() {
		return idRubro;
	}

	public void setIdRubro(Long idRubro) {
		this.idRubro = idRubro;
	}

	/**
	 * Es un número que identifica unívocamente el item del PAC.
	 */
	
	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	/**
	 * Es el importe estimado en $ que el FONTAR deberá otorgar.
	 * @return
	 */
	public BigDecimal getMontoFontar() {
		return montoFontar;
	}

	public void setMontoFontar(BigDecimal montoFontar) {
		this.montoFontar = montoFontar;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * Es el rubro del item y corresponde al rubro del presupuesto (RRHH, Bienes de Capital, Consultoría y Servicios, Materiales e Insumos, Otros Costos).
	 * @return
	 */
	public RubroBean getRubro() {
		return rubro;
	}

	public void setRubro(RubroBean rubro) {
		this.rubro = rubro;
	}

	/**
	 * Identifica el tipo de adquisición 
	 * @return
	 */
	public TipoAdquisicionBean getTipoAdquisicion() {
		return tipoAdquisicion;
	}

	public void setTipoAdquisicion(TipoAdquisicionBean tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}

	public ProyectoBean getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}

	public Boolean getEsPatrimonio() {
		return esPatrimonio;
	}

	public void setEsPatrimonio(Boolean esPatrimonio) {
		if(esPatrimonio==null) this.esPatrimonio = false; 
		else this.esPatrimonio = esPatrimonio;
	}

	public Date getFechaAdjudicacion() {
		return fechaAdjudicacion;
	}

	public void setFechaAdjudicacion(Date fechaAdjudicacion) {
		this.fechaAdjudicacion = fechaAdjudicacion;
	}

	public Date getFechaDesembolso() {
		return fechaDesembolso;
	}

	public void setFechaDesembolso(Date fechaDesembolso) {
		this.fechaDesembolso = fechaDesembolso;
	}

	public BigDecimal getMontoAdjudicacion() {
		return montoAdjudicacion;
	}

	public void setMontoAdjudicacion(BigDecimal montoAdjudicacion) {
		this.montoAdjudicacion = montoAdjudicacion;
	}
	/**
	 * Corresponde en realidad al monto del pedido de desembolso
	 * @return
	 */
	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}
	/**
	 * Corresponde en realidad al monto del pedido de desembolso
	 * @return
	 */
	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public Long getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}

	public MonedaBean getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaBean moneda) {
		this.moneda = moneda;
	}
	
	public BigDecimal getMontoPagado() {
		BigDecimal sum = BigDecimal.ZERO;
		for(DesembolsoUFFABean desembolso : getDesembolsosUFFA()) {
			BigDecimal montoPago = desembolso.getMontoPago();
			if(montoPago!=null) sum = sum.add(montoPago);
		}
		return sum;
	}

}