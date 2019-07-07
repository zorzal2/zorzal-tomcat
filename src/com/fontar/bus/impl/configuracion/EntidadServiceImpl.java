package com.fontar.bus.impl.configuracion;

import java.util.List;

import com.fontar.data.api.dao.ProyectoDatosDAO;
import com.fontar.data.impl.domain.bean.EntidadBean;
import com.fontar.util.ResourceManager;
import com.pragma.bus.impl.GenericABMServiceImpl;
import com.pragma.web.WebContextUtil;

/**
 * 
 * @author gboaglio Implementa los metodos de CRUD de GenericServiceImpl
 */
public class EntidadServiceImpl extends GenericABMServiceImpl<EntidadBean> {

	public EntidadServiceImpl(Class<EntidadBean> type) {
		super(type);
	}

	public void delete(EntidadBean bean) {
		ProyectoDatosDAO proyectoDatosDao = (ProyectoDatosDAO) WebContextUtil.getBeanFactory().getBean("proyectoDatosDao");
		List proyectosAsociados = proyectoDatosDao.findByEntidad(bean.getId());

		// TODO: ODS dice que la entidad no puede estar asociada a una
		// Persona...
		// (ver si hay q agregar esa condición)

		if (proyectosAsociados.size() == 0) {
			bean.setActivo(false);
			super.update(bean);
		}
		else {
			throw new RuntimeException(ResourceManager.getErrorResource("app.error.eliminarEntidad"));
		}
	}
}