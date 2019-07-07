package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.ProyectoDesembolsoBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ttoth
 * 
 */
public interface ProyectoDesembolsoDAO extends GenericDao<ProyectoDesembolsoBean, Long> {

	List<ProyectoDesembolsoBean> findByProyecto(Long idProyecto);

	Object selectMontoPago(Long idProyecto);

}
