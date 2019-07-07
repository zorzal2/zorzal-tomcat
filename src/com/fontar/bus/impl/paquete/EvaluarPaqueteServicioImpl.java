package com.fontar.bus.impl.paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fontar.bus.api.paquete.EvaluarPaqueteServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.EvaluacionPaqueteDAO;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoPaqueteDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.impl.assembler.EvaluacionAssembler;
import com.fontar.data.impl.assembler.PaqueteDTOAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.Recomendacion;
import com.fontar.data.impl.domain.dto.EvaluacionProyectoPaqueteDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.web.WebContextUtil;

/**
 * 
 * @author gboaglio,ssanchez
 * @version 1.01, 30/11/06
 */
public class EvaluarPaqueteServicioImpl implements EvaluarPaqueteServicio {

	ProyectoDAO proyectoDAO;

	EvaluacionDAO evaluacionDAO;

	EvaluacionPaqueteDAO evaluacionPaqueteDAO;

	PaqueteDAO paqueteDAO;

	ProyectoPaqueteDAO proyectoPaqueteDAO;
	
	protected WFNotificacionServicio wfNotificacionServicio;
	
	
	public void setProyectoPaqueteDAO(ProyectoPaqueteDAO proyectoPaqueteDAO) {
		this.proyectoPaqueteDAO = proyectoPaqueteDAO;
	}

	public void setEvaluacionDAO(EvaluacionDAO evaluacionDAO) {
		this.evaluacionDAO = evaluacionDAO;
	}

	public void setEvaluacionPaqueteDAO(EvaluacionPaqueteDAO evaluacionPaqueteDAO) {
		this.evaluacionPaqueteDAO = evaluacionPaqueteDAO;
	}

	public void setPaqueteDAO(PaqueteDAO paqueteDAO) {
		this.paqueteDAO = paqueteDAO;
	}

