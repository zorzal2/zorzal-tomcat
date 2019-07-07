package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.pragma.data.genericdao.GenericDao;

/**
 * 
 * @author ssanchez
 * @deprecated
 */

public interface IdeasProyectoDAO extends GenericDao<IdeaProyectoBean, Long> {

	public Collection getIdeasProyecto();

	public IdeaProyectoBean storeIdeaProyecto(IdeaProyectoBean idea);

	public IdeaProyectoBean getIdeaProyectoById(String id);

	public IdeaProyectoBean updateIdeaProyecto(IdeaProyectoBean ideaProyectoBean);

	public void deleteIdeaProyecto(IdeaProyectoBean ideaProyectoBean);
}
