package com.fontar.bus.api.paquete;

import java.util.Collection;
import java.util.List;

import com.fontar.data.impl.domain.dto.EvaluacionProyectoPaqueteDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;

/**
 * Servicio para el ingreso de nuevos evaluaciones a un paquete
 * @author gboaglio
 */
public interface EvaluarPaqueteServicio {
	public abstract void cargarEvaluacion(long idPaquete, Collection<EvaluacionProyectoPaqueteDTO> datosEvaluacionProyecto, String userName);
	public abstract void cargarEvaluacion(long idPaquete, String codigoActa, String observacion);

	public abstract void confirmarEvaluacion(long idPaquete, String userName, String codigoActa, String observacion);

	public abstract PaqueteDTO obtenerPaquete(Long idPaquete);
	
	/**
	 * Obtiene una lista con todos los <code>Proyectos</code> 
	 * pertenecientes a un <code>Paquete</code> que no tienen 
	 * cargada <code>Evaluacion de Paquete</code>.<br>
	 * El servicio devuelve un <code>List</code> con los 
	 * <i>códigos</i> de <code>Proyecto</code>.<br>
	 * El <code>Paquete</code> se obtiene mediante el parámetro
	 * recibido <i>idPaquete</i>.<br>
	 * @param idPaquete
	 * @return List proySinEvaluaciones
	 * @author ssanchez
	 */
	public List<String> obtenerProyectoPaqueteSinEval(Long idPaquete);
}
