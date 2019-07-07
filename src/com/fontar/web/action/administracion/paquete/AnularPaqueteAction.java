package com.fontar.web.action.administracion.paquete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.PaqueteCabeceraAssembler;
import com.fontar.data.impl.domain.dto.PaqueteCabeceraDTO;
import com.fontar.jbpm.manager.PaqueteTaskInstanceManager;
import com.pragma.util.FormUtil;

public class AnularPaqueteAction extends PaqueteBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		PaqueteTaskInstanceManager instanceManager = new PaqueteTaskInstanceManager(idTaskInstance);
		// String userName = request.getUserPrincipal().getName();
		BeanUtils.setProperty(form, "idPaquete", instanceManager.getIdPaquete());

		PaqueteCabeceraDTO cabeceraDTO = (PaqueteCabeceraDTO)wfPaqueteServicio.getPaqueteDTO(idTaskInstance,new PaqueteCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PAQUETE, cabeceraDTO);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		// String userName = request.getUserPrincipal().getName();
		wfPaqueteServicio.anularPaquete(FormUtil.getLongValue(form, "idPaquete"), FormUtil.getStringValue(form, "observaciones"), idTaskInstance);
	}

}
