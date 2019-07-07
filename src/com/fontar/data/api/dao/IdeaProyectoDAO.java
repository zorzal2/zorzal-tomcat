package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ssanchez
 * 
 */
public interface IdeaProyectoDAO extends GenericDao<IdeaProyectoBean, Long> {
	List<IdeaProyectoBean> findByCodigo(String codigo);

	Long selectLastAvailableCode();
}
