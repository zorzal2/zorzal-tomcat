package com.fontar.bus.api.configuracion;

import java.util.Collection;

import com.fontar.data.impl.domain.dto.VisualizarInstrumentoDefDTO;

/**
 * Servicios b�sicos para acceder a la configuraci�n de definiciones de instrumentos de beneficio. 
 * 
 */
public interface InstrumentoDefServicio {

	/**
	 * Retorna la lista de todos las definiciones de instrumentos de beneficio 
	 * @return
	 */
	public Collection listaInstrumentosDef();
	
	/***
	 * Obtiene un DTO para la visualizacion de una definicion de instrumento.
	 * @param id
	 * @return
	 */
	public VisualizarInstrumentoDefDTO getVisualizarDTO( Long id );

}