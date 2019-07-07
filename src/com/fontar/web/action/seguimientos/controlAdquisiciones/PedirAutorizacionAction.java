package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.api.workflow.WFProcedimientoServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.ProyectoRaizDAO;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.TipoAdquisicionBean;
import com.fontar.data.impl.domain.dto.PacItemDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Action para <i>Ingresar Pedido de Autorización</i> de un proyecto.
 * Esta tarea genera un <i>Procedimiento</i> y crea un workflow 
 * de <code>Control de Adquisición</code>.<br>
 * En primer instancia se muestra una pantalla que permite elegir el tipo de 
 * adquisición, una vez elegido el tipo la pantalla se actualiza y
 * muestra los items del PAC del Proyecto correspondientes que estan en 
 * estado <i>Pediente de Compra</i>; luego se de seleccionar los items 
 * e ingresar los datos se puede crear el <i>Procedimiento</i>.<br> 
 * @author ssanchez
 */
public class PedirAutorizacionAction extends GenericJbpmTaskAction {

	private WFProyectoServicio wfProyectoServicio;
	private WFProcedimientoServicio wfProcedimientoServicio;
	
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}
	public void setWfProcedimientoServicio(WFProcedimientoServicio wfProcedimientoServicio) {
		this.wfProcedimientoServicio = wfProcedimientoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		cargarCabeceraProyecto(request,idTaskInstance);
		cargarDatosIniciales(request,idTaskInstance);
		
		Long idTipoAdquisicion = FormUtil.getLongValue(form,"idTipoAdquisicion");
		if (idTipoAdquisicion!=null) {
			List<PacItemDTO> listaItems = wfProcedimientoServicio.obtenerItemsPorTipoAdquisicion(idTipoAdquisicion,idTaskInstance);
			request.setAttribute("listaItems",listaItems);
		}
	}
	
	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String[] itmesSeleccionados = ((DynaActionForm) form).getStrings("itemsArray");
		if(itmesSeleccionados.length <= 0) {
			addError(messages, "app.controlAdquisicion.requiereItem");
		}
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		super.validateCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();
		ProyectoRaizDAO proyectoRaizDao = (ProyectoRaizDAO)ContextUtil.getBean("proyectoRaizDao");
		ProyectoBean proyecto = (ProyectoBean) proyectoRaizDao.read(idProyecto);
		
		if (!proyecto.getInstrumento().getInstrumentoDef().getPermiteAdquisicion()) {
				addError(messages,"app.proyecto.noPermiteAdquisicion");
		}
	}
		
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		Long idTipoAdquisicion = FormUtil.getLongValue(form,"idTipoAdquisicion");
		String[] itemsSeleccionados = ((DynaActionForm) form).getStrings("itemsArray");
		String codigo = FormUtil.getStringValue(form,"codigo");
		Date fechaRecepcion = FormUtil.getDateValue(form,"fechaRecepcion");
		String descripcion = FormUtil.getStringValue(form, "descripcion");
		
		wfProcedimientoServicio.cargarNuevoProcedimiento(idTaskInstance,idTipoAdquisicion,itemsSeleccionados,codigo,fechaRecepcion,descripcion);
	}

	private void cargarCabeceraProyecto(HttpServletRequest request, Long idTaskInstance) {

		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);

		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) wfProyectoServicio.obtenerDatosVisualizacionProyecto(taskHelper.getIdProyecto());
		request.setAttribute(CabeceraAttribute.PROYECTO, visualizacionDTO);
	}
	
	private void cargarDatosIniciales(HttpServletRequest request, Long idTaskInstance) {

		CollectionHandler handler = new CollectionHandler();
		
		List<TipoAdquisicionBean> list = wfProcedimientoServicio.obtenerTiposAdquisicion();
		Collection tiposAdquisicion = handler.getLabelValueTiposAdquisicion(list);
		request.setAttribute("tiposAdquisicion",tiposAdquisicion);
		
		request.setAttribute("idTaskInstance", idTaskInstance);
	}
}