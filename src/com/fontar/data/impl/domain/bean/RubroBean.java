package com.fontar.data.impl.domain.bean;

import com.fontar.data.impl.domain.codes.rubro.TipoRendicion;
import com.fontar.data.impl.domain.codes.rubro.TipoRubro;
import com.pragma.util.StringUtil;
import com.pragma.util.interfaces.Bean;

/**
 * Los rubros corresponden a una estructura jerarquica para la agrupación de items de los presupuestos de proyectos. 
 * Por ejemplo, exite un rubro RRHH y un rubro Bienes de Capital. Dentro del RRHH 
 * podemos tener dos subrubros como RRHH Propios y RRHH Adicionales. 
 * 
 * Los rubros se emplean, en el seguimiento de proyectos, para asignar una rendición de cuenta a un rubro específico.
 * Además cada ítem del PAC corresponde a un rubro específico del presupuesto del proyecto.
 * 
 * La estructura de rubros esta preparada para manejar únicamente dos niveles, además para los rubros del 
 * primer nivel hay lógica de negocio asociada, por lo tanto al modificar esta estructura se deberá tener en cuenta esta situación.  
 * @see com.fontar.data.impl.domain.codes.rubro.TipoRubro
 * @see com.fontar.data.impl.domain.bean.PacBean
 * @see com.fontar.data.impl.domain.bean.RendicionCuentasBean
 */
public class RubroBean implements Bean {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nombre;
	private String codigo;
	private String codigoLargo;
	
	private TipoRubro tipo;
	private TipoRendicion tipoRendicion;
	
	private Long idRubroPadre;
	private RubroBean rubroPadre;

	private Long nroOrden;
	
	public Long getNroOrden() {
		return nroOrden;
	}
	public void setNroOrden(Long nroOrden) {
		this.nroOrden = nroOrden;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdRubroPadre() {
		return idRubroPadre;
	}
	public void setIdRubroPadre(Long idRubroPadre) {
		this.idRubroPadre = idRubroPadre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public RubroBean getRubroPadre() {
		return rubroPadre;
	}
	public void setRubroPadre(RubroBean rubroPadreBean) {
		this.rubroPadre = rubroPadreBean;
	}
	public TipoRubro getTipo() {
		return tipo;
	}
	public void setTipo(TipoRubro tipo) {
		this.tipo = tipo;
	}
	
	// Los métodos que siguen son usados por la vista para usar siempre el valor enumerado. 
	
	public boolean getEsPersona() {
		return 
			getEsRecursoHumanoPropio() || 
			getEsRecursoHumanoAdicional() ||
			getEsConsejeroTecnologico() ||
			getEsDirectorExperto();
	}
	
	public boolean getEsRecursoHumanoPropio() {
		return this.tipo.equals(TipoRubro.RECURSO_HUMANO_PROPIO);
	}
	
	public boolean getEsRecursoHumanoAdicional() {
		return this.tipo.equals(TipoRubro.RECURSO_HUMANO_ADICIONAL);
	}

	public boolean getEsConsejeroTecnologico() {
		return this.tipo.equals(TipoRubro.CONSEJERO_TECNOLOGICO);
	}

	public boolean getEsConsultor() {
		return this.tipo.equals(TipoRubro.CONSULTOR);
	}
	
	public boolean getEsCanonInstitucional() {
		return this.tipo.equals(TipoRubro.CANON_INSTITUCIONAL);
	}
	
	public boolean getEsDirectorExperto() {
		return this.tipo.equals(TipoRubro.DIRECTOR_EXPERTO);
	}
	
	public boolean getEsGeneral() {
		return 
			this.tipo.equals(TipoRubro.BIEN) ||
			this.tipo.equals(TipoRubro.INSUMO);
	}

	public boolean esRaiz() {
		return idRubroPadre==null;
	}
	public boolean esHoja() {
		return !tipo.equals(TipoRubro.RUBRO_PADRE);
	}
	public String toString() {
		return "<"+codigo+"> "+nombre;
	}
	public TipoRendicion getTipoRendicion() {
		return tipoRendicion;
	}
	public void setTipoRendicion(TipoRendicion tipoRendicion) {
		this.tipoRendicion = tipoRendicion;
	}
	
	/**
	 * El codigo largo se utiliza como definir todos los posibles codigos que corresponde con este rubro.
	 * Cada uno de estos codigo se encuentra separado mediante el caracter '|'  
	 * @return
	 */
	public String getCodigoLargo() {
		return codigoLargo;
	}
	public void setCodigoLargo(String codigoLargo) {
		this.codigoLargo = codigoLargo;
	}
	/**
	 * Devuelve true si el codigo dado es uno de los codigos del rubro.
	 * @param code
	 * @return
	 */
	public boolean hasCode(String code) {
		return
			code.equals(this.codigo) ||
			StringUtil.equalsOneOf(code, codigoLargo.split("\\|"));
	}
}
