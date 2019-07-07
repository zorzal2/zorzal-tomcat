package com.fontar.bus.impl.proyecto;

import java.util.Date;
import java.util.GregorianCalendar;

import com.fontar.bus.api.proyecto.AdmisibilidadProyectoServicio;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.ProyectoAdmisionDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.api.dao.ProyectoReconsideracionDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ProyectoAdmisionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoReconsideracionBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.data.impl.domain.codes.proyecto.ProximoPaso;
import com.fontar.data.impl.domain.dto.AdmisibilidadVisualizarDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.WebContextUtil;

public class AdmisibilidadProyectoServicioImpl implements AdmisibilidadProyectoServicio {
	private ProyectoAdmisionDAO proyectoAdmisionDao;
	private ProyectoReconsideracionDAO proyectoReconsideracionDAO;
	
	public ProyectoReconsideracionDAO getProyectoReconsideracionDAO() {
		return proyectoReconsideracionDAO;
	}

	public void setProyectoReconsideracionDAO(ProyectoReconsideracionDAO proyectoReconsideracionDAO) {
		this.proyectoReconsideracionDAO = proyectoReconsideracionDAO;
	}

	public ProyectoAdmisionDAO getDao() {
		return proyectoAdmisionDao;
	}

	public void setDao(ProyectoAdmisionDAO dao) {
		this.proyectoAdmisionDao = dao;
	}

	/**
	 * Carga la admisiblida del proyecto y finaliza la tarea
	 */
	public void cargarAdmisionAlProyecto(Long idProyecto, java.util.Date fecha, String fundamentacion,
			String disposicion, String resultado, String observacion) {

		ProyectoAdmisionBean admision = new ProyectoAdmisionBean();

		admision.setFecha(fecha);
		admision.setFundamentacion(fundamentacion);
		admision.setDisposicion(disposicion);
		admision.setResultado(resultado);
		admision.setObservacion(observacion);

		BitacoraBean bitacora = admision.getBitacora();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(GregorianCalendar.getInstance().getTime());
		bitacora.setFechaRegistro(GregorianCalendar.getInstance().getTime());
		bitacora.setTema("Evaluación de Admisión");
		bitacora.setTipo(TipoBitacora.ADMISION);
		bitacora.setIdUsuario(this.getUserId());
		
		// cambio estado del proyecto
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);

		if (admision.esAdmitido()) {
			proyectoBean.setEstado(EstadoProyecto.ADMITIDO);
		}
		else {
			proyectoBean.setEstado(EstadoProyecto.NO_ADMITIDO);
		}

		// actualizo el estado del proyecto
		proyectoDao.update(proyectoBean);

