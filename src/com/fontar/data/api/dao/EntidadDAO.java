package com.fontar.data.api.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fontar.data.impl.domain.bean.EntidadBean;
import com.pragma.data.genericdao.GenericDao;

public interface EntidadDAO extends GenericDao<EntidadBean, Long> {

	
	public List<EntidadBean> findByCuit(String cuit);

	public List<EntidadBean> findByName(String name);

	public List<EntidadBean> findByCuitEntidad(Long idEntidad, String cuit);
	
	/**
	 * Devuelve un Map:Rol->Coleccion de objetos, que incluye varios (no todos) los objetos
	 * del negocio que utilizan directa o indirectamente a la entidad dada. El rol es
	 * el que esta entidad representa para todos los objetos de la coleccion asociada. 
	 *   buscarUsosDeEntidad(Long e.id)  incluye  (r, c)  <=>
	 *   para todo o en c, "<em>o</em> conoce con el rol de <em>r</em> a <em>e</em>"
	 * @return
	 */
	public Map<Rol, Collection<Object>> buscarUsosDeEntidad(Long idEntidad);
}