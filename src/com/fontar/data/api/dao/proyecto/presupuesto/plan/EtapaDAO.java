package com.fontar.data.api.dao.proyecto.presupuesto.plan;

import java.util.List;

import com.fontar.data.impl.domain.bean.proyecto.presupuesto.plan.EtapaBean;
import com.pragma.data.genericdao.GenericDao;

public interface EtapaDAO extends GenericDao<EtapaBean, Long> {

	List<EtapaBean> findByPresupuesto(Long idPresupuesto);

	List<String> findByNombres(Long idPresupuesto);

}
