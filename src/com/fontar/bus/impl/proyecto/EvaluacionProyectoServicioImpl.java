package com.fontar.bus.impl.proyecto;

import java.text.ParseException;
import java.util.Date;

import com.fontar.bus.api.proyecto.EvaluacionProyectoServicio;
import com.fontar.data.api.dao.BitacoraDAO;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.seguridad.AuthenticationService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

public class EvaluacionProyectoServicioImpl extends BaseProyectoServicioImpl implements EvaluacionProyectoServicio {
	private EvaluacionDAO evaluacionDao;
	private BitacoraDAO bitacoraDao;

	
	public BitacoraDAO getBitacoraDao() {
		return bitacoraDao;
	}
	public void setBitacoraDao(BitacoraDAO bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}
	public EvaluacionDAO getEvaluacionDao() {
		return evaluacionDao;
	}
	public void setEvaluacionDao(EvaluacionDAO evaluacionDao) {
		this.evaluacionDao = evaluacionDao;
	}
	/**
	 * @author xxx, ssanchez
	 * @version 1.01, 30/11/06
	 */
	@SuppressWarnings("unchecked")
	public void pasarAProximaEtapaSinEvaluacion(Long idProyecto, String fundamentacion) {
		
		ProyectoBean proyecto = (ProyectoBean) proyectoDao.read(idProyecto);
		proyecto.finalizarControlEvaluaciones( ResultadoEvaluacion.A_DEFINIR.getRecomendacionProyecto());

		EvaluacionBean evaluacion = new EvaluacionBean();
		evaluacion.setTipo(TipoEvaluacion.PROX_ETAPA);
		evaluacion.setIdProyecto(proyecto.getId());
		evaluacion.setFechaInicial(DateTimeUtil.getDate());
		evaluacion.setFecha(DateTimeUtil.getDate());
		evaluacion.setResultado(ResultadoEvaluacion.A_DEFINIR);
		evaluacion.setFundamentacion(fundamentacion);
		evaluacion.setEstado(EstadoEvaluacion.CONFIRMADA);

// FIXME
		BitacoraBean bitacora = evaluacion.getBitacora();
		bitacora.setIdProyecto(new Long(proyecto.getId()));
		bitacora.setTipo( TipoBitacora.EVALUACION);
		bitacora.setFechaAsunto(DateTimeUtil.getDate());
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Pase a Próxima Etapa");
		bitacora.setIdUsuario(this.getUserId());
		
		proyectoDao.update(proyecto);
		evaluacionDao.save(evaluacion);
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void finalizarControlEvaluacion(EvaluacionGeneralDTO evaluacionDTO, Long idProyecto, ResultadoEvaluacion resultado) {
		
		ProyectoBean proyecto = (ProyectoBean) proyectoDao.read(idProyecto);
		proyecto.finalizarControlEvaluaciones(resultado.getRecomendacionProyecto());

		EvaluacionBean evaluacion = new EvaluacionBean();
		evaluacion.setIdProyecto(new Long(idProyecto));
		evaluacion.setTipo(TipoEvaluacion.FINAL_CONTROL);
		Date date;
		/*
		 * FIXME Esto asume que la fecha se valida a nivel accion.
		 * Lo correcto seria que al servicio llegue un objeto Date.
		 * La forma en que se parsea una fecha es cuestion del
		 * controller y no del modelo. 
		 */
		try {
			date = DateTimeUtil.getDate(evaluacionDTO.getFecha());
		}
		catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		evaluacion.setFechaInicial(date);
		evaluacion.setFecha(date);
		evaluacion.setFundamentacion(evaluacionDTO.getFundamentacion());
		evaluacion.setResultado(resultado);
		evaluacion.setEstado(EstadoEvaluacion.CONFIRMADA);

		BitacoraBean bitacora = evaluacion.getBitacora();
		bitacora.setIdProyecto(new Long(idProyecto));
		bitacora.setTipo( TipoBitacora.EVALUACION);
		bitacora.setFechaAsunto(date);
		bitacora.setFechaRegistro(DateTimeUtil.getDate());
		bitacora.setTema("Finalizar Control de Evaluaciones");
		bitacora.setIdUsuario(this.getUserId());
		
		evaluacionDao.save(evaluacion);
		proyectoDao.update(proyecto);
		bitacoraDao.save(bitacora);
	}
	
	protected String getUserId(){
		AuthenticationService authenticationService = (AuthenticationService) ContextUtil.getBean("authenticationService");
		return authenticationService.getUserId();
	}
}