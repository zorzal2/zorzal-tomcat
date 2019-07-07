package com.fontar.bus.impl.configuracion;

import java.util.Collection;

import com.fontar.bus.api.configuracion.InstitucionServicio;
import com.fontar.data.api.dao.InstitucionesDAO;
import com.fontar.data.impl.domain.bean.EntidadBean;

public class InstitucionServicioImpl implements InstitucionServicio {

	InstitucionesDAO institucionDao;

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#setInstitucionDao(com.fontar.data.api.dao.InstitucionesDAO)
	 */
	public void setInstitucionDao(InstitucionesDAO institucionDao) {
		this.institucionDao = institucionDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#getInstituciones()
	 */
	public Collection getInstituciones() {
		return institucionDao.getInstituciones();
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#storeInstitucion(com.fontar.data.impl.domain.bean.InstitucionBean)
	 */
	public EntidadBean storeInstitucion(EntidadBean nuevaEntidad) {
		EntidadBean entidad = this.institucionDao.storeInstitucion(nuevaEntidad);
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#getInstitucionById(java.lang.String)
	 */
	public EntidadBean getInstitucionById(String id) {
		return institucionDao.getInstitucionById(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#updateInstitucion(com.fontar.data.impl.domain.bean.InstitucionBean)
	 */
	public EntidadBean updateInstitucion(EntidadBean institucionBean) {
		EntidadBean entidad = this.institucionDao.updateInstitucion(institucionBean);
		return entidad;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.configuracion.InstitucionServicio#deleteInstitucion(com.fontar.data.impl.domain.bean.InstitucionBean)
	 */
	public void deleteInstitucion(EntidadBean institucionBean) {
		institucionDao.deleteInstitucion(institucionBean);
	}
}
