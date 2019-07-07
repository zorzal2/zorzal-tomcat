package com.fontar.bus.impl.proyecto;

import com.fontar.bus.api.proyecto.AdministrarProyectoPitecServicio;
import com.fontar.data.impl.domain.bean.ProyectoPitecBean;
import com.fontar.data.impl.domain.dto.ProyectoEdicionDTO;


/**
 * Servicio para administrar las acciones de un Proyecto Pitec
 * @author gboaglio
 * 
 */
public class AdministrarProyectoPitecServicioImpl extends AdministrarProyectoServicioImpl implements AdministrarProyectoPitecServicio  {

	/**
	 * Carga un proyectoPitec
	 */
	@Override	
	public Long cargarProyecto(ProyectoEdicionDTO datos, boolean vieneDePresentacion, Long idInstrumento, Long idPresentacion, Long idProyectoPitec) {					
		return cargarPresupuestable(datos, vieneDePresentacion, idInstrumento, idPresentacion, idProyectoPitec, new ProyectoPitecBean());		
	}
	
}




