package com.fontar.bus.api.configuracion;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.EvaluadorBean;

/**
 * Servicios para la administración de personas que actuan como evaluadores. 
 * 
 */

public interface EvaluadorServicio {
	
	/**
	 * Obtiene todos los evaluadores registrados.
	 * @return
	 */
	public Collection getEvaluadores();

	/**
	 * Registra una nueva persona evaluadora.
	 * @param evaluador
	 * @return
	 */
	public EvaluadorBean storeEvaluador(EvaluadorBean evaluador);

	/**
	 * Obtiene los datos de un evaluador en funcion del identificador del mismo.
	 * @param id identificador del evaluador
	 * @return
	 */
	public EvaluadorBean getEvaluadorById(String id);

	/**
	 * Actualiza los datos de un evaluador.
	 * @param evaluadorBean
	 * @return
	 */
	public EvaluadorBean updateEvaluador(EvaluadorBean evaluadorBean);

	/**
	 * Suprime del sistema una persona registrada como evaluadora.
	 * @param evaluadorBean
	 */
	public void deleteEvaluador(EvaluadorBean evaluadorBean);
}
