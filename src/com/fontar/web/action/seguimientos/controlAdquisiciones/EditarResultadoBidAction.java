package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.math.BigDecimal;
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

import com.fontar.data.api.dao.MonedaDAO;
import com.fontar.data.impl.domain.bean.MonedaBean;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoUffaBid;
import com.pragma.util.FormUtil;
import com.pragma.web.WebContextUtil;

/**
 * @author ssanchez
 */
public class EditarResultadoBidAction extends EditarResultadosAction{

	@Override
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errores = getErrors(request);

		if (errores.size() <= 0) {

			Long idItem = getIdItem(request);
			ProcedimientoItemBean procedimientoItem = administrarProcedimientoServicio.obtenerProcedimientoItem(idItem);
			
			BeanUtils.copyProperties(form,procedimientoItem);
		
			String resultadoBid = procedimientoItem.getResultadoBid()!=null ? procedimientoItem.getResultadoBid().getName() : null;
			((DynaActionForm)form).set("resultadoBid",resultadoBid);
		}
			
		setCollections(request);
		
		return mapping.findForward("success");
	}
	
	@Override
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProcedimientoItem = getIdItem(request);
		
		String proveedor = FormUtil.getStringValue(form,"proveedor");
		Long idMoneda = FormUtil.getLongValue(form,"idMoneda");
		BigDecimal montoBid = FormUtil.getBigDecimalValue(form,"montoBid");
		String codeResultadoBid = FormUtil.getStringValue(form,"resultadoBid");
		ResultadoUffaBid resultadoBid = ResultadoUffaBid.valueOf(codeResultadoBid);
		
		administrarProcedimientoServicio.modificarResultadoBidItem(idProcedimientoItem,proveedor,idMoneda,montoBid,resultadoBid);
		
		return mapping.findForward("success");
	}
	
	@Override
	public void setCollections(HttpServletRequest request) {
		MonedaDAO monedaDAO = (MonedaDAO) WebContextUtil.getBeanFactory().getBean("monedaDao");
		List<MonedaBean> listaMonedas = monedaDAO.getAll();
		
		Collection monedas = collectionHandler.getLabelValueMonedas(listaMonedas);
		request.setAttribute("monedas",monedas);
		
		Collection resultados = collectionHandler.getComboFiltro(ResultadoUffaBid.class);
		request.setAttribute("resultados",resultados);
		request.setAttribute("resultadosEnum",ResultadoUffaBid.values());
	}
}
