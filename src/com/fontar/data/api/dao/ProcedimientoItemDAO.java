package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * @author ssanchez
 */
public interface ProcedimientoItemDAO extends GenericDao<ProcedimientoItemBean, Long> {
	
	List<ProcedimientoItemBean> findByProcedimiento(Long idProcedimiento);
	
	List<ProcedimientoItemBean> findByProcedimientoResultadoFontar(Long idProcedimiento, String resultadoFontar);
	
	List<ProcedimientoItemBean> findByProcedimientoConResultadoPliego(Long idProcedimiento);
	
	List<ProcedimientoItemBean> findByPac(Long idPacItem);
}
