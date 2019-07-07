package com.fontar.bus.impl.evaluacion;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.seguridad.SeguridadObjetoServicio;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.bus.impl.paquete.AdministrarPaqueteProyectoServicioImpl;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.EvaluacionEvaluadorDAO;
import com.fontar.data.api.dao.EvaluacionGeneralDAO;
import com.fontar.data.api.dao.PersonaDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.Evaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionEvaluadorDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionesFinalizarControlDTO;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.jbpm.handler.assigner.EvaluacionEvaluadorActorAssignerInterceptor;
import com.fontar.seguridad.EvaluacionEvaluadorUpdateEvent;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

/**
 * Servicio que provee de funcionalidades
 * relacionada con las evaluaciones en general.<br> 
 * @author ssanchez 
 */
public class AdministrarEvaluacionesServicioImpl implements AdministrarEvaluacionesServicio {

	private EvaluacionDAO evaluacionDao;
	private ProyectoRaizDAO proyectoRaizDAO;
	private EvaluacionEvaluadorDAO evaluacionEvaluadorDAO;
	private EvaluacionGeneralDAO evaluacionGeneralDAO;
	private BitacoraBuilder builder = new BitacoraBuilder();
	private EvaluacionEvaluadorActorAssignerInterceptor interceptor;
	private PersonaDAO personaDao;
	private SeguridadObjetoServicio seguridadObjetoServicio;
	
	
	public void setPersonaDao(PersonaDAO personaDao) {
		this.personaDao = personaDao;
	}

	public void setSeguridadObjetoServicio(SeguridadObjetoServicio seguridadObjetoServicio) {
		this.seguridadObjetoServicio = seguridadObjetoServicio;
	}

