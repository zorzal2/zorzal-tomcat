package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ComisionBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ssanchez
 * 
 */
public interface ComisionDAO extends GenericDao<ComisionBean, Long> {

	public List<ComisionBean> findByDenominacion(String denominacion);

	public List<ComisionBean> findByDenominacionId(Long idComision, String denominacion);

}
