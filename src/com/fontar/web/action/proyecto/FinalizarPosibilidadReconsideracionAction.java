package com.fontar.web.action.proyecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.dto.BitacoraDTO;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ProyectoDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;

/**
 * Action para marcar un proyecto como no reconsiderable.
 * @author ssanchez
 * @version 1.01, 09/01/07
 */
public class FinalizarPosibilidadReconsideracionAction extends ProyectoBaseTaskAction {

	/**
	 * Action que carga la pantalla para finalizar la posibilidad de
	 * reconsideracion
	 */
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		ProyectoDTO proyecto = wfProyectoServicio.obtenerProyecto(idTaskInstance);
		
		// TODO: SS-en etapa 2º cargar fecha acuse de notificación

		ProyectoCabeceraDTO cabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PROYECTO, cabeceraDTO);
	}

	@Override
	protected void validateEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		String descripcion = FormUtil.getStringValue(form, "descripcion");
		if(descripcion!= null && descripcion.length() > 3500)
			addError(messages, "app.reconsideracion.observacionesMaxLenght");
	}
	
	@Override
	/**
	 * Finaliza la posibilidad de reconsideración de un proyecto
	 */
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		BitacoraDTO bitacora = populateFinReconsideracion(form);

		wfProyectoServicio.finalizarPosibilidadReconsideracion(bitacora, idTaskInstance);
	}

	/**
	 * Carga los datos del formulario a un dto
	 */
	private BitacoraDTO populateFinReconsideracion(ActionForm form) throws Exception {

		BitacoraDTO bitacora = new BitacoraDTO();
		bitacora.setDescripcion(FormUtil.getStringValue(form, "descripcion"));

		return bitacora;
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

}
