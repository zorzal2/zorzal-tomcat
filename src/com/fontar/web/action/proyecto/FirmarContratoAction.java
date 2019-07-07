package com.fontar.web.action.proyecto;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.impl.assembler.ProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.jbpm.manager.ProyectoTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * @author ssanchez 
 */
public class FirmarContratoAction extends GenericJbpmTaskAction {

	private WFProyectoServicio wfProyectoServicio;

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		this.overrideForward(request);
		
		ProyectoCabeceraDTO cabeceraDTO = (ProyectoCabeceraDTO) wfProyectoServicio.getProyectoDTO(idTaskInstance, new ProyectoCabeceraAssembler());
		request.setAttribute(CabeceraAttribute.PROYECTO, cabeceraDTO);
				
		BigDecimal montoSolicitado = wfProyectoServicio.obtenerMontoSolicitadoProyecto(idTaskInstance);
		if (montoSolicitado != null) {
			request.setAttribute("adjudicacion", StringUtil.formatMoneyForPresentation(montoSolicitado));
		}
		
		ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
		Long idProyecto = taskHelper.getIdProyecto();

		BeanUtils.setProperty(form, "idProyecto", idProyecto);		

	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		Long idResponsableLegal = FormUtil.getLongValue(form, "idPersonaLegal");
		String txtResponsableLegal = BeanUtils.getProperty(form, "txtPersonaLegal");
		Date fechaFirma = FormUtil.getDateValue(form, "fechaFirma");
		String observaciones = BeanUtils.getProperty(form, "observaciones");

		wfProyectoServicio.guardarFirmaContrato(idResponsableLegal, txtResponsableLegal, fechaFirma, observaciones, idTaskInstance);

	}

	/**
	 * Valida si el usuario tiene contexto de encriptación y 
	 * si tiene cargado presupuestos antes de firmar
	 * el contrato.
	 */
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
		
		if (messages.isEmpty()) {

			ProyectoTaskInstanceManager taskHelper = new ProyectoTaskInstanceManager(idTaskInstance);
			
			ProyectoDAO proyectoDao = (ProyectoDAO)ContextUtil.getBean("proyectoDao");
			
			ProyectoBean proyecto = proyectoDao.read(taskHelper.getIdProyecto());
			
			if (proyecto.getProyectoPresupuesto() == null) {
				addError(messages,"app.proyecto.noFirmarPresupuestoNoDefinido");
			}
		}
	}

	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}
}