		// grabo la admisibilidad
		proyectoAdmisionDao.create(admision);
	}
	/**
	 * 
	 */
	public boolean isAdmitido(Long idAdmision) {
		ProyectoAdmisionBean bean = proyectoAdmisionDao.read(idAdmision);
		return bean.esAdmitido();
	}
	
	public AdmisibilidadVisualizarDTO getVisualizacionAdmisibilidad(Long id){
		ProyectoAdmisionBean admision = proyectoAdmisionDao.read(id);
		AdmisibilidadVisualizarDTO visualizarDTO = new AdmisibilidadVisualizarDTO();
		visualizarDTO.setFechaAdmision(DateTimeUtil.formatDate(admision.getFecha()));
		visualizarDTO.setFundamentacion(admision.getFundamentacion());
		visualizarDTO.setNroDisposicion(admision.getDisposicion());
		visualizarDTO.setObservaciones(admision.getObservacion());
		
		return visualizarDTO;
	}

	public void solicitarReadmisionAlProyecto(Long idProyecto, Date fecha, String observacion) {
	
		
		BitacoraDAO bitacoraDao = (BitacoraDAO) ContextUtil.getBean("bitacoraDao");
		BitacoraBean bitacora = new BitacoraBean();
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(fecha);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Solicitud de Readmisión");
		bitacora.setDescripcion(observacion);
		bitacora.setIdUsuario(this.getUserId());
		bitacoraDao.save(bitacora);

		// cambio estado del proyecto
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);

		proyectoBean.setEstado(EstadoProyecto.SOL_ADM);

		// actualizo el estado del proyecto
		proyectoDao.update(proyectoBean);
	}

	public void cargarReadmisionAlProyecto(Long idProyecto, Date fecha, String fundamentacion, String resultado, String resolucion, String observacion) {
		
		ProyectoAdmisionBean admision = new ProyectoAdmisionBean();

		admision.setFecha(fecha);
		admision.setFundamentacion(fundamentacion);
//		admision.setDisposicion(disposicion);
		admision.setResultado(resultado);
		admision.setResolucion(resolucion);
		admision.setObservacion(observacion);

		BitacoraBean bitacora = admision.getBitacora();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(fecha);
		bitacora.setFechaRegistro(GregorianCalendar.getInstance().getTime());
		bitacora.setTema("Evaluación Readmisibilidad");
		bitacora.setTipo(TipoBitacora.ADMISION);
		bitacora.setIdUsuario(this.getUserId());

		// cambio estado del proyecto
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);

		if (admision.esAdmitido()) {
			proyectoBean.setEstado(EstadoProyecto.ADMITIDO);
		}
		else {
			proyectoBean.setEstado(EstadoProyecto.CERRADO);
			proyectoBean.setMotivoCierre(MotivoCierre.RECHAZO_ADMISION.getName());
		}

		// actualizo el estado del proyecto
		proyectoDao.update(proyectoBean);

		// grabo la admisibilidad
		proyectoAdmisionDao.create(admision);
	}

	public void analizarReconsideracionAlProyecto(Long idProyecto, Date fecha, String fundamentacion, String resultado, String resolucion, String observacion, String dictamen) {

		ProyectoReconsideracionBean reconsideracionBean = new ProyectoReconsideracionBean();
		
		proyectoReconsideracionDAO = (ProyectoReconsideracionDAO) WebContextUtil.getBeanFactory().getBean("proyectoReconsideracionDao");

		reconsideracionBean.setFecha(fecha);
		reconsideracionBean.setFundamentacion(fundamentacion);
//		admision.setDisposicion(disposicion);
		reconsideracionBean.setResultado(resultado);
		reconsideracionBean.setResolucion(resolucion);
		reconsideracionBean.setObservacion(observacion);
		reconsideracionBean.setDictamen(dictamen);

		BitacoraBean bitacora = reconsideracionBean.getBitacora();
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(fecha);
		bitacora.setFechaRegistro(GregorianCalendar.getInstance().getTime());
		bitacora.setTema("Análisis de Reconsideración");
		bitacora.setTipo(TipoBitacora.RECONSIDERACION);
		bitacora.setIdUsuario(this.getUserId());

		// cambio estado del proyecto
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);

		if (reconsideracionBean.esAdmitido()) {
			proyectoBean.setEstado(EstadoProyecto.RECON);
		}
		else {
			switch (proyectoBean.getRecomendacion()) {
			case ADJUDICADO:
			case APROBADO_ADJUDICADO:
			case APROBADO_MM_ADJUDICADO:
				proyectoBean.setEstado(EstadoProyecto.ADJUDICADO);
				break;
				
			case RECHAZADO:
				proyectoBean.setEstado(EstadoProyecto.CERRADO);
				proyectoBean.setMotivoCierre(MotivoCierre.RECONSIDERACION_RECHAZADA.getName());
				break;
				
			case APROBADO:
			case APROBADO_CON_MODIF_MONTO:
				if (
						proyectoBean.getInstrumento().getMatrizPresupuesto().esTipoCF()||
						proyectoBean.getInstrumento().getMatrizPresupuesto().esTipoCFConsejerias()
					) {
					proyectoBean.setEstado(EstadoProyecto.PEND_ALIC);
				}
				else {
					proyectoBean.setEstado(EstadoProyecto.CONT_DIR_ADJ);
				}
				break;
				
			default:  
				throw new RuntimeException("Funcionalidad no contemplada");
			}
		}
		// actualizo los datos
		proyectoDao.update(proyectoBean);
		proyectoReconsideracionDAO.create(reconsideracionBean);
		
	}

	public void reconsiderarProyecto(Long idProyecto, Date fecha, String paso, String observacion) {
		
		BitacoraDAO bitacoraDao = (BitacoraDAO) ContextUtil.getBean("bitacoraDao");
		BitacoraBean bitacora = new BitacoraBean();
		
		bitacora.setTipo(TipoBitacora.BASICO);
		bitacora.setIdProyecto(idProyecto);
		bitacora.setFechaAsunto(fecha);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Reconsideración Proyecto");
		bitacora.setDescripcion(paso + (StringUtil.isEmpty(observacion)? "" : ": "+observacion));
		bitacora.setIdUsuario(this.getUserId());
		bitacoraDao.save(bitacora);

		// cambio estado del proyecto
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
		ProyectoBean proyectoBean = proyectoDao.read(idProyecto);

		switch (ProximoPaso.valueOf(paso)) {
			case EVALUAR: 
				proyectoBean.setEstado(EstadoProyecto.EVALUACION);
				break;
			case COMISION:
				proyectoBean.setEstado(EstadoProyecto.CONT_COM);
				break;
			case SECRETARIA:
				proyectoBean.setEstado(EstadoProyecto.CONT_SEC);
				break;
			case DIRECTORIO_EVAL:
				proyectoBean.setEstado(EstadoProyecto.CONT_DIR_EVAL);
				break;
				
			default:  
				throw new RuntimeException("Funcionalidad no contemplada");
		}
	
		// actualizo el estado del proyecto
		proyectoDao.update(proyectoBean);
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}