/**
 * 
 */
package com.fontar.data.impl.domain.dto;


public class AdjuntoInfoDTO {
	private Long adjuntableId;
	private AdjuntoDTO adjuntoDTO;
	private AdjuntoContenidoDTO adjuntoContenidoDTO;
	
	public AdjuntoInfoDTO(Long adjuntableId, AdjuntoDTO adjuntoDTO, AdjuntoContenidoDTO adjuntoContenidoDTO) {
		this.adjuntableId = adjuntableId;
		this.adjuntoContenidoDTO = adjuntoContenidoDTO;
		this.adjuntoDTO = adjuntoDTO;
	}

	public Long getAdjuntableId() {
		return adjuntableId;
	}

	public void setAdjuntableId(Long adjuntableId) {
		this.adjuntableId = adjuntableId;
	}

	public AdjuntoContenidoDTO getAdjuntoContenidoDTO() {
		return adjuntoContenidoDTO;
	}

	public void setAdjuntoContenidoDTO(AdjuntoContenidoDTO adjuntoContenidoDTO) {
		this.adjuntoContenidoDTO = adjuntoContenidoDTO;
	}

	public AdjuntoDTO getAdjuntoDTO() {
		return adjuntoDTO;
	}

	public void setAdjuntoDTO(AdjuntoDTO adjuntoDTO) {
		this.adjuntoDTO = adjuntoDTO;
	}

}