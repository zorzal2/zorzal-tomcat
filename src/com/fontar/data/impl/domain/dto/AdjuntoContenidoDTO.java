package com.fontar.data.impl.domain.dto;

public class AdjuntoContenidoDTO {
	private Long idAdjuntoContenido;

	private byte[] blArchivo;

	public byte[] getBlArchivo() {
		return blArchivo;
	}

	public void setBlArchivo(byte[] blArchivo) {
		this.blArchivo = blArchivo;
	}

	public Long getIdAdjuntoContenido() {
		return idAdjuntoContenido;
	}

	public void setIdAdjuntoContenido(Long idAdjuntoContenido) {
		this.idAdjuntoContenido = idAdjuntoContenido;
	}

}
