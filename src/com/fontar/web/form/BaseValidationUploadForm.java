package com.fontar.web.form;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.fontar.data.impl.domain.dto.ArchivoDTO;
/**
 * Form base para upload de archivos con validacion. Para
 * formularios sin validacion usar BaseActionUploadForm.
 * @author llobeto
 *
 */
public class BaseValidationUploadForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	
	private FormFile content;

	public FormFile getContent() {
		return content;
	}
	public void setContent(FormFile content) {
		this.content = content;
	}

	public ArchivoDTO archivo() {

		if(content==null || content.getFileName().equals(""))return null;
		
		InputStream in = null;
		ByteArrayOutputStream out = null;

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
		ArchivoDTO archivo = new ArchivoDTO();
		archivo.setBytes(out.toByteArray());
		archivo.setFecha(new Date());
		archivo.setLongitud(new Long(content.getFileSize()));
		archivo.setNombre(content.getFileName());
		archivo.setTipoContenido(content.getContentType());

		return archivo;
	}	
}
