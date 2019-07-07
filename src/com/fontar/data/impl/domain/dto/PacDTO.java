package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros.RubroDTO;


public class PacDTO {

	private Long id;
	
	private Long idProyecto;
	
	private Long item;
	
	private String etapa;
	
	private Long idRubro;
	
	private RubroDTO rubro;
	
	private BigDecimal montoFontar;

	private BigDecimal montoAdjudicacion;

	private BigDecimal montoDesembolsado;
	
	private Long idTipoAdquisicion;
	
	private TipoAdquisicionDTO tipoAdquisicion;
	
	private Date fecha;
	
	private String descripcion;
	
	private EstadoPacItem codigoEstado;
	
	private Date fechaEstado;

    private Boolean esPatrimonio;

    private Long idMoneda;

    private String descripcionMoneda;

    public EstadoPacItem getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(EstadoPacItem codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
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

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public BigDecimal getMontoFontar() {
		return montoFontar;
	}

	public void setMontoFontar(BigDecimal montoFontar) {
		this.montoFontar = montoFontar;
	}

	public RubroDTO getRubro() {
		return rubro;
	}

	public void setRubro(RubroDTO rubro) {
		this.rubro = rubro;
	}

	public TipoAdquisicionDTO getTipoAdquisicion() {
		return tipoAdquisicion;
	}

	public void setTipoAdquisicion(TipoAdquisicionDTO tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}

	public Boolean getEsPatrimonio() {
		return esPatrimonio;
	}

	public void setEsPatrimonio(Boolean esPatrimonio) {
		this.esPatrimonio = esPatrimonio;
	}

	public BigDecimal getMontoAdjudicacion() {
		return montoAdjudicacion;
	}

	public void setMontoAdjudicacion(BigDecimal montoAdjudicacion) {
		this.montoAdjudicacion = montoAdjudicacion;
	}

	public Long getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Long idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}

	public void setDescripcionMoneda(String descripcionMoneda) {
		this.descripcionMoneda = descripcionMoneda;
	}

	public BigDecimal getMontoDesembolsado() {
		return montoDesembolsado;
	}

	public void setMontoDesembolsado(BigDecimal montoDesembolsado) {
		this.montoDesembolsado = montoDesembolsado;
	}
}
