package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.pragma.data.genericdao.GenericDao;

public interface EvaluacionDAO extends GenericDao<EvaluacionBean, Long> {

	List<EvaluacionBean> findByProyecto(Long idProyecto);
}
