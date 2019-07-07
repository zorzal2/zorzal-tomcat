package com.fontar.bus.impl.proyecto;

import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;

/**
 * Servicio para administrar las acciones de una Idea Proyecto Pitec
 * @author gboaglio
 * 
 */
public class AdministrarIdeaProyectoPitecServicioImpl extends AdministrarProyectoServicioImpl {

	@Override	
	public Long cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento, Long idPresentacion, Long idProyectoPitec) {					
		return cargarPresupuestable(datos, vieneDePresentacion, idInstrumento, idPresentacion, idProyectoPitec, new IdeaProyectoPitecBean());		
	}	

}
