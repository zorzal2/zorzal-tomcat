package com.fontar.bus.api.configuracion;

import com.fontar.data.impl.domain.dto.MatrizCriterioDTO;

/**
 * Servicios para la administración de matriz de selección de criterios. 
* Cada instrumento (de convocatoria o ventanilla) puede definir 
* la matriz de criterios que se emplea en relación a cada <b>Tipo de Proyecto</b>.
* La matriz de criterio se completa durante la carga del resultado de las evaluaciones de caracter técnica de los proyectos.
* Básicamente una matriz de criterio consiste en un conjunto de criterios de evaluación, donde cada uno de estos criterios tiene un subconjunto de categorias con un puntaje asignado.
 */
public interface AdministrarMatrizCriteriosServicio {

	/**
	 * Actualiza los datos de una Matriz de criterio con sus correspondientes criterios y categorias. 
	 * @param id identidador de la matriz de criterio
	 * @param activo estado de la matriz de criterio (Activo o Inactivo).
	 * @param denominacion denominación de la matriz.
	 * @param idItem identificadores de criterios y categorias.
	 * @param tipoItem Tipo del item, puede sercriterio o categoria.
	 * @param txtItem denominación del ite,
	 * @param puntaje en caso que el item sea categoria, puntaje asignado.
	 */
	public void updateDatosMatriz(String id, Boolean activo, String denominacion, String[] idItem, String[] tipoItem, String[] txtItem, String[] puntaje);
	
	/**
	 * Define una nueva Matriz de criterio con sus correspondientes criterios y categorias.
	 * @param id identificador para la nueva matriz de criterio
	 * @param denominacion nombre de la nueva Matriz de Criterio.
	 * @param activo estado de la matriz de criterio.
	 * @return
	 */
	public Long cargarMatrizCriterio(String id, String denominacion, Boolean activo);	
	/**
	 * Define un nuevo criterio en una Matriz de criterio.
	 * @param idItem identificador del nuevo criterio,
	 * @param descItem nombre del nuevo criterio,
	 * @param idMatrizCriterios identificador de la matriz de criterio existente.
	 * @return identificador del nuevo item de criterio.
	 */
	public Long cargarCriterio(String idItem ,String descItem,Long idMatrizCriterios);	
	
	/**
	 * Carga una nueva Categoria dentro de un Criterio para una cierta Matriz de Criterio.
	 * @param idItem Identificador de la nueva categoria.
	 * @param descItem Descripción de la nueva categoria.
	 * @param puntaje puntaje asignado a la nueva categoria.
	 * @param idPadre Identificador del Criterio ya existente
	 * @param idMatrizCriterios identificador de la matriz de criterio
	 */public void cargarCategoria(String idItem ,String descItem,String puntaje,Long idPadre, Long idMatrizCriterios);

	/**
	 * Obtiene los datos de una Matriz de criterio. 
	 * @param id identificador de la Matriz de criterio.
	 * @return
	 */public MatrizCriterioDTO obtenerMatrizCriterio(Long id);
	
	/**
	 * Eliminar un conjunto de categorias o criterios de una matriz de criterios. 
	 * @param id identificador de Matriz de criterio.
	 * @param idItem identificadores criterios o categorias a eliminar.
	 */public void deleteDatosMatriz(String id, String[] idItem);
	
	/**
	 * Informa si existe una matriz de criterios definida con un cierto nombre. 
	 * @param denominacion 
	 * @return
	 */public boolean existeMatrizConNombre(String denominacion);
	
}


