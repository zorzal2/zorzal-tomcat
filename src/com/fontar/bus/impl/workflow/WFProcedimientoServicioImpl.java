package com.fontar.bus.impl.workflow;

import java.util.Date;
import java.util.List;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.bus.api.workflow.WFProcedimientoServicio;
import com.fontar.data.api.dao.ProcedimientoDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.EstadoProcedimiento;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.fontar.jbpm.manager.ControlAdquisicionProcessManager;
import com.fontar.jbpm.manager.ProcedimientoTaskInstanceManager;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

/**
 * 
 * @author ssanchez
 */
public class WFProcedimientoServicioImpl implements WFProcedimientoServicio {

	private AdministrarProcedimientoServicio administrarProcedimientoServicio;

	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}
	
	/**
	 * Obtiene un listado con los 
	 * tipos de adquisición disponibles.<br> 
	 * @author ssanchez
	 */
	public List<TipoAdquisicionBean> obtenerTiposAdquisicion() {
		
		return administrarProcedimientoServicio.obtenerTiposAdquisicion();
	}
	
	/**
	 * Obtiene el <code>TipoAdquisicion</code>
	 * correspondiente al parámetro <i>idTipoAdquisicion</i>.<br>
	 * @param idTipoAdquisicion
	 * @return el tipo de adquisición correspondiente el parámetro
	 */
	public TipoAdquisicionBean obtenerTipoAdquisicion(Long idTipoAdquisicion) {
		
		return administrarProcedimientoServicio.obtenerTipoAdquisicion(idTipoAdquisicion);
	}

	/**
	 * Obtiene un listado de items de un <code>PAC</code>
	 * correspondientes a un tipo de adquisición
	 * y a un proyecto.
	 * @param idTipoAdquisicion
	 * @param idTaskInstance
	 * @return listado de items
	 */
	public List<PacItemDTO> obtenerItemsPorTipoAdquisicion(Long idTipoAdquisicion, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerItemsPorTipoAdquisicion(idTipoAdquisicion,taskHelper.getIdProyecto());
	}

	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con
	 * la <i>listaItems</i> y los datos recibidos por parámetro.<br>
	 * Genera un workflow <code>ControlAdquisicion</code> 
	 * respectivo al procedimiento.<br>
	 * @param idTaskInstance
	 * @param idTipoAdquisicion
	 * @param listaItems
	 * @param codigo
	 * @param fechaRecepcion
	 * @param descripcion
	 */
	public void cargarNuevoProcedimiento(Long idTaskInstance,Long idTipoAdquisicion,String[] listaItems,String codigo,Date fechaRecepcion,String descripcion) {
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		
		Long idProcedimiento = administrarProcedimientoServicio.cargarNuevoProcedimiento(taskHelper.getIdProyecto(),idTipoAdquisicion,listaItems,codigo,fechaRecepcion,descripcion);
		
		crearInstanciaWF(idProcedimiento);
	}
	
	/**
	 * Crea un nuevo <code>ProcedimientoBean</code> con los items
	 * que de un procedimiento que tuvieron resultado 
	 * <i>Aprobado Pliego</i> o <i>Aprobado P. Preclasificación</i>.<br>
	 * Genera un workflow <code>ControlAdquisicion</code> 
	 * respectivo al procedimiento.<br>
	 * @param idTaskInstance
	 * @param fechaRecepcion
	 * @param descripcion
	 */
	public void generarPedidoAutorizacion(Long idTaskInstance,Date fechaRecepcion,String descripcion) {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);

		Long idProcedimiento = administrarProcedimientoServicio.generarPedidoAutorizacion(taskHelper.getIdProcedimiento(),fechaRecepcion,descripcion);
		
		taskHelper.finalizarTarea();			
		
		crearInstanciaWF(idProcedimiento);
	}	
	
	/**
	 * Crea una instancia de Workflow de <code>ControlAdquisicion</code>.<br>
	 * El <code>Process Instance</code> es creado con la variable <i>ID_NOTIFICACION</i>
	 * cuyo valor es obtenido desde el parámetro <i>idNotificacion</i>.
	 */
	private void crearInstanciaWF(Long idProcedimiento) {
		
		ProcedimientoDAO procedimientoDAO = (ProcedimientoDAO) ContextUtil.getBean("procedimientoDao");
		ProcedimientoBean procedimiento = procedimientoDAO.read(idProcedimiento);
		
		ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");		
		ProyectoBean proyecto = proyectoDAO.read(procedimiento.getIdProyecto());
		
		ControlAdquisicionProcessManager processManager = new ControlAdquisicionProcessManager();
		Long idProcessInstance = processManager.nuevoProcessInstance(idProcedimiento, proyecto.getIdInstrumento());
		
		procedimiento.setIdWorkFlow(idProcessInstance);
		procedimientoDAO.update(procedimiento);
	}
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsPorProcedimiento(Long idTaskInstance) {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerItemsPorProcedimiento(taskHelper.getIdProcedimiento());
	}
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para la Uffa.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoUffa(Long idTaskInstance) {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerItemsDeProcedimientoPendientesDeResultadoUffa(taskHelper.getIdProcedimiento());
	}	
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que son
	 * para la Bid.<br>
	 * @param idTaskInstance
	 * @return lista de items
	 */
	public List<ProcedimientoItemBean> obtenerItemsDeProcedimientoPendientesDeResultadoBid(Long idTaskInstance) {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerItemsDeProcedimientoPendientesDeResultadoBid(taskHelper.getIdProcedimiento());
	}	
	
	/**
	 * Obtiene un listado de items <code>ProcedimientoItemBean</code>
	 * pertenecientes a un procedimiento que no fueron ni 
	 * <i>Aprobado Final</i>, <i>No Autorizado</i> ni <i>Desierto</i>.<br>
	 * Para esto revisa los resultados uffa o bid según corresponda.<br>
	 * @param idTaskInstance
	 * @return lista de items ProcedimientoItemBean
	 */
	public List<ProcedimientoItemBean> obtenerItemsProcedimientoNoFinalizados(Long idTaskInstance) {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerItemsProcedimientoNoFinalizados(taskHelper.getIdProcedimiento());
	}	

	/**
	 * Obtiene un <code>ProcedimientoBean</code>
	 * en base al <i>idTaskInstance</i> recibido
	 * como parámetro.<br>
	 * @param idTaskInstance
	 * @return un procedimiento
	 */
	public ProcedimientoBean obtenerProcedimiento(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerProcedimiento(taskHelper.getIdProcedimiento());
	}

	/**
	 * Finaliza la tarea especificada por <i>idTaskInstance</i>
	 * del workflow <code>ControlAdquisición</code>.<br>
	 * @param idTaskInstance
	 */
	public void finalizarTarea(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		taskHelper.finalizarTarea();
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de evauador técnico.<br>
	 * @param idTaskInstance
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoEvaluadorTecnico(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoEvaluadorTecnico(taskHelper.getIdProcedimiento()); 
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de fundamentación evauador.<br>
	 * @param idTaskInstance
	 * @return true si tiene datos evaluador 
	 */
	public Boolean obtenerEstadoFundamentacionEvaluador(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoFundamentacionEvaluador(taskHelper.getIdProcedimiento()); 
	}
	
	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a uffa.<br>
	 * @param idTaskInstance
	 * @return true o false 
	 */
	public Boolean obtenerEstadoDatosRemisionUffa(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoDatosRemisionUffa(taskHelper.getIdProcedimiento()); 
	}

	/**
	 * Devuelve <i>true</i> si el procedimiento
	 * tiene cargado datos de remisión a bid.<br>
	 * @param idTaskInstance
	 * @return true o false 
	 */
	public Boolean obtenerEstadoDatosRemisionBid(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoDatosRemisionBid(taskHelper.getIdProcedimiento()); 
	}
	
	
	/**
	 * Modifica el estado de un <code>ProcedimientoBean</code>.<br>
	 * @param procedimiento
	 * @throws ConversionException 
	 */
	public void modificarEstado(Long idTaskInstance, EstadoProcedimiento estado) throws ConversionException {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(taskHelper.getIdProcedimiento());
		procedimiento.setEstado(estado);

		administrarProcedimientoServicio.modificarProcedimiento(procedimiento); 
	}
	
	/**
	 * Modifica un procedimiento con los datos no nulos
	 * del <code>ProcedimientoBean</code> recibido
	 * como parámetro.<br>
	 * @param idTaskInstance
	 * @param procedimiento
	 * @throws ConversionException 
	 */
	public void modificarProcedimiento(Long idTaskInstance, ProcedimientoBean procedimiento) throws ConversionException {
		
		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		procedimiento.setId(taskHelper.getIdProcedimiento());

		administrarProcedimientoServicio.modificarProcedimiento(procedimiento); 
	}	
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Fontar</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoFontar
	 * @param observacionResultadoFontar
	 * @throws ConversionException
	 */
	public void cargarResultadoFontar(Long idTaskInstance, Date fechaResultadoFontar, String observacionResultadoFontar) throws ConversionException {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		Long idProcedimiento = taskHelper.getIdProcedimiento();
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(idProcedimiento);
		procedimiento.setFechaResultadoFontar(fechaResultadoFontar);
		procedimiento.setObservacionResultadoFontar(observacionResultadoFontar);
		
		if (administrarProcedimientoServicio.obtenerTieneItemsParaBid(idProcedimiento)
			|| administrarProcedimientoServicio.obtenerTieneItemsParaUffa(idProcedimiento)) {
		
			procedimiento.setEstado(EstadoProcedimiento.EN_AUTORIZACION);
		
		} else if (administrarProcedimientoServicio.obtenerTieneItemsAprobadosParcialmente(idProcedimiento)) {
			procedimiento.setEstado(EstadoProcedimiento.CON_RESULTADO);
		
		} else {
			procedimiento.setEstado(EstadoProcedimiento.FINALIZADO);
		}

		administrarProcedimientoServicio.modificarProcedimiento(procedimiento);
		administrarProcedimientoServicio.modificarEstadoItemsFontar(idProcedimiento);
		
		taskHelper.finalizarTarea();
	}
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Uffa</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * Cambia el estado de los items que fueron
	 * <i>Aprobados Final</i> al estado <i>Adjudicado</i>.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoUffa
	 * @param observacionResultadoUffa
	 * @throws ConversionException
	 */
	public void cargarResultadoUffa(Long idTaskInstance, Date fechaResultadoUffa, String observacionResultadoUffa) throws ConversionException {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		Long idProcedimiento = taskHelper.getIdProcedimiento();
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(idProcedimiento);
		procedimiento.setFechaResultadoUffa(fechaResultadoUffa);
		procedimiento.setObservacionResultadoUffa(observacionResultadoUffa);
		
		Boolean uffaPendiente = administrarProcedimientoServicio.obtenerTieneResultadoUffaPendiente(idProcedimiento);
		if(uffaPendiente) {
			
			if (administrarProcedimientoServicio.obtenerTieneItemsAprobadosParcialmente(idProcedimiento)) { 
				procedimiento.setEstado(EstadoProcedimiento.CON_RESULTADO);
			} else {
				procedimiento.setEstado(EstadoProcedimiento.FINALIZADO);
			}
		}
		
		administrarProcedimientoServicio.modificarProcedimiento(procedimiento); 
		administrarProcedimientoServicio.modificarEstadoItemsUffa(idProcedimiento,fechaResultadoUffa);
		
		taskHelper.finalizarTarea();
	}
	
	/**
	 * Finaliza la tarea <i>Cargar Resultado Bid</i> del
	 * workflow <code>ControlAdquisicion</code> y persiste
	 * los datos en el procedimiento correspondiente.<br>
	 * @param idTaskInstance
	 * @param fechaResultadoBid
	 * @param observacionResultadoBid
	 * @throws ConversionException
	 */
	public void cargarResultadoBid(Long idTaskInstance, Date fechaResultadoBid, String observacionResultadoBid) throws ConversionException {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		Long idProcedimiento = taskHelper.getIdProcedimiento();
		
		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(idProcedimiento);
		procedimiento.setFechaResultadoBid(fechaResultadoBid);
		procedimiento.setObservacionResultadoBid(observacionResultadoBid);
		
		Boolean bidPendiente = administrarProcedimientoServicio.obtenerTieneResultadoBidPendiente(idProcedimiento);
		if(bidPendiente) {
			
			if (administrarProcedimientoServicio.obtenerTieneItemsAprobadosParcialmente(idProcedimiento)) { 
				procedimiento.setEstado(EstadoProcedimiento.CON_RESULTADO);
			} else {
				
				procedimiento.setEstado(EstadoProcedimiento.FINALIZADO);
			}
		}
		
		administrarProcedimientoServicio.modificarProcedimiento(procedimiento); 
		administrarProcedimientoServicio.modificarEstadoItemsBid(idProcedimiento,fechaResultadoBid);
		
		taskHelper.finalizarTarea();		
	}
	
	
	/**
	 * Determina si cada item de un procedimiento
	 * tiene cargado su resultado fontar: esto
	 * implica tener cagada la moneda, el montoFontar
	 * y el resultadoFontar.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoFontar(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoItemsResultadoFontar(taskHelper.getIdProcedimiento()); 
	}
	
	/**
	 * Determina si los item de un procedimiento
	 * asignados para la uffa tienen cargado
	 * su resultado uffa: esto implica tener
	 * cargado la moneda, el montoUffa
	 * y el resultadoUffa.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoUffa(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoItemsResultadoUffa(taskHelper.getIdProcedimiento()); 
	}
	
	/**
	 * Determina si los item de un procedimiento
	 * asignados para la bid tienen cargado
	 * su resultado bid: esto implica tener
	 * cargado la moneda, el montoBid
	 * y el resultadoBid.<br>
	 * @param idTaskInstance
	 * @return true o false
	 */	
	public Boolean obtenerEstadoItemsResultadoBid(Long idTaskInstance) {

		ProcedimientoTaskInstanceManager taskHelper = new ProcedimientoTaskInstanceManager(idTaskInstance);
		
		return administrarProcedimientoServicio.obtenerEstadoItemsResultadoBid(taskHelper.getIdProcedimiento()); 
	}	
	
}
