package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluacionSeguimientoBean;
import com.pragma.data.genericdao.GenericDao;

public interface EvaluacionSeguimientoDAO extends GenericDao<EvaluacionSeguimientoBean, Long> {
	List<EvaluacionSeguimientoBean> findBySeguimiento(Long idSeguimiento);
	List<EvaluacionSeguimientoBean> findByProyecto(Long idProyecto);
}
