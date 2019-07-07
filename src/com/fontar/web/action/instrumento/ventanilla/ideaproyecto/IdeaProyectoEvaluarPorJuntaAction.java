package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.OpcionDeEvaluacionPorJunta;
import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.dto.IdeaProyectoEvaluarPorJuntaDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action de la tarea de ingreso de evaluación por junta a una idea proyecto
 * @author gboaglio, ssanchez
 * @version 1.01, 10/01/07
 */
public class IdeaProyectoEvaluarPorJuntaAction extends GenericJbpmTaskAction {

	protected WFIdeaProyectoServicio wfIdeaProyectoServicio;

	
	public void setWfIdeaProyectoServicio(WFIdeaProyectoServicio wfIdeaProyectoServicio) {
		this.wfIdeaProyectoServicio = wfIdeaProyectoServicio;
	}

	/**
	 * Carga de datos para la vista de la tarea, también se extraen los datos de
	 * contexto desde la tarea.
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		IdeaProyectoEvaluarPorJuntaDTO dto = wfIdeaProyectoServicio.obtenerDatosEvaluacionPorJunta(idTaskInstance);

		String idIdeaProyecto = dto.getId();
		BeanUtils.setProperty(form, "idProyecto", idIdeaProyecto);

		request.setAttribute("txtProyecto", dto.getCodigoIdeaProyecto());
		request.setAttribute("txtEstado", dto.getEstado().getDescripcion());

		request.setAttribute(IDEA_PROYECTO, dto);
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.validateCargarTarea(mapping, form, request, response, messages, idTaskInstance);
				
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		IdeaProyectoBean ideaProyecto = (IdeaProyectoBean) proyectoRaizDao.read(new Long(idProyecto));
				

		/*
		 
		List<String> evalAbiertas = ideaProyecto.tieneEvaluacionesAbiertas();
		Iterator<String> i = evalAbiertas.iterator();
		while (i.hasNext()) {
			addError(messages,"app.proyecto.noEvJuntaIPEvalAbierta",i.next());
		}*/

		List<String> evalAbiertas = ideaProyecto.tieneEvaluacionesAbiertas();
		request.setAttribute("evaluacionesAbiertas", evalAbiertas.isEmpty()? null : evalAbiertas);
		if(messages.isEmpty() && evalAbiertas.isEmpty()) {
			ActionMessages infoMessages =  this.getMessages( request );
			ActionUtil.alertForEncription(request, infoMessages);
			saveMessages(request, infoMessages);
		}
	}
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		if(!ActionUtil.checkValidEncryptionContext(messages)) return;
		
		String idProyecto = BeanUtils.getProperty(form, "idProyecto");
		Date fechaEvaluacion = DateTimeUtil.getDate(BeanUtils.getProperty(form, "fechaEvaluacion"));
		OpcionDeEvaluacionPorJunta aceptaProyecto = OpcionDeEvaluacionPorJunta.valueOf(FormUtil.getStringValue(form, "aceptaProyecto"));
		String recomendacion = BeanUtils.getProperty(form, "recomendacion");
		String fundamentacion = BeanUtils.getProperty(form, "fundamentacion");
		if(
					StringUtil.isEmpty(recomendacion) 
				&& 	aceptaProyecto.requiereEspecificacionDeInstrumentoRecomendado()) {
			addError(messages,"app.proyecto.faltaRecomendacion");
		} else {
			wfIdeaProyectoServicio.cargarEvaluacionPorJunta(idProyecto, fechaEvaluacion, recomendacion, aceptaProyecto, fundamentacion, idTaskInstance);
		}
}

}
