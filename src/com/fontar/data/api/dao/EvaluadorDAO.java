package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.pragma.data.genericdao.GenericDao;

public interface EvaluadorDAO extends GenericDao<EvaluadorBean, Long> {
	List<EvaluadorBean> findByIdDePersona(Long idPersona);
}
