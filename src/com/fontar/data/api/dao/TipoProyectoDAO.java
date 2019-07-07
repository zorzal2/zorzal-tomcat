package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.TipoProyectoBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ttoth
 * 
 */
public interface TipoProyectoDAO extends GenericDao<TipoProyectoBean, Long> {

	public List<TipoProyectoBean> findByNombre(String nombre);

	public List<TipoProyectoBean> findByNombreCorto(String nombreCorto);

	public List<TipoProyectoBean> findByNombreTipo(Long idTipoProyecto, String nombre);

	public List<TipoProyectoBean> findByNombreCortoId(Long idTipoProyecto, String nombreCorto);

}
