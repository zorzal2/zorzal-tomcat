package com.fontar.data.impl.domain.dto;

import java.math.BigDecimal;
import java.util.Collection;

import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;

/**
 * 
 * @author gboaglio
 * 
 */
public class ProyectoEdicionDTO extends ProyectoAgregarDTO {
	private static final long serialVersionUID = 1L;

	private Long idProyecto;
		
	private String codigo; // codigo del proyecto

	private ProyectoAgregarDTO datosProyectoDto = new ProyectoAgregarDTO();

	private Long id;

	private String titulo;

	private String resumen;

	private String objetivo;

	private String palabraClave;

	private String duracion;

	private String observacion;

	private Long idPersonaLegal;

	private String txtPersonaLegal;

	private Long idPersonaDirector;

	private String txtPersonaDirector;

	private Long idPersonaRepresentante;

	private String txtPersonaRepresentante;

	private Long idEntidadBeneficiaria;

	private String txtEntidadBeneficiaria;

	private Long idTipoProyecto;

	private Long idCiiu;

	private String txtCiiu;

	private String codigoCiiu;

	private BigDecimal tir;

	private BigDecimal van;

	private LocalizacionDTO localizacion;

	private EmpleoPermanenteDTO empleo;

	private Collection<EntidadIntervinientesDTO> intervinientes;

	private Long idEntidadBancaria;

	private String descripcionEntidadBancaria;

	private BigDecimal porcentajeTasaInteres;
	
	private Long idJurisdiccion = null;

	private String emerix;
	
	private String proyectoPitec;

	private Recomendacion recomendacion;
	
	public void readFrom(ProyectoEdicionDTO other) {
		super.readFrom(other);
		this.idProyecto = other.idProyecto;
		this.codigo = other.codigo;
		this.datosProyectoDto = other.datosProyectoDto;
		this.id = other.id;
		this.titulo = other.titulo;
		this.resumen = other.resumen;
		this.objetivo = other.objetivo;
		this.palabraClave = other.palabraClave;
		this.duracion = other.duracion;
		this.observacion = other.observacion;
		this.idPersonaLegal = other.idPersonaLegal;
		this.txtPersonaLegal = other.txtPersonaLegal;
		this.idPersonaDirector = other.idPersonaDirector;
		this.txtPersonaDirector = other.txtPersonaDirector;
		this.idPersonaRepresentante = other.idPersonaRepresentante;
		this.txtPersonaRepresentante = other.txtPersonaRepresentante;
		this.idEntidadBeneficiaria = other.idEntidadBeneficiaria;
		this.txtEntidadBeneficiaria = other.txtEntidadBeneficiaria;
		this.idTipoProyecto = other.idTipoProyecto;
		this.idCiiu = other.idCiiu;
		this.txtCiiu = other.txtCiiu;
		this.codigoCiiu = other.codigoCiiu;
		this.tir = other.tir;
		this.van = other.van;
		this.localizacion = other.localizacion;
		this.empleo = other.empleo;
		this.intervinientes = other.intervinientes;
		this.idEntidadBancaria = other.idEntidadBancaria;
		this.descripcionEntidadBancaria = other.descripcionEntidadBancaria;
		this.porcentajeTasaInteres = other.porcentajeTasaInteres;	
		this.idJurisdiccion = other.idJurisdiccion;
		this.emerix = other.emerix;	
		this.proyectoPitec = other.proyectoPitec;
		this.recomendacion = other.recomendacion;
	}
	/**
	 * Agrega al dto los datos del dto dados que no esten definidos
	 * en el dto actual.
	 * @param other
	 */
	public void updateWith(ProyectoEdicionDTO other) {
		super.updateWith(other);
		if(this.idProyecto==null) this.idProyecto = other.idProyecto;
		if(this.codigo==null) this.codigo = other.codigo;
		if(this.datosProyectoDto==null) this.datosProyectoDto = other.datosProyectoDto;
		if(this.id==null) this.id = other.id;
		if(this.titulo==null) this.titulo = other.titulo;
		if(this.resumen==null) this.resumen = other.resumen;
		if(this.objetivo==null) this.objetivo = other.objetivo;
		if(this.palabraClave==null) this.palabraClave = other.palabraClave;
		if(this.duracion==null) this.duracion = other.duracion;
		if(this.observacion==null) this.observacion = other.observacion;
		if(this.idPersonaLegal==null) this.idPersonaLegal = other.idPersonaLegal;
		if(this.txtPersonaLegal==null) this.txtPersonaLegal = other.txtPersonaLegal;
		if(this.idPersonaDirector==null) this.idPersonaDirector = other.idPersonaDirector;
		if(this.txtPersonaDirector==null) this.txtPersonaDirector = other.txtPersonaDirector;
		if(this.idPersonaRepresentante==null) this.idPersonaRepresentante = other.idPersonaRepresentante;
		if(this.txtPersonaRepresentante==null) this.txtPersonaRepresentante = other.txtPersonaRepresentante;
		if(this.idEntidadBeneficiaria==null) this.idEntidadBeneficiaria = other.idEntidadBeneficiaria;
		if(this.txtEntidadBeneficiaria==null) this.txtEntidadBeneficiaria = other.txtEntidadBeneficiaria;
		if(this.idTipoProyecto==null) this.idTipoProyecto = other.idTipoProyecto;
		if(this.idCiiu==null) this.idCiiu = other.idCiiu;
		if(this.txtCiiu==null) this.txtCiiu = other.txtCiiu;
		if(this.codigoCiiu==null) this.codigoCiiu = other.codigoCiiu;
		if(this.tir==null) this.tir = other.tir;
		if(this.van==null) this.van = other.van;
		if(this.localizacion==null) this.localizacion = other.localizacion;
		if(this.empleo==null) this.empleo = other.empleo;
		if(this.intervinientes==null) this.intervinientes = other.intervinientes;
		if(this.idEntidadBancaria==null) this.idEntidadBancaria = other.idEntidadBancaria;
		if(this.descripcionEntidadBancaria==null) this.descripcionEntidadBancaria = other.descripcionEntidadBancaria;
		if(this.porcentajeTasaInteres==null) this.porcentajeTasaInteres = other.porcentajeTasaInteres;	
		if(this.idJurisdiccion==null) this.idJurisdiccion = other.idJurisdiccion;
		if(this.emerix==null) this.emerix = other.emerix;	
		if(this.proyectoPitec==null) this.proyectoPitec = other.proyectoPitec;
		if(this.recomendacion==null) this.recomendacion = other.recomendacion;
	}

