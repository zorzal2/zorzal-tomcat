/**
 * @author gboaglio
 *
 */

package com.fontar.bus.impl.convocatoria;

import static com.fontar.data.impl.domain.codes.ventanillaPermanente.EstadoVentanillaPermanente.ANULADO;

import java.util.List;

import com.fontar.data.api.dao.DistribucionFinanciamientoDAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.InstrumentoVersionBean;
import com.fontar.data.impl.domain.bean.VentanillaPermanenteBean;
import com.fontar.util.ResourceManager;
import com.pragma.bus.BusinessException;
import com.pragma.bus.impl.GenericServiceImpl;
import com.pragma.web.WebContextUtil;

public class VentanillaPermanenteServicioImpl extends GenericServiceImpl {

	public VentanillaPermanenteServicioImpl(Class type) {
		super(type);
	}

	@Override
	public void delete(Object objeto) {

		VentanillaPermanenteBean bean = (VentanillaPermanenteBean) objeto;
		ProyectoDAO proyectoDao = (ProyectoDAO) WebContextUtil.getBeanFactory().getBean("proyectoDao");
		List ideasProyecto = proyectoDao.findByInstrumentoNoAnuladas(bean.getId());

		if (ideasProyecto.size() == 0) {
			bean.setEstado(ANULADO);
			super.update(bean);
		}
		else {
			throw new BusinessException(ResourceManager.getErrorResource("app.error.eliminarVentanilla"));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Guarda una nueva llamada a convocatoria con sus objetos relacionados
	 */
	public void storeBeans(Object... arguments) {
		VentanillaPermanenteBean ventanillaPermanenteBean = (VentanillaPermanenteBean) arguments[0];
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) arguments[1];
		List<DistribucionFinanciamientoBean> distribucionFinanciamientos = (List) arguments[2];
		List<DistribucionTipoProyectoBean> distribucionTipoProyectos = (List) arguments[3];

		// guardo una nuevo ventanilla permanente
		saveBean(ventanillaPermanenteBean);

		// guardo las distribuciones de financiamiento
		for (DistribucionFinanciamientoBean bean : distribucionFinanciamientos) {
			bean.setIdInstrumento(ventanillaPermanenteBean.getId());
			saveBean(bean);
		}

		// guardo las distribuciones de tipo de proyecto
		if (distribucionTipoProyectos != null) {
			for (DistribucionTipoProyectoBean bean : distribucionTipoProyectos) {
				bean.setIdInstrumento(ventanillaPermanenteBean.getId());
				saveBean(bean);
			}
		}

		// guardo nueva version para la ventanilla
		instrumentoVersionBean.setIdInstrumento(ventanillaPermanenteBean.getId());
		saveBean(instrumentoVersionBean);

		// actualizo las referencias de los objetos relacionados
		ventanillaPermanenteBean.setIdInstrumentoVersion(instrumentoVersionBean.getId());
		updateBean(ventanillaPermanenteBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Modifica una llamada a convocatoria con sus objetos relacionados Por cada
	 * modificacion se crea un objeto nuevo en la version
	 */
	public void updateBeans(Object... arguments) {
		VentanillaPermanenteBean ventanillaPermanenteBean = (VentanillaPermanenteBean) arguments[0];
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) arguments[1];
		List<DistribucionFinanciamientoBean> distribucionFinanciamientos = (List) arguments[2];
		List<DistribucionTipoProyectoBean> distribucionTipoProyectos = (List) arguments[3];

		DistribucionFinanciamientoDAO distribucionFinanciamientoDAO = (DistribucionFinanciamientoDAO) getDao(DistribucionFinanciamientoBean.class);
		List<DistribucionFinanciamientoBean> distribucionFinanciamientoDLT = distribucionFinanciamientoDAO.findByInstrumento(ventanillaPermanenteBean.getId());
		// elimino todas las distribuciones de tipo de proyecto para luego
		// actualizarla con las nuevas
		if (distribucionFinanciamientoDLT != null) {
			for (DistribucionFinanciamientoBean bean : distribucionFinanciamientoDLT) {
				deleteBean(bean);
			}
		}

		// guardo las distribuciones de financiamiento
		if (distribucionFinanciamientos != null) {
			for (DistribucionFinanciamientoBean bean : distribucionFinanciamientos) {
				bean.setIdInstrumento(ventanillaPermanenteBean.getId());
				saveBean(bean);
			}
		}

		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) getDao(DistribucionTipoProyectoBean.class);
		List<DistribucionTipoProyectoBean> distribucionProyectosDLT = tipoProyectoDAO.findByInstrumento(ventanillaPermanenteBean.getId());
		// elimino todas las distribuciones de tipo de proyecto para luego
		// actualizarla con las nuevas
		if (distribucionProyectosDLT != null) {
			for (DistribucionTipoProyectoBean bean : distribucionProyectosDLT) {
				deleteBean(bean);
			}
		}

		// guardo las distribuciones de tipo de proyecto
		if (distribucionTipoProyectos != null) {
			for (DistribucionTipoProyectoBean bean : distribucionTipoProyectos) {
				bean.setIdInstrumento(ventanillaPermanenteBean.getId());
				saveBean(bean);
			}
		}

		// guardo nueva version para la ventanilla
		saveBean(instrumentoVersionBean);

		// actualizo las referencias de los objetos relacionados
		ventanillaPermanenteBean.setIdInstrumentoVersion(instrumentoVersionBean.getId());
		updateBean(ventanillaPermanenteBean);
	}
}
