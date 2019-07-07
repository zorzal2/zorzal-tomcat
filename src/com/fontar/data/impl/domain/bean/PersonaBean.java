package com.fontar.data.impl.domain.bean;

import com.fontar.util.ResourceManager;
import com.pragma.util.StringUtil;

/**
 * Estos objetos representa personas que participan en los proyectos y
 * personas que actúan como evaluadores de proyectos.
 *   
 * Para un proyecto se cargan personas para registrar el responsable, el director, el firmante del contrato, etc.
 * Además en el seguimiento de un proyecto, para la rendición de cuentas de RRHH 
 * se registran las personas a las cuales se les imputa el gasto.   
 * 
 * @see com.fontar.data.impl.domain.bean.RendicionCuentasBean
 * @see com.fontar.data.impl.domain.bean.EvaluadorBean
 */
public class PersonaBean {

	private Long id;

	private String tituloGrado;

	private String sexo;

	private String nombre;

	private String cuit;

	private Long idLocalizacion;

	private LocalizacionBean localizacion;

	private String cargo;

	private Boolean activo;
	
	private Boolean borrado = false;

	private String observacion;

	private Boolean esEvaluador;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLocalizacion() {
		return idLocalizacion;
	}

	public void setIdLocalizacion(Long idLocalizacion) {
		this.idLocalizacion = idLocalizacion;
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

	public Boolean getEsEvaluador() {
		return esEvaluador;
	}

	public void setEsEvaluador(Boolean esEvaluador) {
		this.esEvaluador = esEvaluador;
	}

	public LocalizacionBean getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionBean localizacion) {
		this.localizacion = localizacion;
	}

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}
	public void destroy() {
		setNombre(ResourceManager.getInformationalResource("app.msj.deleted", getNombre()));
		if(StringUtil.isNotEmpty(getCuit())) {
			setCuit("["+getCuit()+"]");
		}
	}
}
