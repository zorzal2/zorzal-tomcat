package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.EvaluacionPaqueteBean;
import com.pragma.data.genericdao.GenericDao;

public interface EvaluacionPaqueteDAO extends GenericDao<EvaluacionPaqueteBean, Long> {
	List<EvaluacionPaqueteBean> findByProyectoPaqueteActivo(Long idProyecto, Long idPaquete);
}
