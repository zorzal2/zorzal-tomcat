package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.RubroBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;

/**
 * Dto de <code>ProcedimientoBean</code>.<br>
 * @author ssanchez
 */
public class PacItemDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idProyecto;
	private ProyectoBean proyecto;
	private Long codigo;
	private String etapa;
	private Long idRubro;
	private RubroBean rubro;
	private String montoEstimadoFontarDTO;
	private Long idTipoAdquisicion;
	private TipoAdquisicionBean tipoAdquisicion;
	private String fechaEstimadaCompraDTO;
	private String descripcion;
	private EstadoPacItem estado;
	private String fechaEstado;
	private String observaciones;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public EstadoPacItem getEstado() {
		return estado;
	}
	public void setEstado(EstadoPacItem estado) {
		this.estado = estado;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	public String getFechaEstimadaCompraDTO() {
		return fechaEstimadaCompraDTO;
	}
	public void setFechaEstimadaCompraDTO(String fechaEstimadaCompra) {
		this.fechaEstimadaCompraDTO = fechaEstimadaCompra;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}
	public Long getIdRubro() {
		return idRubro;
	}
	public void setIdRubro(Long idRubro) {
		this.idRubro = idRubro;
	}
	public Long getIdTipoAdquisicion() {
		return idTipoAdquisicion;
	}
	public void setIdTipoAdquisicion(Long idTipoAdquisicion) {
		this.idTipoAdquisicion = idTipoAdquisicion;
	}
	public String getMontoEstimadoFontarDTO() {
		return montoEstimadoFontarDTO;
	}
	public void setMontoEstimadoFontarDTO(String montoEstimadoFontar) {
		this.montoEstimadoFontarDTO = montoEstimadoFontar;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public ProyectoBean getProyecto() {
		return proyecto;
	}
	public void setProyecto(ProyectoBean proyecto) {
		this.proyecto = proyecto;
	}
	public RubroBean getRubro() {
		return rubro;
	}
	public void setRubro(RubroBean rubro) {
		this.rubro = rubro;
	}
	public TipoAdquisicionBean getTipoAdquisicion() {
		return tipoAdquisicion;
	}
	public void setTipoAdquisicion(TipoAdquisicionBean tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}
}