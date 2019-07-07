package com.fontar.bus.impl.bitacora;

import java.util.Date;

import com.fontar.data.Constant.BitacoraDescripcion;
import com.fontar.data.Constant.BitacoraTema;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.seguridad.AuthenticationService;
import com.fontar.util.ResourceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;


/** 
 * Encapsula la creación de la bitácora
 **/

public class BitacoraBuilder {

	public BitacoraBean bitacoraManual(Long idProyecto, String tema, Date fechaAsunto, String descripcion){
		
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setTipo(TipoBitacora.MANUAL);
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(fechaAsunto);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema(tema);
		bitacora.setDescripcion(descripcion);
		bitacora.setIdUsuario(this.getUserId());
		return bitacora;
	}
	
	private BitacoraBean getPrototype(Long idProyecto){
		
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setIdUsuario( this.getUserId() );
		return bitacora;
	}
	
	public BitacoraBean anularEvaluacion(EvaluacionBean evaluacion, String observaciones){
		BitacoraBean bitacora = this.getPrototype(evaluacion.getIdProyecto());
		bitacora.setIdSeguimiento(evaluacion.getBitacora().getIdSeguimiento());
		bitacora.setTema("Anular Evaluación"); //FIXME
		bitacora.setDescripcion(observaciones);
		bitacora.setIdEvaluacion( evaluacion.getId());
		return bitacora;
	}
	
	public BitacoraBean confirmarEvaluacion(EvaluacionBean evaluacion, String observaciones){
		BitacoraBean bitacora = this.getPrototype(evaluacion.getIdProyecto());
		bitacora.setTema("Confirmar Evaluación");
		bitacora.setDescripcion(observaciones);
		bitacora.setIdEvaluacion( evaluacion.getId());
		bitacora.setIdSeguimiento(evaluacion.getBitacora().getIdSeguimiento());
		return bitacora;
	}
	
	public BitacoraBean cargarEvaluacion(ProyectoRaizBean raiz){
		BitacoraBean bitacora = this.getPrototype(raiz.getId());
		bitacora.setTipo(TipoBitacora.EVALUACION);
		bitacora.setTema("Alta de Evaluación");
		
		if (raiz instanceof IdeaProyectoBean)
			bitacora.setDescripcion("Alta de evaluación de Idea Proyecto");
		else
			bitacora.setDescripcion("Alta de evaluación de Proyecto");
		
		return bitacora;
	}
	
	public BitacoraBean cargarEvaluacionSeguimiento(SeguimientoBean bean){
		BitacoraBean bitacora = this.getPrototype(bean.getIdProyecto());
		bitacora.setTipo(TipoBitacora.EVALUACION);
		bitacora.setIdSeguimiento(bean.getId());
		bitacora.setTema("Alta de Evaluación");
		
		bitacora.setDescripcion("Alta de evaluación de Seguimiento");
		
		return bitacora;
	}
	
	public BitacoraBean modificarEvaluacion(EvaluacionGeneralBean evaluacion){
		BitacoraBean bitacora = this.getPrototype(evaluacion.getIdProyecto());
		bitacora.setTipo(TipoBitacora.EVALUACION);
		bitacora.setIdEvaluacion(evaluacion.getId());
		bitacora.setTema("Modificación de Evaluación");
		
		bitacora.setDescripcion("Modificación de Evaluación");
		
		return bitacora;
	}	
	
	public BitacoraBean cargarMovExp(Long idProyecto){
		BitacoraBean bitacora = this.getPrototype(idProyecto);
		bitacora.setTipo(TipoBitacora.MOV_EXPEDIENTE);
		bitacora.setTema("Movimiento Expediente");
		bitacora.setDescripcion("Movimiento de Expediente");
		return bitacora;
	}
	
	public BitacoraBean cargarEntidadIntervinientes(ProyectoBean bean){
		BitacoraBean bitacora = this.getPrototype(bean.getId());
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Asociación de Entidad Interviniente");
		bitacora.setDescripcion("Nueva ");
		bitacora.setTipo(TipoBitacora.ENTIDAD_INTERVINIENTE);
		
		return bitacora;
	}
	
	public BitacoraBean cargarResultadoEvaluacion(EvaluacionGeneralBean evaluacion) {
		//TODO completar el metodo
		BitacoraBean bitacora = this.getPrototype(evaluacion.getIdProyecto());
		bitacora.setIdEvaluacion( evaluacion.getId());
		bitacora.setIdSeguimiento(evaluacion.getBitacora().getIdSeguimiento());
		return bitacora; 
	}
	
	public BitacoraBean autorizarEvaluacion(EvaluacionGeneralBean evaluacion, String descripcion) {
		BitacoraBean bitacora = this.getPrototype(evaluacion.getIdProyecto());
		bitacora.setTema("Autorización de Evaluación");
		bitacora.setDescripcion(descripcion);
		return bitacora;
	}
	
