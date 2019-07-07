package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.JurisdiccionBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * @author gboaglio
 */
public interface JurisdiccionDAO extends GenericDao<JurisdiccionBean, Long> {
	
	public List<JurisdiccionBean> findByCodigo(String codigo);

	public List<JurisdiccionBean> findByCodigoId(String codigo, Long idJurisdiccion);
	
}
