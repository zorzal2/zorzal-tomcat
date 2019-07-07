package com.fontar.bus.impl.ventanilla;

import java.util.Date;

import com.fontar.bus.api.ventanilla.EvaluarPorJuntaIdeaProyectoServicio;
import com.fontar.bus.api.workflow.OpcionDeEvaluacionPorJunta;
import com.fontar.bus.impl.bitacora.BitacoraBuilder;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.impl.assembler.IdeaProyectoEvaluarPorJuntaAssembler;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;

/**
 * 
 * @author gboaglio
 * 
 */

public class EvaluarPorJuntaIdeaProyectoServicioImpl implements EvaluarPorJuntaIdeaProyectoServicio {
	private IdeaProyectoDAO ideaProyectoDao;

	public void cargarEvaluacionPorJunta(String idIdeaProyecto, Date fechaEvaluacion, String recomendacion,
			OpcionDeEvaluacionPorJunta opcion, String fundamentacion) {

		EvaluacionDAO evaluacionDao = (EvaluacionDAO) ContextUtil.getBean("evaluacionDao");
		
		// Evaluacion General -----------------------------------------------
		EvaluacionBean evaluacion = new EvaluacionBean();
		evaluacion.setTipo(TipoEvaluacion.EVAL_JUNTA);
		evaluacion.setIdProyecto(new Long(idIdeaProyecto));
		evaluacion.setFechaInicial(DateTimeUtil.getDate());
		evaluacion.setFecha(fechaEvaluacion);
		
		ResultadoEvaluacion resultado = opcion.getResultadoEvaluacion();
		
		evaluacion.setResultado(resultado);
		evaluacion.setFundamentacion(fundamentacion);
		evaluacion.setRecomendacion(recomendacion);
		evaluacion.setEstado(EstadoEvaluacion.CONFIRMADA);
		
		// IdeaProyecto -----------------------------------------------
		IdeaProyectoBean ideaProyecto = ideaProyectoDao.read(new Long(idIdeaProyecto));
		ideaProyecto.setEstado(opcion.getEstadoIdeaProyecto());
		
		// Bitacora -----------------------------------------------
		BitacoraBean bitacora  = new BitacoraBuilder().cargarEvaluacion( ideaProyecto);
		bitacora.setTema("Carga de Resultado de Evaluación de Junta");
		evaluacion.setBitacora(bitacora);
		
		// Grabo / Actualizo Beans
		ideaProyectoDao.update(ideaProyecto);
		evaluacionDao.save(evaluacion);
	}

	/**
	 * Devuelve un dto con los datos necesarios para resolver la pantalla de
	 * Evaluación Por Junta
	 */
	public IdeaProyectoEvaluarPorJuntaDTO obtenerDatos(Long idIdeaProyecto) {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) ideaProyectoDao.read(idIdeaProyecto);

		IdeaProyectoEvaluarPorJuntaAssembler assembler = new IdeaProyectoEvaluarPorJuntaAssembler();
		IdeaProyectoEvaluarPorJuntaDTO dto = assembler.buildDTO(ideaProyectoBean);
		return dto;
	}


	public void setIdeaProyectoDao(IdeaProyectoDAO ideaProyectoDao) {
		this.ideaProyectoDao = ideaProyectoDao;
	}

}
