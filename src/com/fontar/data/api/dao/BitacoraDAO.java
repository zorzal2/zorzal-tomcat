package com.fontar.data.api.dao;

import java.util.List;

import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.pragma.data.genericdao.GenericDao;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

@HasNamedParams
public interface BitacoraDAO extends GenericDao<BitacoraBean, Long> {

	List<ProyectoBean> findByProyecto(Long idProyecto);
	List<BitacoraBean> findByProyectoTipo(@ParamName("idProyecto") Long idProyecto, @ParamName("tipo") String tipo);
	List<BitacoraBean> findBySeguimiento(Long idSeguimiento);
	List<BitacoraBean> findBySeguimientoTipo(@ParamName("idSeguimiento") Long idSeguimiento, @ParamName("tipo") String tipo);

}
