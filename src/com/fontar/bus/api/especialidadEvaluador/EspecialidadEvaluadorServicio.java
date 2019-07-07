package com.fontar.bus.api.especialidadEvaluador;

import java.util.List;

import com.fontar.data.impl.domain.dto.EspecialidadEvaluadorDTO;

/**
 * Servicios para la administraci�n de especialidades de los evaluadores.
 */
public interface EspecialidadEvaluadorServicio {

	/**
	 * Incorpora una nueva especialidad de evaluaci�n.
	 * @param codigo c�digo de la nueva especialidad
	 * @param nombre nombre de la nueva especialidad
	 * @return
	 */
	public abstract Long agregarEspecialidadEvaluador(String codigo, String nombre);

	/**
	 * Actualiza una especialidad de evaluaci�n
	 * @param id identidicador de la especialidad
	 * @param codigo nuevo c�digo
	 * @param nombre nuevo nombre
	 */
	public abstract void modificarEspecialidadEvaluador(Long id, String codigo, String nombre);

	/**
	 * Retorna las Especialidades de evaluaci�n que existen
	 */
	public abstract List<EspecialidadEvaluadorDTO> obtenerEspecialidadEvaluador();

	/**
	 * Retorna la entidad de Especialidad de evaluaci�n a partir del identificador de la misma. 
	 */
	public abstract EspecialidadEvaluadorDTO obtenerEspecialidadEvaluador(Long id);
}