package com.fontar.data.impl.domain.bean;

import java.util.Date;

import com.fontar.util.ResourceManager;
/**
 * Estos objetos representan las personas registradas como evaluadores.
 * Estos evaluadores pueden ser asignados para confeccionar las distintas evaluaciones de los proyectos.
 * @see com.fontar.data.impl.domain.bean.EvaluacionGeneralBean   
 */
public class EvaluadorBean extends Personable {

	private Long id;

	private Long idEspecialidadPrimaria;
	
	private String txtEspecialidadPrimaria;

	private Long idEspecialidadSecundaria;

	private String tituloPosgrado;
	
	private String txtEntidadesDesemp;

	private Date fechaIngreso;
	
	private Boolean borrado = false;
	
	public String getNombre() {
		String nombre = getPersona().getNombre();
		if(!getPersona().getBorrado() && getBorrado()) {
			nombre = ResourceManager.getInformationalResource("app.msj.noEsMasEvaluador", nombre);
		}
		return nombre;
	}

	public String getCuit() {

		return getPersona().getCuit();
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

	public Long getIdEspecialidadPrimaria() {
		return idEspecialidadPrimaria;
	}
	public String getTxtEspecialidadPrimaria() {
		return txtEspecialidadPrimaria;
	}
	public void setTxtEspecialidadPrimaria(String txtEspecialidadPrimaria) {
		this.txtEspecialidadPrimaria = txtEspecialidadPrimaria;
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
	
	public String getTxtEntidadesDesemp() {
		return txtEntidadesDesemp;
	}

	public void setTxtEntidadesDesemp(String txtEntidadesDesemp) {
		this.txtEntidadesDesemp = txtEntidadesDesemp;
	}

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}
	/**
	 * Decide si este objeto está borrado. Esto es,
	 * si el objeto está marcado como borrado, o
	 * corresponde a una persona marcada como borrada.
	 * @return
	 */
	public boolean estaBorrado() {
		return getBorrado() || getPersona().getBorrado();
	}
}