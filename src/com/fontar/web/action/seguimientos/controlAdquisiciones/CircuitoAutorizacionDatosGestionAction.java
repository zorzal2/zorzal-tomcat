package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.EvaluadorDAO;
import com.fontar.data.impl.domain.bean.EvaluadorBean;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.util.SessionHelper;
import com.pragma.web.WebContextUtil;

/**
 * Action para visualizar/editar los datos de gestion  
 * de un <code>ProcedimientoBean</code>.<br>
 * @author ssanchez
 */
public class CircuitoAutorizacionDatosGestionAction extends ProcedimientoBaseTaskAction {

	public ActionForward visualizarDatosGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		obtenerProcedimiento(request);
		
		String forward = SessionHelper.getForwardTiles(request);
		
		return mapping.findForward(forward);
	}
	
	public ActionForward editarDatosGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ProcedimientoBean procedimiento = obtenerProcedimiento(request);

		ActionMessages errores = getErrors(request);
		if (errores.size() <= 0) {

			BeanUtils.copyProperties(form,procedimiento);
		}
			
		cargarDatosIniciales(request);

		return mapping.findForward("success");
	}	
	
	public ActionForward imprimirDatosCompletos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		obtenerProcedimiento(request);

		return mapping.findForward("success");
	}	
	
	public ActionForward guardarDatosGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProcedimiento = obtenerIdProcedimiento(request);

		ProcedimientoBean procedimiento = new ProcedimientoBean();
		procedimiento.setId(idProcedimiento);
		
		BeanUtils.copyProperties(procedimiento,((DynaActionForm)form));
		
		administrarProcedimientoServicio.modificarProcedimiento(procedimiento);

		return mapping.findForward("success");
	}	
	
	private ProcedimientoBean obtenerProcedimiento(HttpServletRequest request) {

		Long idProcedimiento = obtenerIdProcedimiento(request);
		
		ProcedimientoBean procedimiento = administrarProcedimientoServicio.obtenerProcedimiento(idProcedimiento);
		request.setAttribute(PROCEDIMIENTO,procedimiento);
		
		return procedimiento;
	}
	
	public void cargarDatosIniciales(HttpServletRequest request) {

		CollectionHandler handler = new CollectionHandler();

		EvaluadorDAO evaluadorDAO = (EvaluadorDAO) WebContextUtil.getBeanFactory().getBean("evaluadorDao");
		List<EvaluadorBean> evaluadoresBean = evaluadorDAO.getAll();
		Collection evaluadores = handler.getLabelValueEvaluadores(evaluadoresBean);

		request.setAttribute("evaluadores",evaluadores);
	}
}
