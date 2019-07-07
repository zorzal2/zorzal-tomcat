package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluacionEvaluadorBean;
import com.pragma.data.genericdao.GenericDao;

public interface EvaluacionEvaluadorDAO extends GenericDao<EvaluacionEvaluadorBean, Long> {

	List<EvaluacionEvaluadorBean> findByEvaluacion(Long id);

}
