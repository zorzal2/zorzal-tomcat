package com.fontar.bus.impl.workflow;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.fontar.bus.api.configuracion.UsuarioService.CambioDeAsignacionDeUsuarioAPersona;
import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.proyecto.AdministrarProyectoRaizServicio;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.bus.api.workflow.WFNotificacionServicio;
import com.fontar.bus.api.workflow.WFProyectoRaizServicio;
import com.fontar.data.api.assembler.ProyectoRaizGeneralAssembler;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.EvaluadorDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.EvaluacionEvaluadorAssembler;
import com.fontar.data.impl.assembler.ProyectoRaizAssembler;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.bean.PersonaBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.codes.notificacion.TipoNotificacion;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTODecorator;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.NotificacionDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizCerrarDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.jbpm.manager.EvaluacionProcessManager;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.util.event.NotificationListener;

public class WFProyectoRaizServicioImpl implements WFProyectoRaizServicio {

	AdministrarProyectoRaizServicio administrarProyectoRaizServicio;
	AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	protected WFNotificacionServicio wfNotificacionServicio;
	SeguridadObjetoServicio seguridadObjetoServicio;
	UsuarioService usuarioService;
	
	private NotificationListener<CambioDeAsignacionDeUsuarioAPersona> listener = null;
	private NotificationListener<CambioDeAsignacionDeUsuarioAPersona> listener() {
		if(listener==null) 
			listener = new NotificationListener<CambioDeAsignacionDeUsuarioAPersona>() {
				public void listen(CambioDeAsignacionDeUsuarioAPersona notification) {
					listenCambioDeAsignacionDeUsuarioAPersona(notification.personaAfectada(), notification.usuarioAnterior(), notification.usuarioNuevo());
				}
			};
		return listener;
	}
	public void setUsuarioService(UsuarioService service) {
		//Elimino las suscripciones que hubiera
		if(usuarioService!=null) {
			this.usuarioService.onCambioDeAsignacionDeUsuarioAPersona().unSubscribe(listener());
		}
		if(service!=null) {
			//Agrego una suscripcion
			service.onCambioDeAsignacionDeUsuarioAPersona().subscribe(listener());
		}
		usuarioService = service;
	}
	

	public void setSeguridadObjetoServicio(SeguridadObjetoServicio seguridadObjetoServicio) {
		this.seguridadObjetoServicio = seguridadObjetoServicio;
	}

