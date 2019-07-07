package com.fontar.bus.api.convocatoria;

import java.util.List;

import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.pragma.bus.api.GenericService;

/**
 * Servicios para la administración de instrumentos de beneficio de Llamados 
 * a Convocatoria. 
 */
public interface LlamadoConvocatoriaServicio extends GenericService {

	/**
	 * Obtiene una lista de instrumentos de llamados a convocatoria activos y ordenados
	 */
	public abstract List<LlamadoConvocatoriaBean> getLlamadosConvocatorias();

	/**
	 * Obtiene el llamado a convocatoria correspondiente
	 * al parámetro <i>idLlamado</i>.<br>
	 * @param idLlamado
	 * @return el llamado a convocatoria correspondiente a <i>idLlamado</i>
	 */
	public LlamadoConvocatoriaBean obtenerLlamadoConvocatoria(Long idLlamado);
	
	/**
	 * Devuelve una lista de llamados a convocatorias ordenados por identificador
	 * solo de crédito.
	 */
	public List<LlamadoConvocatoriaBean> getLlamadosConvocatoriasDeCredito();
}
