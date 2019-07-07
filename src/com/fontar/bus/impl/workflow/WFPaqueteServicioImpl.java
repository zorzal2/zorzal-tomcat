package com.fontar.bus.impl.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fontar.bus.api.paquete.AdministrarPaqueteServicio;
import com.fontar.bus.api.paquete.ControlarPaqueteServicio;
import com.fontar.bus.api.paquete.EvaluarPaqueteServicio;
import com.fontar.bus.api.workflow.WFPaqueteServicio;
import com.fontar.bus.impl.ProyectoSinWflException;
import com.fontar.data.api.assembler.PaqueteGeneralAssembler;
import com.fontar.data.api.dao.PaqueteDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.PaqueteBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPaqueteBean;
import com.fontar.data.impl.domain.dto.DTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;
import com.fontar.jbpm.manager.PaqueteProcessManager;
import com.fontar.jbpm.manager.PaqueteTaskInstanceManager;
import com.pragma.util.ContextUtil;

/**
 * @author ssanchez
 * @version 1.01, 12/01/07
 */
public class WFPaqueteServicioImpl implements WFPaqueteServicio {

	private EvaluarPaqueteServicio evaluarPaqueteServicio;

	private AdministrarPaqueteServicio administrarPaqueteServicio;

	private ControlarPaqueteServicio controlarPaqueteServicio;

	public void setAdministrarPaqueteServicio(AdministrarPaqueteServicio administrarPaqueteServicio) {
		this.administrarPaqueteServicio = administrarPaqueteServicio;
	}

	public void setEvaluarPaqueteServicio(EvaluarPaqueteServicio evaluarPaqueteServicio) {
		this.evaluarPaqueteServicio = evaluarPaqueteServicio;
	}

	public void setControlarPaqueteServicio(ControlarPaqueteServicio controlarPaqueteServicio) {
		this.controlarPaqueteServicio = controlarPaqueteServicio;
	}

	/* SERVICIOS de TAREAS */
	public DTO getPaqueteDTO(Long idTaskInstance, PaqueteGeneralAssembler assembler) {
		PaqueteTaskInstanceManager taskHelper = new PaqueteTaskInstanceManager(idTaskInstance);
		Long idPaquete = taskHelper.getIdPaquete();

		return administrarPaqueteServicio.getPaqueteDTO(idPaquete, assembler);
	}
	
	
	public void cargarEvaluacion(Long idPaquete, String codigoActa, String observacion, Long idTaskInstance) {
		evaluarPaqueteServicio.cargarEvaluacion(idPaquete, codigoActa, observacion);
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		instanceManager.finalizarTarea();
	}
	
	public void confirmarEvaluacion(Long idPaquete, String userName, Long idTaskInstance, String codigoActa, String observacion) {
		evaluarPaqueteServicio.confirmarEvaluacion(idPaquete, userName, codigoActa, observacion);

		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		instanceManager.finalizarTarea();
	}

	public PaqueteDTO obtenerPaquete(Long idTaskInstance) {
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		return evaluarPaqueteServicio.obtenerPaquete(instanceManager.getIdPaquete());
	}

	/* SERVICIOS */

	public void armarPaquete(String[] proyectoArray, Long idInstrumento, String tratamiento, String tipoPaquete) {
		PaqueteDAO paqueteDao = (PaqueteDAO) ContextUtil.getBean("paqueteDao");

		// alta de paquete
		Long idPaquete = administrarPaqueteServicio.armarPaquete(proyectoArray, idInstrumento, tratamiento, tipoPaquete);

		// intercambio de ids
		PaqueteBean paquete = paqueteDao.read(idPaquete);

		Set<ProyectoPaqueteBean> proyectosPaquete = paquete.getProyectosPaquete();
		List<Long> idWorkFlowProyectos = this.obtenerIdWorkFlow(proyectoArray);

		PaqueteProcessManager processManager = new PaqueteProcessManager();
		Long idProcessInstance = processManager.nuevoProcessInstance(idPaquete, tipoPaquete, idWorkFlowProyectos, paquete.getIdInstrumento());

		paquete.setIdWorkFlow(idProcessInstance);

		// actualizacion del paquete
		paqueteDao.update(paquete);
	}

	public void modificarPaquete(Long idPaquete, String[] proyectosSeleccionados, Long idTaskInstance) {
		administrarPaqueteServicio.modificarPaquete(idPaquete, proyectosSeleccionados);
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);

		List<Long> idWorkFlowProyectos = this.obtenerIdWorkFlow(proyectosSeleccionados);
		instanceManager.finalizarTareaModifcarPaquete(idWorkFlowProyectos);
	}

	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectos(Long instrumento, String tratamiento, String tipoPaquete) {
		return administrarPaqueteServicio.obtenerProyectos(instrumento, tratamiento, tipoPaquete);
	}

	public List<ProyectoFilaModificacionPaqueteDTO> obtenerProyectosPaquete(Long idTaskInstance, String tipoPaquete) {
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		return administrarPaqueteServicio.obtenerProyectosPaquete(instanceManager.getIdPaquete(), tipoPaquete);
	}

	public void controlarPaquete(Long idPaquete, String[] proyectosSeleccionados, Long idTaskInstance) {
		controlarPaqueteServicio.controlarPaquete(idPaquete, proyectosSeleccionados);
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		instanceManager.finalizarTarea();
	}

	public void anularPaquete(Long idPaquete, String observaciones, Long idTaskInstance) {
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		administrarPaqueteServicio.anularPaquete(idPaquete, observaciones);
		instanceManager.finalizarTarea();
	}

	/**
	 * Devuelve una lista con los idWorkflow a partir de un String[] de
	 * idProyectos
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Long> obtenerIdWorkFlow(String[] proyectoArray) {
		ArrayList<Long> idWorkFlowProyectos = new ArrayList<Long>();
		ProyectoDAO proyectoDao = (ProyectoDAO) ContextUtil.getBean("proyectoDao");

		// Obtengo los idWorkFlow de los Proyectos para pasarlos al Paquete
		for (int i = 0; i < proyectoArray.length; i++) {
			ProyectoBean proyecto = proyectoDao.read(new Long(proyectoArray[i]));
			Long idWorkFlow = proyecto.getIdWorkFlow();
			if (idWorkFlow == null) {
				throw new ProyectoSinWflException(new String[] { proyecto.getCodigo() });
			}
			idWorkFlowProyectos.add(idWorkFlow);
		}
		return idWorkFlowProyectos;
	}
	
	/**
	 * Obtiene una lista con todos los <code>Proyectos</code> 
	 * pertenecientes a un <code>Paquete</code> que no tienen 
	 * cargada <code>Evaluacion de Paquete</code>.<br>
	 * El servicio devuelve un <code>List</code> con los 
	 * <i>códigos</i> de <code>Proyecto</code>.<br>
	 * @param idTaskInstance
	 * @return List 
	 * @author ssanchez
	 */
	public List<String> obtenerProyectoPaqueteSinEval(Long idTaskInstance) {
		
		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);

		return evaluarPaqueteServicio.obtenerProyectoPaqueteSinEval(instanceManager.getIdPaquete());
	}	
}
