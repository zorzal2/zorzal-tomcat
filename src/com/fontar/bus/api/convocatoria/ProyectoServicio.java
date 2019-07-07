package com.fontar.bus.api.convocatoria;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.pragma.bus.api.GenericService;

/**
 * Serivicios basicos para la administracion de proyectos.
 * 
 */
public interface ProyectoServicio extends GenericService {

	/**
	 * Servicio para la creacion de un nuevo proyecto a partir de un proyecto Bean.
	 * @param proyectoBean
	 */
	public void createProyecto(ProyectoBean proyectoBean);
}
