package com.fontar.bus.api.paquete;

import java.util.Collection;
import java.util.List;

import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.fontar.data.impl.domain.dto.VisualizarPaqueteDTO;

/**
 * Conjunto de servicios para la administración de los paquetes (sin tareas Workflows). 
 */
public interface AdministrarPaqueteServicio {

	/**
	 * Define un nuevo paquete a partir de un conjunto de identificadores de proyectos. 
	 * Estos proyectos deben compartir el mismo instrumento y tratamiento.
	 * @param proyectoArray conjunto de indentificadores de proyectos.
	 * @param idInstrumento Instrumento de Beneficio.
	 * @param tratamiento Tratamiento definido para el nuevo paquete (EVALUACION, RECONSIDERACION, ADJUDICACION). 
	 * @param tipoPaquete Tipo de paquete (COMISICION, SECRETARIA, DIRECTORIO)
	 * @return
	 */
	public abstract Long armarPaquete(String[] proyectoArray, Long idInstrumento, String tratamiento, String tipoPaquete);

	/**
	 * Quita del paquete dado todos los proyectos que no esten seleccionados.
	 * @param idPaquete identificador del paquete.
	 * @param proyectosSeleccionados identificadores de proyectos seleccionados.
	 */
	public abstract void modificarPaquete(Long idPaquete, String[] proyectosSeleccionados);

	/**
	 * Retorna los proyectos de un paquete (un proyecto inactivo NO pertenece a
	 * un paquete).
	 * @param idPaquete Identificador del paquete.
	 * @param tipoPaquete Tipo de paquete (COMISICION, SECRETARIA, DIRECTORIO)
	 * @return
	 */
	public abstract List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectosPaquete(Long idPaquete, String tipoPaquete);

	/**
	 * Retorna los proyectos candidatos a formar parte de un paquete, NO incluye
	 * los proyectos que estan en un paquete.
	 * @param instrumento Instrumento de Beneficio.
	 * @param tratamiento Tratamiento definido para el nuevo paquete (EVALUACION, RECONSIDERACION, ADJUDICACION).
	 * @param tipoPaquete Tipo de paquete (COMISICION, SECRETARIA, DIRECTORIO)
	 * @return
	 */
	public abstract List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectos(Long instrumento, String tratamiento,
			String tipoPaquete);

	/**
	 * Anula un paquete dejando el paquete en estado ANULADO
	 * @param idPaquete identificador del paquete.
	 * @param descripcion motivo de la anulación.
	 */
	public abstract void anularPaquete(Long idPaquete, String descripcion);

	/**
	 * Obtiene los datos completos de un Paquete
	 */
	public PaqueteBean obtenerPaquete(Long idPaquete);

	/**
	 * Obtiene los datos completos de un Paquete para la visualizacion del mismo
	 */
	public VisualizarPaqueteDTO obtenerVisualizacionPaquete(Long idPaquete);

	/**
	 * Obtiene un dto desde un bean. El dto es construido en base al assembler
	 * enviado por parametro
	 */
	public DTO getPaqueteDTO(Long idPaquete, PaqueteGeneralAssembler assembler);

	/**
	 * Obtiene el identificador de paquete a partir de un identificador de un proyecto en el paquete.
	 * @param idProyectoPaquete identificador del proyecto en el paquete.
	 * @return identificador de paquete
	 */public abstract Long getIdPaquete(Long idProyectoPaquete);
	
	 /**
	  *	Permite incorprar nuevos proyectos a un paquete. 
	  * @param proyectoArray identificadores de proyectos a incorporar.
	  * @param paqueteBean paquete a modificar.
	  */
	public void agregarProyectosAPaquete(String[] proyectoArray, PaqueteBean paqueteBean);

	/**
	 * Permite incorporar nuevos proyectos en un paquete.
	 * @param proyectos listado de proyectos a incorporar al paquete.
	 * @param paqueteBean paquete a modificar.
	 */
	public void agregarProyectosAPaquete(Collection<ProyectoBean> proyectos, PaqueteBean paqueteBean);
	
	/**
	 * Permite quitar un subconjunto de proyectos de un paquete.
	 * @param proyectoArray identificadores de proyectos a eliminar.
	 * @param paqueteBean paquete a modificar.
	 */
	public void eliminarProyectosDePaquete(String[] proyectoArray, PaqueteBean paqueteBean);
	
	/**
	 * Permite quitar un subconjunto de proyectos de un paquete. 
	 * @param proyectos proyectos a eliminar.
	 * @param paqueteBean paquete a modificar.
	 */public void eliminarProyectosDePaquete(Collection<ProyectoBean> proyectos, PaqueteBean paqueteBean);

}