	public void setInterceptor(EvaluacionEvaluadorActorAssignerInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public void setEvaluacionDao(EvaluacionDAO evaluacionDao) {
		this.evaluacionDao = evaluacionDao;
	}

	public void setEvaluacionEvaluadorDAO(EvaluacionEvaluadorDAO evaluacionEvaluadorDAO) {
		this.evaluacionEvaluadorDAO = evaluacionEvaluadorDAO;
	}

	public void setEvaluacionGeneralDAO(EvaluacionGeneralDAO evaluacionGeneralDAO) {
		this.evaluacionGeneralDAO = evaluacionGeneralDAO;
	}

	public void setProyectoRaizDAO(ProyectoRaizDAO proyectoRaizDAO) {
		this.proyectoRaizDAO = proyectoRaizDAO;
	}
	
	public void setDao(EvaluacionDAO evaluacionDao) {
		this.evaluacionDao = evaluacionDao;
	}
	
	/**
	 * Dado un id de Evaluacion devuelve un DTO con los datos de una Evaluacion
	 * @param idEvaluacion
	 * @return EvaluacionInventarioDTO
	 */
	public EvaluacionBean obtenerEvaluacion(Long idEvaluacion) {
		EvaluacionBean evaluacion = (EvaluacionBean) evaluacionDao.read(idEvaluacion);
		return evaluacion;
	}

	/*public EvaluacionGeneralDTO getEvaluacionDTO(Long idEvaluacion, EvaluacionGeneralAssembler assembler) {
		EvaluacionGeneralBean bean = this.obtenerEvaluacion(idEvaluacion);
		return assembler.buildDTO(bean);
	}*/

	public ProyectoBean obtenerProyecto(Long idProyecto) {
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		ProyectoBean proyecto = (ProyectoBean) proyectoRaizDao.read(idProyecto);
		return proyecto;
	}
	
	public Collection obtenerEvaluaciones(Long idProyecto) {
		ProyectoBean proyecto = obtenerProyecto(idProyecto);
		return this.getEvaluaciones(proyecto, Constant.AdministrarEvaluacionAttribute.EVALUAR_RESULTADO);
	}	
	
	public Collection obtenerEvaluacionesFinCtrl(Long idProyecto) {
		ProyectoBean proyecto = obtenerProyecto(idProyecto);
		return this.getEvaluaciones(proyecto, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);
	}
	
	public Collection<Evaluacion> getEvaluaciones(ProyectoBean proyecto, String tipo) {
	
		Collection<Evaluacion> evaluacionesList = new ArrayList<Evaluacion>();
		VisualizarEvaluacionAssembler visualizarEvaluacionAssembler = new VisualizarEvaluacionAssembler();
		AdministrarPaqueteProyectoServicioImpl administrarPaqueteProyectoServicio = new AdministrarPaqueteProyectoServicioImpl();
		
		Set<EvaluacionBean> evaluaciones = proyecto.getEvaluaciones();
		
		for (EvaluacionBean evaluacion : evaluaciones) {
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_GEN)) {
				VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator) this.getEvaluacionDTO(evaluacion.getId(), new VisualizarEvaluacionAssembler());
				if(dto.getEstado().equals(EstadoEvaluacion.CONFIRMADA)) {
					if(tipo.equals(Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL)) {
						EvaluacionesFinalizarControlDTO efcDTO = new EvaluacionesFinalizarControlDTO();
						efcDTO.setIdEvaluacion(evaluacion.getId());
						efcDTO.setTipo(dto.getTipos());
						efcDTO.setEvaluadores(visualizarEvaluacionAssembler.getShowEvaluadores(dto.getEvaluadores()));
						if(evaluacion.getResultado() != null) {
							efcDTO.setResultado(evaluacion.getResultado().getDescripcion());
						}
						efcDTO.setEstado(evaluacion.getEstado());
						efcDTO.setRecomendacion(evaluacion.getRecomendacion());
						evaluacionesList.add(efcDTO);
					}
					if((tipo.equals(Constant.AdministrarEvaluacionAttribute.EVALUAR_RESULTADO)) || (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION))) {
						EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();
						erpDTO.setIdEvaluacion(evaluacion.getId());
						erpDTO.setEvaluaciones(visualizarEvaluacionAssembler.getShowEvaluacion((EvaluacionGeneralDTO) dto.getDto()));
						try {
							if(evaluacion.getResultado() != null) {
								erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
							}
						} catch (Exception e) {
							erpDTO.setResultado("CRIPTO");
						}
						if (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
							erpDTO.setEvaluaciones(erpDTO.getEvaluaciones().replace(")<br/>",")"));
						}
						erpDTO.setElegibleString("checked");
						erpDTO.setEsElegible(evaluacion.esElegible());
						evaluacionesList.add(erpDTO);
					}
				}
			}
		}
		if(tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
			Collection<EvaluarResultadoProyectoDTO> lista = administrarPaqueteProyectoServicio.obtenerEvaluacionesPaquete(proyecto);
			for(Evaluacion evaluacion : lista) {
				evaluacionesList.add(evaluacion);
			}
		}
		return evaluacionesList;
	}
	
	public EvaluacionDTO getEvaluacionDTO(Long idEvaluacion, EvaluacionAssembler assembler) {
		EvaluacionBean evaluacion  = evaluacionDao.read( idEvaluacion );
		return assembler.buildDTO( evaluacion );
	}
	
	/**
	 * Obtiene las evaluaciones correspondiente a un proyecto raíz independientemente de la clase
	 * a la que pertenezca: Proyecto o IdeaProyecto
	 * @param idProyectoRaiz
	 * @return lista de evaluaciones
	 * @author ssanchez
	 */
	@SuppressWarnings("unchecked")
	public Collection obtenerEvaluaciones(Long idProyectoRaiz, String tipo) {
		Collection evaluacionesList = new ArrayList();
		VisualizarEvaluacionAssembler visualizarEvaluacionAssembler = new VisualizarEvaluacionAssembler();
		AdministrarPaqueteProyectoServicioImpl administrarPaqueteProyectoServicio = new AdministrarPaqueteProyectoServicioImpl();
		
		ProyectoRaizBean proyectoRaizBean = proyectoRaizDAO.read(idProyectoRaiz);
		Set<EvaluacionBean> evaluaciones = proyectoRaizBean.getEvaluaciones();
		
		for (EvaluacionBean evaluacion : evaluaciones) {
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_GEN)) {
				VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator) this.getEvaluacionDTO(evaluacion.getId(), new VisualizarEvaluacionAssembler());
				if(dto.getEstado().equals(EstadoEvaluacion.CONFIRMADA)) {
					if(tipo.equals(Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL)) {
						EvaluacionesFinalizarControlDTO efcDTO = new EvaluacionesFinalizarControlDTO();
						efcDTO.setIdEvaluacion(evaluacion.getId());
						efcDTO.setTipo(dto.getTipos());
						efcDTO.setEvaluadores(visualizarEvaluacionAssembler.getShowEvaluadores(dto.getEvaluadores()));
						if(evaluacion.getResultado() != null) {
							efcDTO.setResultado(evaluacion.getResultado().getDescripcion());
						}
						efcDTO.setEstado(evaluacion.getEstado());
						efcDTO.setRecomendacion(evaluacion.getRecomendacion());
						evaluacionesList.add(efcDTO);
					}
					if((tipo.equals(Constant.AdministrarEvaluacionAttribute.EVALUAR_RESULTADO)) || (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION))) {
						EvaluarResultadoProyectoDTO erpDTO = new EvaluarResultadoProyectoDTO();
						erpDTO.setIdEvaluacion(evaluacion.getId());
						erpDTO.setEvaluaciones(visualizarEvaluacionAssembler.getShowEvaluacion((EvaluacionGeneralDTO) dto.getDto()));
						if(evaluacion.getResultado() != null) {
							erpDTO.setResultado(evaluacion.getResultado().getDescripcion());
						}
						if (tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
							erpDTO.setEvaluaciones(erpDTO.getEvaluaciones().replace(")<br/>",")"));
						}
						erpDTO.setElegibleString("checked");
						erpDTO.setEsElegible(evaluacion.esElegible());
						evaluacionesList.add(erpDTO);
					}
				}	
			}
		}
		
		if(tipo.equals(Constant.AdministrarEvaluacionAttribute.RECONSIDERACION)) {
			if (proyectoRaizBean instanceof ProyectoBean) {
				Collection<EvaluarResultadoProyectoDTO> lista = administrarPaqueteProyectoServicio.obtenerEvaluacionesPaquete((ProyectoBean) proyectoRaizBean);
				Iterator i = lista.iterator();
				while(i.hasNext()) {
					Evaluacion erpDTO = (Evaluacion)i.next();
					evaluacionesList.add(erpDTO);
				}
			}
		}
		
		return evaluacionesList;
	}

	public EvaluacionDTO getEvaluacionDTO(Long idEvaluacion) {
		return com.fontar.data.impl.assembler.EvaluacionAssembler.getInstance().buildDto(evaluacionDao.read(idEvaluacion));
	}

	public void savePresupuestoId(Long idEvaluacion, Long idPresupueso) {
		EvaluacionBean bean = obtenerEvaluacion(idEvaluacion);
		bean.setIdPresupuesto(idPresupueso);
		evaluacionDao.update(bean);
	}
	
	/**
	 * Modifica datos varios de una evaluación.<br>
	 * @param idEvaluacion
	 * @param evaluacionDTO
	 * @author ssanchez
	 * @throws ParseException 
	 */
	public Long modificarEvaluacion(Long idEvaluacion, EvaluacionGeneralDTO evaluacionDTO) throws ParseException {
		
		EvaluacionGeneralBean evaluacion = evaluacionGeneralDAO.read(idEvaluacion);
		
		modificarEvaluadoresEvaluacion(evaluacion,evaluacionDTO);
		
		BitacoraBean bitacora = this.builder.modificarEvaluacion(evaluacion);
		evaluacion.setBitacora(bitacora);
		
		evaluacion.setFechaInicial(DateTimeUtil.getDate(evaluacionDTO.getFechaInicial()));
		evaluacion.setFechaEntregaComprometida(DateTimeUtil.getDate(evaluacionDTO.getFechaEntregaComprometida()));
		evaluacion.setObservacion(evaluacionDTO.getObservacion());
		
		evaluacionGeneralDAO.update(evaluacion);

		return evaluacion.getId();
	}	
	
	/**
	 * Modifica los evaluadores de una evaluación.<br>
	 * @param evaluacion
	 * @param evaluacionDTO
	 * @author ssanchez
	 */
	private void modificarEvaluadoresEvaluacion(EvaluacionGeneralBean evaluacion, EvaluacionGeneralDTO evaluacionDTO) {
		
		Set<EvaluacionEvaluadorBean> evaluadores = evaluacion.getEvaluadores();
		for (EvaluacionEvaluadorBean evaluadorBean : evaluadores) {
			evaluacionEvaluadorDAO.delete(evaluadorBean);
		}
		String idPermiso;
		if(evaluacion instanceof EvaluacionSeguimientoBean) {
			idPermiso = "WF-EVALUACION-SEGUIMIENTO-CARGAR-RESULTADO";
		} else {
			idPermiso = "WF-EVALUACION-PROYECTO-CARGAR-RESULTADO";
		}
		EvaluacionEvaluadorBean evaluador;
		evaluadores = new HashSet<EvaluacionEvaluadorBean>();
		Collection<EvaluacionEvaluadorDTO> evaluadoresDTO = evaluacionDTO.getEvaluadores();
		seguridadObjetoServicio.anularPermiso(idPermiso, evaluacion);
		if (evaluadoresDTO != null) {
			for(EvaluacionEvaluadorDTO evaluadorDTO : evaluadoresDTO) {
				evaluador = new EvaluacionEvaluadorBean();
				evaluador.setEvaluador(evaluadorDTO.getIdEvaluador());
				evaluador.setInstitucion(evaluadorDTO.getIdEntidadEvaluadora());
				evaluador.setLugarEvaluacion(evaluadorDTO.getLugar());
				evaluador.setIdEvaluacion(evaluacion.getId());
				
				evaluacionEvaluadorDAO.save(evaluador);
				/*
				 * Doy permiso de evaluacion al usuario vinculado con el evaluador 
				 * si tiene uno. 
				 */
				if(!StringUtil.isEmpty(evaluadorDTO.getIdEvaluador())) {
					String userId = personaDao.read(new Long(evaluadorDTO.getIdEvaluador())).getUserId();
					if(userId!=null) {
						seguridadObjetoServicio.permitir(userId, idPermiso, evaluacion);
					}
				}
			}
		}
		//Notifico el cambio para la actualizacion del pool de actores
		EvaluacionEvaluadorUpdateEvent updateEvent = new EvaluacionEvaluadorUpdateEvent(evaluacion);
		interceptor.update(updateEvent);
	}
}