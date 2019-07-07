package com.fontar.web.action.administracion.paquete;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.PaqueteCabeceraAssembler;
import com.fontar.data.impl.domain.dto.PaqueteCabeceraDTO;
import com.fontar.data.impl.domain.dto.PaqueteDTO;
import com.fontar.data.impl.domain.dto.ProyectoFilaModificacionPaqueteDTO;

public class ModificarPaqueteAction extends PaqueteBaseTaskAction {

	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		String[] proyectoSeleccionados = ((DynaActionForm) form).getStrings("proyectoArray");
		if(proyectoSeleccionados.length <= 0) {
			addError(messages, "app.paquete.requiereUnProyecto");
		}
	}
	/**
	 * 
	 */
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Long idPaquete = new Long(BeanUtils.getProperty(form, "id"));
		// obtengo los proyectos siguen seleccionados desde la vista
		String[] proyectoSeleccionados = ((DynaActionForm) form).getStrings("proyectoArray");

		wfPaqueteServicio.modificarPaquete(idPaquete, proyectoSeleccionados, idTaskInstance);

	}

	/**
	 * Edición de un paquete
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		// levanto el paquete
		PaqueteDTO paqueteDto = wfPaqueteServicio.obtenerPaquete(idTaskInstance);
		String tipoPaquete = paqueteDto.getTipo();
		request.setAttribute("tipoPaquete", paqueteDto.getTipoPaquete().getName());

		Long instrumento = paqueteDto.getIdInstrumento();
		String tratamiento = paqueteDto.getTratamiento().getDescripcion();
		((DynaActionForm) form).set("instrumento", instrumento.toString());
		((DynaActionForm) form).set("tratamiento", tratamiento);
		((DynaActionForm) form).set("id", paqueteDto.getId().toString());
		request.setAttribute("id", paqueteDto.getId().toString());
		request.setAttribute("comision", paqueteDto.getComision());
		
		PaqueteCabeceraDTO cabeceraDTO = (PaqueteCabeceraDTO) wfPaqueteServicio.getPaqueteDTO(idTaskInstance,new PaqueteCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PAQUETE,cabeceraDTO);

		// si no vengo de una validacion cargo los datos del FORM
//		if (getErrors(request).isEmpty()){
			List<ProyectoFilaModificacionPaqueteDTO> proyectosList = new ArrayList<ProyectoFilaModificacionPaqueteDTO>();
			// traigo para mostrar los proyectos que son parte del paquete...
			proyectosList = wfPaqueteServicio.obtenerProyectosPaquete(idTaskInstance, tipoPaquete);
			// ...y los que son candidatos a ser agregados al paquete
			proyectosList.addAll(wfPaqueteServicio.obtenerProyectos(instrumento, tratamiento, tipoPaquete));
			request.setAttribute("proyectosList", proyectosList);
//		}
	}
}