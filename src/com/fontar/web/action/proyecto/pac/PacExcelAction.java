package com.fontar.web.action.proyecto.pac;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.impl.proyecto.pac.PacExcelParsingException;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.data.impl.domain.dto.PacDTO;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.web.form.proyecto.pac.PacExcelUploadForm;
import com.fontar.web.util.ActionUtil;
import com.pragma.PragmaException;
import com.pragma.excel.exception.ParsingException;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * Action de la tarea de ingreso de pac desde excel
 * @author llobeto
 */
public class PacExcelAction extends BaseMappingDispatchAction {

	private AdministrarPACServicio pacService;
	
	public void setPacService(AdministrarPACServicio pacService) {
		this.pacService = pacService;
	}

	public ActionForward ingresar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = getErrors(request);
		ActionUtil.checkValidEncryptionContext(messages);
		if(!messages.isEmpty()) {
			request.setAttribute("errorMessage", (ResourceManager.getErrorResource("app.error.encrypt")));
			return mapping.findForward(FORWARD_INVALID);			
		}

		PacExcelUploadForm uploadForm = (PacExcelUploadForm) form;

		
		ArchivoDTO archivo = uploadForm.archivo();
		
		if(archivo==null) {
			request.setAttribute("errorMessage", ResourceManager.getErrorResource("app.file.fileNotFound"));
			return mapping.findForward(FORWARD_INVALID);
		}
		
		if(uploadForm.getProyectoId()==null)
			uploadForm.setProyectoId(SessionHelper.getIdProyecto(request));
		
		Collection<PacDTO> pacs;
		try {
			pacs = pacService.parseArchivo(archivo, uploadForm.getProyectoId());
		} catch(PacExcelParsingException e) {
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
		
		String message = ResourceManager.getInformationalResource("app.msj.pacsAgregados", String.valueOf(pacs.size()));

		request.setAttribute("message", message);
		request.setAttribute("pacs", pacs);
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
