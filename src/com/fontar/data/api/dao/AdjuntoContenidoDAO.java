package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.AdjuntoContenidoBean;
import com.pragma.data.genericdao.GenericDao;

public interface AdjuntoContenidoDAO extends GenericDao<AdjuntoContenidoBean, Long> {

	Collection getAdjuntoContenido();

	AdjuntoContenidoBean storeAdjuntoContenido(AdjuntoContenidoBean adjuntoContenido);

	AdjuntoContenidoBean getAdjuntoContenidoById(Long id);

}
