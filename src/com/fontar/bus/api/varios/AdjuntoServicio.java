package com.fontar.bus.api.varios;

import java.util.Collection;

import com.fontar.data.impl.domain.dto.AdjuntoContenidoDTO;
import com.fontar.data.impl.domain.dto.AdjuntoDTO;

/***
 * Servicios que permiten manejar en forma genérica los adjuntos de archivos en entidades.   
 */
public interface AdjuntoServicio {
	/**
	 * Retorna el conjunto de todos los adjuntos definidos.
	 * @return
	 */
	public Collection obtenerAdjuntos();
	
	/**
	 * Retorna el adjunto a partir de un identificador.
	 * @param id
	 * @return
	 */
	public Collection obtenerAdjuntos(Long id);
	
	/**
	 * Actualiza el adjunto relacionado con id, con los datos definidos en adjuntoDTO y el archivo en adjuntoContenidoDTO
	 * @param adjuntoDTO
	 * @param adjuntoContenidoDTO
	 * @param id
	 */
	public void uploadAdjunto(AdjuntoDTO adjuntoDTO, AdjuntoContenidoDTO adjuntoContenidoDTO, Long id);
	
	/**
	 * Elimina un adjunto dado.
	 * @param idAdjunto
	 * @param id
	 */
	public void borrarAdjunto(Long idAdjunto,Long id);
	/**
	 * Devuelve el contenido relacionado con un identificador de contenido. 
	 * @param idAdjuntoContenido
	 * @return
	 */
	public AdjuntoContenidoDTO obtenerAdjuntoContenido(Long idAdjuntoContenido);
}
