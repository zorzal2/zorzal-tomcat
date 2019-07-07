package com.fontar.data.api.dao.proyecto;

import java.util.List;

import com.fontar.data.impl.domain.bean.RubroBean;
import com.pragma.data.genericdao.GenericDao;

public interface RubroDAO extends GenericDao<RubroBean, Long> {

	// Devuelve una lista con los ids de los rubros de tipo RUBRO_PADRE
	List<Long> findIdsPadres();
	
	List<RubroBean> findSinPadres();

	List<RubroBean> findSinPadresOrdenados();
	
	List<RubroBean> findByCodigo(String codigo);
}
