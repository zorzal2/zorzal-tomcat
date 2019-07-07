package com.fontar.web.action.administracion;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;
import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.bus.api.workflow.WFProyectoRaizServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoRaizBean;
import com.fontar.data.impl.domain.codes.general.MotivoCierre;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoRaizEvaluarDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Cierra un proyecto.
 * @author ssanchez
 */
public class CerrarProyectoAction extends GenericJbpmTaskAction {

	private WFProyectoRaizServicio wfProyectoRaizServicio;
	private WFProyectoServicio wfProyectoServicio;
	private WFIdeaProyectoServicio wfIdeaProyectoServicio;

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		setCollections(request);

		ProyectoRaizEvaluarDTO proyecto = wfProyectoRaizServicio.obtenerDatosEvaluacion(idTaskInstance);
		if (proyecto.getClase().compareToIgnoreCase(CabeceraAttribute.PROYECTO) == 0) {
			ProyectoCabeceraDTO proyectoCabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());		
			request.setAttribute(PROYECTO, proyectoCabeceraDTO);
		} else
			if (proyecto.getClase().compareToIgnoreCase(CabeceraAttribute.IDEA_PROYECTO) == 0) {
				IdeaProyectoCabeceraDTO ideaProyectoCabeceraDTO = (IdeaProyectoCabeceraDTO) wfIdeaProyectoServicio.getIdeaProyectoDTO(idTaskInstance, new IdeaProyectoCabeceraAssembler());
				request.setAttribute(IDEA_PROYECTO, ideaProyectoCabeceraDTO);
		}
	}

	/**
	 * Valida que el proyecto no este en paquete
	 * y que no tenga evaluaciones ni seguimientos
	 * abiertos.
	 */
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		
		ProyectoRaizBean proyectoRaiz = proyectoRaizDao.read(taskHelper.getIdProyecto());
		
		if((proyectoRaiz instanceof ProyectoBean) && ((ProyectoBean)proyectoRaiz).estaEnPaquete()){
			addError(messages,"app.proyecto.noCerrarProyEnPaquete");
		}
		
		List<String> evalAbiertas = proyectoRaiz.tieneEvaluacionesAbiertas();		
		Iterator i = evalAbiertas.iterator();
		while (i.hasNext()) {
			addError(messages,"app.proyecto.noCerrarEvalAbierta",i.next());
		}
		
		// Chequeo que el proyecto no tenga seguimientos abiertos
		List<String> seguimientos = proyectoRaiz.seguimientosAbiertos();
		i = seguimientos.iterator();
		while (i.hasNext()) {
			addError(messages,"app.proyecto.noCerrarSeguimientoAbierto",i.next());
		}
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		MotivoCierre motivo= MotivoCierre.valueOf( FormUtil.getStringValue(form, "motivo"));
		String obs= FormUtil.getStringValue( form, "observacion");
		wfProyectoRaizServicio.cerrarProyecto( motivo, obs, idTaskInstance);
	}

	@SuppressWarnings("unchecked")
	private void setCollections(HttpServletRequest request) throws Exception {
		
		CollectionHandler collectionHandler = new CollectionHandler();

		Collection motivoList = new ArrayList();

		motivoList = collectionHandler.getComboFormulario(MotivoCierre.class, false);
		request.setAttribute("motivos", motivoList);
	}

	public void setWfProyectoRaizServicio(WFProyectoRaizServicio wfProyectoRaizServicio) {
		this.wfProyectoRaizServicio = wfProyectoRaizServicio;
	}

	public void setWfIdeaProyectoServicio(WFIdeaProyectoServicio wfIdeaProyectoServicio) {
		this.wfIdeaProyectoServicio = wfIdeaProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
}