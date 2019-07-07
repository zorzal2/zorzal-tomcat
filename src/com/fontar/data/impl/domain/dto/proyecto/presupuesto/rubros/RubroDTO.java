package com.fontar.data.impl.domain.dto.proyecto.presupuesto.rubros;

import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.pragma.util.interfaces.DTO;



public class RubroDTO implements DTO {
	private Long id;
	private String nombre;
	private String codigo;
	private String codigoLargo;
	private RubroDTO padre;
	private TipoRubro tipo;
	private TipoRendicion tipoRendicion;
	private Long nroOrden;
	
	public Long getNroOrden() {
		return nroOrden;
	}
	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}
	public TipoRendicion getTipoRendicion() {
		return tipoRendicion;
	}
	public void setTipoRendicion(TipoRendicion tipoRendicion) {
		this.tipoRendicion = tipoRendicion;
	}
	public void setTipo(TipoRubro tipo) {
		this.tipo = tipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public RubroDTO getRubroPadre() {
		return padre;
	}
	public boolean esRaiz() {
		return padre==null;
	}
	public boolean esHoja() {
		return !tipo.equals(TipoRubro.RUBRO_PADRE);
	}
	public void setRubroPadre(RubroDTO padre) {
		this.padre = padre;
	}
	public TipoRubro getTipo() {
		return tipo;
	}
	public String toString() {
		return "<"+codigo+"> "+nombre;
	}
	public String getCodigoLargo() {
		return codigoLargo;
	}
	public void setCodigoLargo(String codigoLargo) {
		this.codigoLargo = codigoLargo;
	}
}
