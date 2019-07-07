package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.pragma.data.genericdao.GenericDao;

public interface EntidadEvaluadoraDAO extends GenericDao<EntidadEvaluadoraBean, Long> {

	public List<EntidadEvaluadoraBean> findByEvaluador(EvaluadorBean evaluador);
}
