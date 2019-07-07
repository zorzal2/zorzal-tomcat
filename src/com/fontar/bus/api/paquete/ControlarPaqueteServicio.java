package com.fontar.bus.api.paquete;

/**
 * Servicio para el ingreso de control de un paquete  
 */
public interface ControlarPaqueteServicio {
	/**
	 * Registra la accion de controlar un paquete de proyectos.  
	 * @param idPaquete
	 * @param proyectosSeleccionados
	 */
	public abstract void controlarPaquete(Long idPaquete, String[] proyectosSeleccionados);

}
