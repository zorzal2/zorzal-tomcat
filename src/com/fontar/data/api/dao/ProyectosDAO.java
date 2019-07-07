package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.ProyectoBean;

/**
 * 
 * @author ssanchez
 * 
 */
public interface ProyectosDAO {

	public Collection getProyectos();

	public ProyectoBean storeProyecto(ProyectoBean proyectoBean);

	public ProyectoBean updateProyecto(ProyectoBean proyectoBean);

	public void deleteProyecto(ProyectoBean proyectoBean);

	public ProyectoBean getProyectoById(String id);

}
