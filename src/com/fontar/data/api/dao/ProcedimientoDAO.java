package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * @author ssanchez
 */
public interface ProcedimientoDAO extends GenericDao<ProcedimientoBean, Long> {
	public List<ProcedimientoBean> findByIdEvaluador(Long idEvaluador);
}
