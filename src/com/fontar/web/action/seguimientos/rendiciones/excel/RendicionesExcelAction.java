package com.fontar.web.action.seguimientos.rendiciones.excel;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.impl.seguimientos.seguimientos.RendicionesExcelParsingException;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.RendicionCuentasDTO;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.web.form.administracion.seguimiento.rendiciones.RendicionesExcelUploadForm;
import com.fontar.web.util.ActionUtil;
import com.pragma.PragmaException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.web.NavigationManager;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de rendiciones desde excel
 * @author llobeto
 */
public class RendicionesExcelAction extends BaseMappingDispatchAction {

	private AdministrarSeguimientoServicio seguimientoService;
	
	public void setSeguimientoService(AdministrarSeguimientoServicio seguimientoService) {
		this.seguimientoService = seguimientoService;
	}

	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Chequeo si el seguimiento tiene ya rendiciones
		boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
		((RendicionesExcelUploadForm)form).setSeguimientoId(SessionHelper.getIdSeguimiento(request));
		
		request.setAttribute("tieneRendiciones", tieneRendiciones);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RendicionesExcelUploadForm uploadForm = (RendicionesExcelUploadForm) form;
		if(uploadForm.getSeguimientoId()!=null) {
			ActionMessages messages = getErrors(request);
			ActionUtil.checkValidEncryptionContext(messages);
			if(!messages.isEmpty()) {
				request.setAttribute("errorMessage", (ResourceManager.getErrorResource("app.error.encrypt")));
				boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
				request.setAttribute("tieneRendiciones", tieneRendiciones);
				return mapping.findForward(FORWARD_INVALID);			
			}
			
			ArchivoDTO archivo = uploadForm.archivo();
			
			if(archivo==null) {
				request.setAttribute("errorMessage", ResourceManager.getErrorResource("app.file.fileNotFound"));
				return mapping.findForward(FORWARD_INVALID);
			}
			
			Collection<RendicionCuentasDTO> rendiciones;
			try {
				rendiciones = seguimientoService.parseArchivo(archivo, uploadForm.getSeguimientoId(), uploadForm.getBorrarExistentes());
			} catch(RendicionesExcelParsingException e) {
				request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getMessageKey(), e.getMessageParameters())).split("\n"));
				boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
				request.setAttribute("tieneRendiciones", tieneRendiciones);
				return mapping.findForward(FORWARD_INVALID);
			} catch(ParsingException e) {
				request.setAttribute("errorMessage", e.getMessageArray());
				boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
				request.setAttribute("tieneRendiciones", tieneRendiciones);
				return mapping.findForward(FORWARD_INVALID);
			} catch(PragmaException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", (ResourceManager.getErrorResource(e.getBundleKey())).split("\n"));
				boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
				request.setAttribute("tieneRendiciones", tieneRendiciones);
				return mapping.findForward(FORWARD_INVALID);
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", (ResourceManager.getErrorResource("app.unknownError")).split("\n"));
				boolean tieneRendiciones = seguimientoService.getTieneRendiciones(SessionHelper.getIdSeguimiento(request));
				request.setAttribute("tieneRendiciones", tieneRendiciones);
				return mapping.findForward(FORWARD_INVALID);
			}
			
			//String message = ResourceManager.getInformationalResource("app.msj.rendicionesAgregadas", String.valueOf(rendiciones.size()));
			
			ActionMessages infoMessages = getMessages(request);
			addInformationMessage(infoMessages, "app.msj.rendicionesAgregadas", String.valueOf(rendiciones.size()));
			//request.setAttribute("message", message);
			request.setAttribute("rendiciones", rendiciones);
			saveMessages(request, infoMessages);
		} 
		
		return NavigationManager.getPreviousAction(request);
	}
	public ActionForward cancelar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward previousAction = NavigationManager.getPreviousAction(request);
		previousAction.setRedirect(true);
		return previousAction;
		
	}
}
