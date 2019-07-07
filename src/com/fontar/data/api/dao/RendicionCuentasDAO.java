package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.RendicionCuentasBean;
import com.pragma.data.genericdao.GenericDao;

/** 
 * @author gboaglio
 */
public interface RendicionCuentasDAO extends GenericDao<RendicionCuentasBean, Long> {
	
	public List<RendicionCuentasBean> findRendicionesBeanPorSeguimiento(Long idSeguimiento);
	
	public List<RendicionCuentasBean> findRendicionesBeanPorProyecto(Long idProyecto);
	
	public List<RendicionCuentasBean> findRendicionesBeanPorPersona(Long idPersona);
}
