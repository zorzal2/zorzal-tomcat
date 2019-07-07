package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoEvaluacionCriterioBean;
import com.pragma.data.genericdao.GenericDao;

public interface ProyectoEvaluacionCriterioDAO extends GenericDao<ProyectoEvaluacionCriterioBean, Long> {
	
	List<ProyectoEvaluacionCriterioBean> findByEvaluacion(Long idEvaluacion);
}
