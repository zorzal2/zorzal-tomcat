package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.MatrizCriterioItemBean;
import com.pragma.data.genericdao.GenericDao;

public interface MatrizCriterioItemDAO extends GenericDao<MatrizCriterioItemBean, Long> {
	
	// Devuelve una lista de todos los Criterios (son los items con idItemPadre == null)	
	public List<MatrizCriterioItemBean> findCriterios();
	
	// TODO: GB / Puede ser útil a futuro
	// Devuelve una lista de todas las categorías para un criterio
	// public List<MatrizCriterioItemBean> findCategoriasByCriterio(Long idCriterioPadre);

}





