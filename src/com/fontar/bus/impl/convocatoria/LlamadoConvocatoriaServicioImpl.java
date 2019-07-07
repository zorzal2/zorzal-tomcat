package com.fontar.bus.impl.convocatoria;

import java.util.ArrayList;
import java.util.List;

import com.fontar.bus.api.convocatoria.LlamadoConvocatoriaServicio;
import com.fontar.bus.impl.LlamamdoConPresentacionesException;
import com.fontar.data.api.dao.DistribucionFinanciamientoDAO;
import com.fontar.data.api.dao.DistribucionTipoProyectoDAO;
import com.fontar.data.api.dao.LlamadoConvocatoriaDAO;
import com.fontar.data.api.dao.PresentacionConvocatoriaDAO;
import com.fontar.data.impl.domain.bean.DistribucionFinanciamientoBean;
import com.fontar.data.impl.domain.bean.DistribucionTipoProyectoBean;
import com.fontar.data.impl.domain.bean.InstrumentoVersionBean;
import com.fontar.data.impl.domain.bean.LlamadoConvocatoriaBean;
import com.fontar.data.impl.domain.bean.PresentacionConvocatoriaBean;
import com.fontar.data.impl.domain.codes.instrumentoDef.TipoInstrumentoDef;
import com.fontar.data.impl.domain.codes.llamadoConvocatoria.EstadoLlamadoConvocatoria;
import com.pragma.bus.impl.GenericServiceImpl;

/**
 * Implementacion de metodos
 * @author ssanchez
 * 
 */
public class LlamadoConvocatoriaServicioImpl extends GenericServiceImpl implements LlamadoConvocatoriaServicio {

	public LlamadoConvocatoriaServicioImpl(Class type) {
		super(type);
	}

