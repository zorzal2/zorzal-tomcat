package com.fontar.bus.api.fuenteFinanciamiento;

import java.util.List;

import com.fontar.data.impl.domain.dto.FuenteFinanciamientoDTO;

/**
 * Servicios para registrar las distintas fuentes de financiamiento empleadas en el sistema.
 * Las fuentes de financiamiento se emplean en las Definiciones de Instrumentos para registrar
 * el origen de los recursos en relación al instrumento.  
 */
public interface FuenteFinanciamientoServicio {

	/**
	 * Define una nueva fuente de financiamiento.
	 * @param identificador idenfitificador de la fuente de financiamiento.
	 * @param denominacion nombre de la fuente de financiamiento.
	 * @return identificador de la nueva fuente de financiamiento
	 */
	public abstract Long agregarFuenteFinanciamiento(String identificador, String denominacion);

	/**
	 * Modifica los datos de una fuente de financiamiento existente.
	 * @param id identificador de la fuente de financiamiento existente
	 * @param identificador nuevo identificador
	 * @param denominacion nueva denominación
	 */
	public abstract void modificarFuenteFinanciamiento(Long id, String identificador, String denominacion);

	/**
	 * Retorna las Fuentes de Financiamiento que existen
	 * @return
	 */
	public abstract List<FuenteFinanciamientoDTO> obtenerFuenteFinanciamiento();

	/**
	 * Retorna una Fuente Financiamiento
	 * @param id
	 * @return
	 */
	public abstract FuenteFinanciamientoDTO obtenerFuenteFinanciamiento(Long id);
}