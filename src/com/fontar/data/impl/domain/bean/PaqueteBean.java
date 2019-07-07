package com.fontar.data.impl.domain.bean;

import java.util.HashSet;
import java.util.Set;

import com.fontar.data.api.domain.Adjuntable;
import com.fontar.data.api.domain.Workflowable;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete;

/**
 *  Un paquete representa un conjunto de proyectos de un mismo instrumento 
 *  de Beneficio que deben ser evaluados en forma conjunta.
 *  El tipo de un paquete (Comisión, Secretaria, Directorio) determina porquien será evaluado el paquete.
 *  En caso de ser Comisión se indica el comisión especifica.
 *  Además un paquete sólo puede contener proyectos para un mismo tratamiento (Evaluación, Reconsideración, etc). 
 *  Todo los paquetes tienen un estado y un circuito de worklow de negocios asociado.
 *  
 * @see com.fontar.data.impl.domain.bean.ProyectoPaqueteBean
 * @see com.fontar.data.impl.domain.codes.paquete.EstadoPaquete
 * @see com.fontar.data.impl.domain.codes.paquete.TipoPaquete
 * @see com.fontar.data.impl.domain.codes.paquete.TratamientoPaquete
 */
public class PaqueteBean implements Workflowable,Adjuntable {

	private Long id;

	private Long idComision;

	private Long idInstrumento;

	private String observacion;

	private String codigoActa;

	private InstrumentoBean instrumento;

	private ComisionBean comision;

	private Long idWorkFlow;

	private Set<ProyectoPaqueteBean> proyectosPaquete;

	private EstadoPaquete estado;

	private TratamientoPaquete tratamiento;

	private TipoPaquete tipo;

	private Set adjuntos = new HashSet();

	public Set getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(Set adjuntos) {
		this.adjuntos = adjuntos;
	}

	public String getCodigoActa() {
		return codigoActa;
	}

	public void setCodigoActa(String codigoActa) {
		this.codigoActa = codigoActa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdComision() {
		return idComision;
	}

	public void setIdComision(Long idComision) {
		this.idComision = idComision;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(Long idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Set<ProyectoPaqueteBean> getProyectosPaquete() {
		return proyectosPaquete;
	}

	public void setProyectosPaquete(Set<ProyectoPaqueteBean> proyectos) {
		this.proyectosPaquete = proyectos;
	}

	public InstrumentoBean getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(InstrumentoBean instrumento) {
		this.instrumento = instrumento;
	}

	public ComisionBean getComision() {
		return comision;
	}

	public void setComision(ComisionBean comision) {
		this.comision = comision;
	}

	public Long getIdWorkFlow() {
		return idWorkFlow;
	}

	public void setIdWorkFlow(Long idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	/**
	 * Usa una enumeración para mostrar la descripción de datos obtenidos desde
	 * DB en la vista
	 */
	public EstadoPaquete getEstado() {
		return estado;
	}

	/**
	 * Usa una enumeración para cargar valores consistentes en el bean
	 */
	public void setEstado(EstadoPaquete estado) {
		this.estado = estado;
	}

	@SuppressWarnings("unused")
	private String getCodigoEstado() {
		return estado.getName();
	}

	@SuppressWarnings("unused")
	private void setCodigoEstado(String codigoEstado) {
		estado = EstadoPaquete.valueOf(codigoEstado);
	}

	/**
	 * Usa una enumeración para mostrar la descripción de datos obtenidos desde
	 * DB en la vista
	 */
	public TratamientoPaquete getTratamiento() {
		return tratamiento;
	}

	/**
	 * Usa una enumeración para cargar valores consistentes en el bean
	 */
	public void setTratamiento(TratamientoPaquete tratamiento) {
		this.tratamiento = tratamiento;
	}

	@SuppressWarnings("unused")
	private String getCodigoTratamiento() {
		return tratamiento.getName();
	}

	@SuppressWarnings("unused")
	private void setCodigoTratamiento(String codigoTratamiento) {
		tratamiento = TratamientoPaquete.valueOf(codigoTratamiento);
	}

	/**
	 * Usa una enumeración para mostrar la descripción de datos obtenidos desde
	 * DB en la vista
	 */
	public TipoPaquete getTipo() {
		return tipo;
	}

	/**
	 * Usa una enumeración para cargar valores consistentes en el bean
	 */
	public void setTipo(TipoPaquete tipo) {
		this.tipo = tipo;
	}

	@SuppressWarnings("unused")
	private String getCodigoTipo() {
		return tipo.name();
	}

	@SuppressWarnings("unused")
	private void setCodigoTipo(String codigoTipo) {
		tipo = TipoPaquete.valueOf(codigoTipo);
	}

	public String getBusinessDescription() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("Nro:");
		sb.append(this.id);
		sb.append(" ");
		sb.append("Instrumento:");
		sb.append(this.getInstrumento().getDenominacion());
		sb.append(" ");
		sb.append("Tipo:");
		sb.append(this.tipo.getDescripcion());
		
		return sb.toString();
	}
}
