package com.fontar.web.action.adjuntos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.fontar.bus.api.varios.AdjuntoServicio;
import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.web.form.AdjuntoDownloadForm;

public class AdjuntoDownloadAction extends DownloadAction {

	AdjuntoServicio adjuntoServicio;

	public void setAdjuntoServicio(AdjuntoServicio adjuntoServicio) {
		this.adjuntoServicio = adjuntoServicio;
	}

	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {

		AdjuntoDownloadForm downloadForm = (AdjuntoDownloadForm) form;
		if(downloadForm.getId()==null) {
			if(req.getParameter("id")==null)
				downloadForm.setId((Long)req.getAttribute("id"));
			else 
				downloadForm.setId(new Long(req.getParameter("id")));
				
		}
		if(downloadForm.getFilename()==null) {
			if(req.getParameter("filename")==null)
				downloadForm.setFilename((String)req.getAttribute("filename"));
			else 
				downloadForm.setFilename(req.getParameter("filename"));
		}
		if(downloadForm.getContentType()==null) {
			if(req.getParameter("contentType")==null)
				downloadForm.setContentType((String)req.getAttribute("contentType"));
			else
				downloadForm.setContentType(req.getParameter("contentType"));
		}

		AdjuntoContenidoDTO dto = (AdjuntoContenidoDTO) adjuntoServicio.obtenerAdjuntoContenido(downloadForm.getId());
		// Obtengo el content
		byte[] attachmentBytes = dto.getBlArchivo();
		resp.setHeader("Content-disposition", "attachment;filename=" + downloadForm.getFilename());
		// Esto es para que sobre ssl no de error al querer ver el adjunto
		resp.setHeader("PRAGMA","");
		resp.setHeader("EXPIRES","");
		
		return new ByteArrayStreamInfo(downloadForm.getContentType(), attachmentBytes);

	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] bytes) {
			this.contentType = contentType;
			this.bytes = bytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
