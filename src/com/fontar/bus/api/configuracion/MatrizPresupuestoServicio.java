package com.fontar.bus.api.configuracion;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.MatrizPresupuestoBean;

/**
 * Servicios para la administración de Matrices de Presupuesto. 
 * Cada Instrumento de Beneficio (de convocatoria o ventanilla) define la Matriz de presupuesto 
 * a ser utilizada por los proyectos presentados bajo este instrumento.  
 * Luego, los proyectos presentan un presupuesto, y siempre tienen un presupuesto vigente. 
*/
public interface MatrizPresupuestoServicio {
	/**
	 * Listado de los diferentes tipos de Matrices de presupuesto. 
	 * @return
	 */
	public Collection getMatricesPresupuesto();

	/**
	 * Almacena una nueva Matriz de Presupuesto.
	 * @param matrizBean
	 * @return
	 */
	public MatrizPresupuestoBean storeMatrizPresupuesto(MatrizPresupuestoBean matrizBean);

	/**
	 * Obtiene los datos de una matriz de Presupuesto en función de un identificador.
	 * @param id
	 * @return
	 */
	public MatrizPresupuestoBean getMatrizPresupuestoById(String id);

	/**
	 * Actualiza los datos de una matriz de Presupuesto.
	 * @param matrizBean
	 * @return
	 */
	public MatrizPresupuestoBean updateMatrizPresupuesto(MatrizPresupuestoBean matrizBean);

	/**
	 * Elimina una Matriz de presupuesto.
	 * @param matrizBean
	 */
	public void deleteMatrizPresupuesto(MatrizPresupuestoBean matrizBean);

}
