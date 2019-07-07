package com.fontar.bus.impl.ventanilla;

import java.util.List;

import com.fontar.bus.api.ventanilla.IdeaProyectoService;
import com.fontar.data.api.dao.EvaluacionDAO;
import com.fontar.data.api.dao.IdeaProyectoDAO;
import com.fontar.data.impl.domain.bean.BitacoraBean;
import com.fontar.data.impl.domain.bean.EvaluacionBean;
import com.fontar.data.impl.domain.bean.IdeaProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoDatosBean;
import com.fontar.data.impl.domain.bean.ProyectoJurisdiccionBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.pragma.bus.impl.GenericServiceImpl;

/**
 * Implementa los metodos de CRUD para IdeaProyectoBean
 * @author ssanchez
 * 
 */
public class IdeaProyectoServiceImpl extends GenericServiceImpl implements IdeaProyectoService {

	/**
	 * Constructor
	 * @param type
	 */
	public IdeaProyectoServiceImpl(Class type) {
		super(type);
	}

	/**
	 * Guarda una nueva Idea Proyecto y sus objetos relacionados
	 */
	public void storeBeans(Object... arguments) {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) arguments[0];
		ProyectoDatosBean datosBean = (ProyectoDatosBean) arguments[1];
		ProyectoPresupuestoBean presupuestoBean = (ProyectoPresupuestoBean) arguments[2];
		ProyectoJurisdiccionBean jurisdiccionBean = (ProyectoJurisdiccionBean) arguments[3];
		BitacoraBean bitacoraIdeaProy = (BitacoraBean) arguments[4];

		Long codigoIdeaProyecto = ((IdeaProyectoDAO) getDao(IdeaProyectoBean.class)).selectLastAvailableCode();
		ideaProyectoBean.setCodigoIdeaProyecto(codigoIdeaProyecto);
		// guardo nuevo proyecto
		saveBean(ideaProyectoBean);

		// guardo la bitacora de la Idea Proyecto
		bitacoraIdeaProy.setIdProyecto(ideaProyectoBean.getId());
		saveBean(bitacoraIdeaProy);

		// guardo nuevo proyectoDatos
		datosBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(datosBean);

		// guardo relación entre un proyecto y una jurisdicción
		presupuestoBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(presupuestoBean);

		// guardo relación entre un proyecto y una jurisdicción
		jurisdiccionBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(jurisdiccionBean);

		// actualizo las referencias de el objeto Proyecto a sus objetos
		// relacionados
		// TODO: SS-el codigo debe generarse automaticamete, este puede ser una
		// forma de implementarlo ?
		ideaProyectoBean.setCodigo(ideaProyectoBean.getId().toString());
		jurisdiccionBean.setCodigo(ideaProyectoBean.getCodigo());
		ideaProyectoBean.setIdDatos(datosBean.getId());
		ideaProyectoBean.setIdPresupuestoOriginal(presupuestoBean.getId());
		ideaProyectoBean.setIdProyectoJurisdiccion(jurisdiccionBean.getId());
		updateBean(jurisdiccionBean);
		updateBean(ideaProyectoBean);
	}

	/**
	 * Modifica una Idea Proyecto y sus objetos relacionados. Los objetos
	 * relacionados no se modifican, se crean nuevas instancias y se generan
	 * nuevas bitacoras para cada uno
	 */
	public void updateBeans(Object... arguments) {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) arguments[0];
		ProyectoDatosBean datosBean = (ProyectoDatosBean) arguments[1];
		ProyectoPresupuestoBean presupuestoBean = (ProyectoPresupuestoBean) arguments[2];
		ProyectoJurisdiccionBean jurisdiccionBean = (ProyectoJurisdiccionBean) arguments[3];

		// Modifico los datos el proyecto
		updateBean(ideaProyectoBean);

		// Agrego datos del proyecto
		datosBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(datosBean);

		// Agrego una relación entre un proyecto y una jurisdicción
		presupuestoBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(presupuestoBean);

		// Agrego una relación entre un proyecto y una jurisdicción
		jurisdiccionBean.getBitacora().setIdProyecto(ideaProyectoBean.getId());
		saveBean(jurisdiccionBean);

		// Actualizo el proyecto
		ideaProyectoBean.setIdDatos(datosBean.getId());
		ideaProyectoBean.setIdPresupuestoOriginal(presupuestoBean.getId());
		ideaProyectoBean.setIdProyectoJurisdiccion(jurisdiccionBean.getId());
		updateBean(ideaProyectoBean);
	}

	@Override
	/**
	 * Sobreescribe el método delete del objeto GenericServiceImpl Borrado
	 * lógico de una Idea Proyecto y sus objetos relacionados. Estado = anulado
	 */
	public void delete(Object bean) {
		IdeaProyectoBean ideaProyectoBean = (IdeaProyectoBean) bean;

		// cambio el estado anulada = elimino
		ideaProyectoBean.setEstadoAnulada();

		// modifico el objeto
		updateBean(ideaProyectoBean);
	}

	public EvaluacionBean getUltimaEvaluacionPorJunta(Long id)  {
		EvaluacionDAO evaluacionDao = (EvaluacionDAO) getDao(EvaluacionBean.class);
		List<EvaluacionBean> evaluaciones = evaluacionDao.findByProyecto(id);
		EvaluacionBean ret = null;
		for (EvaluacionBean evaluacion : evaluaciones) {
			//Salteo las anuladas
			if(evaluacion.esAnulada()) continue;
			
			if(evaluacion.getTipo().equals(TipoEvaluacion.EVAL_JUNTA)) {
				if(ret==null) ret = evaluacion;
				else {
					if(ret.getFechaInicial().before(evaluacion.getFechaInicial())) {
						//Si hay otra posterior la tomo
						ret = evaluacion;
					}
				}
			}
		}
		return ret;
	}

	public void updateEstado(Long idIdeaProyecto, EstadoIdeaProyecto estado) {
		IdeaProyectoBean  bean = (IdeaProyectoBean)load(idIdeaProyecto);
		bean.setEstado(estado);
		update(bean);
	}

}
