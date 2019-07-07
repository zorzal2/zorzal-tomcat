package com.fontar.web.action.proyecto.datos;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.datos.ProyectosExcelServicio;
import com.fontar.bus.impl.proyecto.datos.ProyectosExcelFeedbackRequiredException;
import com.fontar.bus.impl.proyecto.datos.ProyectosExcelParsingException;
import com.fontar.bus.impl.proyecto.datos.UserCancelledException;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.web.form.proyecto.datos.ProyectosExcelUploadForm;
import com.pragma.PragmaException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;
import com.pragma.web.userfeedback.UserFeedbackResponse;

/**
 * Action de la tarea de ingreso de presupuesto a un proyecto
 * @author llobeto
 */
public class ProyectosExcelAction extends BaseMappingDispatchAction {
	
	private static final String FORWARD_CANCELLED = "cancelled";
	private static final String FORWARD_FEEDBACK_REQUIRED = "feedbackRequired";

	private ProyectosExcelServicio proyectosExcelServicio;
	
	public ProyectosExcelServicio getProyectosExcelServicio() {
		return proyectosExcelServicio;
	}

	public void setProyectosExcelServicio(ProyectosExcelServicio proyectosExcelServicio) {
		this.proyectosExcelServicio = proyectosExcelServicio;
	}

	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProyectosExcelUploadForm uploadForm = (ProyectosExcelUploadForm) form;
		
		
		ArchivoDTO archivo = uploadForm.archivo();
		
		if(archivo==null) {
			request.setAttribute("errorMessage", ResourceManager.getErrorResource("app.file.fileNotFound"));
			return mapping.findForward(FORWARD_INVALID);
		}
		
		Collection<ProyectoDTO> proyectos;
		try {
			proyectos = proyectosExcelServicio.parseArchivo(archivo);
		} catch(ProyectosExcelFeedbackRequiredException e) {
			request.setAttribute("feedback", e.getFeedbackRequests());
			SessionHelper.setArchivoExcel(request, archivo);
			return mapping.findForward(FORWARD_FEEDBACK_REQUIRED);
		} catch(ProyectosExcelParsingException e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getMessageKey(), e.getMessageParameters())).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		} catch(ParsingException e) {
			request.setAttribute("errorMessage", e.getMessageArray());
			return mapping.findForward(FORWARD_INVALID);
		} catch(PragmaException e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getBundleKey())).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		} catch(Exception e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource("app.unknownError")).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		}
		
		request.setAttribute("proyectos", proyectos);
		return mapping.findForward(FORWARD_SUCCESS);
	}
	public ActionForward reintentar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ArchivoDTO archivo = SessionHelper.getArchivoExcel(request);
		SessionHelper.setArchivoExcel(request, null);
		
		//Levanto el feedback del usuario
		Collection<UserFeedbackResponse> feedback = new ArrayList<UserFeedbackResponse>();
		for(String parameter : FormUtil.getStringValue(form, "feedback").split(",")) {
			String[] strings = parameter.split(">>");
			feedback.add(new UserFeedbackResponse(strings[0], strings[1]));
			
		}
		
		if(archivo==null) {
			request.setAttribute("errorMessage", ResourceManager.getErrorResource("app.file.fileNotFound"));
			return mapping.findForward(FORWARD_INVALID);
		}
		
		
		Collection<ProyectoDTO> proyectos;
		try {
			proyectos = proyectosExcelServicio.parseArchivo(archivo, feedback);
		} catch(UserCancelledException e) {
			return mapping.findForward(FORWARD_CANCELLED);
		} catch(ProyectosExcelParsingException e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getMessageKey(), e.getMessageParameters())).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		} catch(ParsingException e) {
			request.setAttribute("errorMessage", e.getMessageArray());
			return mapping.findForward(FORWARD_INVALID);
		} catch(PragmaException e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getBundleKey())).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		} catch(Exception e) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource("app.unknownError")).split("\n"));
			return mapping.findForward(FORWARD_INVALID);
		}

		request.setAttribute("proyectos", proyectos);
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
