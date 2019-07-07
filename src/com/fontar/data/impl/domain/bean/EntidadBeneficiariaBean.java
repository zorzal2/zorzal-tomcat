package com.fontar.data.impl.domain.bean;

import java.util.Date;

/**
 * Esta clase contiene las entidades Beneficiarias.
 * Estas entidades son aquellas que actuan como beneficiarias al presentar proyectos bajo los instrumentos de beneficio.
 * 
 */
public class EntidadBeneficiariaBean extends Entidable {

	private Long id;

	private Long idTributaria;

	private TributariaBean tributaria;
	
	private Long emerix;

	private String tipoEmpresa;

	private String tipoNoEmpresa;

	private String tipo;

	private Date fechaInicioActividad;

	private String codigoCategorizacion;

	private String descEmpresa;

	private Integer numeroConstitucion;

	private Long idLocalizacionEconomica;

	private LocalizacionBean localizacionEconomica;

	private Long idEmpleoPermanente;

	private EmpleoPermanenteBean empleoPermanente;

	private Long idCiiu;

	// private CiiuBean ciiu;

	/** **************************************************** */

	public String getCuit() {

		return (getEntidad() == null) ? "Entidad=NULL" : getEntidad().getCuit();
	}
	
	public String getDenominacion() {
		
		return (getEntidad() == null) ? "Entidad=NULL" : getEntidad().getDenominacion();
	}

	public void setDenominacion(String denominacion) {

		if(getEntidad() != null) {
			getEntidad().setDenominacion(denominacion);
		}
	}

	/** **************************************************** */

	// public CiiuBean getCiiu() {
	// return ciiu;
	// }
	//
	// public void setCiiu(CiiuBean ciiu) {
	// this.ciiu = ciiu;
	// }
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

	public EmpleoPermanenteBean getEmpleoPermanente() {
		return empleoPermanente;
	}

	public void setEmpleoPermanente(EmpleoPermanenteBean empleoPermanente) {
		this.empleoPermanente = empleoPermanente;
	}

	public Date getFechaInicioActividad() {
		return fechaInicioActividad;
	}

	public void setFechaInicioActividad(Date fechaInicioActividad) {
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

	public LocalizacionBean getLocalizacionEconomica() {
		return localizacionEconomica;
	}

	public void setLocalizacionEconomica(LocalizacionBean localizacionEconomica) {
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

	public Long getEmerix() {
		return emerix;
	}

	public void setEmerix(Long emerix) {
		this.emerix = emerix;
	}

	public Long getIdTributaria() {
		return idTributaria;
	}

	public void setIdTributaria(Long idTributaria) {
		this.idTributaria = idTributaria;
	}

	public TributariaBean getTributaria() {
		return tributaria;
	}

	public void setTributaria(TributariaBean tributaria) {
		this.tributaria = tributaria;
	}

}
