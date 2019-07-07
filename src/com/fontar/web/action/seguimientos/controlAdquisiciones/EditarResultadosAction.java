package com.fontar.web.action.seguimientos.controlAdquisiciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.data.impl.domain.bean.ProcedimientoItemBean;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * @author ssanchez
 */
public class EditarResultadosAction extends BaseMappingDispatchAction{

	protected AdministrarProcedimientoServicio administrarProcedimientoServicio;
	
	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}

	/**
	 * Obtiene el <i>idItem</i> desde el request.<br>
	 * @param request
	 * @return idItem
	 */
	protected Long getIdItem(HttpServletRequest request){
		Long idItem = null;
		
		if (validateParameter(request,"id")){
			idItem = new Long(request.getParameter("id"));
			SessionHelper.setIdProcedimientoItem(request,idItem);
		} else {
			idItem = SessionHelper.getIdProcedimientoItem(request);
		}
		
		return idItem;
	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idItem = getIdItem(request);
		ProcedimientoItemBean procedimientoItem = administrarProcedimientoServicio.obtenerProcedimientoItem(idItem);
		
		BeanUtils.copyProperties(form,procedimientoItem);

		setCollections(request);
		
		return mapping.findForward("success");
	}
	
	public ActionForward guardarEdicion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("success");
	}
	
	protected void setCollections(HttpServletRequest request) {
	}
}
