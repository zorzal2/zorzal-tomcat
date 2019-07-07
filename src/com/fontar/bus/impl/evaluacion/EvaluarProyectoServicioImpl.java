package com.fontar.bus.impl.evaluacion;

import static com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion.PEND_RESULT;

import java.util.List;

import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionGeneralDAO;
import com.fontar.data.api.dao.ProyectoEvaluacionCriterioDAO;
import com.fontar.data.api.dao.ProyectoPresupuestoDAO;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.EvaluacionGeneralAssembler;
import com.fontar.data.impl.assembler.EvaluacionSeguimientoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.EvaluacionGeneralBean;
import com.fontar.data.impl.domain.bean.InstrumentoBean;
import com.fontar.data.impl.domain.bean.ProyectoEvaluacionCriterioBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * Servicio para el ingreso de nuevos evaluaciones a un proyecto
 * @author fferrara
 */
public class EvaluarProyectoServicioImpl implements EvaluarProyectoServicio {
	private EvaluacionGeneralDAO evaluacionDao;
	
	private ProyectoEvaluacionCriterioDAO proyectoEvaluacionCriterioDAO;
	
	private BitacoraDAO bitacoraDao;
	
	private BitacoraBuilder builder = new BitacoraBuilder();
	
	private AdministrarSeguimientoServicio administrarSeguimientoServicio;

	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public void setEvaluacionDao(EvaluacionGeneralDAO evaluacionDao) {
		this.evaluacionDao = evaluacionDao;
	}
	
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.evaluacion.EvaluarProyectoServicio2#obtenerEvaluacionGeneral(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idEvaluacion) {
		EvaluacionGeneralAssembler assembler = EvaluacionGeneralAssembler.getInstance();
		EvaluacionGeneralDTO dto = assembler.buildDto((EvaluacionGeneralBean) evaluacionDao.read(idEvaluacion));
		return dto;
	}
	
	/**
	 * Obtiene la <code>EvaluacionGeneralBean</code>
	 * correspondiente al <i>idEvaluacion</i>.<br>
	 * @param idEvaluacion
	 * @return una EvaluacionGeneralBean
	 */
	public EvaluacionGeneralBean obtenerEvaluacionGeneralBean(Long idEvaluacion) {
		
		return evaluacionDao.read(idEvaluacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.evaluacion.EvaluarProyectoServicio2#cargarResultadoEvaluacion(com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO)
	 */
	@SuppressWarnings("unchecked")
	public void cargarResultadoEvaluacion(EvaluacionGeneralDTO evaluacionDTO, boolean aprobado) {
		BitacoraDAO bitacoraDAO = (BitacoraDAO)ContextUtil.getBean("bitacoraDao");
		EvaluacionGeneralBean evaluacion = (EvaluacionGeneralBean) evaluacionDao.read(new Long(evaluacionDTO.getId()));
		EvaluacionGeneralAssembler assembler = EvaluacionGeneralAssembler.getInstance();

		// actualizo los datos del bean con los del DTO
		assembler.updateBeanNotNull(evaluacion, evaluacionDTO);

		// actualizo el estado de la evaluación
		if (aprobado) {
			evaluacion.setResultado(ResultadoEvaluacion.APROBADO); //TODO implementar metodos en evaluacion aprobar, rechazar, etc
		}
		else {
			evaluacion.setResultado(ResultadoEvaluacion.RECHAZADO);
		}

		evaluacion.setEstado(EstadoEvaluacion.CON_RESULTADO);

		evaluacion.getProyecto().setEstado(EstadoProyecto.EVALUACION);
		//evaluacion.getProyecto().getProyectoDatos().setTipoProyecto(idTipoProyecto);
		BitacoraBean bitacora = this.builder.cargarResultadoEvaluacion(evaluacion);
		bitacora.setTema("Carga de resultado de Evaluación");
		bitacoraDAO.save(bitacora);
		
		evaluacion.setBitacora( bitacora);
		evaluacionDao.update(evaluacion);
		

		// actualizo los datos del DTO por si se usan después
		assembler.updateDtoNotNull(evaluacionDTO, evaluacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.evaluacion.EvaluarProyectoServicio2#anularEvaluacion(com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void anularEvaluacion(EvaluacionGeneralDTO evaluacionDTO, String observaciones) {
		EvaluacionGeneralBean evaluacion = (EvaluacionGeneralBean) evaluacionDao.read(new Long(evaluacionDTO.getId()));

		evaluacion.setEstado(EstadoEvaluacion.ANULADA);
		evaluacionDao.update(evaluacion);
		
		BitacoraBean bitacora = this.builder.anularEvaluacion( evaluacion, observaciones);
		bitacoraDao.save(bitacora);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.evaluacion.EvaluarProyectoServicio2#autorizarEvaluacion(com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO,
	 * boolean)
	 */
	@SuppressWarnings("unchecked")
	public void autorizarEvaluacion(Long idEvaluacion, boolean autorizada, String fundamentacion) {
		EvaluacionGeneralBean evaluacion = (EvaluacionGeneralBean) evaluacionDao.read(idEvaluacion);
		
		StringBuffer descripcionBitacora = new StringBuffer("Autorización de evaluacion: ");
		if (autorizada) {
			descripcionBitacora.append("Aprobada ");
			evaluacion.setEstado(PEND_RESULT);
		}
		else {
			descripcionBitacora.append("Rechazada ");
			evaluacion.setEstado(EstadoEvaluacion.NO_AUTORIZADA);
		}
		evaluacion.setFundamentacion(fundamentacion);
		descripcionBitacora.append(evaluacion.getFundamentacion());

		BitacoraBean bitacora = builder.autorizarEvaluacion(evaluacion, descripcionBitacora.toString());
				
		bitacoraDao.save(bitacora);
		evaluacionDao.update(evaluacion); //actualiza el estado de la evaluacion
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.evaluacion.EvaluarProyectoServicio2#confirmarEvaluacion(com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO)
	 */
	@SuppressWarnings("unchecked")
	public void confirmarEvaluacion(Long idEvaluacion, String observaciones) {
		EvaluacionGeneralBean evaluacion = (EvaluacionGeneralBean) evaluacionDao.read(idEvaluacion);

		evaluacion.setEstado(EstadoEvaluacion.CONFIRMADA);
		
		if(evaluacion.getProyectoPresupuesto() != null){
			//Se actualiza el presupuesto en el proyecto
			ProyectoRaizBean proyectoRaizBean = evaluacion.getProyecto();
			//si hay algun presupuesto anterior. Propago el avance y observaciones de actividades y etapas.
			ProyectoPresupuestoBean presupuestoAnterior = proyectoRaizBean.getUltimoPresupuesto();
			if(presupuestoAnterior!=null) {
				presupuestoAnterior.propagarDatosA(evaluacion.getProyectoPresupuesto());
				((ProyectoPresupuestoDAO)ContextUtil.getBean("proyectoPresupuestoDao")).update(evaluacion.getProyectoPresupuesto());
			}

			proyectoRaizBean.setIdPresupuesto( evaluacion.getProyectoPresupuesto().getId() );
			if(proyectoRaizBean.getIdPresupuestoOriginal()==null) proyectoRaizBean.setIdPresupuestoOriginal(evaluacion.getProyectoPresupuesto().getId());

			ProyectoRaizDAO dao =  (ProyectoRaizDAO) ContextUtil.getBean("proyectoRaizDao");
			dao.save(proyectoRaizBean );
		}

		//si es evaluación de seguimiento y de tipo contable, actualizo sus rendiciones de gestión las aprobadas
		if(evaluacion.getBitacora().getIdSeguimiento()!=null && evaluacion.esTipoContable()){

			administrarSeguimientoServicio.cargarRendicionesDeGestionConAprobadas(evaluacion.getBitacora().getIdSeguimiento());
		}
		
		BitacoraBean bitacora = this.builder.confirmarEvaluacion( evaluacion, observaciones);
		bitacoraDao.save(bitacora);
		
		evaluacionDao.update(evaluacion);
	}

	public EvaluacionGeneralDTO obtenerEvaluacionGeneral(Long idEvaluacion,
			com.fontar.bus.impl.evaluacion.EvaluacionGeneralAssembler assembler) {
		return assembler.buildDTO((EvaluacionGeneralBean) evaluacionDao.read(idEvaluacion));
	}

	public EvaluacionDTO obtenerEvaluacionGeneral(Long idEvaluacion, EvaluacionAssembler assembler) {
		return assembler.buildDTO( evaluacionDao.read(idEvaluacion) );
	}

	public void cargarCriterioEvaluacion(Long idEvaluacion, Object idCriterios) {
		String[] ids = null;
		if (idCriterios != null) {
			ids = (String[]) idCriterios;
		}
		proyectoEvaluacionCriterioDAO = (ProyectoEvaluacionCriterioDAO) ContextUtil.getBean("proyectoEvaluacionCriterioDao");
		List<ProyectoEvaluacionCriterioBean> list = proyectoEvaluacionCriterioDAO.findByEvaluacion(idEvaluacion);
		if (!list.isEmpty()) {
			Boolean exists = false;
			for (ProyectoEvaluacionCriterioBean bean : list) {
				if (idCriterios != null) {
					for (int i=0; i<ids.length; i++) {
//						if (!StringUtil.isNullOrBlank(ids[i])) {
						if (!StringUtil.isEmpty(ids[i])) {
							if ((bean.getIdCriterio().equals(new Long(ids[i]))) && (bean.getIdEvaluacion().equals(idEvaluacion))) {
								ids[i] = null;
								exists = true;
							}
						}
					}
				}				
				if (!exists) {
					proyectoEvaluacionCriterioDAO.delete(bean);
				}
			}
		}
		if (idCriterios != null) {
			for (int i=0; i<ids.length; i++) {
				if (!StringUtil.isEmpty(ids[i])) {
					ProyectoEvaluacionCriterioBean bean = new ProyectoEvaluacionCriterioBean();
					bean.setIdEvaluacion(idEvaluacion);
					bean.setIdCriterio(new Long (ids[i]));
					proyectoEvaluacionCriterioDAO.save(bean);
				}
			}
		}
	}

	public void setProyectoEvaluacionCriterioDAO(ProyectoEvaluacionCriterioDAO proyectoEvaluacionCriterioDAO) {
		this.proyectoEvaluacionCriterioDAO = proyectoEvaluacionCriterioDAO;
	}

	public String[] obtenerCriterioEvaluacion(Long idEvaluacion) {
		proyectoEvaluacionCriterioDAO = (ProyectoEvaluacionCriterioDAO) ContextUtil.getBean("proyectoEvaluacionCriterioDao");
		List <ProyectoEvaluacionCriterioBean> list = proyectoEvaluacionCriterioDAO.findByEvaluacion(idEvaluacion);
		String[] ids = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getIdCriterio().toString();
		}
		return ids;
	}
	
	/**
	 * Obtiene los datos para la cabecera de <code>Evaluacion de Seguimiento</code>.<br>
	 * @param idEvaluacion
	 * @return EvaluacionSeguimientoCabeceraDTO
	 * @author ssanchez
	 */
	public EvaluacionSeguimientoCabeceraDTO obtenerCabeceraEvaluacionSeguimiento(Long idEvaluacion) {
		
		EvaluacionGeneralBean evaluacionBean = evaluacionDao.read(idEvaluacion);
		
		EvaluacionSeguimientoCabeceraAssembler assembler = new EvaluacionSeguimientoCabeceraAssembler();
		EvaluacionSeguimientoCabeceraDTO cabeceraDTO = assembler.buildDTO(evaluacionBean);
		
		return cabeceraDTO;
		
	}
	public EvaluacionResumenDePresupuestoDTO obtenerResumenDePresupuesto(Long idEvaluacion) {
		EvaluacionResumenDePresupuestoDTO dto = new EvaluacionResumenDePresupuestoDTO();
		
		EvaluacionBean evaluacion = evaluacionDao.read(idEvaluacion);
		
		InstrumentoBean instrumento = evaluacion.getProyecto().getInstrumento();
		if(instrumento==null) return null;
		
		boolean desplegarEnParteYContraparte = !evaluacion.getProyecto().getInstrumento().aplicaCargaAlicuotaCF();
		dto.setDesplegarEnParteYContraparte(desplegarEnParteYContraparte);
		
		try {
			//Monto solicitado: es el monto del presupuesto original del proyecto
			ProyectoPresupuestoBean presupuestoOriginal = evaluacion.getProyecto().getProyectoPresupuestoOriginal();
			if(presupuestoOriginal!=null) {
				dto.setMontoTotalSolicitado(presupuestoOriginal.getMontoTotal());
				if(desplegarEnParteYContraparte) {
					dto.setMontoFontarSolicitado(presupuestoOriginal.getMontoSolicitado());
					dto.setMontoContraparteSolicitado(presupuestoOriginal.getMontoEmpresa());
				}
			}
			//Monto aprobado: es el presupuesto que quedó luego de la evaluacion
			ProyectoPresupuestoBean presupuestoFinal = evaluacion.getProyectoPresupuesto();
			if(presupuestoFinal==null) presupuestoFinal = evaluacion.getProyectoPresupuestoInicial();
			if(presupuestoFinal!=null) {
				dto.setMontoTotalAprobado(presupuestoFinal.getMontoTotal());
				if(desplegarEnParteYContraparte) {
					dto.setMontoFontarAprobado(presupuestoFinal.getMontoSolicitado());
					dto.setMontoContraparteAprobado(presupuestoFinal.getMontoEmpresa());
				}
				//Si este es el primer presupuesto, lo asumo como original
				if(presupuestoOriginal==null) {
					dto.setMontoTotalSolicitado(presupuestoFinal.getMontoTotal());
					if(desplegarEnParteYContraparte) {
						dto.setMontoFontarSolicitado(presupuestoFinal.getMontoSolicitado());
						dto.setMontoContraparteSolicitado(presupuestoFinal.getMontoEmpresa());
					}
				}
			}
		} catch (SecurityException e) {
			dto.setRequiereContextoDeEncriptacion(true);
		}
		return dto;
	}
}