	public Long getIdCiiu() {
		return idCiiu;
	}

	public void setIdCiiu(Long idCiiu) {
		this.idCiiu = idCiiu;
	}

	public String getTxtCiiu() {
		return txtCiiu;
	}

	public void setTxtCiiu(String txtCiiu) {
		this.txtCiiu = txtCiiu;
	}

	public String getTxtEntidadBeneficiaria() {
		return txtEntidadBeneficiaria;
	}

	public void setTxtEntidadBeneficiaria(String txtEntidadBeneficiaria) {
		this.txtEntidadBeneficiaria = txtEntidadBeneficiaria;
	}

	public Long getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(Long idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTxtPersonaDirector() {
		return txtPersonaDirector;
	}

	public void setTxtPersonaDirector(String txtPersonaDirector) {
		this.txtPersonaDirector = txtPersonaDirector;
	}

	public String getTxtPersonaLegal() {
		return txtPersonaLegal;
	}

	public void setTxtPersonaLegal(String txtPersonaLegal) {
		this.txtPersonaLegal = txtPersonaLegal;
	}

	public String getTxtPersonaRepresentante() {
		return txtPersonaRepresentante;
	}

	public void setTxtPersonaRepresentante(String txtPersonaRepresentante) {
		this.txtPersonaRepresentante = txtPersonaRepresentante;
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

	public String getDescripcionEntidadBancaria() {
		return descripcionEntidadBancaria;
	}

	public void setDescripcionEntidadBancaria(String descripcionEntidadBancaria) {
		this.descripcionEntidadBancaria = descripcionEntidadBancaria;
	}

	public Long getIdEntidadBancaria() {
		return idEntidadBancaria;
	}

	public void setIdEntidadBancaria(Long idEntidadBancaria) {
		this.idEntidadBancaria = idEntidadBancaria;
	}

	public BigDecimal getPorcentajeTasaInteres() {
		return porcentajeTasaInteres;
	}

	public void setPorcentajeTasaInteres(BigDecimal porcentajeTasaInteres) {
		this.porcentajeTasaInteres = porcentajeTasaInteres;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public EmpleoPermanenteDTO getEmpleo() {
		return empleo;
	}

	public void setEmpleo(EmpleoPermanenteDTO empleo) {
		this.empleo = empleo;
	}

	public LocalizacionDTO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionDTO localizacion) {
		this.localizacion = localizacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getTir() {
		return tir;
	}

	public void setTir(BigDecimal tir) {
		this.tir = tir;
	}

	public BigDecimal getVan() {
		return van;
	}

	public void setVan(BigDecimal van) {
		this.van = van;
	}

	public String getCodigoPresentacion() {
		return datosProyectoDto.getCodigoPresentacion();
	}

	public String getEntidadBeneficiariaOrigen() {
		return datosProyectoDto.getEntidadBeneficiariaOrigen();
	}

	public Boolean getPermiteFinanciamientoBancario() {
		return datosProyectoDto.getPermiteFinanciamientoBancario();
	}

	public void setCodigoPresentacion(String codigoPresentacion) {
		datosProyectoDto.setCodigoPresentacion(codigoPresentacion);
	}

	public void setEntidadBeneficiariaOrigen(String entidadBeneficiariaOrigen) {
		datosProyectoDto.setEntidadBeneficiariaOrigen(entidadBeneficiariaOrigen);
	}

	public void setPermiteFinanciamientoBancario(Boolean permiteFinanciamientoBancario) {
		datosProyectoDto.setPermiteFinanciamientoBancario(permiteFinanciamientoBancario);
	}

	public ProyectoAgregarDTO getDatosProyectoDto() {
		return datosProyectoDto;
	}

	public void setDatosProyectoDto(ProyectoAgregarDTO datosProyectoDto) {
		this.datosProyectoDto = datosProyectoDto;
	}

	public Collection<EntidadIntervinientesDTO> getIntervinientes() {
		return intervinientes;
	}

	public void setIntervinientes(Collection<EntidadIntervinientesDTO> intervinientes) {
		this.intervinientes = intervinientes;
	}

	public String getEmerix() {
		return emerix;
	}

	public void setEmerix(String emerix) {
		this.emerix = emerix;
	}

	public String getCodigoCiiu() {
		return codigoCiiu;
	}

	public void setCodigoCiiu(String codigoCiiu) {
		this.codigoCiiu = codigoCiiu;
	}

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public String getProyectoPitec() {
		return proyectoPitec;
	}

	public void setProyectoPitec(String proyectoPitec) {
		this.proyectoPitec = proyectoPitec;
	}

	public Recomendacion getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(Recomendacion recomendacion) {
		this.recomendacion = recomendacion;
	}


}
