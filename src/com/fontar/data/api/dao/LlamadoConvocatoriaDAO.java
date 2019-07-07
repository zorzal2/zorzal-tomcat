package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author gboaglio
 * 
 */
public interface LlamadoConvocatoriaDAO extends GenericDao<LlamadoConvocatoriaBean, Long> {

	List<LlamadoConvocatoriaBean> findByEstado(String estado);

	List<LlamadoConvocatoriaBean> findAllActivosOrdenados();

	List<LlamadoConvocatoriaBean> findByIdentificador(String identificador);

	List<LlamadoConvocatoriaBean> findByIdentificadorId(Long idLlamado, String identificador);

}
