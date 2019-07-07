package com.fontar.data.impl.domain.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.pragma.util.DateTimeUtil;

/**
 * Estos objetos agrupan información del proyecto, especificamente de un ProyectoRaizBean. 
 * Es decir que aplica tanto para Ideas Proyecto como Proyectos de Convocatoria y Ventanilla. 
 * Cabe aclarar que los datos en estos objetos, en principio, se modifican por fuera de los circuitos de workflow.     
 * Por ejemplo, estos objetos tiene la información del titulo del proyecto, resumen, fecha de ingreso, responsable legal, etc.
 * @see com.fontar.data.impl.domain.bean.ProyectoRaizBean 
 */
public class ProyectoDatosBean extends Auditable {

	private Long id;

	private Date fechaIngreso;

	private Long idLocalizacion;

	private String titulo;

	private Long idCiiu;

	private Date fechaCertificadoContratar;

	private Integer duracion;

	private String resumen;

	private String palabraClave;

	private String observacion;

	private String objetivo;

	private BigDecimal tir;

	private BigDecimal van;

	private Long idPersonaDirector;

	private Long idPersonaLegal;

	private Long idPersonaRepresentante;

	private Long idTipoProyecto;

	private Long idEntidadBeneficiaria;

	private Long idEntidadBancaria;

	private String descripcionEntidadBancaria;

	private String instrumentoSolicitado;

	private BigDecimal porcentajeTasaInteres;

	private EntidadBeneficiariaBean entidadBeneficiaria;

	private LocalizacionBean localizacion;

	private PersonaBean personaLegal;

	private PersonaBean personaDirector;

	private PersonaBean personaRepresentante;

	private CiiuBean ciiu;

	private TipoProyectoBean tipoProyecto;

	private EntidadBancariaBean entidadBancaria;

	public EntidadBancariaBean getEntidadBancaria() {
		return entidadBancaria;
	}

	public void setEntidadBancaria(EntidadBancariaBean entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}

	public TipoProyectoBean getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyectoBean tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public CiiuBean getCiiu() {
		return ciiu;
	}

	public void setCiiu(CiiuBean ciiu) {
		this.ciiu = ciiu;
	}

	public String getInstrumentoSolicitado() {
		return instrumentoSolicitado;
	}

	public void setInstrumentoSolicitado(String instrumentoSolicitado) {
		this.instrumentoSolicitado = instrumentoSolicitado;
	}

	public String getDescripcionEntidadBancaria() {
		return descripcionEntidadBancaria;
	}

	public void setDescripcionEntidadBancaria(String descripcionEntidadBancaria) {
		this.descripcionEntidadBancaria = descripcionEntidadBancaria;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Date getFechaCertificadoContratar() {
		return fechaCertificadoContratar;
	}

	public void setFechaCertificadoContratar(Date fechaCertificadoContratar) {
		this.fechaCertificadoContratar = fechaCertificadoContratar;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
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

	public Long getIdEntidadBancaria() {
		return idEntidadBancaria;
	}

	public void setIdEntidadBancaria(Long idEntidadBancaria) {
		this.idEntidadBancaria = idEntidadBancaria;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public Long getIdLocalizacion() {
		return idLocalizacion;
	}

	public void setIdLocalizacion(Long idLocalizacion) {
		this.idLocalizacion = idLocalizacion;
	}

	public Long getIdPersonaDirector() {
		return idPersonaDirector;
	}

	public void setIdPersonaDirector(Long idPersonaDirector) {
		this.idPersonaDirector = idPersonaDirector;
	}

	public Long getIdPersonaLegal() {
		return idPersonaLegal;
	}

	public void setIdPersonaLegal(Long idPersonaLegal) {
		this.idPersonaLegal = idPersonaLegal;
	}

	public Long getIdPersonaRepresentante() {
		return idPersonaRepresentante;
	}

	public void setIdPersonaRepresentante(Long idPersonaRepresentante) {
		this.idPersonaRepresentante = idPersonaRepresentante;
	}

	public Long getIdTipoProyecto() {
		return idTipoProyecto;
	}

	public void setIdTipoProyecto(Long idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getPalabraClave() {
		return palabraClave;
	}

	public void setPalabraClave(String palabraClave) {
		this.palabraClave = palabraClave;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public BigDecimal getTir() {
		return tir;
	}

	public void setTir(BigDecimal tir) {
		this.tir = tir;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getVan() {
		return van;
	}

	public void setVan(BigDecimal van) {
		this.van = van;
	}

	public EntidadBeneficiariaBean getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(EntidadBeneficiariaBean entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public BigDecimal getPorcentajeTasaInteres() {
		return porcentajeTasaInteres;
	}

	public void setPorcentajeTasaInteres(BigDecimal porcentajeTasaInteres) {
		this.porcentajeTasaInteres = porcentajeTasaInteres;
	}

	public LocalizacionBean getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionBean localizacion) {
		this.localizacion = localizacion;
	}

	public PersonaBean getPersonaDirector() {
		return personaDirector;
	}

	public void setPersonaDirector(PersonaBean personaDirector) {
		this.personaDirector = personaDirector;
	}

	public PersonaBean getPersonaLegal() {
		return personaLegal;
	}

	public void setPersonaLegal(PersonaBean personaLegal) {
		this.personaLegal = personaLegal;
	}

	public PersonaBean getPersonaRepresentante() {
		return personaRepresentante;
	}

	public void setPersonaRepresentante(PersonaBean personaRepresentante) {
		this.personaRepresentante = personaRepresentante;
	}

	/**
	 * Devuelve el año de ingreso.
	 * Es necesario construir un <code>Calendar</code> 
	 * porque la clase <code>Date</code> está deprecada.
	 * 
	 */
	public int getAnioIngreso() {						
		return DateTimeUtil.getCalendar(this.fechaIngreso).get(Calendar.YEAR);
	}
}
