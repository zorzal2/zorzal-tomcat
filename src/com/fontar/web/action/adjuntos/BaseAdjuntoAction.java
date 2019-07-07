package com.fontar.web.action.adjuntos;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoInfoDTO;
import com.fontar.data.impl.domain.dto.ArchivoDTO;
import com.fontar.web.form.AdjuntoDownloadForm;
import com.fontar.web.form.AdjuntoUploadForm;
import com.pragma.web.action.BaseMappingDispatchAction;

public abstract class BaseAdjuntoAction extends BaseMappingDispatchAction {

	private AdjuntoServicio adjuntoServicio;

	public void setAdjuntoServicio(AdjuntoServicio adjuntoServicio) {
		this.adjuntoServicio = adjuntoServicio;
	}

	public ActionForward uploadAdjunto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;

		ArchivoDTO archivo = uploadForm.archivo();

		Long adjuntableId = uploadForm.getId();
		
		AdjuntoDTO adjuntoDTO = new AdjuntoDTO();
		adjuntoDTO.setCantidadLongitud(archivo.getLongitud());
		adjuntoDTO.setNombre(archivo.getNombre());
		adjuntoDTO.setTipoContenido(archivo.getTipoContenido());
		adjuntoDTO.setFecha(archivo.getFecha());
		adjuntoDTO.setDescripcion(uploadForm.getDescription());

		AdjuntoContenidoDTO adjuntoContenidoDTO = new AdjuntoContenidoDTO();
		adjuntoContenidoDTO.setBlArchivo(archivo.getBytes());

		persistir(new AdjuntoInfoDTO(adjuntableId, adjuntoDTO, adjuntoContenidoDTO));

		return mapping.findForward(FORWARD_SUCCESS);
	}
	
	protected void persistir(AdjuntoInfoDTO adjuntoData) {
		adjuntoServicio.uploadAdjunto(adjuntoData.getAdjuntoDTO(), adjuntoData.getAdjuntoContenidoDTO(), adjuntoData.getAdjuntableId());
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		// Get the form file property from the form
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;

		Long id = uploadForm.getId();

		uploadForm.setDescription("");
		uploadForm.setFecha(null);
		uploadForm.setId(id);
		uploadForm.setNombre("");

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Get the form file property from the form
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;

		Long id = uploadForm.getId();

		adjuntoServicio.borrarAdjunto(uploadForm.getIdAdjunto(), id);

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Get the form file property from the form
		AdjuntoDownloadForm adjuntoDownloadForm = (AdjuntoDownloadForm) form;

		adjuntoDownloadForm.setId(null);

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward cargarDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Get the form file property from the form
		AdjuntoUploadForm adjuntoUploadForm = (AdjuntoUploadForm) form;
		
		List adjuntos = (List) adjuntoServicio.obtenerAdjuntos(adjuntoUploadForm.getId());

		request.setAttribute("adjuntos", adjuntos);

		return mapping.findForward(FORWARD_SUCCESS);
	}
}