	public void setProyectoDAO(ProyectoDAO proyectoDAO) {
		this.proyectoDAO = proyectoDAO;
	}

	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}

	/**
	 * @author gboaglio, ssanchez
	 * @version 1.01, 29/11/06
	 */
	public void cargarEvaluacion(long idPaquete, Collection<EvaluacionProyectoPaqueteDTO> datosEvaluacionProyecto, String userName) {
		PaqueteBean paqueteBean = (PaqueteBean) paqueteDAO.read(idPaquete);
		ProyectoBean proyectoBean = new ProyectoBean();
		EvaluacionPaqueteBean evaluacionPaqueteBean;
		BitacoraBean bitacoraBean;

		for (EvaluacionProyectoPaqueteDTO evaluacionProyecto : datosEvaluacionProyecto) {
			proyectoBean = (ProyectoBean) proyectoDAO.read(new Long(evaluacionProyecto.getIdProyecto()));

			// Si el paquete ya fue evaluado levanto y edito la evaluación
			// existente.

			List<EvaluacionPaqueteBean> evaluacionPaqueteList = evaluacionPaqueteDAO.findByProyectoPaqueteActivo(proyectoBean.getId(), idPaquete);
			evaluacionPaqueteBean = evaluacionPaqueteList.get(0);
			evaluacionPaqueteBean.setEstado(EstadoEvaluacion.CON_RESULTADO);
			evaluacionPaqueteBean.setResultado(ResultadoEvaluacion.valueOf(evaluacionProyecto.getResultado())); 
			evaluacionPaqueteBean.setFundamentacion(evaluacionProyecto.getFundamentacion());
			evaluacionPaqueteBean.setFecha(DateTimeUtil.getDate());
			evaluacionPaqueteBean.setEsDictamen(evaluacionProyecto.esDictamen());
						
			// Creo un registro de Bitacora
			bitacoraBean = new BitacoraBean();
			bitacoraBean.setIdProyecto(proyectoBean.getId());
			bitacoraBean.setTipo(TipoBitacora.BASICO);
			bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraBean.setIdEvaluacion(evaluacionPaqueteBean.getId());
			bitacoraBean.setTema(Constant.BitacoraTema.EVALUACION_PAQUETE + " Nro. " + paqueteBean.getId());
			bitacoraBean.setDescripcion("Carga de resultado");
			bitacoraBean.setIdUsuario(this.getUserId());
			
			//	Ahora debo ver las evaluaciones de los proyectos
			Set<EvaluacionBean> evaluaciones = proyectoBean.getEvaluaciones();
			for (EvaluacionBean evaluacion : evaluaciones) {
				if(evaluacion.getIdPaqRechElegibilidad() == null) {
					Long[] noElegibles = evaluacionProyecto.getIdEvaluacionesNoElegibles();
					for(int i = 0; i < noElegibles.length; i++)
						if(evaluacion.getId().equals(noElegibles[i]))
							evaluacion.setIdPaqRechElegibilidad(evaluacionProyecto.getIdpaqueteNoElegible());
				}
			}
			proyectoBean.setPorcentajeAlicuotaAdjudicada(evaluacionProyecto.getAlicuotaAdjudicada());
			
			evaluacionPaqueteDAO.save(evaluacionPaqueteBean);
			proyectoDAO.update(proyectoBean);
			BitacoraDAO bitacoraDAO = (BitacoraDAO) WebContextUtil.getBeanFactory().getBean("bitacoraDao");
			bitacoraDAO.save(bitacoraBean);
		}
	}
	public void cargarEvaluacion(long idPaquete, String codigoActa, String observacion) {
		// Actualizo la información del Paquete
		PaqueteBean paqueteBean = (PaqueteBean) paqueteDAO.read(idPaquete);
		paqueteBean.setEstado(EstadoPaquete.EN_EVALUACION);
		paqueteBean.setCodigoActa(codigoActa);
		paqueteBean.setObservacion(observacion);
		
		paqueteDAO.update(paqueteBean);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.paquete.EvaluarPaqueteServicio2#confirmarEvaluacion(long,
	 * java.lang.String)
	 */
	public void confirmarEvaluacion(long idPaquete, String userName, String codigoActa, String observacion) {

		// Obtengo el paquete a confirmar de la BD
		PaqueteBean paqueteBean = paqueteDAO.read(new Long(idPaquete));
		paqueteBean.setCodigoActa(codigoActa);
		paqueteBean.setObservacion(observacion);
		TipoPaquete tipoPaquete = paqueteBean.getTipo();
		paqueteBean.setEstado(EstadoPaquete.CONFIRMADO);

		// Obtengo la lista de proyectos del paquete
		Set proyectosPaquete = paqueteBean.getProyectosPaquete();
		Iterator iterProyectoPaquetes = proyectosPaquete.iterator();
		Collection<ProyectoBean> listaProyectos = new ArrayList<ProyectoBean>();

		Long idProyecto;
		ProyectoPaqueteBean proyectoPaqueteBean;
		while (iterProyectoPaquetes.hasNext()) {
			proyectoPaqueteBean = (ProyectoPaqueteBean) iterProyectoPaquetes.next();

			if (proyectoPaqueteBean.getEsActivo()) {
				idProyecto = proyectoPaqueteBean.getIdProyecto();
				listaProyectos.add(proyectoDAO.read(idProyecto));
			}
		}

		BitacoraBean bitacoraBean;
		BitacoraDAO bitacoraDAO = (BitacoraDAO) WebContextUtil.getBeanFactory().getBean("bitacoraDao");

		EvaluacionPaqueteBean evaluacionBean;

		// Recorro los proyectos
		for (ProyectoBean proyectoBean : listaProyectos) {
			// --------------- Creo nuevo registro de Bitácora para el proyecto
			// -----------

			bitacoraBean = new BitacoraBean();

			bitacoraBean.setIdProyecto(proyectoBean.getId());
			bitacoraBean.setTipo(TipoBitacora.BASICO);
			bitacoraBean.setFechaRegistro(DateTimeUtil.getDate());
			bitacoraBean.setFechaAsunto(DateTimeUtil.getDate());
			bitacoraBean.setTema(Constant.BitacoraTema.CONFIRMACION_EVALUACION_PAQUETE+ " Nro. " + paqueteBean.getId());
			bitacoraBean.setDescripcion(Constant.BitacoraDescripcion.NA);
			bitacoraBean.setIdUsuario(this.getUserId());
			
			evaluacionBean = (EvaluacionPaqueteBean) evaluacionPaqueteDAO.findByProyectoPaqueteActivo(proyectoBean.getId(), new Long(idPaquete)).get(0);
			bitacoraBean.setIdEvaluacion(evaluacionBean.getId());

			// Cambio el estado de la evaluación asociada al paquete
			evaluacionBean.setEstado(EstadoEvaluacion.CONFIRMADA);

			// -------------------------- Actualizo datos del proyecto
			// ---------------------

			if(evaluacionBean.getProyectoPresupuesto() != null){
				ProyectoPresupuestoBean presupuestoAnterior = proyectoBean.getUltimoPresupuesto();
				if(presupuestoAnterior!=null) {
					ProyectoPresupuestoBean presupuestoNuevo = evaluacionBean.getProyectoPresupuesto();
					presupuestoAnterior.propagarDatosA(presupuestoNuevo);
					((ProyectoPresupuestoDAO)ContextUtil.getBean("proyectoPresupuestoDao")).update(presupuestoNuevo);
				}
				
				//Se actualiza el presupuesto en el proyecto
				proyectoBean.setIdPresupuesto( evaluacionBean.getProyectoPresupuesto().getId() );
				if(proyectoBean.getIdPresupuestoOriginal()==null) proyectoBean.setIdPresupuestoOriginal(evaluacionBean.getProyectoPresupuesto().getId()); 
			}
			
			proyectoBean.setEstadoEnPaquete(false);

			// Actualizo recomendación y estado de acuerdo al resultado de la
			// evaluación
			ResultadoEvaluacion resultado = evaluacionBean.getResultado();

			// Actualiza la RECOMENDACION del proyecto, excepto si el resultado
			// es A_DEFINIR, además si es paguete de Directorio define la Recomendación Final
			if (!resultado.equals(ResultadoEvaluacion.A_DEFINIR)){
				proyectoBean.setRecomendacion(resultado.getRecomendacionProyecto());
				if (TipoPaquete.DIRECTORIO.equals(paqueteBean.getTipo())) {
					proyectoBean.setRecomendacionFinal(resultado.getRecomendacionProyecto());
				}
			}

			// Actualiza el ESTADO proyecto
			if (resultado.equals(ResultadoEvaluacion.A_EVALUAR))
				proyectoBean.setEstado(EstadoProyecto.EVALUACION);

			if (resultado.equals(ResultadoEvaluacion.NO_ADJUDICADO))
				proyectoBean.setEstado(EstadoProyecto.CONT_DIR_ADJ);

			if (resultado.equals(ResultadoEvaluacion.ADJUDICADO) || resultado.equals(ResultadoEvaluacion.APROB_ADJ)
					|| resultado.equals(ResultadoEvaluacion.APROB_MM_ADJ)){
				proyectoBean.setEstado(EstadoProyecto.ADJUDICADO);
				proyectoBean.updateResolucion(codigoActa);
			}

			if (resultado.equals(ResultadoEvaluacion.RECHAZADO) || resultado.equals(ResultadoEvaluacion.APROBADO)
					|| resultado.equals(ResultadoEvaluacion.APRO_MODIF_MONTO)) {

				if (tipoPaquete.equals(TipoPaquete.COMISION)) {
					if (paqueteBean.getInstrumento().getPermiteSecretaria())
						proyectoBean.setEstado(EstadoProyecto.CONT_SEC);
					else
						proyectoBean.setEstado(EstadoProyecto.CONT_DIR_EVAL);
				}

				if (tipoPaquete.equals(TipoPaquete.SECRETARIA))
					proyectoBean.setEstado(EstadoProyecto.CONT_DIR_EVAL);

				if (tipoPaquete.equals(TipoPaquete.DIRECTORIO)) {
					if (resultado.equals(ResultadoEvaluacion.APROBADO)) {
						EstadoProyecto estado = null;

						if (
								proyectoBean.getInstrumento().getMatrizPresupuesto().esTipoCF() ||
								proyectoBean.getInstrumento().getMatrizPresupuesto().esTipoCFConsejerias()
							)
							estado = EstadoProyecto.PEND_ALIC;
						else
							estado = EstadoProyecto.CONT_DIR_ADJ;

						proyectoBean.setEstado(estado);
					}

					if (resultado.equals(ResultadoEvaluacion.RECHAZADO)
							|| resultado.equals(ResultadoEvaluacion.APRO_MODIF_MONTO))
						
						proyectoBean.setEstado(EstadoProyecto.POS_RECON);
				}
			}
			// /GB: TODO: Etapa 2: "Notificación" (ver "ODS Confirmar
			// Paquete") -----
			
			//si el proyecto tiene estado adjudicado o con posibilidad 
			//de reconsideración se crea una notificación
			if (proyectoBean.getEstado().equals(EstadoProyecto.ADJUDICADO)) {
				crearNotificacion(proyectoBean, TipoNotificacion.ADJUDICACION);
			} else if (proyectoBean.getEstado().equals(EstadoProyecto.POS_RECON)) {
				crearNotificacion(proyectoBean, TipoNotificacion.POSI_RECONSIDERACION);
			}

			// Guardo en la BD
			bitacoraDAO.save(bitacoraBean);
			evaluacionDAO.update(evaluacionBean);
			proyectoDAO.update(proyectoBean);

		}
		// actualizo el paquete
		paqueteDAO.update(paqueteBean);
	}

	/**
	 * Crea una <code>Notificacion</code> nueva.<br>
	 * La <code>Notificacion</code> corresponde al <code>Proyecto</code>
	 * recibido por parámetro <i>proyectoBean</i> y es del tipo 
	 * <i>tipoNotificacion</i>.<br>
	 * @param proyectoBean
	 * @param tipoNotificacion
	 * @author ssanchez
	 */
	private void crearNotificacion(ProyectoBean proyectoBean, TipoNotificacion tipoNotificacion) {
		
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setIdProyecto(proyectoBean.getId());
		notificacionDTO.setTipoNotificacion(tipoNotificacion);
		if (TipoNotificacion.ADJUDICACION.equals( tipoNotificacion)) {
				notificacionDTO.setDescripcion("Notificación del resultado de la evaluación por Directorio. Proyecto Adjudicado.");
		}
		else if (TipoNotificacion.POSI_RECONSIDERACION.equals( tipoNotificacion)){
			notificacionDTO.setDescripcion("Notificación del resultado de la evaluación por Directorio. Proyecto con Posibilidad de Reconsideración.");
		}
		wfNotificacionServicio.cargarNotificacion(notificacionDTO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.paquete.EvaluarPaqueteServicio2#obtenerPaquete(java.lang.Long)
	 */
	public PaqueteDTO obtenerPaquete(Long idPaquete) {
		PaqueteBean paqueteBean = paqueteDAO.read(idPaquete);

		PaqueteDTO dto = PaqueteDTOAssembler.buildDto(paqueteBean);

		// GB/ TODO: Ver si se puede resolver haciendo un mapping --
		// Asigno a cada proyectoFilaDto el resultado actual de la evaluación
		List<EvaluacionPaqueteBean> evaluacionList;

		for (ProyectoFilaDTO proyectoFilaDto : dto.getFilasProyectos()) {
			evaluacionList = this.evaluacionPaqueteDAO.findByProyectoPaqueteActivo(proyectoFilaDto.getIdProyecto(), paqueteBean.getId());
			if (evaluacionList.size() > 0) {
				EvaluacionPaqueteBean evaluacion = evaluacionList.get(0);
				proyectoFilaDto.setResultado(evaluacion.getResultadoEvaluacion());
				proyectoFilaDto.setIdEvaluacion(evaluacion.getId());
				proyectoFilaDto.setEvaluacion(EvaluacionAssembler.getInstance().buildDto(evaluacion));
				//completo el total aprobado que dependia de esta evaluacion.
				ProyectoPresupuestoBean presupuesto = evaluacion.getProyectoPresupuesto();
				if(presupuesto==null) presupuesto = evaluacion.getProyectoPresupuestoInicial();
				
				if(presupuesto!=null && correspondeMontoAprobado(proyectoFilaDto)) {
					proyectoFilaDto.setBeneficioFONTARAprobado(presupuesto.getMontoSolicitado());
					proyectoFilaDto.setTotalAprobado(presupuesto.getMontoTotal());
				}
			}
		}
		// --

		return dto;
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
	
	/**
	 * Obtiene una lista con todos los <code>Proyectos</code> 
	 * pertenecientes a un <code>Paquete</code> que no tienen 
	 * cargada <code>Evaluacion de Paquete</code>.<br>
	 * El servicio devuelve un <code>List</code> con los 
	 * <i>códigos</i> de <code>Proyecto</code>.<br>
	 * El <code>Paquete</code> se obtiene mediante el parámetro
	 * recibido <i>idPaquete</i>.<br>
	 * @param idPaquete
	 * @return List proySinEvaluaciones
	 * @author ssanchez
	 */
	public List<String> obtenerProyectoPaqueteSinEval(Long idPaquete) {
		
		PaqueteBean paqueteBean = paqueteDAO.read(idPaquete);

		List<EvaluacionPaqueteBean> evaluacionList;
		List<String> proySinEvaluaciones = new ArrayList<String>();

		Collection<ProyectoPaqueteBean> proyectosPaqueteList = paqueteBean.getProyectosPaquete();
		for (ProyectoPaqueteBean proyectoPaquete : proyectosPaqueteList) {
			if (proyectoPaquete.getEsActivo()) {
				evaluacionList = this.evaluacionPaqueteDAO.findByProyectoPaqueteActivo(proyectoPaquete.getIdProyecto(), paqueteBean.getId());
				if (evaluacionList.size() <= 0) {
					proySinEvaluaciones.add(proyectoPaquete.getProyecto().getCodigo());
				} 
			}
		}

		return proySinEvaluaciones;
	}
	private boolean correspondeMontoAprobado(ProyectoFilaDTO proyectoFilaDto) {
		ResultadoEvaluacion resultadoEvaluacion = null;
		try {
			resultadoEvaluacion = proyectoFilaDto.getResultadoEvaluacion();
		} catch(SecurityException e) {
			return false;
		}
		
		if(resultadoEvaluacion==null) {
			//Todavia no hay un resultado
			Recomendacion recomendacion = proyectoFilaDto.getRecomendacion();
			if(recomendacion==null) return false;
			else {
				if(recomendacion.implicaAprobacion()) return true;
				else return false;
			}
		} else {
			if(resultadoEvaluacion.implicaAprobacion()) return true;
			else {
				if(resultadoEvaluacion.potencialAprobacion()) {
					//Puede aprobarse o no. Uso la recomendacion para decidir.
					Recomendacion recomendacion = proyectoFilaDto.getRecomendacion();
					if(recomendacion==null) return false;
					else {
						if(recomendacion.implicaAprobacion()) return true;
						else return false;
					}
				} else return false;
			}
		}
	}
}