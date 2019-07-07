package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.MatrizCriterioBean;
import com.pragma.data.genericdao.GenericDao;

public interface MatrizCriterioDAO extends GenericDao<MatrizCriterioBean, Long> {
	public List<MatrizCriterioBean> findByNombre(String nombre);
	
}
