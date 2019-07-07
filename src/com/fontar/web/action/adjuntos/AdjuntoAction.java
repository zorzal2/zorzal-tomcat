package com.fontar.web.action.adjuntos;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;
import com.fontar.web.form.AdjuntoDownloadForm;
import com.fontar.web.form.AdjuntoUploadForm;
import com.pragma.web.action.BaseMappingDispatchAction;

public class AdjuntoAction extends BaseMappingDispatchAction {

	protected AdjuntoServicio adjuntoServicio;

	public void setAdjuntoServicio(AdjuntoServicio adjuntoServicio) {
		this.adjuntoServicio = adjuntoServicio;		
	}

	public ActionForward uploadAdjunto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setHeader(mapping,form,request,response);

		// Get the form file property from the form
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;
		FormFile content = uploadForm.getContent();

		InputStream in = null;
		ByteArrayOutputStream out = null;

		AdjuntoDTO adjuntoDTO = new AdjuntoDTO();
		AdjuntoContenidoDTO adjuntoContenidoDTO = new AdjuntoContenidoDTO();

		try {
			// Get an input stream on the form file
			in = content.getInputStream();
			// Create an output stream to a file
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			while (in.read(buffer) != -1) {
				out.write(buffer);
			}
		}
		catch (FileNotFoundException exeption) {
			exeption.printStackTrace();
		}
		catch (IOException exeption) {
			exeption.printStackTrace();
		}
		finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			}
			catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		Long id = getId(uploadForm,request);
		
		adjuntoDTO.setCantidadLongitud(new Long(content.getFileSize()));
		adjuntoDTO.setNombre(content.getFileName());
		adjuntoDTO.setTipoContenido(content.getContentType());
		adjuntoDTO.setFecha(new Date());
		adjuntoDTO.setDescripcion(uploadForm.getDescription());

		adjuntoContenidoDTO.setBlArchivo(out.toByteArray());

		adjuntoServicio.uploadAdjunto(adjuntoDTO, adjuntoContenidoDTO,id);

		ActionForward af = mapping.findForward(FORWARD_SUCCESS);
		ActionForward af2 = new ActionForward(af);

		StringBuffer afPath = new StringBuffer();
		afPath.append(af.getPath());
		afPath.append("?id=" + id);

		af2.setPath(afPath.toString());
		return af2;
	}

	public ActionForward cargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		setHeader(mapping,form,request,response);
		
		// Get the form file property from the form
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;

		Long id = getId(uploadForm,request);
		uploadForm.setFecha(null);
		uploadForm.setId(id);
		
		if(this.getErrors(request).isEmpty()){
			uploadForm.setDescription("");
			uploadForm.setNombre("");
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		setHeader(mapping,form,request,response);

		// Get the form file property from the form
		AdjuntoUploadForm uploadForm = (AdjuntoUploadForm) form;

		Long id = getId(uploadForm,request);
		adjuntoServicio.borrarAdjunto(uploadForm.getIdAdjunto(), id);

		ActionForward af = mapping.findForward(FORWARD_SUCCESS);
		ActionForward af2 = new ActionForward(af);

		StringBuffer afPath = new StringBuffer();
		afPath.append(af.getPath());
		afPath.append("?id=" + id);

		af2.setPath(afPath.toString());
		return af2;
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
		
		
		//check, si no esta el ID declarado, es Adjunto del Proyecto 
		Long id = getId(adjuntoUploadForm,request);

		setHeader(mapping,form,request,response);
		
		List adjuntos = (List) adjuntoServicio.obtenerAdjuntos(id);

		request.setAttribute("adjuntos", adjuntos);

		return mapping.findForward(FORWARD_SUCCESS);
	}
	
	protected void setHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	}
	
	protected Long getId(AdjuntoUploadForm form, HttpServletRequest request) {
		return form.getId();
	}
}
