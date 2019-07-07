package com.fontar.data.api.dao;

import java.util.Collection;

import com.fontar.data.impl.domain.bean.EntidadBean;

public interface InstitucionesDAO {

	public abstract Collection getInstituciones();

	public abstract EntidadBean storeInstitucion(EntidadBean institucion);

	public abstract EntidadBean getInstitucionById(String id);

	public abstract EntidadBean updateInstitucion(EntidadBean institucion);

	public abstract void deleteInstitucion(EntidadBean institucion);

}