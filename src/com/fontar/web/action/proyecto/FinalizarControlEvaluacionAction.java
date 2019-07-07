package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.codes.evaluacion.ResultadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionResumenDePresupuestoDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.proyecto.presupuesto.ProyectoPresupuestoDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.seguridad.ObjectUtils;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.PragmaDynaValidatorForm;

/**
 * Finaliza el control de un evaluacion de un proyecto
 * @author ssanchez
 * @version 1.01, 09/01/07
 */
public class FinalizarControlEvaluacionAction extends ProyectoBaseTaskAction {

	private static final String EVALUACIONES_LIST = "evaluacionesList";
	
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		ProyectoCabeceraDTO cabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());		
		request.setAttribute(PROYECTO, cabeceraDTO);
		
		request.setAttribute("recomendaciones", new CollectionHandler().getRecomendacionesEvaluacion());
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		ProyectoBean proyecto = (ProyectoBean) proyectoRaizDao.read(idProyecto);

		Collection evaluacionesList = administrarEvaluacionesServicio.getEvaluaciones(proyecto, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);
		
		request.setAttribute(EVALUACIONES_LIST, evaluacionesList);
		
		//Presupuesto inicial de la evaluacion
		ProyectoPresupuestoDTO presupuesto = wfProyectoServicio.obtenerPresupuesto(idTaskInstance);
		if(presupuesto==null) {
			request.setAttribute("idPresupuestoInicial", null);
		} else {
			request.setAttribute("idPresupuestoInicial", presupuesto.getId());
		}
		
		//Para la edicion del presuouesto
		String randomVarName = "idPresupuestoDefinitivo_"+Math.random();
		request.setAttribute("idPresupuestoDefinitivo_randomVarName", randomVarName);
		
		//Informacion del presupuesto
		EvaluacionResumenDePresupuestoDTO resumenDePresupuesto = wfProyectoServicio.obtenerResumenDePresupuestoParaFinalizarControl(idTaskInstance);
		request.setAttribute("resumenDePresupuesto", resumenDePresupuesto);
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ActionUtil.checkValidEncryptionContext(messages);
		super.validateCargarTarea(mapping, form, request, response, messages, idTaskInstance);
	
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		ProyectoBean proyecto = (ProyectoBean) proyectoRaizDao.read(idProyecto);
		//No debe haber evaluaciones abiertas
		List<String> evalAbiertas = proyecto.tieneEvaluacionesAbiertas();

		Iterator i = evalAbiertas.iterator();
		while (i.hasNext()) {
			addError(messages,"app.proyecto.noFinCtrlEvalAbierta",i.next());
		}
		//Debe haber un presupuesto
		if(
				proyecto.getIdPresupuesto()==null &&
				proyecto.getIdPresupuestoOriginal()==null) {
			addError(messages,"app.proyecto.presupuesto.empty");
		}
		
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		PragmaDynaValidatorForm dynaForm = (PragmaDynaValidatorForm) form;

		EvaluacionGeneralDTO evaluacion = new EvaluacionGeneralDTO();

		Long idProyecto = (Long)dynaForm.get("idProyecto");
		String fechaEvaluacion = (String)dynaForm.get("fechaEvaluacion");
		String fundamentacion = (String)dynaForm.get("fundamentacion");

		//Presupuesto
		Long idPresupuestoInicial = (Long)dynaForm.get("idPresupuestoInicial");
		evaluacion.setIdPresupuestoInicial(idPresupuestoInicial);
		
		//Obtengo, si lo hubo, el ultimo presupuesto guardado
		String randomVarName = request.getParameter("idPresupuestoDefinitivo_randomVarName");
		ProyectoPresupuestoDTO presupuestoDefinitivo = (ProyectoPresupuestoDTO)request.getSession().getAttribute(randomVarName);
		
		//impacto el presupuesto en el proyecto y en la evaluacion
		if(presupuestoDefinitivo!=null) {
			evaluacion.setIdPresupuesto(presupuestoDefinitivo.getId());
		}

		ResultadoEvaluacion resultado = ResultadoEvaluacion.valueOf(BeanUtils.getProperty(form, "recomendacion")); 

		evaluacion.setFecha(fechaEvaluacion);
		evaluacion.setFundamentacion(fundamentacion);

		wfProyectoServicio.finalizarControlEvaluacion(presupuestoDefinitivo, evaluacion, idProyecto, resultado, idTaskInstance);
	}
}