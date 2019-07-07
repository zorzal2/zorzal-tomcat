package com.fontar.bus.api.especialidadEvaluador;

import java.util.List;

import com.fontar.data.impl.domain.dto.EspecialidadEvaluadorDTO;

/**
 * Servicios para la administración de especialidades de los evaluadores.
 */
public interface EspecialidadEvaluadorServicio {

	/**
	 * Incorpora una nueva especialidad de evaluación.
	 * @param codigo código de la nueva especialidad
	 * @param nombre nombre de la nueva especialidad
	 * @return
	 */
	public abstract Long agregarEspecialidadEvaluador(String codigo, String nombre);

	/**
	 * Actualiza una especialidad de evaluación
	 * @param id identidicador de la especialidad
	 * @param codigo nuevo código
	 * @param nombre nuevo nombre
	 */
	public abstract void modificarEspecialidadEvaluador(Long id, String codigo, String nombre);

	/**
	 * Retorna las Especialidades de evaluación que existen
	 */
	public abstract List<EspecialidadEvaluadorDTO> obtenerEspecialidadEvaluador();

	/**
	 * Retorna la entidad de Especialidad de evaluación a partir del identificador de la misma. 
	 */
	public abstract EspecialidadEvaluadorDTO obtenerEspecialidadEvaluador(Long id);
}