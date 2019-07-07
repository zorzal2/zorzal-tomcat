package com.fontar.bus.impl.convocatoria.presentacion;

import java.util.List;

import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.codes.presentacionConvocatoria.EstadoPresentacion;
import com.fontar.util.ResourceManager;
import com.pragma.bus.impl.GenericABMServiceImpl;
import com.pragma.web.WebContextUtil;

/**
 * 
 * @author gboaglio, ssanchez
 * @version 1.01, 14/12/06
 * 
 */
public class PresentacionConvocatoriaServicioImpl extends GenericABMServiceImpl<PresentacionConvocatoriaBean> {

	public PresentacionConvocatoriaServicioImpl(Class<PresentacionConvocatoriaBean> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.convocatoria.presentacion.PresentacionConvocatoriaServicio#delete(com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean)
	 */
	@Override
	public void delete(PresentacionConvocatoriaBean bean) {

		ProyectoDAO proyectosDao = (ProyectoDAO) WebContextUtil.getBeanFactory().getBean("proyectoDao");
		List proyectos = proyectosDao.findByPresentacionNoAnulados(bean.getId());

		if (proyectos.size() == 0) {
			bean.setEstado(EstadoPresentacion.ANULADA);
			super.update(bean);
		}
		else {
			throw new RuntimeException(ResourceManager.getErrorResource("app.error.eliminarPresentacion"));
		}
	}
}
