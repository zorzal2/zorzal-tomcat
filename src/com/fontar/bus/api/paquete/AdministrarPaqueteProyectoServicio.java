package com.fontar.bus.api.paquete;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.EvaluarResultadoProyectoDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;

/**
 *Servicios relacionados con la administracion de paquetes de proyectos. 
 */
public interface AdministrarPaqueteProyectoServicio {
	
	/**
	 * Determina si en paqueteDto el proyectoBean tiene o no dictamen.
	 * Una evaluacion de paquete con dictamen se define cuando al tratar el paquete deciden  
	 * realizar una nueva evaluacion del proyeto (por ejemplo, Economica o Tecnica). 
	 * @param proyectoBean
	 * @param paqueteDto
	 * @return
	 */
	public boolean tieneDictamen(ProyectoBean proyectoBean, PaqueteDTO paqueteDto);
	
	/**
	 * Evalua si en el paquete en el que se encuentra el proyecto hay que agregar la evaluación de 'No Elegida'.
	 * Si el paquete en el que se encuentra el proyeto es posterior al paquete que la rechazo entonces no se debe agregar. 
	 * @param proyectoBean
	 * @param paqueteDto
	 * @return
	 */
	public Collection<EvaluarResultadoProyectoDTO> obtenerEvaluacionesElegibles(ProyectoBean proyectoBean, PaqueteDTO paqueteDto);

	/**
	 * Obtiene las evaluaciones del proyeto con Dictamen en un paquete dado (identificador de paquete). 
	 * @param proyectoBean
	 * @param idPaquete
	 * @return
	 */
	public Collection<EvaluarResultadoProyectoDTO> obtenerEvaluacionesDictamen(ProyectoBean proyectoBean, long idPaquete);
	
	/**
	 * Obtiene las evaluaciones del proyeto con Dictamen en un paquete dado (paquete DTO). 
	 * @param proyectoBean
	 * @param paqueteDto
	 * @return
	 */
	public Collection<EvaluarResultadoProyectoDTO> obtenerEvaluacionesDictamen(ProyectoBean proyectoBean, PaqueteDTO paqueteDto);
	
	/**
	 * Obtiene todas las 'evaluaciones de paquete' para un proyectoBean dado.
	 * @param proyectoBean
	 * @return
	 */
	public Collection<EvaluarResultadoProyectoDTO> obtenerEvaluacionesPaquete(ProyectoBean proyectoBean);
	
	/**
	 * Obtiene todos los identificadores de evaluaciones a partir de un conjunto de Resultados de evaluacion de proyectos.
	 * @param evaluacionesList
	 * @return
	 */
	public Long[] obtenerIdEvaluaciones(Collection<EvaluarResultadoProyectoDTO> evaluacionesList);
	
	/**
	 * Modifica la lista de evaluaciones (evaluacionesList) definiendo como elegibles 
	 * aquellas cuyo identificador se encuentra en informadas en elegibleArray.  
	 * @param elegibleArray
	 * @param evaluacionesList
	 */
	 public void cambiarElegible(String[] elegibleArray, Collection evaluacionesList);
	
	/**
	 * Modifica la lista de evaluaciones (evaluacionesList) definiendo como elegibles 
	 * aquellas cuyo identificador se encuentra en informadas en idEvaluacionesNoElegibles.
	 * @param idEvaluacionesNoElegibles
	 * @param evaluacionesList
	 */
	 public void cambiarNoElegible(Long[] idEvaluacionesNoElegibles, Collection evaluacionesList);
}