	public BitacoraBean autorizarPago(SeguimientoBean seguimientoBean, String descripcion) {
		BitacoraBean bitacora = this.getPrototype(seguimientoBean.getIdProyecto());
		bitacora.setTipo(TipoBitacora.EVALUACION);
		bitacora.setTema("Autorización de Pago de Seguimiento");
		bitacora.setDescripcion(descripcion);
		return bitacora;
	}
	
	public BitacoraBean firmarContratoProyecto(Long idProyecto, Date fechaFirma, String observaciones, String txtResponsableLegal) {
		BitacoraBean bitacora = this.getPrototype(idProyecto);
		bitacora.setFechaAsunto(fechaFirma);
		bitacora.setTema(BitacoraTema.FIRMA_CONTRATO);
		String infoResponsableLegal = ResourceManager.getLabelResource("app.label.responsableLegal") + ":" + txtResponsableLegal + ".";
		int longitud = observaciones.length() + infoResponsableLegal.length();
		String descripcion;
		if(longitud > 3493)
			descripcion = infoResponsableLegal + " Obs.: " + observaciones.substring(0, 3500 - infoResponsableLegal.length());
		else
			descripcion = infoResponsableLegal + " Obs.: " + observaciones;
		bitacora.setDescripcion(descripcion);
		return bitacora;
	}

	/**
	 * Crea una bitácora de creación o modificación de un seguimiento
	 * según corresponda, y la devuelve. 
	 */
	public BitacoraBean guardarSeguimiento(SeguimientoBean seguimiento) {
		
		BitacoraBean bitacora = new BitacoraBean();
		
		// Si es edición de seguimiento
		if (seguimiento.getId() != null) {
			bitacora = this.getPrototype(seguimiento.getIdProyecto());
			bitacora.setTema(BitacoraTema.ACT_SEGUIMIENTO);			
			bitacora.setIdSeguimiento(seguimiento.getId());
		}
		// Si es creación
		else {
			bitacora = this.bitacoraManual(seguimiento.getIdProyecto(), BitacoraTema.NUEVO_SEGUIMIENTO, DateTimeUtil.getDate(), BitacoraDescripcion.NA);
			bitacora.setTipo(TipoBitacora.SEGUIMIENTO);
			bitacora.setIdSeguimiento(seguimiento.getId());
		}
		
		return bitacora;
	}

	/**
	 * Crea una bitácora de anulación de seguimiento y la devuelve
	 * con descripcion = <i>observacion</i>.<br> 
	 * @param seguimiento
	 * @param observacion
	 * @return bitacora para la anulación de un seguimento
	 */
	public BitacoraBean anularSeguimiento(SeguimientoBean seguimiento, String observacion) {
		BitacoraBean bitacora = new BitacoraBean();
		
		bitacora = this.bitacoraManual(seguimiento.getIdProyecto(), BitacoraTema.ANULACION_SEGUIMIENTO, DateTimeUtil.getDate(), observacion);
		bitacora.setTipo(TipoBitacora.SEGUIMIENTO);
		bitacora.setIdSeguimiento(seguimiento.getId());
		
		return bitacora;	
	}

	/**
	 * Crea una bitácora de cerrado de seguimiento y la devuelve
	 * con descripcion = <i>observacion</i>.<br> 
	 * @param seguimiento
	 * @param observacion
	 * @return bitacora para cerrado de seguimiento
	 */
	public BitacoraBean cerrarSeguimiento(SeguimientoBean seguimiento, String observacion) {
		BitacoraBean bitacora = new BitacoraBean();
		
		bitacora = this.bitacoraManual(seguimiento.getIdProyecto(), BitacoraTema.CERRADO_SEGUIMIENTO, DateTimeUtil.getDate(), observacion);
		bitacora.setTipo(TipoBitacora.SEGUIMIENTO);
		
		return bitacora;	
	}	
	
	private String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
	
	/**
	 * Crea una bitácora de gestión de pago
	 * de seguimiento.<br>
	 * @param seguimiento
	 * @param observacion
	 * @return bitacora con datos de la gestión de pago
	 */
	public BitacoraBean cargarGestionPago(SeguimientoBean seguimiento, String observacion) {
		
		BitacoraBean bitacora = this.getPrototype(seguimiento.getIdProyecto());
		
		bitacora.setIdSeguimiento(seguimiento.getId());
		bitacora.setTipo(TipoBitacora.SEGUIMIENTO);
		bitacora.setTema(BitacoraTema.GESTION_PAGO_SEGUIMIENTO);
		bitacora.setDescripcion(observacion);
		
		return bitacora;	
	}	
	
	public BitacoraBean finalizarControlEvaluacion(SeguimientoBean seguimiento) {
		
		BitacoraBean bitacora = this.getPrototype(seguimiento.getIdProyecto());
		
		bitacora.setTema(BitacoraTema.FINALIZAR_CONTROL_EVALUACIONES);
		bitacora.setIdSeguimiento(seguimiento.getId());
		bitacora.setTipo(TipoBitacora.EVALUACION);
		
		return bitacora;	
	}
}

