package com.fontar.proyecto.datos.modelo;

import com.fontar.data.impl.domain.codes.entidad.TipoEmpresa;
import com.fontar.data.impl.domain.codes.entidad.TipoNoEmpresa;
import com.pragma.util.StringUtil;

public class EmpresaInfo {
	private String nombre;
	private String cuit;
	private String tipo;
	private Boolean esEmpresa;
	private TipoEmpresa SubTipoEmpresaEnum;
	private TipoNoEmpresa SubTipoNoEmpresaEnum;
	private String subtipo;
	private LocalizacionInfo localizacion = new LocalizacionInfo();
	private LocalizacionInfo localizacionActividad = new LocalizacionInfo();
	
	public LocalizacionInfo getLocalizacionActividad() {
		return localizacionActividad;
	}
	public void setLocalizacionActividad(LocalizacionInfo localizacionActividad) {
		this.localizacionActividad = localizacionActividad;
	}
	public Boolean getEsEmpresa() {
		return esEmpresa;
	}
	public void setEsEmpresa(Boolean esEmpresa) {
		this.esEmpresa = esEmpresa;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public LocalizacionInfo getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(LocalizacionInfo localizacion) {
		this.localizacion = localizacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSubtipo() {
		return subtipo;
	}
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public TipoEmpresa getSubTipoEmpresaEnum() {
		return SubTipoEmpresaEnum;
	}
	public void setSubTipoEmpresaEnum(TipoEmpresa subTipoEmpresaEnum) {
		SubTipoEmpresaEnum = subTipoEmpresaEnum;
	}
	public TipoNoEmpresa getSubTipoNoEmpresaEnum() {
		return SubTipoNoEmpresaEnum;
	}
	public void setSubTipoNoEmpresaEnum(TipoNoEmpresa subTipoNoEmpresaEnum) {
		SubTipoNoEmpresaEnum = subTipoNoEmpresaEnum;
	}
	public boolean isEmpty() {
		return !StringUtil.anyNotEmpty(
			nombre,
			tipo,
			cuit,
			
			localizacion.getDireccion(),
			localizacion.getLocalidad(),
			localizacion.getJurisdiccion(),
			localizacion.getPais(),
			localizacion.getTelefono(),
			localizacion.getEmail(),
			localizacion.getWeb(),
			localizacion.getCp(),
			localizacion.getFax(),
			
			localizacionActividad.getDireccion(),
			localizacionActividad.getLocalidad(),
			localizacionActividad.getJurisdiccion(),
			localizacionActividad.getPais(),
			localizacionActividad.getTelefono(),
			localizacionActividad.getEmail(),
			localizacionActividad.getWeb(),
			localizacionActividad.getCp(),
			localizacionActividad.getFax()
		);
	}
}
