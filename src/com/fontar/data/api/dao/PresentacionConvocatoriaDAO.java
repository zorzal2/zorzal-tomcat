package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.pragma.data.genericdao.GenericDao;

public interface PresentacionConvocatoriaDAO extends GenericDao<PresentacionConvocatoriaBean, Long> {
	List<PresentacionConvocatoriaBean> findByEstado(String estado);

	List<PresentacionConvocatoriaBean> findByInstrumentoNoAnuladas(Long idInstrumento);

	List<PresentacionConvocatoriaBean> findByCodigo(String codigo);

	List<PresentacionConvocatoriaBean> findByCodigoId(Long idPresentacion, String codigo);
}