package com.fontar.data.impl.domain.dto;

public class PersonaDTO {

	private Long id;

	private String tituloGrado;

	private String sexo;

	private String nombre;

	private String cuit;

	private LocalizacionDTO localizacion;

	private String cargo;

	private String activo;

	private String observacion;

	private Boolean esEvaluador;

	private EvaluadorDTO evaluadorDTO;

	public EvaluadorDTO getEvaluadorDTO() {
		return evaluadorDTO;
	}

	public void setEvaluadorDTO(EvaluadorDTO evaluadorDTO) {
		this.evaluadorDTO = evaluadorDTO;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public Boolean getEsEvaluador() {
		return esEvaluador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalizacionDTO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionDTO localizacion) {
		this.localizacion = localizacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTituloGrado() {
		return tituloGrado;
	}

	public void setTituloGrado(String tituloGrado) {
		this.tituloGrado = tituloGrado;
	}

	public void setEsEvaluador(Boolean esEvaluador) {
		this.esEvaluador = esEvaluador;
	}

}
