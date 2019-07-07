package com.fontar.web.action.seguimientos.seguimientos;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.bus.api.seguimientos.seguimientos.AnalisisGastosSeguimientoServicio;
import com.pragma.util.FormUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class ModificarCamposDeAnalisisDeGastosAction extends BaseMappingDispatchAction {
	private AnalisisGastosSeguimientoServicio analisisGastosSeguimientoService;
	private AdministrarSeguimientoServicio administracionSeguimientoService;
	
	public void setAdministracionSeguimientoService(AdministrarSeguimientoServicio administracionSeguimientoService) {
		this.administracionSeguimientoService = administracionSeguimientoService;
	}
	public void setAnalisisGastosSeguimientoService(AnalisisGastosSeguimientoServicio analisisGastosSeguimientoService) {
		this.analisisGastosSeguimientoService = analisisGastosSeguimientoService;
	}
	public ActionForward cargarPresupuestoSegunAvance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long idSeguimiento = FormUtil.getLongValue(form, "id");
		BeanUtils.setProperty(form, "valor", analisisGastosSeguimientoService.getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(idSeguimiento).getPresupuestoSegunInformeDeAvance());		
		return mapping.findForward("success");
	}
	public ActionForward guardarPresupuestoSegunAvance(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long idSeguimiento = FormUtil.getLongValue(form, "id");
		BigDecimal valor = FormUtil.getBigDecimalValue(form, "valor");
		administracionSeguimientoService.guardarPresupuestoSegunAvance(idSeguimiento, valor);
		return mapping.findForward("success");
	}
	public ActionForward cargarPendienteDeRendicion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long idSeguimiento = FormUtil.getLongValue(form, "id");
		BigDecimal valor = FormUtil.getBigDecimalValue(form, "valor");
		if(valor==null) valor = analisisGastosSeguimientoService.getCalculosDeAnalisisDeGastosParaEvaluarGestionDePago(idSeguimiento).getPendienteDeRendicion();
		BeanUtils.setProperty(form, "valor", valor);		
		return mapping.findForward("success");
	}
	public ActionForward guardarPendienteDeRendicion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long idSeguimiento = FormUtil.getLongValue(form, "id");
		BigDecimal valor = FormUtil.getBigDecimalValue(form, "valor");
		administracionSeguimientoService.guardarPendienteDeRendicion(idSeguimiento, valor);
		return mapping.findForward("success");
	}
}
