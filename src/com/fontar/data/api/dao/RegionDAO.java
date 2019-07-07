package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.RegionBean;
import com.pragma.data.genericdao.GenericDao;

public interface RegionDAO extends GenericDao<RegionBean, Long> {

	public List<RegionBean> findByNombre(String nombre);

	public List<RegionBean> findByNombreID(Long idRegion, String nombre);

}