	public DTO getProyectoRaizDTO(Long idTaskInstance, ProyectoRaizGeneralAssembler assembler) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		return administrarProyectoRaizServicio.getProyectoRaizDTO(idProyecto, assembler);
	}

	/**
	 * Obtiene los datos necesarios para Cerrar un Proyecto / Idea Proyecto
	 */
	public ProyectoRaizCerrarDTO obtenerDatosCierre(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		return administrarProyectoRaizServicio.obtenerDatosCierre(idProyecto);
	}

	/**
	 * Cierra un Proyecto / Idea Proyecto
	 */
	public void cerrarProyecto(MotivoCierre motivo, String observacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		administrarProyectoRaizServicio.cerrarProyecto(idProyecto, motivo, observacion);

		taskHelper.finalizarTarea();
	}

	/**
	 * Anula un Proyecto / Idea Proyecto
	 */
	public void anularProyecto(String observacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		administrarProyectoRaizServicio.anularProyecto(idProyecto, observacion);

		taskHelper.finalizarTarea();
	}
	
	/**
	 * Obtiene los datos necesarios para agregar una Evaluación a un Proyecto /
	 * Idea Proyecto
	 */
	public ProyectoRaizEvaluarDTO obtenerDatosEvaluacion(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		return administrarProyectoRaizServicio.obtenerDatosEvaluacion(idProyecto);
	}

	/**
	 * Llamo al servicio de carga de evaluación y al servicio de alta de
	 * workflow en la misma transacción.
	 */
	public void cargarEvaluacionAProyecto(EvaluacionGeneralDTO evaluacionDTO, Long idTaskInstance) {

		// Del instrumento sacar st_secretaria = true
		// PENDIENTE_DE_RESULTADO
		// PENDIENTE_DE_AUTORIZACIÓN

		EvaluacionDAO evaluacionDao = (EvaluacionDAO) ContextUtil.getBean("evaluacionDao");
		EvaluacionProcessManager evaluacionProcessManager = new EvaluacionProcessManager();

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		Long idEvaluacion = administrarProyectoRaizServicio.cargarEvaluacionAProyecto(evaluacionDTO, idProyecto);

		EvaluacionGeneralBean evaluacion = (EvaluacionGeneralBean) evaluacionDao.read(idEvaluacion);

		//grabo en la tabla de permisos por objeto los usuarios que pueden cargar el resultado
		EvaluacionEvaluadorDAO evaluacionEvaluadorDao = (EvaluacionEvaluadorDAO) ContextUtil.getBean("evaluacionEvaluadorDao");
		List<EvaluacionEvaluadorBean> evaluacionEvaluadores = evaluacionEvaluadorDao.findByEvaluacion(idEvaluacion);
		EvaluadorDAO evaluadorDao = (EvaluadorDAO) ContextUtil.getBean("evaluadorDao");
		
		evaluacion.setIdWorkFlow(evaluacionProcessManager.nuevoProcessInstance(evaluacion.getId(), evaluacion.getProyecto().getIdInstrumento()));
		evaluacion.setFecha(DateTimeUtil.getDate());

		/*
		 * Actualizo los permisos.
		 */
		for(EvaluacionEvaluadorBean evaluacionEvaluador : evaluacionEvaluadores) {
			EvaluacionEvaluadorDTODecorator evaluadorDTO = EvaluacionEvaluadorAssembler.getInstance().buildDto(evaluacionEvaluador);

			if(!StringUtil.isEmpty(evaluadorDTO.getIdEvaluador())) {
				EvaluadorBean evaluadorBean = evaluadorDao.read(new Long(evaluadorDTO.getIdEvaluador()));
				String userId = evaluadorBean.getPersona().getUserId();
				if(userId!=null) {
					seguridadObjetoServicio.permitir(userId, "WF-EVALUACION-PROYECTO-CARGAR-RESULTADO", evaluacion);
				}
			}
		}

		// actualizo la evaluación con el idWorkFlow
		evaluacionDao.update(evaluacion);

		
		
		
		//si la evaluacion requiere autorización se crea una notificacion
		//para que el solicitante apruebe o no el evaluador
		if (evaluacion.getEstado().equals(EstadoEvaluacion.PEND_AUTORIZ)) {
			ProyectoRaizDAO proyectoRaizDAO  = (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
			ProyectoRaizBean proyectoRaizBean = proyectoRaizDAO.read(idProyecto);
			
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			notificacionDTO = cargarNotificacionDTO(proyectoRaizBean, TipoNotificacion.CONFORMIDAD_EVAL);
			notificacionDTO.setDescripcion("Notificación de nueva evaluación para el proyecto. Se requiere autorización para la  evaluación " + evaluacion.getId()+".");
			wfNotificacionServicio.cargarNotificacion(notificacionDTO);
		}
		
		//finalizo la tarea de carga de evaluacion al proyecto
		taskHelper.finalizarTarea();
	}

	/**
	 * Carga los datos, al dto <code>NotificacionDTO</code>, necesarios
	 * para crear una nueva <code>Notificacion</code>.<br>
	 * @param proyectoRaiz
	 * @param tipoNotificacion
	 * @return NotificacionDTO
	 * @author ssanchez
	 */ 	
	private NotificacionDTO cargarNotificacionDTO(ProyectoRaizBean proyectoRaiz, TipoNotificacion tipoNotificacion) {

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		notificacionDTO.setIdProyecto(proyectoRaiz.getId());
		notificacionDTO.setTipoNotificacion(tipoNotificacion);
		
		return notificacionDTO;
	}	
	
	/**
	 * Obtiene que clase de objeto ProyectoRaiz es: Proyecto, IdeaProyecto
	 * @author ssanchez
	 */	
	public ProyectoRaizEvaluarDTO obtenerClaseProyectoRaiz(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		return administrarProyectoRaizServicio.obtenerClaseProyectoRaiz(taskHelper.getIdProyecto());
	}
	
	/**
	 * Obtiene las evaluaciones correspondiente a un proyecto raíz independientemente de la clase
	 * a la que pertenezca: Proyecto o IdeaProyecto
	 * @param idTaskInstance
	 * @return lista de evaluaciones
	 * @author ssanchez
	 */
	public Collection obtenerEvaluaciones(Long idTaskInstance,String tipo) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		return administrarEvaluacionesServicio.obtenerEvaluaciones(taskHelper.getIdProyecto(),tipo);
	}
	
	/**
	 * Pone un ProyectoRaiz(proyecto o ideaproyecto) en solicitud de reconsideración
	 * @author ssanchez
	 */
	public void solicitarReconsideracionDeProyectoRaiz(Date fecha, String observacion, Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
	
		administrarProyectoRaizServicio.solicitarReconsideracionDeProyectoRaiz(taskHelper.getIdProyecto(), fecha, observacion);
		
		taskHelper.finalizarTarea();
	}	
	
	/**
	 * Obtiene los datos de un proyecto raiz desde un idTaskInstance
	 * @author ssanchez
	 */
	public DTO obtenerProyectoRaiz(Long idTaskInstance) {
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		
		return administrarProyectoRaizServicio.getProyectoRaizDTO(taskHelper.getIdProyecto(),new ProyectoRaizAssembler());
	}
	
	public void setAdministrarProyectoRaizServicio(AdministrarProyectoRaizServicio administrarProyectoRaizServicio) {
		this.administrarProyectoRaizServicio = administrarProyectoRaizServicio;
	}

	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}

	public void setWfNotificacionServicio(WFNotificacionServicio wfNotificacionServicio) {
		this.wfNotificacionServicio = wfNotificacionServicio;
	}
	/**
	 * Se llama por notificacion ante el cambio del usuario asignado a una persona.
	 */
	protected void listenCambioDeAsignacionDeUsuarioAPersona(PersonaBean persona, String oldUserId, String newUserId) {
		//Si no es evaluador no me interesa.
		if(!persona.getEsEvaluador()) return;
		System.err.println("Cambio de asignacion de usuario a persona no seguro.");
		//FIXME Este cambio de asignacion deberia reflejarse en los permisos
		//de los usuarios sobre las evaluaciones.
		/*
		
		if(oldUserId!=null) {
			//Si el usuario anterior tenia permisos sobre evaluaciones, se los saco.
			List<Long> instanciasPermitidas = seguridadObjetoServicio.instanciasPermitidas(oldUserId, EvaluacionGeneralBean.class, AccionSobreObjeto.CARGAR_RESULTADO);
			for(Long idInstancia : instanciasPermitidas) {
				seguridadObjetoServicio.anularPermiso(oldUserId, AccionSobreObjeto.CARGAR_RESULTADO, EvaluacionGeneralBean.class, idInstancia);
			}
			if(newUserId!=null) {
				//Le asigno sus permisos al nuevo usuario
				for(Long idInstancia : instanciasPermitidas) {
					seguridadObjetoServicio.permitir(newUserId, AccionSobreObjeto.CARGAR_RESULTADO, EvaluacionGeneralBean.class, idInstancia);
				}
			}
		} else {
			if(newUserId!=null) {
				/*
				 * Tengo que:
				 * - Obtener las evaluaciones en las que participa este evaluador
				 * - Darle al evaluador permisos sobre todas las instancias en las
				 *   que participa.
				 * - Obtener las TaskIntances correspondientes a la CARGA_RESULTADO
				 *   para cada evaluacion.
				 * - Agregar al usuario al pool de actores de cada TaskInstance.
				 *
			}
		}*/
	}
}