package com.fontar.data.impl.domain.dto;

import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;

public class ProyectoDTO extends DTO implements EvaluableDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;

	private String codigo;

	private EncryptedObject recomendacion;

	private String idCronograma;

	private String idDatos;

	private String idEmpleoPermanente;

	private String idInstrumento;

	private String idPlanTrabajo;

	private String idPresentacion;

	private String idPresupuesto;

	private String idProyectoOrigen;

	private String idProyectoPitec;

	private String porcentajeAlicuotaAdjudicada;

	private String porcentajeAlicuotaSolicitada;

	private EstadoProyecto estado;

	private String estadoReconsideracion;

	private String estadoEnPaquete;

	private String idProyectoPaquete;

	private String idProyectoJurisdiccion;

	private String idEntidadBeneficiaria;

	private String idWorkFlow;

	private String idPresupuestoOriginal;

	private Long idLocalizacion;

	private ProyectoEdicionDTO proyectoDatos;

	private EntidadBeneficiariaDTO entidadBeneficiaria;

	private ProyectoJurisdiccionDTO proyectoJurisdiccion;

	private ProyectoPresupuestoDTO proyectoPresupuesto;

	private ProyectoPresupuestoDTO proyectoPresupuestoOriginal;

	private LocalizacionDTO localizacion;

	private EmpleoPermanenteDTO empleo;

	private String codigoPresentacion;

	public String getCodigoPresentacion() {
		return codigoPresentacion;
	}

	public void setCodigoPresentacion(String codigoPresentacion) {
		this.codigoPresentacion = codigoPresentacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EntidadBeneficiariaDTO getEntidadBeneficiaria() {
		return entidadBeneficiaria;
	}

	public void setEntidadBeneficiaria(EntidadBeneficiariaDTO entidadBeneficiaria) {
		this.entidadBeneficiaria = entidadBeneficiaria;
	}

	public String getEstadoEnPaquete() {
		return estadoEnPaquete;
	}

	public void setEstadoEnPaquete(String estadoEnPaquete) {
		this.estadoEnPaquete = estadoEnPaquete;
	}

	public String getEstadoReconsideracion() {
		return estadoReconsideracion;
	}

	public void setEstadoReconsideracion(String estadoReconsideracion) {
		this.estadoReconsideracion = estadoReconsideracion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCronograma() {
		return idCronograma;
	}

	public void setIdCronograma(String idCronograma) {
		this.idCronograma = idCronograma;
	}

	public String getIdDatos() {
		return idDatos;
	}

	public void setIdDatos(String idDatos) {
		this.idDatos = idDatos;
	}

	public String getIdEmpleoPermanente() {
		return idEmpleoPermanente;
	}

	public void setIdEmpleoPermanente(String idEmpleoPermanente) {
		this.idEmpleoPermanente = idEmpleoPermanente;
	}

	public String getIdEntidadBeneficiaria() {
		return idEntidadBeneficiaria;
	}

	public void setIdEntidadBeneficiaria(String idEntidadBeneficiaria) {
		this.idEntidadBeneficiaria = idEntidadBeneficiaria;
	}

	public String getIdInstrumento() {
		return idInstrumento;
	}

	public void setIdInstrumento(String idInstrumento) {
		this.idInstrumento = idInstrumento;
	}

	public String getIdPlanTrabajo() {
		return idPlanTrabajo;
	}

	public void setIdPlanTrabajo(String idPlanTrabajo) {
		this.idPlanTrabajo = idPlanTrabajo;
	}

	public String getIdPresentacion() {
		return idPresentacion;
	}

	public void setIdPresentacion(String idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	public String getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(String idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public String getIdPresupuestoOriginal() {
		return idPresupuestoOriginal;
	}

	public void setIdPresupuestoOriginal(String idPresupuestoOriginal) {
		this.idPresupuestoOriginal = idPresupuestoOriginal;
	}

	public String getIdProyectoJurisdiccion() {
		return idProyectoJurisdiccion;
	}

	public void setIdProyectoJurisdiccion(String idProyectoJurisdiccion) {
		this.idProyectoJurisdiccion = idProyectoJurisdiccion;
	}

	public String getIdProyectoOrigen() {
		return idProyectoOrigen;
	}

	public void setIdProyectoOrigen(String idProyectoOrigen) {
		this.idProyectoOrigen = idProyectoOrigen;
	}

	public String getIdProyectoPaquete() {
		return idProyectoPaquete;
	}

	public void setIdProyectoPaquete(String idProyectoPaquete) {
		this.idProyectoPaquete = idProyectoPaquete;
	}

	public String getIdProyectoPitec() {
		return idProyectoPitec;
	}

	public void setIdProyectoPitec(String idProyectoPitec) {
		this.idProyectoPitec = idProyectoPitec;
	}

	public String getIdWorkFlow() {
		return idWorkFlow;
	}

	public void setIdWorkFlow(String idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	public String getPorcentajeAlicuotaAdjudicada() {
		return porcentajeAlicuotaAdjudicada;
	}

	public void setPorcentajeAlicuotaAdjudicada(String porcentajeAlicuotaAdjudicada) {
		this.porcentajeAlicuotaAdjudicada = porcentajeAlicuotaAdjudicada;
	}

	public String getPorcentajeAlicuotaSolicitada() {
		return porcentajeAlicuotaSolicitada;
	}

	public void setPorcentajeAlicuotaSolicitada(String porcentajeAlicuotaSolicitada) {
		this.porcentajeAlicuotaSolicitada = porcentajeAlicuotaSolicitada;
	}

	public ProyectoEdicionDTO getProyectoDatos() {
		return proyectoDatos;
	}

	public void setProyectoDatos(ProyectoEdicionDTO proyectoDatos) {
		this.proyectoDatos = proyectoDatos;
	}

	public ProyectoJurisdiccionDTO getProyectoJurisdiccion() {
		return proyectoJurisdiccion;
	}

	public void setProyectoJurisdiccion(ProyectoJurisdiccionDTO proyectoJurisdiccion) {
		this.proyectoJurisdiccion = proyectoJurisdiccion;
	}

	public ProyectoPresupuestoDTO getProyectoPresupuesto() {
		return proyectoPresupuesto;
	}

	public void setProyectoPresupuesto(ProyectoPresupuestoDTO proyectoPresupuesto) {
		this.proyectoPresupuesto = proyectoPresupuesto;
	}


	public Recomendacion getRecomendacion() {
		return (recomendacion!=null)? (Recomendacion) recomendacion.getObject() : null;
	}
	
	public String getDescripcionRecomendacion() {
		try{
			Recomendacion recomendacion = this.getRecomendacion();
			return (recomendacion!=null)?recomendacion.getDescripcion() : "";
		}catch (SecurityException e) {
			return ObjectUtils.ENCRIPTION_WARNING;
		}
	}

	public EncryptedObject getRecomendacionProyecto() {
		return this.recomendacion;
	}
	
	public void setRecomendacionProyecto(EncryptedObject object) {
		this.recomendacion = object;
	}
	
	public ProyectoPresupuestoDTO getProyectoPresupuestoOriginal() {
		return proyectoPresupuestoOriginal;
	}

	public void setProyectoPresupuestoOriginal(ProyectoPresupuestoDTO proyectoPresupuestoOriginal) {
		this.proyectoPresupuestoOriginal = proyectoPresupuestoOriginal;
	}

	public LocalizacionDTO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionDTO localizacion) {
		this.localizacion = localizacion;
	}

	public EmpleoPermanenteDTO getEmpleo() {
		return empleo;
	}

	public void setEmpleo(EmpleoPermanenteDTO empleo) {
		this.empleo = empleo;
	}

	public Long getIdLocalizacion() {
		return idLocalizacion;
	}

	public void setIdLocalizacion(Long idLocalizacion) {
		this.idLocalizacion = idLocalizacion;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public String getDescripcionEstado() {
		return this.getEstado().getDescripcion();
	}

	public boolean estaAbierto() {
		return !estaTerminado();
	}
	
	public boolean estaTerminado() {
		return 
			estado.equals(EstadoProyecto.FINALIZADO) ||
			estado.equals(EstadoProyecto.CERRADO) ||
			estado.equals(EstadoProyecto.ANULADO);
	}
}
