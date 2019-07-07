package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.entidad.TipoEmpresa;
import com.fontar.data.impl.domain.codes.entidad.TipoNoEmpresa;
import com.pragma.util.StringUtil;

public class EntidadBeneficiariaDTO {
	/**
	 * 
	 * @author ttoth
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idTributaria;

	private String emerix;

	private String tipoEmpresa;

	private String tipoNoEmpresa;

	private String tipo;

	private String fechaInicioActividad;

	private String codigoCategorizacion;

	private String descEmpresa;

	private Integer numeroConstitucion;

	private Long idLocalizacionEconomica;

	private LocalizacionDTO localizacionEconomica;

	private Long idEmpleoPermanente;

	private EmpleoPermanenteDTO empleoPermanente;

	private Long idCiiu;

	private String txtCiiu;

	private FacturacionDTO facturacionDTO;

	private String[] idEntidades;
	
	private String[] Cuits;
	
	private String[] Denominaciones;
	
	public String[] getCuits() {
		return Cuits;
	}

	public void setCuits(String[] cuits) {
		Cuits = cuits;
	}

	public String[] getDenominaciones() {
		return Denominaciones;
	}

	public void setDenominaciones(String[] denominaciones) {
		Denominaciones = denominaciones;
	}

	public String[] getIdEntidades() {
		return idEntidades;
	}

	public void setIdEntidades(String[] idEntidades) {
		this.idEntidades = idEntidades;
	}

	public FacturacionDTO getFacturacionDTO() {
		return facturacionDTO;
	}

	public void setFacturacionDTO(FacturacionDTO facturacionDTO) {
		this.facturacionDTO = facturacionDTO;
	}

	public String getCodigoCategorizacion() {
		return codigoCategorizacion;
	}

	public void setCodigoCategorizacion(String codigoCategorizacion) {
		this.codigoCategorizacion = codigoCategorizacion;
	}

	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public EmpleoPermanenteDTO getEmpleoPermanente() {
		return empleoPermanente;
	}

	public void setEmpleoPermanente(EmpleoPermanenteDTO empleoPermanente) {
		this.empleoPermanente = empleoPermanente;
	}

	public String getFechaInicioActividad() {
		return fechaInicioActividad;
	}

	public void setFechaInicioActividad(String fechaInicioActividad) {
		this.fechaInicioActividad = fechaInicioActividad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCiiu() {
		return idCiiu;
	}

	public void setIdCiiu(Long idCiiu) {
		this.idCiiu = idCiiu;
	}

	public Long getIdEmpleoPermanente() {
		return idEmpleoPermanente;
	}

	public void setIdEmpleoPermanente(Long idEmpleoPermanente) {
		this.idEmpleoPermanente = idEmpleoPermanente;
	}

	public Long getIdLocalizacionEconomica() {
		return idLocalizacionEconomica;
	}

	public void setIdLocalizacionEconomica(Long idLocalizacionEconomica) {
		this.idLocalizacionEconomica = idLocalizacionEconomica;
	}

	public LocalizacionDTO getLocalizacionEconomica() {
		return localizacionEconomica;
	}

	public void setLocalizacionEconomica(LocalizacionDTO localizacionEconomica) {
		this.localizacionEconomica = localizacionEconomica;
	}

	public Integer getNumeroConstitucion() {
		return numeroConstitucion;
	}

	public void setNumeroConstitucion(Integer numeroConstitucion) {
		this.numeroConstitucion = numeroConstitucion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getTipoNoEmpresa() {
		return tipoNoEmpresa;
	}

	public void setTipoNoEmpresa(String tipoNoEmpresa) {
		this.tipoNoEmpresa = tipoNoEmpresa;
	}

	public String getTxtCiiu() {
		return txtCiiu;
	}

	public void setTxtCiiu(String txtCiiu) {
		this.txtCiiu = txtCiiu;
	}

	public String getEmerix() {
		return emerix;
	}

	public void setEmerix(String emerix) {
		this.emerix = emerix;
	}

	public Long getIdTributaria() {
		return idTributaria;
	}

	public void setIdTributaria(Long idTributaria) {
		this.idTributaria = idTributaria;
	}
	
	public String getTipoEmpresaDescripcion() {
		if(StringUtil.isEmpty(tipoEmpresa)) return "";
		else return TipoEmpresa.valueOf(tipoEmpresa).getDescripcion();
	}
	
	public String getTipoNoEmpresaDescripcion() {
		if(StringUtil.isEmpty(tipoNoEmpresa)) return "";
		else return TipoNoEmpresa.valueOf(tipoNoEmpresa).getDescripcion();
	}
}