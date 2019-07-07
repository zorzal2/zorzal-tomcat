package com.fontar.bus.api.bitacora;

import java.util.Date;

import com.fontar.data.api.assembler.BitacoraDTOAssembler;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.CompositeBitacoraDTO;
/**
 * La bitacora es la historia de eventos en relación a un determinado Proyecto.
 * En la bitacora de un proyecto puede haber dos tipos de eventos: los registrados en forma manual por el usuario y los registrados en forma integrada por el sistema (por ejemplo, mediante la accion admitir proyecto, asignar evaluación, etc).   
 * Mediante estos servicios se puede administrar acceder al registro de todos los eventos y administrar los eventos de registración manual. 
 */
public interface AdministrarBitacoraServicio {

	/**
	 * Obtiene la historia de eventos registrada para un determinado proyecto. 
	 * @param proyectoId
	 * @return Lista de eventos del proyecto
	 */
	CompositeBitacoraDTO getBitacora(Long proyectoId, BitacoraDTOAssembler assembler);
	
	/**
	 * Obtiene el registro completo de una Bitacora en funcion de un identificador de Bitacora.
	 * @param idBitacora
	 * @return
	 */BitacoraDTO obtenerBitacora(Long idBitacora);
	
	/**
	 * Registra un nuevo evento de la bitacora de un proyecto.
	 * @param idProyecto es el proyecto al cual corresponde el evento 
	 * @param tema es una clasificacion temática del evento.  
	 * @param fechaAsunto corresponde a la fecha de ocurrencia del evento. 
	 * @param descripcion es una breve descripción del evento.
	 */
	 void cargarBitacora(Long idProyecto, String tema, Date fechaAsunto ,String descripcion);
	
	 /**
	  * ACtualiza los datos de un evento de la bitacora de un proyecto.
	  * @param idBitacora es el registro de la bitacora a actualizar.
	  * @param tema es una clasificacion temática del evento.  
	  * @param fechaAsunto corresponde a la fecha de ocurrencia del evento. 
	  * @param descripcion es una breve descripción del evento.
	  */void actualizarBitacora(Long idBitacora, String tema,Date fechaAsunto, String descripcion);
	  
	  /**
	   * Permite suprimir un regisro de la bitacora, siempre que el mismo no sea un registro automático (admisión de proyecto, asignación de evaluacion, etc).     
	   * @param idBitacora
	   */
	void eliminarBitacoraManual(Long idBitacora);
}