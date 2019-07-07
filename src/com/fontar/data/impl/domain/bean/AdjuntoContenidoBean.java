package com.fontar.data.impl.domain.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.lob.BlobImpl;

/**
 * Estos objetos representan el archivos fisico de un adjunto.
 * @see com.fontar.data.impl.domain.bean.AdjuntoBean
 */
public class AdjuntoContenidoBean {
	Long idAdjuntoContenido;

	private byte[] blArchivo;

	public void setContentAsBlob(Blob blArchivo) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = blArchivo.getBinaryStream();
			// Utils.bufferedStreamCopy(in, out);
			byte[] buffer = new byte[4096];
			long bytesSoFar = 0;

			for (int len = in.read(buffer); len > -1; len = in.read(buffer)) {
				out.write(buffer, 0, len);
				bytesSoFar += len;
			}

			out.flush();
			in.close();
			out.close();
			in = null;
			out = null;

			this.blArchivo = out.toByteArray();
		}
		catch (SQLException exeption) {
			exeption.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Blob getContentAsBlob() {
		return new BlobImpl(blArchivo);
	}

	public Long getIdAdjuntoContenido() {
		return idAdjuntoContenido;
	}

	public void setIdAdjuntoContenido(Long idAdjuntoContenido) {
		this.idAdjuntoContenido = idAdjuntoContenido;
	}

	public byte[] getBlArchivo() {
		return blArchivo;
	}

	public void setBlArchivo(byte[] blArchivo) {
		this.blArchivo = blArchivo;
	}
}
