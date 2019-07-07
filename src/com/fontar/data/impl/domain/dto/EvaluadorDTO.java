package com.fontar.data.impl.domain.dto;

public class EvaluadorDTO {

	private Long id;

	private String fechaIngreso;

	private Long idEntidadesDesemp;

	private String txtEntidadesDesemp;

	private String tituloPosgrado;

	private Long idEspecialidadPrimaria;

	private String txtEspecialidadPrimaria;

	private Long idEspecialidadSecundaria;

	private String txtEspecialidadSecundaria;
	
	//Datos de la persona
	
	private String cuit;
	
	private String nombre;

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEspecialidadPrimaria() {
		return idEspecialidadPrimaria;
	}

	public void setIdEspecialidadPrimaria(Long idEspecialidadPrimaria) {
		this.idEspecialidadPrimaria = idEspecialidadPrimaria;
	}

	public Long getIdEspecialidadSecundaria() {
		return idEspecialidadSecundaria;
	}

	public void setIdEspecialidadSecundaria(Long idEspecialidadSecundaria) {
		this.idEspecialidadSecundaria = idEspecialidadSecundaria;
	}

	public String getTituloPosgrado() {
		return tituloPosgrado;
	}

	public void setTituloPosgrado(String tituloPosgrado) {
		this.tituloPosgrado = tituloPosgrado;
	}

	public String getTxtEspecialidadPrimaria() {
		return txtEspecialidadPrimaria;
	}

	public void setTxtEspecialidadPrimaria(String txtEspecialidadPrimaria) {
		this.txtEspecialidadPrimaria = txtEspecialidadPrimaria;
	}

	public String getTxtEspecialidadSecundaria() {
		return txtEspecialidadSecundaria;
	}

	public void setTxtEspecialidadSecundaria(String txtEspecialidadSecundaria) {
		this.txtEspecialidadSecundaria = txtEspecialidadSecundaria;
	}

	public Long getIdEntidadesDesemp() {
		return idEntidadesDesemp;
	}

	public void setIdEntidadesDesemp(Long idEntidadesDesemp) {
		this.idEntidadesDesemp = idEntidadesDesemp;
	}

	public String getTxtEntidadesDesemp() {
		return txtEntidadesDesemp;
	}

	public void setTxtEntidadesDesemp(String txtEntidadesDesemp) {
		this.txtEntidadesDesemp = txtEntidadesDesemp;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}