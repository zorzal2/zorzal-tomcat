package com.fontar.bus.api.ventanilla;

import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;

/**
 * Servicios para la administracion de instrumentos de Ventanilla Permanente.
 */
public interface AdministrarVentanillaPermanenteServicio {

	/**
	 * Obtiene el llamado a convocatoria correspondiente
	 * al parámetro <i>idLlamado</i>.<br>
	 * @param idLlamado
	 * @return el llamado a convocatoria correspondiente a <i>idLlamado</i>
	 */
	public VentanillaPermanenteBean obtenerLlamadoConvocatoria(Long idLlamado);
}