package com.fontar.web.action.seguimientos.seguimientos;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.Constant;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.api.dao.SeguimientoDAO;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoDTO;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.jbpm.manager.SeguimientoTaskInstanceManager;
import com.fontar.web.action.evaluacion.EvaluacionBaseTaskAction;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.ContextUtil;

/**
 * @author ttoth
 */
public class EvaluarAutorizacionPagoSeguimientoAction extends EvaluacionBaseTaskAction {

	private static final String EVALUACIONES_LIST = "evaluacionesList";
	private WFSeguimientoServicio wfSeguimientoServicio;
	protected AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	
	public WFSeguimientoServicio getWfSeguimientoServicio() {
		return wfSeguimientoServicio;
	}

	public void setWfSeguimientoServicio(WFSeguimientoServicio wfSeguimientoServicio) {
		this.wfSeguimientoServicio = wfSeguimientoServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		SeguimientoVisualizacionCabeceraDTO seguimientoDTO = wfSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idTaskInstance);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, seguimientoDTO);
		
		request.setAttribute("recomendaciones", new CollectionHandler().getRecomendacionesEvaluacionSeguimiento());
//		
		SeguimientoTaskInstanceManager taskHelper = new SeguimientoTaskInstanceManager(idTaskInstance);
		Long idSeguimiento = taskHelper.getIdSeguimiento();
		SeguimientoDAO seguimientoDAO = (SeguimientoDAO)ContextUtil.getBean("seguimientoDao");
		SeguimientoBean seguimientoBean = (SeguimientoBean) seguimientoDAO.read(idSeguimiento);
//
		Collection evaluacionesList = wfSeguimientoServicio.getEvaluaciones(seguimientoBean, Constant.AdministrarEvaluacionAttribute.FINALIZAR_CONTROL);
//		
		request.setAttribute(EVALUACIONES_LIST, evaluacionesList);
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		// TODO Auto-generated method stub

		ActionUtil.checkValidEncryptionContext(messages);
	}


	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		boolean autorizada = BeanUtils.getProperty(form, "autorizaEvaluacion").equals("1");
		EvaluacionSeguimientoDTO evaluacion = new EvaluacionSeguimientoDTO();
		//
		String fechaEvaluacion = BeanUtils.getProperty(form, "fechaEvaluacion");
		String fundamentacion = BeanUtils.getProperty(form, "fundamentacion");
				
		evaluacion.setFecha(fechaEvaluacion);
		evaluacion.setFundamentacion(fundamentacion);
		wfSeguimientoServicio.autorizarPago(autorizada, evaluacion, idTaskInstance);
	}

	public AdministrarEvaluacionesServicio getAdministrarEvaluacionesServicio() {
		return administrarEvaluacionesServicio;
	}

	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}
}