package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.pragma.data.genericdao.GenericDao;

public interface ProyectoDatosDAO extends GenericDao<ProyectoDatosBean, Long> {
	List<ProyectoDatosBean> findByEntidad(Long idEntidad);
}