	public void delete(Object objeto) {

		LlamadoConvocatoriaBean bean = (LlamadoConvocatoriaBean) objeto;
		// PresentacionConvocatoriaDAO presentacionesDao =
		// (PresentacionConvocatoriaDAO)WebContextUtil.getBeanFactory().getBean("presentacionConvocatoriaDao");
		PresentacionConvocatoriaDAO convocatoriaDAO = (PresentacionConvocatoriaDAO) getDao(PresentacionConvocatoriaBean.class);

		List presentaciones = convocatoriaDAO.findByInstrumentoNoAnuladas(bean.getId());

		if (presentaciones.size() == 0) {
			bean.setEstado(EstadoLlamadoConvocatoria.ANULADO);
			super.update(bean);
		}
		else {
			throw new LlamamdoConPresentacionesException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Guarda una nueva llamada a convocatoria con sus objetos relacionados
	 */
	public void storeBeans(Object... arguments) {
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) arguments[0];
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) arguments[1];
		List<DistribucionFinanciamientoBean> distribucionFinanciamientos = (List) arguments[2];
		List<DistribucionTipoProyectoBean> distribucionTipoProyectos = (List) arguments[3];

		// guardo nuevo llamado convoctoria
		saveBean(llamadoConvocatoriaBean);

		// guardo las distribuciones de financiamiento
		for (DistribucionFinanciamientoBean bean : distribucionFinanciamientos) {
			bean.setIdInstrumento(llamadoConvocatoriaBean.getId());
			saveBean(bean);
		}

		// guardo las distribuciones de tipo de proyecto
		if (distribucionTipoProyectos != null) {
			for (DistribucionTipoProyectoBean bean : distribucionTipoProyectos) {
				bean.setIdInstrumento(llamadoConvocatoriaBean.getId());
				saveBean(bean);
			}
		}

		// guardo nueva version para el llamado
		instrumentoVersionBean.setIdInstrumento(llamadoConvocatoriaBean.getId());
		saveBean(instrumentoVersionBean);

		// actualizo las referencias de los objetos relacionados
		llamadoConvocatoriaBean.setIdInstrumentoVersion(instrumentoVersionBean.getId());
		updateBean(llamadoConvocatoriaBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Modifica una llamada a convocatoria con sus objetos relacionados Por cada
	 * modificacion se crea un objeto nuevo en la version
	 */
	public void updateBeans(Object... arguments) {
		LlamadoConvocatoriaBean llamadoConvocatoriaBean = (LlamadoConvocatoriaBean) arguments[0];
		InstrumentoVersionBean instrumentoVersionBean = (InstrumentoVersionBean) arguments[1];
		List<DistribucionFinanciamientoBean> distribucionFinanciamientos = (List) arguments[2];
		List<DistribucionTipoProyectoBean> distribucionTipoProyectos = (List) arguments[3];

		DistribucionFinanciamientoDAO distribucionFinanciamientoDAO = (DistribucionFinanciamientoDAO) getDao(DistribucionFinanciamientoBean.class);
		List<DistribucionFinanciamientoBean> distribucionFinanciamientoDLT = distribucionFinanciamientoDAO.findByInstrumento(llamadoConvocatoriaBean.getId());
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
				bean.setIdInstrumento(llamadoConvocatoriaBean.getId());
				saveBean(bean);
			}
		}

		DistribucionTipoProyectoDAO tipoProyectoDAO = (DistribucionTipoProyectoDAO) getDao(DistribucionTipoProyectoBean.class);
		List<DistribucionTipoProyectoBean> distribucionProyectosDLT = tipoProyectoDAO.findByInstrumento(llamadoConvocatoriaBean.getId());
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
				bean.setIdInstrumento(llamadoConvocatoriaBean.getId());
				saveBean(bean);
			}
		}

		// guardo nueva version para el llamado
		saveBean(instrumentoVersionBean);

		// actualizo las referencias de los objetos relacionados
		llamadoConvocatoriaBean.setIdInstrumentoVersion(instrumentoVersionBean.getId());
		updateBean(llamadoConvocatoriaBean);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.fontar.bus.impl.convocatoria.llamados.AdministrarLlamadosConvocatoriaServicio#getLlamadosConvocatorias()
	 */
	public List<LlamadoConvocatoriaBean> getLlamadosConvocatorias() {

		LlamadoConvocatoriaDAO llamadoConvocatoriaDao = (LlamadoConvocatoriaDAO) getDao(LlamadoConvocatoriaBean.class);
		List<LlamadoConvocatoriaBean> list = llamadoConvocatoriaDao.findAllActivosOrdenados();

		return list;
	}
	
	/**
	 * Obtiene el llamado a convocatoria correspondiente
	 * al parámetro <i>idLlamado</i>.<br>
	 * @param idLlamado
	 * @return el llamado a convocatoria correspondiente a <i>idLlamado</i>
	 */
	public LlamadoConvocatoriaBean obtenerLlamadoConvocatoria(Long idLlamado) {
		LlamadoConvocatoriaDAO llamadoConvocatoriaDao = (LlamadoConvocatoriaDAO) getDao(LlamadoConvocatoriaBean.class);
		return llamadoConvocatoriaDao.read(idLlamado);
	}

	public List<LlamadoConvocatoriaBean> getLlamadosConvocatoriasDeCredito() {
		LlamadoConvocatoriaDAO llamadoConvocatoriaDao = (LlamadoConvocatoriaDAO) getDao(LlamadoConvocatoriaBean.class);
		List<LlamadoConvocatoriaBean> list = llamadoConvocatoriaDao.findAllActivosOrdenados();
		List<LlamadoConvocatoriaBean> ret = new ArrayList<LlamadoConvocatoriaBean>();
		for(LlamadoConvocatoriaBean llamado : list) {
			if(llamado.getInstrumentoDef().getTipo().equals(TipoInstrumentoDef.CREDITO)) {
				ret.add(llamado);
			}
		}
		return ret;		
	}
}
